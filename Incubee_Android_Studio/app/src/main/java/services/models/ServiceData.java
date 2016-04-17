package services.models;

/**
 * Created by sanattripathi on 9/9/15.
 */
public class ServiceData {
    private String company_id;
    private String user_type;


    public ServiceData(String id, String userType) {
        company_id = id;
        user_type = userType;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getUserType() {
        return user_type;
    }
}
