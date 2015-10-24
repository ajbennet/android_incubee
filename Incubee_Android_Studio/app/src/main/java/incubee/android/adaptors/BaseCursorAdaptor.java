package incubee.android.adaptors;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by samuh on 10/24/2015.
 */
public abstract class BaseCursorAdaptor<T> extends CursorAdapter {

    protected ArrayList<T> mSelectedList = new ArrayList<T>();

    private int mLayoutResId;

    private LayoutInflater inflator;

    public BaseCursorAdaptor(Context context, Cursor c, int layoutResId) {
        super(context, c, false);

        mLayoutResId = layoutResId;

        mContext= context;

        inflator = LayoutInflater.from(context);

    }

    protected Context mContext;

    protected abstract T getValueObject(Cursor c);

    public T getValueObjAt(int position){

        Cursor cursor = getCursor();
        if(cursor.moveToPosition(position)){
            return getValueObject(cursor);
        }

        return null;

    }

    @Override
    public T getItem(int position) {
        return getValueObjAt(position);
    }

    /**
     * @param object
     * @return true, if the particular item is selected via checkbox by the user
     */
    protected boolean isSelected(T object){
        if(mSelectedList != null && mSelectedList.size() > 0){
            return mSelectedList.contains(object);
        }
        return false;
    }

    /**
     * @param object
     *            The object to be selected. T must ovveride equals and hashcode
     */
    public void setSelected(T object){
        mSelectedList.add(object);

    }

    public void removeFromSelection(T object){
        mSelectedList.remove(object);
    }

    public void clearSelection(){
        mSelectedList.clear();
    }

    public void selectAllMessages(){
        mSelectedList.clear();
        final Cursor cursor = getCursor();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mSelectedList.add(getValueObject(cursor));
            cursor.moveToNext();
        }
    }

    public ArrayList<T> getSelectedItems(){
        return mSelectedList;
    }

    public int getSelectedItemsCount(){
        return mSelectedList.size();
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflator.inflate(mLayoutResId, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        T valueObject = getValueObject(cursor);
        bindView(view, mContext, valueObject);
    }

    public abstract void bindView(View view, Context context, T valueObject);
}
