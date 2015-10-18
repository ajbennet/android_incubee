package incubee.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import incubee.android.R;
import incubee.android.adaptors.SimpleCardsAdapter;
import stackedlist.view.CardListView;
import stackedlist.view.Orientations;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.card_list_fragment, container,
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
		
		mList.setAdapter(new SimpleCardsAdapter(getActivity(), cards));//coz there is a videoview inside each card that needs activity's window token
		
	
		
	}
}