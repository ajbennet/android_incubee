package incubee.android.storage.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samuh on 10/18/2015.
 */
public class Entitlement implements Parcelable {

    private String mDisplayName;
    private String mUserId;
    private String mCompanyId;
    private String mEmailId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeString(mDisplayName);
        pc.writeString(mUserId);
        pc.writeString(mCompanyId);
        pc.writeString(mEmailId);
    }

    public Entitlement(){

    }

    /**
     * For Parcelable implementation
     */
    public Entitlement(Parcel pc) {
        mDisplayName = pc.readString();
        mUserId = pc.readString();
        mCompanyId = pc.readString();
        mEmailId = pc.readString();
    }

    /**
     * For Parcelable implementation
     */
    public static final Parcelable.Creator<Entitlement> CREATOR = new
            Parcelable.Creator<Entitlement>() {
                public Entitlement createFromParcel(Parcel pc) {
                    return new Entitlement(pc);
                }

                @Override
                public Entitlement[] newArray(int size) {
                    return new Entitlement[size];
                }
            };

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        this.mCompanyId = companyId;
    }

    public String getEmailId() {
        return mEmailId;
    }

    public void setEmailId(String emailId) {
        this.mEmailId = emailId;
    }
}
