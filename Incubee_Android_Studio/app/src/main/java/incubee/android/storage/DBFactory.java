package incubee.android.storage;

import android.content.Context;

/**
 * Created by samuh on 10/18/2015.
 */
public class DBFactory {

    private static boolean clearDB = false;

    /**
     * Returns DB interface class for the Entitlement Database
     */
    public static EntitlementInterface getEntitlementDB(Context context) {
        return EntitlementHandler.getInstance(context);
    }

    /**
     * Returns DB interface class for the Entitlement Database
     */
    public static IncubeeProfileInterface getIncubeeProfileDB(Context context) {
        return IncubeeProfileHandler.getInstance(context);
    }

    /**
     * Flags DB to clear instances on next cleanup call
     */
    public static void flagDBToCleanUp() {
        clearDB = true;
    }

    /**
     * Closes different app DB instances of the logged in user. To be invoked
     * only when logging out
     */
    public static void closeDB() {
        if (clearDB) {
            clearDB = false;

            EntitlementHandler.closeInstance();
            IncubeeProfileHandler.closeInstance();

        }
    }
}
