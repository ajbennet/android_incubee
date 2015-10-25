package services.models;

import java.util.List;

/**
 * Created by Sanat.
 */
public class AllCustomers {
    public String statusMessage;
    public String statusCode;
    private List<Customer> customerList;

    public AllCustomers(String sm, String sc, List<Customer> list) {
        statusMessage = sm;
        statusCode = sc;
        customerList = list;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }
}
