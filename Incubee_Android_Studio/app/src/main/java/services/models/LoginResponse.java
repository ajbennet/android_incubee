package services.models;

/**
 * Created by sanattripathi on 9/9/15.
 */
public class LoginResponse {
    private String statusMessage;
    private String statusCode;
    private ServiceData servicedata;

    public LoginResponse(String statusMessage, String statusCode, ServiceData servicedata) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.servicedata = servicedata;
    }

    public ServiceData getServicedata() {
        return servicedata;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
