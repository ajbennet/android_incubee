package incubee.android.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import incubee.android.App;
import incubee.android.R;
import incubee.android.adaptors.SimpleCardsAdapter;
import incubee.android.storage.DBFactory;
import incubee.android.storage.IncubeeProfileInterface;
import rx.Subscriber;
import rx.functions.Action1;
import services.ServiceProvider;
import services.models.IncubeeProfile;
import stackedlist.view.CardListView;
import stackedlist.view.CardStackAdapter;
import stackedlist.view.Orientations;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends BaseFragment {

	private Object allIncubeeInformation;
	private static final String TAG = "HomeFragment";

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

		getAllIncubeeInformation();
	}

	private CardStackAdapter<IncubeeProfile> mAdaptor = null;

	private void onDataLoaded(){
		mList.setAdapter(mAdaptor);
	}

	public void getAllIncubeeInformation() {
		ServiceProvider.getInstance().getUserService().getAllIncubees()
				.subscribeOn(App.getIoThread())
				.observeOn(App.getMainThread())
				.doOnError(new Action1<Throwable>() {
					@Override
					public void call(Throwable throwable) {
						Log.e(TAG, "do on error");
					}
				})
				.subscribe(new Subscriber<ArrayList<IncubeeProfile>>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						Log.e(TAG, "onError: " +e.getMessage(), e);
					}

					@Override
					public void onNext(ArrayList<IncubeeProfile> incubeeProfiles) {

						IncubeeProfileInterface database = DBFactory.getIncubeeProfileDB(mAppContext);
						database.saveIncubeeProfiles(mAppContext, incubeeProfiles);

						Log.d(TAG, "getAllIncubees" + " onNext called!");
						mAdaptor = new SimpleCardsAdapter(getActivity(), incubeeProfiles);

						onDataLoaded();

					}
				});
	}
}