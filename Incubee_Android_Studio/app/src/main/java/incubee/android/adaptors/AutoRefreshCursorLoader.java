package incubee.android.adaptors;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

/**
 * Created by samuh on 10/24/2015.
 */
public abstract class AutoRefreshCursorLoader extends CursorLoader{

    protected Context mAppContext = null;

    protected Cursor mCursor = null;

    private ContentObserver mObserver;

    private Uri mURI;

    public AutoRefreshCursorLoader(Context context, Uri notificationURI) {
        super(context);

        mObserver = new ForceLoadContentObserver();

        mURI = notificationURI;

        mAppContext = context.getApplicationContext();

    }

    private boolean isCursorValid(Cursor cursor){
        return (cursor != null && !cursor.isClosed());
    }

    @Override
    protected final void onStartLoading() {
        if (isCursorValid(mCursor)) {
            deliverResult(this.mCursor);
        }
        // this takeContentChanged() is important!
        if (takeContentChanged() || !isCursorValid(mCursor)) {
            forceLoad();
        }
    }

    @Override
    public final Cursor loadInBackground() {

        mCursor = buildCursor();

        if(mURI != null){
            mCursor.registerContentObserver(mObserver);
            mCursor.setNotificationUri(getContext().getContentResolver(), mURI);
        }

        return mCursor;
    }

    protected abstract Cursor buildCursor();
}
