package be.ida.jetpack.carve.manager.util;

import be.ida.jetpack.carve.annotations.CarveId;
import be.ida.jetpack.carve.manager.constants.PersistenceConstants;
import be.ida.jetpack.carve.manager.exception.PersistorException;
import be.ida.jetpack.carve.manager.impl.function.PersistorFunction;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.ClassUtils;
import org.apache.sling.models.annotations.Model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class ModelManagerUtil {
    /**
     * Checks if a class is considered a model or not by validating if the model annotation is avialable on the class definiton.
     *
     * @param model
     * @return
     */
    public static boolean isModel(Object model) {
        Class clazz = model.getClass();

        return Arrays.stream(clazz.getDeclaredAnnotations())
                .anyMatch(annotation -> (Model.class).equals(annotation.annotationType()));
    }

    /**
     * Determines the collection root path based on the class to persist.
     *
     * @param clazz
     * @return
     */
    public static String getCollectionPath(Class clazz) {
        return Optional.of(clazz)
                .map(Class::getSimpleName)
                .map(simpleName -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, simpleName))
                .map(simpleName -> String.format("%s/%s", PersistenceConstants.PERSISTENCE_ROOT, simpleName))
                .orElse(null);
    }

    /**
     * Checks if the field holds a value.
     * String, Date or primitive types: true
     * References: false
     *
     * @param field
     * @return
     */
    public static boolean isValue(Field field) {
        return field.getType().equals(String.class) || ClassUtils.isPrimitiveOrWrapper(field.getType()) || field.getType().equals(Date.class) || field.getType().equals(Calendar.class);
    }

    /**
     * Infers the serializable id of a model based on the CarveId annotation
     *
     * @param model
     * @return
     * @throws IllegalAccessException
     * @throws PersistorException
     */
    public static String getCarveId(Object model) throws IllegalAccessException, PersistorException {
        Class modelClass = model.getClass();

        for (Field field : modelClass.getDeclaredFields()) {
            Annotation[] annotations = field.getDeclaredAnnotations();

            boolean found = Arrays.stream(annotations)
                    .anyMatch(annotation -> annotation instanceof CarveId);

            if (found && field.getType().isAssignableFrom(String.class)) {
                field.setAccessible(true);
                // TODO make this work with Integer (int), Long (long) and String
                return (String) field.get(model);
            }
        }

        throw new PersistorException(PersistorFunction.MSG_SERIALIZABLE_ID_NOT_FOUND);
    }
}
