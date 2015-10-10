package services.models;

/**
 * Created by sanat.
 */
public class Msg {
    private String mid;
    private String to;
    private String eid;
    private long time;
    private long stime;
    private String status;
    private String name;
    private String body;
    private String type;
    private String dir;
    private double longitude;
    private double latitude;
    private String media;

    public Msg(String md, String t, String uid, long tim, long stim, String stat, String n, String b, String ty, String d, double lo, double lat, String med) {
        mid = md;
        to = t;
        eid = uid;
        time = tim;
        stime = stim;
        status = stat;
        name = n;
        body =b;
        type = ty;
        dir = d;
        longitude = lo;
        latitude = lat;
        media = med;
    }

    public String getStatus() {
        return status;
    }

    public String getMid() {
        return mid;
    }

    public String getMedia() {
        return media;
    }

    public String getDir() {
        return dir;
    }

    public long getTime() {
        return time;
    }

    public long getStime() {
        return stime;
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
