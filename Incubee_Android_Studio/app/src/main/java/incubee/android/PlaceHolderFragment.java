package incubee.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import stackedlist.view.CardListView;
import stackedlist.view.Orientations;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceHolderFragment extends Fragment {

	private Context mAppContext;
	
	public PlaceHolderFragment() {
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		mAppContext = activity.getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		initializeView(rootView);
		
		return rootView;
	}

	private CardListView mList;
	private void initializeView(View rootView) {
		
		mList = (CardListView) rootView.findViewById(R.id.main_list);
		mList.setOrientation(Orientations.Orientation.Ordered);
		
		ArrayList<String> cards = new ArrayList<String>();
		
		cards.add("One");
		cards.add("Two");
		cards.add("Three");
		cards.add("Four");
		
		mList.setAdapter(new SimpleCardsAdapter(mAppContext, cards));
		
	
		
	}
}