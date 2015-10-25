package incubee.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import incubee.android.R;

/**
 * A placeholder fragment for implementing Search functionality
 */
public class SearchFragment extends BaseFragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.search_fragment, container,
				false);
		
		initializeView(rootView);
		
		return rootView;
	}

	private void initializeView(View rootView) {
		

		
	}
}