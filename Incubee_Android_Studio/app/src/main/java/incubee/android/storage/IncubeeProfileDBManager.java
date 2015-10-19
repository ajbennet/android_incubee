package incubee.android.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by samuh on 10/18/2015.
 */
public class IncubeeProfileDBManager extends BaseDataBaseManager {

    private static final String TAG = IncubeeProfileDBManager.class.getSimpleName();

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "incubee.db";


    public IncubeeProfileDBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating Incubee profile database");
        db.beginTransaction();
        try{
            createTables(db);

            db.setTransactionSuccessful();
        }
        finally{
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade Incubee profile database");
        db.beginTransaction();
        try {
            switch(oldVersion) {
                case 1:
                    //New DB table Constants added
                    dropTables(db);

                    // the upgrade will follow a cumulative logic, so no break statement here...
                default:
            }

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    /**
     * Returns true if the sqllite database exists
     */
    public boolean checkDataBase(Context context) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(getDatabaseFullPath(context, DB_NAME),
                    null,
                    SQLiteDatabase.OPEN_READONLY);
            if(checkDB!=null)
                checkDB.close();
        }
        catch (SQLiteException e) {
            return false;
        }
        return checkDB != null ? true : false;
    }

    /**
     * Function to create the tables. Note it will not be responsible for starting and ending the transaction
     */
    private void createTables(SQLiteDatabase db) {
        db.execSQL(TABLE_IncubeeProfile.CREATE.toString());
        db.execSQL(TABLE_IncubeeImages.CREATE.toString());

    }

    /**
     * Function to drop the tables. Note it will not be responsible for starting and ending the transaction
     */
    @SuppressWarnings("unused")
    private void dropTables(SQLiteDatabase db) {
        db.execSQL(new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_IncubeeProfile.TBL_NAME).toString());
        db.execSQL(new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_IncubeeImages.TBL_NAME).toString());
    }

    /**
     * Database Table Schema - Table for maintaining the entitlement response received from server. Each
     * user will have one and only one record in this table.
     */
    protected static final class TABLE_IncubeeProfile {

        public static final String TBL_NAME = "TBL_INCUBEE_PROFILE";

        public static final String FIELD_ID 				= "_id";

        /** Date-Time when the login request was made */
        public static final String FIELD_INCUBEE_ID 		= "inc_id";
        public static final String FIELD_COMPANY_NAME		= "inc_company_name";

        /** Fields to hold data */
        public static final String FIELD_COMPANY_URL 		= "inc_company_url";
        public static final String FIELD_LOGO_URL 			= "inc_logo_url";
        public static final String FIELD_HIGH_CONCEPT		= "inc_high_concept";
        public static final String FIELD_DESCRIPTION 		= "inc_desc";

        public static final String FIELD_TWITTER_URL		= "inc_twitter_url";
        public static final String FIELD_VIDEO_URL 			= "inc_video_url";
        public static final String FIELD_FOUNDER			= "inc_founder";
        public static final String FIELD_LOCATION 			= "inc_location";

        public static final String FIELD_CONTACT_EMAIL		= "inc_contact_email";
        public static final String FIELD_VIDEO 				= "inc_video";
        public static final String FIELD_FUNDING			= "inc_funding";
        public static final String FIELD_PROJECT_STATUS		= "inc_project_status";
        public static final String FIELD_AREA 			    = "inc_field";

        //Additional fields created for future use
        public static final String FIELD_EXTRA_1			= "xx_etl_extra_1";
        public static final String FIELD_EXTRA_2			= "xx_etl_extra_2";
        public static final String FIELD_EXTRA_3			= "xx_etl_extra_3";

        private static final StringBuilder CREATE = new StringBuilder();
        static {
            CREATE.append("CREATE TABLE ").append(TBL_NAME).append("(");
            CREATE.append(FIELD_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
            CREATE.append(FIELD_INCUBEE_ID).append(" VARCHAR(32) UNIQUE,");
            CREATE.append(FIELD_COMPANY_NAME).append(" INTEGER NOT NULL,");
            CREATE.append(FIELD_COMPANY_URL).append(" VARCHAR(32),");
            CREATE.append(FIELD_LOGO_URL).append(" VARCHAR(32),");
            CREATE.append(FIELD_HIGH_CONCEPT).append(" VARCHAR(32),");
            CREATE.append(FIELD_DESCRIPTION).append(" VARCHAR(32),");
            CREATE.append(FIELD_TWITTER_URL).append(" VARCHAR(32),");
            CREATE.append(FIELD_VIDEO_URL).append(" VARCHAR(32),");
            CREATE.append(FIELD_FOUNDER).append(" VARCHAR(32),");
            CREATE.append(FIELD_LOCATION).append(" VARCHAR(32),");
            CREATE.append(FIELD_CONTACT_EMAIL).append(" VARCHAR(32),");
            CREATE.append(FIELD_VIDEO).append(" VARCHAR(32),");
            CREATE.append(FIELD_FUNDING).append(" INTEGER,");
            CREATE.append(FIELD_PROJECT_STATUS).append(" VARCHAR(32),");
            CREATE.append(FIELD_AREA).append(" VARCHAR(32),");
            CREATE.append(FIELD_EXTRA_1).append(" VARCHAR(32),");
            CREATE.append(FIELD_EXTRA_2).append(" VARCHAR(32),");
            CREATE.append(FIELD_EXTRA_3).append(" INTEGER");
            CREATE.append(");");
        }
    }


    /**
     * Database Table Schema - Table for maintaining the entitlement response received from server. Each
     * user will have one and only one record in this table.
     */
    protected static final class TABLE_IncubeeImages {

        public static final String TBL_NAME = "TBL_INCUBEE_IMAGES";

        /** Date-Time when the login request was made */
        public static final String FIELD_INCUBEE_ID 		= "inc_id";
        public static final String FIELD_IMAGE_URL 			= "inc_image_url";

        //Additional fields created for future use
        public static final String FIELD_EXTRA_1			= "xx_etl_extra_1";


        private static final StringBuilder CREATE = new StringBuilder();
        static {
            CREATE.append("CREATE TABLE ").append(TBL_NAME).append("(");
            CREATE.append(FIELD_INCUBEE_ID).append(" VARCHAR(32),");

            CREATE.append(FIELD_IMAGE_URL).append(" VARCHAR(32),");

            CREATE.append(FIELD_EXTRA_1).append(" VARCHAR(32)");

            CREATE.append(");");
        }
    }


}
