package services.models;

import java.util.List;

/**
 * created by sanat.
 */
public class GetMsgResponseBody {
    private String statusMessage;
    private String statusCode;
    private List<Msg> messages;

    public GetMsgResponseBody(String sM, String sC, List<Msg> msgs) {
        statusMessage = sM;
        statusCode = sC;
        messages = msgs;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public List<Msg> getMessages() {
        return messages;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
