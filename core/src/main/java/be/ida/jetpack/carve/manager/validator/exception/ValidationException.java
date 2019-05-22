package be.ida.jetpack.carve.manager.validator.exception;

public class ValidationException extends Exception {
    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(String msg, Throwable t) {
        super(msg, t);
    }
}
