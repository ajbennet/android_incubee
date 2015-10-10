package services.models;

/**
 * Created by sanat.
 */
public class SignupRequest {
    public String name;
    public String id;
    public String image_url;
    public String email;
    public String token;

    public SignupRequest(String n, String ID, String url, String e, String t) {
        name = n;
        id = ID;
        image_url = url;
        email = e;
        token = t;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
