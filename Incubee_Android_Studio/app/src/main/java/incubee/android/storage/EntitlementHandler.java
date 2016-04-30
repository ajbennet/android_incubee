package incubee.android.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import incubee.android.activities.UserType;
import incubee.android.storage.EntitlementDBManager.TABLE_Entitlement;
import incubee.android.storage.model.Entitlement;

/**
 * Access layer for Entitlements Database. Use this for persisting and reading
 * Entitlements information.
 */
public class EntitlementHandler implements EntitlementInterface {

    private static final String TAG = EntitlementHandler.class.getSimpleName();

    private EntitlementDBManager mDBHandle;
    private static EntitlementHandler mInstance;

    /**
     * Private constructor. The class is singleton and can be instantiated only through the static
     * getInstance function
     */
    private EntitlementHandler(Context context) {
        if (mDBHandle == null) {
            mDBHandle = new EntitlementDBManager(context.getApplicationContext());
        }
    }

    /**
     * Returns an instance of the Entitlement Data Handler object. Note that the class is singleton,
     * so there will be only one instance of the object in the application
     */
    public static EntitlementHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new EntitlementHandler(context);
        }

        return mInstance;
    }

    /**
     * Closes the Entitlement database instance gracefully
     */
    public static void closeInstance() {
        if (mInstance != null) {
            mInstance.closeDBManager();
            mInstance = null;
        }
    }

    /**
     * Closes the DB Manager instance
     */
    private void closeDBManager() {
        if (mDBHandle != null) {
            mDBHandle.close();
        }
    }

    /**
     * Returns a writable instance of the database
     */
    private SQLiteDatabase getDBManager() {
        return mDBHandle.getWritableDatabase();
    }

    @Override
    public boolean doesStorageExist(Context context) {
        return mDBHandle.checkDataBase(context);
    }


    @Override
    public Entitlement getUserEntitlement(Context context, String uid) {

        if (TextUtils.isEmpty(uid)) {
           throw new IllegalArgumentException("User Id cannot be null");
        }

        SQLiteDatabase sqliteDb = getDBManager();

        String whereClause = TABLE_Entitlement.FIELD_UID + "=?";

        Cursor cursor = sqliteDb.query(
                TABLE_Entitlement.TBL_NAME,
                new String[] {
                        TABLE_Entitlement.FIELD_ID,
                        TABLE_Entitlement.FIELD_DATE,
                        TABLE_Entitlement.FIELD_DNAME,
                        TABLE_Entitlement.FIELD_UID,
                        TABLE_Entitlement.FIELD_COMPANY_ID,
                        TABLE_Entitlement.FIELD_EMAIL_ID,
                        TABLE_Entitlement.FIELD_TOKEN,
                        TABLE_Entitlement.FIELD_USER_TYPE
                },
                whereClause,
                new String[] { uid },
                null,
                null,
                null);

        Entitlement entitlement = null;
        try {

            if (cursor == null || cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();

            String userID = cursor.getString(cursor.getColumnIndex(TABLE_Entitlement.FIELD_UID));
            String companyID = cursor.getString(cursor.getColumnIndex(TABLE_Entitlement.FIELD_COMPANY_ID));
            String emailID = cursor.getString(cursor.getColumnIndex(TABLE_Entitlement.FIELD_EMAIL_ID));
            String displayName = cursor.getString(cursor.getColumnIndex(TABLE_Entitlement.FIELD_DNAME));
            String token = cursor.getString(cursor.getColumnIndex(TABLE_Entitlement.FIELD_TOKEN));
            String userType = cursor.getString(cursor.getColumnIndex(TABLE_Entitlement.FIELD_USER_TYPE));


            entitlement = new Entitlement();
            entitlement.setUserId(userID);
            entitlement.setCompanyId(companyID);
            entitlement.setDisplayName(displayName);
            entitlement.setEmailId(emailID);
            entitlement.setToken(token);
            entitlement.setUserType(UserType.valueOf(userType));


        }
        finally {
            if(cursor != null) {
                cursor.close();
            }
        }


        return entitlement;
    }

    @Override
    public boolean saveEntitlement(Context context, Entitlement data) {

        Log.d(TAG, "Persisting Entitlements to DB");

        if(data == null){
            throw new IllegalArgumentException("Entitlement data cannot be null");
        }

        long rowsUpdated = 0;

        SQLiteDatabase sqliteDb = getDBManager();
        sqliteDb.beginTransaction();

        try {
            // Add the entry to the entitlements table. This would be a replace operation so that if
            //  the record is already present then just replace it. ASSUMES that the uid column is set
            //  as UNIQUE in the DB schema
            ContentValues values = new ContentValues();

            values.put(TABLE_Entitlement.FIELD_UID, data.getUserId());
            values.put(TABLE_Entitlement.FIELD_COMPANY_ID, data.getCompanyId());
            values.put(TABLE_Entitlement.FIELD_EMAIL_ID, data.getEmailId());
            values.put(TABLE_Entitlement.FIELD_DNAME, data.getDisplayName());
            values.put(TABLE_Entitlement.FIELD_DATE, System.currentTimeMillis());
            values.put(TABLE_Entitlement.FIELD_TOKEN, data.getToken());
            values.put(TABLE_Entitlement.FIELD_USER_TYPE, data.getUserType().toString());

            rowsUpdated = sqliteDb.replace(TABLE_Entitlement.TBL_NAME, null, values);

            sqliteDb.setTransactionSuccessful();
        }
        finally {
            sqliteDb.endTransaction();
        }

        return (rowsUpdated > 0);
    }
}
