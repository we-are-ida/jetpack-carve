package be.ida_mediafoundry.jetpack.carve.manager.serializer.impl;

import be.ida_mediafoundry.jetpack.carve.manager.serializer.Serializer;
import be.ida_mediafoundry.jetpack.carve.manager.serializer.exception.SerializerException;
import be.ida_mediafoundry.jetpack.carve.manager.validator.FieldValidator;
import be.ida_mediafoundry.jetpack.carve.manager.validator.exception.ValidationException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;

public class SimpleSerializerImpl implements Serializer {
    private final static Logger LOG = LoggerFactory.getLogger(SimpleSerializerImpl.class);
    private final Field field;

    public SimpleSerializerImpl(Field field) throws ValidationException {
        FieldValidator.isValid(field);
        this.field = field;
    }

    private static Object getValue(Field field, Object model) {
        try {
            return field.get(model);
        } catch (IllegalAccessException e) {
            throw new SerializerException(String.format("Failed to read the value of field %s", field.getName()), e);
        }
    }

    @Override
    public void serialize(Resource resource, Object model) {
        String propertyName = getPropertyName();
        Object value = getValue(field, model);

        if (value != null) {
            ModifiableValueMap modifiableValueMap = resource.adaptTo(ModifiableValueMap.class);
            modifiableValueMap.put(propertyName, value);
        } else {
            LOG.warn("The value for field {} was null and thus not serialized.", field.getName());
        }
    }

    @Override
    public void serializeMultiValue(Resource resource, Object model) {
        try {
            Object values[] = ((Collection)field.get(model)).toArray();
            ModifiableValueMap modifiableValueMap = resource.adaptTo(ModifiableValueMap.class);
            modifiableValueMap.put(field.getName(), values);
        } catch (IllegalAccessException e) {
            LOG.error("Can't convert collection to array");
        }
    }

    private String getPropertyName() {
        return Optional.ofNullable(field.getDeclaredAnnotation(Named.class))
                .map(Named::value)
                .orElse(field.getName());
    }
}
