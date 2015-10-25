package incubee.android.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by samuh on 10/25/2015.
 */
public abstract class IconTabPagerAdapter extends FragmentStatePagerAdapter{
    public IconTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public abstract int getPageIcon(int page);
}
