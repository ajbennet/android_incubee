package services.errors;

/**
 * Copyright © 2015 Zonoff, Inc.  All Rights Reserved.
 */
public class ServerUnreachable extends RuntimeException {
    public ServerUnreachable() {

    }

    public ServerUnreachable(Throwable cause) {
        super(cause);
    }
}
