package services.models;

import java.util.List;

/**
 * created by sanattripathi.
 */
public class AllLikesModel {
    public String statusMessage;
    public String statusCode;
    private List<String> incubeeList;

    public AllLikesModel(String sm, String sc, List<String> list) {
        statusMessage = sm;
        statusCode = sc;
        incubeeList = list;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public List<String> getIncubeeList() {
        return incubeeList;
    }
}
