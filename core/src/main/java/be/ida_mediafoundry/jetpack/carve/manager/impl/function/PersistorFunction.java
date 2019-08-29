package be.ida_mediafoundry.jetpack.carve.manager.impl.function;

import be.ida_mediafoundry.jetpack.carve.annotations.CarveId;
import be.ida_mediafoundry.jetpack.carve.manager.constants.PersistenceConstants;
import be.ida_mediafoundry.jetpack.carve.manager.exception.ModelManagerException;
import be.ida_mediafoundry.jetpack.carve.manager.exception.PersistorException;
import be.ida_mediafoundry.jetpack.carve.manager.serializer.impl.SimpleSerializerImpl;
import be.ida_mediafoundry.jetpack.carve.manager.util.ModelManagerUtil;
import be.ida_mediafoundry.jetpack.carve.manager.validator.FieldValidator;
import be.ida_mediafoundry.jetpack.carve.manager.validator.ModelValidator;
import be.ida_mediafoundry.jetpack.carve.manager.validator.exception.ValidationException;
import com.day.crx.JcrConstants;
import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

import static be.ida_mediafoundry.jetpack.carve.manager.util.ModelManagerUtil.canStoreInMultiValueProperty;

public class PersistorFunction extends ManagerFunction implements Function<ResourceResolver, Object> {
    private static final String MSG_CAN_NOT_SERIALIZE_MODEL = "Can not serialize model.";
    private static final String MSG_CAN_NOT_AUTHENTICATE = "Can not authenticate, the user mappings are not configured correctly.";
    public static final String MSG_SERIALIZABLE_ID_NOT_FOUND = "Unable to find the SeriazableId annotation. Did you forget to add it?";
    private final static Logger LOG = LoggerFactory.getLogger(PersistorFunction.class);

    private final Object model;
    private final String parentResourcePath;
    private final String resourceName;

    public PersistorFunction(Object model, String parentResourcePath, String resourceName) throws ValidationException {
        ModelValidator.validate(model);
        this.model = model;
        this.parentResourcePath = parentResourcePath;
        this.resourceName = resourceName;
    }

    public PersistorFunction(Object model) throws ValidationException {
        ModelValidator.validate(model);
        this.model = model;
        this.parentResourcePath = getLocation(model.getClass());
        this.resourceName = null;
    }

    private static String determineChildResourceName(Field field) {
        // TODO expand this with a pluggable factory to support other annotations as well
        // This only works for @ChildResource.
        Annotation[] annotations = field.getDeclaredAnnotations();

        return Arrays.stream(annotations)
                .filter(annotation -> annotation instanceof ChildResource)
                .findFirst()
                .map(annotation -> ((ChildResource) annotation).name())
                .orElse(null);
    }

    private static Resource getOrCreateModelResource(Object model, String parentResourcePath, String resourceName, ResourceResolver resolver) throws IllegalAccessException, PersistenceException, PersistorException, ModelManagerException {
        String relativeResourcePath = resourceName;

        if (resourceName == null) {
            // When saving the root model, the name needs to be determined based on it's id
            String id = ModelManagerUtil.getCarveId(model);

            relativeResourcePath = getPathPolicy(model.getClass()).apply(id);
        }

        String currentResourcePath = parentResourcePath + "/" + relativeResourcePath;
        return ResourceUtil.getOrCreateResource(
                resolver,
                currentResourcePath,
                PersistenceConstants.RESOURCE_TYPE_MODEL,
                PersistenceConstants.RESOURCE_TYPE_COLLECTION, true);
    }

    @Override
    public Object apply(ResourceResolver resourceResolver) {
        try {
            Resource resource = getOrCreateModelResource(model, parentResourcePath, resourceName, resourceResolver);
            ModifiableValueMap modifiableValueMap = resource.adaptTo(ModifiableValueMap.class);
            modifiableValueMap.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);

            for (Field field : model.getClass().getDeclaredFields()) {
                if (FieldValidator.isEligible(field)) {
                    field.setAccessible(true);
                    if (ModelManagerUtil.isValue(field)) {
                        serializeValue(resource, field);
                    } else if (Collection.class.isAssignableFrom(field.getType())) {
                        serializeCollection(resource, field);
                    } else {
                        serializeField(resourceResolver, resource, field);
                    }
                    // TODO support persisting of maps?
                } else {
                    LOG.debug("Field {} is not eligible for persistence.", field.getName());
                }
            }

            resourceResolver.commit();
        } catch (PersistenceException | IllegalAccessException | ValidationException | ModelManagerException e) {
            throw new PersistorException(MSG_CAN_NOT_SERIALIZE_MODEL, e);
        } catch (IllegalStateException e) {
            throw new PersistorException(MSG_CAN_NOT_AUTHENTICATE, e);
        }

        return model;
    }

    private void serializeCollection(Resource resource, Field field) throws ValidationException {
        // check if this is a collection of types we can store in a multivalue property
        if (canStoreInMultiValueProperty(field)) {
            storeMultiValueProperty(resource, field);
        } else {
            storeCollectionOfModels(resource, field);
        }
    }

    private void storeMultiValueProperty(Resource resource, Field field) throws ValidationException {
        new SimpleSerializerImpl(field).serializeMultiValue(resource, model);
    }

    private void storeCollectionOfModels(Resource resource, Field field) throws ValidationException {
        try {
            // create container node
            final String containerNodePath = String.format("%s/%s", resource.getPath(), field.getName());
            ResourceUtil.getOrCreateResource(
                    resource.getResourceResolver(),
                    containerNodePath,
                    PersistenceConstants.RESOURCE_TYPE_COLLECTION,
                    PersistenceConstants.RESOURCE_TYPE_COLLECTION,
                    true);

            // store child nodes
            Object models[] = ((Collection)field.get(model)).toArray();
            for (Object model : models) {
                if (ModelManagerUtil.isModel(model)) {
                    new PersistorFunction(model, containerNodePath, determineCarveId(model)).apply(resource.getResourceResolver());
                } else {
                    LOG.error("Can't persist non-annotated model in field: {}", field.getName());
                }
            }
        } catch (PersistenceException e) {
            LOG.error("Couldn't create container node", e);
        } catch (IllegalAccessException e) {
            LOG.error("Couldn't store child in container", e);
        }
    }

    private String determineCarveId(Object model) {
        // see if a field has the CarveId annotation
        Field annotatedField = Arrays.stream(model.getClass().getDeclaredFields())
                     .filter(field -> field.getAnnotationsByType(CarveId.class).length > 0)
                     .findFirst()
                     .orElse(null);
        if (annotatedField != null) {
            try {
                annotatedField.setAccessible(true);
                return String.valueOf(annotatedField.get(model));
            } catch (IllegalAccessException e) {
                LOG.error("Couldnt get carveid field value", e);
            }
        }
        // can't persist, we need a unique name
        throw new PersistorException("Can't determine unique name for child model");
    }

    private void serializeField(ResourceResolver resourceResolver, Resource resource, Field field) throws ValidationException, IllegalAccessException {
        // Prevent trying to persist services/components that are referenced by the model
        if (ModelManagerUtil.isModel(field.get(model))) {
            String child = determineChildResourceName(field);
            new PersistorFunction(field.get(model), resource.getPath(), child).apply(resourceResolver);
        } else {
            LOG.debug("Field {} is not eligible for persistence.", field.getName());
        }
    }

    private void serializeValue(Resource resource, Field field) throws ValidationException {
        new SimpleSerializerImpl(field).serialize(resource, model);
    }
}
