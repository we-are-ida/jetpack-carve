package be.ida.jetpack.carve.manager.impl.function;

import be.ida.jetpack.carve.manager.exception.ModelManagerException;
import be.ida.jetpack.carve.manager.exception.RetrieverException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.function.Function;

public class RetrieverFunction<T> extends ManagerFunction implements Function<ResourceResolver, T> {
    private static final String MSG_CAN_NOT_RETRIEVE_MODEL = "Can not retrieve model.";
    private static final String MSG_CAN_NOT_AUTHENTICATE = "Can not authenticate, the user mappings are not configured correctly.";

    private final Class<T> clazz;
    private final String id;

    public RetrieverFunction(Class<T> clazz, String id) {
        this.clazz = clazz;
        this.id = id;
    }

    @Override
    public T apply(ResourceResolver resourceResolver) {
        T model;
        try {
            String modelPath = String.format("%s/%s", getLocation(clazz), getPathPolicy(clazz).apply(id));
            Resource resource = resourceResolver.getResource(modelPath);
            model = resource != null ? resource.adaptTo(clazz) : null;
        } catch (ModelManagerException e) {
            throw new RetrieverException(MSG_CAN_NOT_RETRIEVE_MODEL, e);
        } catch (IllegalStateException e) {
            throw new RetrieverException(MSG_CAN_NOT_AUTHENTICATE, e);
        }
        return model;
    }
}