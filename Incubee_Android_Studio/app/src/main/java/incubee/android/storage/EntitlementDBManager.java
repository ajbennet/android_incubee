package incubee.android.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by samuh on 10/18/2015.
 */
public class EntitlementDBManager extends BaseDataBaseManager {

    private static final String TAG = EntitlementDBManager.class.getSimpleName();

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "entitlements.db";


    public EntitlementDBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating etl database");
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
        Log.d(TAG, "onUpgrade etl database");
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
        db.execSQL(TABLE_Entitlement.CREATE.toString());

    }

    /**
     * Function to drop the tables. Note it will not be responsible for starting and ending the transaction
     */
    @SuppressWarnings("unused")
    private void dropTables(SQLiteDatabase db) {
        db.execSQL(new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_Entitlement.TBL_NAME).toString());
    }

    /**
     * Database Table Schema - Table for maintaining the entitlement response received from server. Each
     * user will have one and only one record in this table.
     */
    protected static final class TABLE_Entitlement {
        public static final String TBL_NAME = "TBL_ENTITLEMENT";

        public static final String FIELD_ID 				= "_id";

        /** Date-Time when the login request was made */
        public static final String FIELD_DATE 				= "etl_date";

        /** Fields to hold data */
        public static final String FIELD_DNAME 				= "etl_dispName";
        public static final String FIELD_UID 				= "etl_uid";
        public static final String FIELD_COMPANY_ID			= "etl_company_id";
        public static final String FIELD_EMAIL_ID 			= "etl_email";
        public static final String FIELD_TOKEN   			= "etl_token";
        public static final String FIELD_USER_TYPE			= "xx_etl_extra_1";

        //Additional fields created for future use
        public static final String FIELD_EXTRA_2			= "xx_etl_extra_2";
        public static final String FIELD_EXTRA_3			= "xx_etl_extra_3";

        private static final StringBuilder CREATE = new StringBuilder();
        static {
            CREATE.append("CREATE TABLE ").append(TBL_NAME).append("(");
            CREATE.append(FIELD_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
            CREATE.append(FIELD_DATE).append(" INTEGER NOT NULL,");
            CREATE.append(FIELD_DNAME).append(" VARCHAR(32),");
            CREATE.append(FIELD_UID).append(" VARCHAR(32) UNIQUE,");
            CREATE.append(FIELD_COMPANY_ID).append(" VARCHAR(32) UNIQUE,");
            CREATE.append(FIELD_EMAIL_ID).append(" VARCHAR(32) UNIQUE,");
            CREATE.append(FIELD_TOKEN).append(" VARCHAR(32) UNIQUE,");
            CREATE.append(FIELD_USER_TYPE).append(" VARCHAR(32),");
            CREATE.append(FIELD_EXTRA_2).append(" VARCHAR(32),");
            CREATE.append(FIELD_EXTRA_3).append(" INTEGER");
            CREATE.append(");");
        }
    }


}
