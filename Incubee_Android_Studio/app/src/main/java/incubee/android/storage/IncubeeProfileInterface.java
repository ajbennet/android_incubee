package incubee.android.storage;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import services.models.IncubeeProfile;

/**
 * Created by samuh on 10/18/2015.
 */
public interface IncubeeProfileInterface {

    /**
     * Returns true if the storage exists for the user
     */
    boolean doesStorageExist(Context context);

    Cursor getAllIncubeeProfiles(Context context);

    /**
     * Returns a value object documenting the profile for incubee id
     */
    Cursor getIncubeeProfile(Context context, String incubeeId);

    /**
     * Save the entitlement object into storage. It will return false if the operation was unsuccessful
     * or if the parameter passed is invalid
     */
    boolean saveIncubeeProfiles(Context context, ArrayList<IncubeeProfile> list);


}
