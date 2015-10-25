package incubee.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

	private void initializeView(View rootView) {
		

		
	}
}