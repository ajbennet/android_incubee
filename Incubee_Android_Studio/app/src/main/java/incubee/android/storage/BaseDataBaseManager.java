package incubee.android.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by samuh on 10/18/2015.
 */
public abstract class BaseDataBaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_CONTEXT = "/databases/";
    public static final String LOCAL_DB_PATH = "/Incubee/DB";

    private static final boolean DEBUG = false;

    public BaseDataBaseManager(Context context, String db_name,
                               CursorFactory factory, int version) {
        super(context,
                getDatabaseFullPath(context, db_name),
                factory,
                version);

    }

    /**
     * Returns the path of the Entitlements database
     */
    protected static String getDatabaseFullPath(Context context, String db_name) {
        String path = new StringBuilder(context.getApplicationInfo().dataDir)
                .append(DATABASE_CONTEXT)
                .append(db_name).toString();

        if (DEBUG && Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ) {
            String debugPath =
                    new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                    .append(LOCAL_DB_PATH)
                            .toString();

            if (checkDebugPath(debugPath)) {
                path = new StringBuilder(debugPath).append(File.separator).append(db_name).toString();
            }
        }

        Log.d("BaseDataBaseManager", "Path to DB: " + path);
        return path.toString();
    }

    private static boolean checkDebugPath(String path) {
        File folder = new File(path);

        if (!folder.exists()) {
            return folder.mkdirs();
        }
        return true;
    }

}
