package incubee.android.adaptors;

import android.content.Context;
import android.database.Cursor;

import incubee.android.storage.DBFactory;
import incubee.android.storage.IncubeeProfileInterface;

/**
 * Created by samuh on 10/24/2015.
 */
public class IncubeeProfileLoader extends AutoRefreshCursorLoader{

    public IncubeeProfileLoader(Context context) {
        super(context, IncubeeProfileInterface.INC_PROF_MOD_URI);
    }

    @Override
    protected Cursor buildCursor() {

        IncubeeProfileInterface profileDB = DBFactory.getIncubeeProfileDB(mAppContext);

        return profileDB.getAllIncubeeProfiles(mAppContext);
    }
}
