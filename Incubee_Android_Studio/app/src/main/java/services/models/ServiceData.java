package services.models;

/**
 * Created by sanattripathi on 9/9/15.
 */
public class ServiceData {
    private String company_id;

    public ServiceData(String id) {
        company_id = id;
    }

    public String getCompany_id() {
        return company_id;
    }
}
