package services.errors;

/**
 * sanat.
 */
public class UserAlreadyCreated extends RuntimeException{
    public UserAlreadyCreated() {

    }

    public  UserAlreadyCreated(Throwable throwable) {
        super(throwable);
    }
}
