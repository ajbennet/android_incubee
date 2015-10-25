package services.models;

import java.util.List;

/**
 * Created by sanat
 */
public class IncubeeProfile {
    private String company_name;
    private String company_url;
    private String logo_url;
    private String high_concept;
    private String description;
    private String twitter_url;
    private String video_url;
    private String founder;
    private String location;
    private String contact_email;
    private List<String> images;
    private String video;
    private boolean funding;
    private String project_status;
    private String field;
    private String id;

    private boolean isCustomer;

    public IncubeeProfile(String c, String cU, String lu, String hC, String d, String tu, String vu, String f, String l, String ce, List<String> img, String v, boolean fu, String ps, String fie, String ID) {
        company_name = c;
        company_url = cU;
        logo_url = lu;
        high_concept = hC;
        description = d;
        twitter_url = tu;
        video_url = vu;
        founder = f;
        location = l;
        contact_email = ce;
        images = img;
        video = v;
        funding = fu;
        project_status = ps;
        field = fie;
        id = ID;
    }

    public boolean getfunding() {
        return funding;
    }

    public List<String> getImages() {
        return images;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getVideo() {
        return video;
    }

    public String getTwitter_url() {
        return twitter_url;
    }

    public String getProject_status() {
        return project_status;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public String getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public String getHigh_concept() {
        return high_concept;
    }

    public String getCompany_url() {
        return company_url;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getDescription() {
        return description;
    }

    public String getField() {
        return field;
    }

    public String getFounder() {
        return founder;
    }


    public String getContact_email() {
        return contact_email;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(boolean isCustomer) {
        this.isCustomer = isCustomer;
    }
}
