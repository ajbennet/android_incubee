package services.errors;

/**
 * Copyright © 2015 Zonoff, Inc.  All Rights Reserved.
 */
public class UserAlreadyCreated extends RuntimeException{
    public UserAlreadyCreated() {

    }

    public  UserAlreadyCreated(Throwable throwable) {
        super(throwable);
    }
}
