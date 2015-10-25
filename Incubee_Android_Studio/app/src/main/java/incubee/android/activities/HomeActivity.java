package incubee.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import incubee.android.R;
import incubee.android.fragment.BaseFragment;
import incubee.android.fragment.ContactsFragment;
import incubee.android.fragment.HomeFragment;
import incubee.android.fragment.MessagesFragment;
import incubee.android.fragment.SearchFragment;
import incubee.android.views.IconTabPagerAdapter;
import incubee.android.views.SlidingTabLayout;
import incubee.android.views.TabsFragmentPager;

public class HomeActivity extends GSConnectionActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "HomeActivity";

    private TabsFragmentPager mViewPager;
    private ModulesAdapter mViewPagerAdapter;
    private View mRoot;

    private final int DEFAULT_PAGE = 0;

    private int mCurrentPage = DEFAULT_PAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        mRoot = findViewById(R.id.root_view);
        mViewPager= (TabsFragmentPager) findViewById(R.id.modules_pager);
        mViewPager.setPagingEnabled(false);

        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.pager_tab_strip);

        mViewPagerAdapter = new ModulesAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setCurrentItem(mCurrentPage);

        tabs.setCustomTabView(R.layout.home_page_tabs, R.id.tab_title, R.id.tab_icon);

        tabs.setDistributeEvenly(true);
        tabs.setViewPager(mViewPager);

        tabs.setOnPageChangeListener(this);

    }

    @Override
    protected void onUserSignedOut() {
        Toast.makeText(getApplicationContext(),
                "User Signed out..", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_signout) {
            performSignOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getIntent(Context context) {

        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    public static void startActivity(Activity activity) {
        activity.startActivity(HomeActivity.getIntent(activity.getApplicationContext()));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, String.format("Page Selected :%d", position));
        if(mCurrentPage != position){
            Fragment deselectedFragment = ((ModulesAdapter)mViewPager.getAdapter()).getItem(mCurrentPage);
            if(deselectedFragment != null){
                ((BaseFragment)deselectedFragment).onRemovedFromSelection();
            }

            Fragment selectedFragment = ((ModulesAdapter)mViewPager.getAdapter()).getItem(position);

            if(selectedFragment != null){
                ((BaseFragment)selectedFragment).onFragmentSelected();
            }

            mCurrentPage = position;

        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * Class that backs the Fragments hosted in ViewPager.
     * It maintains a Cache to avoid creating new Fragments
     * every time.
     *
     * @author samuh
     *
     */
    public static class ModulesAdapter extends IconTabPagerAdapter {

        private final int[] TITLES = { R.string.tab_title_home,
                R.string.tab_title_search,
                R.string.tab_title_messages,
                R.string.tab_title_contacts};

        private final int[] ICONS = {
                R.drawable.h_home,
                R.drawable.h_search,
                R.drawable.h_messages,
                R.drawable.h_profile
        };

        private Fragment mCurrentPrimaryItem = null;

        private HashMap<Integer, Fragment> mFragmentsRef = new HashMap<Integer, Fragment>();
        private WeakReference<HomeActivity> mActivity;

        private static final int HOME = 0;
        private static final int SEARCH = 1;
        private static final int MESSAGES = 2;
        private static final int CONTACTS = 3;

        public ModulesAdapter(FragmentManager fm, HomeActivity activity) {
            super(fm);
            mActivity = new WeakReference<HomeActivity>(activity);
        }

        @Override
        public Object instantiateItem(ViewGroup viewgroup, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(viewgroup, position);
            mFragmentsRef.put(position, fragment);
            return super.instantiateItem(viewgroup, position);
        }

        /**
         * Return the Fragment associated with a specified position.
         */
        public Fragment getItem(final int position) {
            Fragment child = null;

            switch(position) {
                case HOME:
                    child =
                            (mFragmentsRef.get(position) == null) ?
                            new HomeFragment() : mFragmentsRef.get(position);
                    break;

                case SEARCH:
                    child = (mFragmentsRef.get(position) == null) ?
                            new SearchFragment() : mFragmentsRef.get(position);
                    break;

                case MESSAGES:
                    child = (mFragmentsRef.get(position) == null) ?
                            new MessagesFragment() : mFragmentsRef.get(position);
                    break;

                case CONTACTS:
                default:
                    child = (mFragmentsRef.get(position) == null) ?
                            new ContactsFragment() : mFragmentsRef.get(position);
                    break;
            }

            return child;
        }

        public Fragment getPrimaryItem(){
            return mCurrentPrimaryItem;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            Fragment fragment = (Fragment)object;
            try{
                if (fragment != mCurrentPrimaryItem) {
                    mCurrentPrimaryItem = fragment;
                    ((FragmentActivity)fragment.getActivity()).invalidateOptionsMenu();
                }
            }catch(Exception e){
                Log.e(TAG, "Error setting primary item", e);
            }

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d(TAG, String.format(" Destroying Fragment at %d", position));
            super.destroyItem(container, position, object);
            mFragmentsRef.remove(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mActivity.get().getString(TITLES[position]);
        }

        @Override
        public int getPageIcon(int position) {
            return ICONS[position];
        }
    }

}
