package services.models;

/**
 * Created by sanat.
 */
public class StatusResponse {
    public String statusMessage;
    public String statusCode;

    public StatusResponse(String sm, String sc) {
        statusMessage = sm;
        statusCode =sc;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
