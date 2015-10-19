package incubee.android.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import incubee.android.storage.IncubeeProfileDBManager.TABLE_IncubeeImages;
import incubee.android.storage.IncubeeProfileDBManager.TABLE_IncubeeProfile;
import services.models.IncubeeProfile;


/**
 * Access layer for Incubee Profile Database. Use this for persisting and reading
 * Company(Incubee) information.
 */
public class IncubeeProfileHandler implements IncubeeProfileInterface {

    private static final String TAG = IncubeeProfileHandler.class.getSimpleName();

    private IncubeeProfileDBManager mDBHandle;
    private static IncubeeProfileHandler mInstance;

    /**
     * Private constructor. The class is singleton and can be instantiated only through the static
     * getInstance function
     */
    private IncubeeProfileHandler(Context context) {
        if (mDBHandle == null) {
            mDBHandle = new IncubeeProfileDBManager(context.getApplicationContext());
        }
    }

    /**
     * Returns an instance of the Entitlement Data Handler object. Note that the class is singleton,
     * so there will be only one instance of the object in the application
     */
    public static IncubeeProfileHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new IncubeeProfileHandler(context);
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
    public Cursor getAllIncubeeProfiles(Context context) {

        StringBuilder rawQuery =
                new StringBuilder("Select ")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_INCUBEE_ID).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_COMPANY_NAME).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_COMPANY_URL).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_LOGO_URL).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_HIGH_CONCEPT).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_DESCRIPTION).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_TWITTER_URL).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_VIDEO_URL).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_FOUNDER).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_LOCATION).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_CONTACT_EMAIL).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_FUNDING).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_PROJECT_STATUS).append(",")
                        .append("p.").append(TABLE_IncubeeProfile.FIELD_AREA).append(",")
                        .append("group_concat(i.").append(TABLE_IncubeeImages.FIELD_IMAGE_URL).append(")")
                        .append(" from ").append(TABLE_IncubeeProfile.TBL_NAME)
                        .append(" as p ").append("LEFT JOIN ")
                        .append(TABLE_IncubeeImages.TBL_NAME).append(" as i ")
                        .append("ON p.").append(TABLE_IncubeeProfile.FIELD_INCUBEE_ID)
                        .append("=i.").append(TABLE_IncubeeImages.FIELD_INCUBEE_ID)
                        .append(" group by p.").append((TABLE_IncubeeProfile.FIELD_INCUBEE_ID));

        SQLiteDatabase sqliteDb = getDBManager();

        Cursor cursor = sqliteDb.rawQuery(rawQuery.toString(),null);

        Log.d(TAG, "Total  rows returned : " + cursor.getCount());


        return cursor;


    }

    @Override
    public Cursor getIncubeeProfile(Context context, String incubeeId) {

        if (TextUtils.isEmpty(incubeeId)) {
            throw new IllegalArgumentException("incubee Id cannot be null");
        }

        SQLiteDatabase sqliteDb = getDBManager();

        String whereClause = TABLE_IncubeeProfile.FIELD_INCUBEE_ID + "=?";


        return null;
    }

    @Override
    public boolean saveIncubeeProfiles(Context context, ArrayList<IncubeeProfile> list) {

        Log.d(TAG, "Persisting IncubeeProfile to DB");

        if (list == null || list.isEmpty()) {
            return false;
        }

        long rowsUpdated = 0;

        SQLiteDatabase sqliteDb = getDBManager();
        sqliteDb.beginTransaction();

        try {
            // Add the entry to the entitlements table. This would be a replace operation so that if
            //  the record is already present then just replace it. ASSUMES that the uid column is set
            //  as UNIQUE in the DB schema

            for (IncubeeProfile profile : list) {
                ContentValues values = new ContentValues();

                values.put(TABLE_IncubeeProfile.FIELD_INCUBEE_ID, profile.getId());
                values.put(TABLE_IncubeeProfile.FIELD_COMPANY_NAME, profile.getCompany_name());
                values.put(TABLE_IncubeeProfile.FIELD_COMPANY_URL, profile.getCompany_url());
                values.put(TABLE_IncubeeProfile.FIELD_LOGO_URL, profile.getLogo_url());

                values.put(TABLE_IncubeeProfile.FIELD_HIGH_CONCEPT, profile.getHigh_concept());
                values.put(TABLE_IncubeeProfile.FIELD_DESCRIPTION, profile.getDescription());
                values.put(TABLE_IncubeeProfile.FIELD_TWITTER_URL, profile.getTwitter_url());
                values.put(TABLE_IncubeeProfile.FIELD_VIDEO_URL, profile.getVideo_url());

                values.put(TABLE_IncubeeProfile.FIELD_FOUNDER, profile.getFounder());
                values.put(TABLE_IncubeeProfile.FIELD_LOCATION, profile.getLocation());
                values.put(TABLE_IncubeeProfile.FIELD_CONTACT_EMAIL, profile.getContact_email());
                values.put(TABLE_IncubeeProfile.FIELD_VIDEO, profile.getVideo());

                values.put(TABLE_IncubeeProfile.FIELD_FUNDING, profile.getfunding());
                values.put(TABLE_IncubeeProfile.FIELD_PROJECT_STATUS, profile.getProject_status());
                values.put(TABLE_IncubeeProfile.FIELD_AREA, profile.getField());

                rowsUpdated = sqliteDb.replace(TABLE_IncubeeProfile.TBL_NAME, null, values);

                List<String> imageList = profile.getImages();

                if (imageList != null) {
                    for (String imageURL : imageList) {
                        ContentValues image = new ContentValues();

                        image.put(TABLE_IncubeeImages.FIELD_INCUBEE_ID, profile.getId());
                        image.put(TABLE_IncubeeImages.FIELD_IMAGE_URL, imageURL);

                        sqliteDb.replace(TABLE_IncubeeImages.TBL_NAME, null, image);
                    }
                }


            }


            sqliteDb.setTransactionSuccessful();
        } finally {
            sqliteDb.endTransaction();
        }

        return (rowsUpdated > 0);
    }
}
