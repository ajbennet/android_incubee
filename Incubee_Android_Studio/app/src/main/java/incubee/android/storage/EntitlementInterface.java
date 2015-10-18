package incubee.android.storage;

import android.content.Context;

import incubee.android.storage.model.Entitlement;

/**
 * Created by samuh on 10/18/2015.
 */
public interface EntitlementInterface {

    /**
     * Returns true if the storage exists for the user
     */
    boolean doesStorageExist(Context context);

    /**
     * Returns a value object documenting the Entitlements for the uid in arguement
     */
    Entitlement getUserEntitlement(Context context, String uid);

    /**
     * Save the entitlement object into storage. It will return false if the operation was unsuccessful
     * or if the parameter passed is invalid
     */
    boolean saveEntitlement(Context context, Entitlement data);


}
