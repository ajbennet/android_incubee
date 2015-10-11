package services;

/**
 * Created by sanattripathi.
 */
public class ServiceProvider {

    private static ServiceProvider sServiceProvider = new ServiceProvider();
    private UserService mUserService;

    private ServiceProvider() {
        mUserService = new UserService();
    }

    public static ServiceProvider getInstance() {
        return sServiceProvider;
    }

    public UserService getUserService() {
        return mUserService;
    }
}
