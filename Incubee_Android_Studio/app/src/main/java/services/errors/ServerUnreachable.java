package services.errors;

/**
 * sanat.
 */
public class ServerUnreachable extends RuntimeException {
    public ServerUnreachable() {

    }

    public ServerUnreachable(Throwable cause) {
        super(cause);
    }
}
