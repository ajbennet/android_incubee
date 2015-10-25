package incubee.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import incubee.android.R;
import incubee.android.views.SlidingTabLayout;
import incubee.android.views.TabsFragmentPager;

/**
 * A placeholder fragment for implementing Messages List
 */
public class MessagesFragment extends BaseFragment implements ViewPager.OnPageChangeListener {


	private TabsFragmentPager mViewPager;
	private MessagesAdapter mViewPagerAdapter;
	private int mCurrentPage = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.messages_fragment, container,
				false);
		
		initializeView(rootView);
		
		return rootView;
	}

	private void initializeView(View rootView) {
		mViewPager = (TabsFragmentPager) rootView.findViewById(R.id.messages_pager);

		SlidingTabLayout tabs = (SlidingTabLayout) rootView.findViewById(R.id.messages_pager_tab_strip);;

		mViewPagerAdapter = new MessagesAdapter(getChildFragmentManager(), this);
		mViewPager.setAdapter(mViewPagerAdapter);

		mViewPager.setCurrentItem(mCurrentPage);
		mViewPager.setOffscreenPageLimit(2);

		tabs.setCustomTabView(R.layout.messages_page_tabs, R.id.tab_title, 0);

		tabs.setDistributeEvenly(true);
		tabs.setViewPager(mViewPager);

		tabs.setOnPageChangeListener(this);
		mViewPager.setPagingEnabled(false);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	public static class MessagesAdapter extends FragmentStatePagerAdapter {

		private static final String TAG = "MessagesAdapter";
		private final int[] TITLES = { R.string.saved_projects,
				R.string.customers,
				R.string.others};

		private Fragment mCurrentPrimaryItem = null;

		private SparseArray<Fragment> mFragmentsRef = new SparseArray<Fragment>();
		private WeakReference<MessagesFragment> mFragment;

		private static final int PROJECTS = 0;
		private static final int CUSTOMERS = 1;
		private static final int OTHERS = 2;

		public MessagesAdapter(FragmentManager fm, MessagesFragment fragment) {
			super(fm);
			mFragment = new WeakReference<MessagesFragment>(fragment);
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
				case PROJECTS:
					child =
							(mFragmentsRef.get(position) == null) ?
									new SavedProjects() : mFragmentsRef.get(position);
					break;

				case CUSTOMERS:
					child = (mFragmentsRef.get(position) == null) ?
							new CustomersFragment() : mFragmentsRef.get(position);
					break;

				case OTHERS:
				default:
					child = (mFragmentsRef.get(position) == null) ?
							new Others() : mFragmentsRef.get(position);
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
					(fragment.getActivity()).invalidateOptionsMenu();
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
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragment.get().getString(TITLES[position]);
		}
	}
}