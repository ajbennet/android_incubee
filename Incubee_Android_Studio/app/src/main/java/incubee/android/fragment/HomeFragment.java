package incubee.android.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import incubee.android.App;
import incubee.android.R;
import incubee.android.adaptors.SimpleCardsAdapter;
import incubee.android.storage.DBFactory;
import incubee.android.storage.IncubeeProfileInterface;
import incubee.android.storage.PrefManager;
import incubee.android.storage.model.Entitlement;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import services.ServiceProvider;
import services.models.IncubeeProfile;
import services.models.StatusResponse;
import stackedlist.view.CardEventsListener;
import stackedlist.view.CardListView;
import stackedlist.view.Orientations;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends BaseFragment {

	private Object allIncubeeInformation;
	private static final String TAG = "HomeFragment";
    private Button mDisLikeButton;
    private Button mLikeButton;
    private ArrayList<IncubeeProfile> mIncubeeProfiles;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private Button mCartButton;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.card_list_fragment, container,
				false);
		
		initializeView(rootView);
		
		return rootView;
	}

	private CardListView mCardList;
    private TextView mHighConcept;

	private void initializeView(View rootView) {
		
		mCardList = (CardListView) rootView.findViewById(R.id.main_list);
        mHighConcept = (TextView) rootView.findViewById(R.id.company_high_concept);
        mLikeButton = (Button) rootView.findViewById(R.id.likeButton);
        mDisLikeButton = (Button) rootView.findViewById(R.id.dislikeButton);
        mCartButton = (Button) rootView.findViewById(R.id.cart_button);


        mCardList.setOrientation(Orientations.Orientation.Ordered);
        mCardList.setCardEventListener(mCardEventListener);
        mLikeButton.setOnClickListener(mButtonClickListener);
        mDisLikeButton.setOnClickListener(mButtonClickListener);
        mCartButton.setOnClickListener(mButtonClickListener);

		getAllIncubeeInformation();

	}

	private SimpleCardsAdapter mCardStackAdaptor = null;

    private CardEventsListener mCardEventListener = new CardEventsListener() {
        @Override
        public void cardLiked(int position) {
            String userID = PrefManager.getUserID(getActivity());
            Entitlement entitlement = DBFactory.getEntitlementDB(getActivity().getApplicationContext())
                    .getUserEntitlement(getActivity(), userID);

            String token = entitlement.getToken();
            IncubeeProfile incubeeProfile = mIncubeeProfiles.get(position);

            Log.d("incubee_id: ",incubeeProfile.getId());
            Log.d("userId: ",userID);

            mSubscriptions.add(ServiceProvider.getInstance().getUserService()
                            .like(incubeeProfile.getId(), userID, token)
                            .subscribe(new Subscriber<StatusResponse>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(TAG, "cardLiked completed!");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e(TAG, "cardLiked error : " + e.getMessage());
                                }

                                @Override
                                public void onNext(StatusResponse statusResponse) {
                                    Log.d(TAG, "cardLiked ? statusCode: " + statusResponse.getStatusCode());
                                }
                            })
            );

            updateCurrentView();
        }

        @Override
        public void cardDisliked(int position) {
            IncubeeProfile incubeeProfile = mIncubeeProfiles.get(position);
            Log.d(TAG, "incubeeID: "+incubeeProfile.getId()+" disliked!");
            updateCurrentView();
        }

        @Override
        public void onCardClicked(int position) {
            Log.d(TAG, "pos clicked: "+position);
            Log.d(TAG, "list count: " + mCardStackAdaptor.getCount());
        }
    };

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.likeButton:
                    mCardList.remove(true);
                    break;
                case R.id.dislikeButton:
                    mCardList.remove(false);
                    break;
                case R.id.cart_button:
                    becomeCustomer();
                    break;
            }
            updateCurrentView();
        }
    };

    private void becomeCustomer() {
        String userID = PrefManager.getUserID(getActivity());
        Entitlement entitlement = DBFactory.getEntitlementDB(getActivity().getApplicationContext())
                .getUserEntitlement(getActivity(), userID);

        String token = entitlement.getToken();

        int firstVi = mCardList.getVisibleChildPosition();
        Log.d(TAG, "visible pos: "+firstVi);
        IncubeeProfile incubeeProfile = mIncubeeProfiles.get(firstVi);
        mSubscriptions.add(
                ServiceProvider.getInstance().getUserService().customerLike(incubeeProfile.getId(), userID, token)
                .subscribeOn(App.getIoThread())
                .observeOn(App.getMainThread())
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "becomeCustomer/onError: " + e.getMessage(), e);
                    }

                    @Override
                    public void onNext(StatusResponse statusResponse) {
                        Log.d(TAG, "becomeCustomer successfull: "+statusResponse.getStatusCode());
                    }
                })
        );
    }

    private void updateCurrentView(){
        int visibleChild = mCardList.getVisibleChildPosition();

        Log.d(TAG, "Updating current view at :" + visibleChild);

        mHighConcept.setText(mIncubeeProfiles.get(visibleChild).getHigh_concept());


    }

    public void getAllIncubeeInformation() {
        mSubscriptions.add(
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
                                Log.e(TAG, "onError: " + e.getMessage(), e);
                            }

                            @Override
                            public void onNext(ArrayList<IncubeeProfile> incubeeProfiles) {

                                IncubeeProfileInterface database = DBFactory.getIncubeeProfileDB(mAppContext);
                                database.saveIncubeeProfiles(mAppContext, incubeeProfiles);

                                Log.d(TAG, "getAllIncubees" + " onNext called!");
                                mIncubeeProfiles = incubeeProfiles;
                                mCardStackAdaptor = new SimpleCardsAdapter(getActivity(), mIncubeeProfiles);
                                mCardList.setAdapter(mCardStackAdaptor);

                                updateCurrentView();
                            }
                        })
        );
	}

    @Override
    public void onDestroy() {
        mSubscriptions.clear();
        super.onDestroy();
    }
}