package services.models;

/**
 * Created by sanattripathi on 9/9/15.
 */
public class LoginRequest {
    private String name;
    private String id;
    private String image_url;
    private String email;
    private String token;

    public LoginRequest(String name, String id, String image_url, String email, String token) {
        this.name = name;
        this.id = id;
        this.image_url = image_url;
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
