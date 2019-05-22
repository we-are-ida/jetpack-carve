package be.ida.jetpack.carve.manager.validator;

import be.ida.jetpack.carve.manager.validator.exception.ValidationException;

import java.lang.reflect.Field;

public class FieldValidator {
    public static void isValid(Field field) throws ValidationException {

    }

    public static boolean isEligible(Field field) {
        return true;
    }
}
