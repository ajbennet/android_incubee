package incubee.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import incubee.android.R;

/**
 * A placeholder fragment for implementing Search functionality
 */
public class SearchFragment extends Fragment {


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