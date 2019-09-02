package be.ida_mediafoundry.jetpack.carve.manager.validator;

import be.ida_mediafoundry.jetpack.carve.annotations.CarveId;
import be.ida_mediafoundry.jetpack.carve.annotations.CarveModel;
import be.ida_mediafoundry.jetpack.carve.manager.validator.exception.ValidationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ModelValidator {
    public static void validate(Object model) throws ValidationException {
        Class clazz = model.getClass();

        validateCarveModel(clazz);
        validateCarveId(clazz);
    }

    private static void validateCarveModel(Class clazz) throws ValidationException {
        Annotation[] annotations = clazz.getAnnotations();
        Arrays.stream(annotations)
                .filter(annotation -> annotation instanceof CarveModel)
                .findFirst()
                .orElseThrow(() -> new ValidationException(String.format("No @CarveModel annotation was found. Please add it to class %s", clazz)));
    }

    private static void validateCarveId(Class clazz) throws ValidationException {
        boolean found = false;
        for (Field field : clazz.getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation instanceof CarveId) {
                    found = true;
                }
            }
        }

        if (!found) {
            throw new ValidationException(String.format("No @CarveId annotation was found. Please add it to one of the members of class %s", clazz));
        }
    }
}
