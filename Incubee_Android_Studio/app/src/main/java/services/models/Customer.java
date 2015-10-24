package services.models;

/**
 * Copyright © 2015 Zonoff, Inc.  All Rights Reserved.
 */
public class Customer {
    private String name;
    private String id;
    private String image_url;
    private String email;

    public Customer(String name, String id, String image_url, String email) {
        this.name = name;
        this.id = id;
        this.image_url = image_url;
        this.email = email;
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
}
