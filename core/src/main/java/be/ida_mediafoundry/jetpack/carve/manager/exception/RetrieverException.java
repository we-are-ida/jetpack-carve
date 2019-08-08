package be.ida_mediafoundry.jetpack.carve.manager.exception;

public class RetrieverException extends RuntimeException {
    public RetrieverException(String msg) {
        super(msg);
    }

    public RetrieverException(String msg, Throwable t) {
        super(msg, t);
    }
}
