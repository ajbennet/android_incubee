package services.models;

/**
 * Created by sanat.
 */
public class SendMessageBody {
    private String body;
    private String eid;
    private String name;
    private String to;
    private double longitude;
    private double latitude;
    private String type;

    public SendMessageBody(String b, String uid, String n, String t, double lo, double lat, String ty) {
        body =b;
        eid = uid;
        name = n;
        to = t;
        longitude = lo;
        latitude = lat;
        type = ty;
    }

    public String getType() {
        return type;
    }

    public String getTo() {
        return to;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getEid() {
        return eid;
    }

    public String getBody() {
        return body;
    }
}
