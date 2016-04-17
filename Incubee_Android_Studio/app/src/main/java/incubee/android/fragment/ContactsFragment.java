package incubee.android.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import incubee.android.R;

/**
 * A placeholder fragment for implementing Contacts/About
 */
public class ContactsFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.contact_fragment, container,
				false);
		
		initializeView(rootView);
		
		return rootView;
	}

	private TextView mVersionName = null;
	private void initializeView(View rootView) {

		mVersionName = (TextView)rootView.findViewById(R.id.version_name);

		try {
			String versionName = mAppContext.getPackageManager()
                    .getPackageInfo(mAppContext.getPackageName(), 0).versionName;


			mVersionName.setText("v" + versionName);

		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}


		

		
	}
}