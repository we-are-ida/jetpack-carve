package be.ida.jetpack.carve.manager.exception;

public class PersistorException extends RuntimeException {
    public PersistorException(String msg) {
        super(msg);
    }

    public PersistorException(String msg, Throwable t) {
        super(msg, t);
    }
}
