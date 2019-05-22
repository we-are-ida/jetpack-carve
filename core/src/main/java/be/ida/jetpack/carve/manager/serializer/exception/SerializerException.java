package be.ida.jetpack.carve.manager.serializer.exception;

public class SerializerException extends RuntimeException {
    public SerializerException(String msg, Throwable t) {
        super(msg, t);
    }
}
