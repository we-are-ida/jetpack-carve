package be.ida.jetpack.carve.manager.exception;

public class ModelManagerException extends Exception {
    public ModelManagerException(String msg) {
        super(msg);
    }
    public ModelManagerException(String msg, Throwable t) {
        super(msg, t);
    }
}
