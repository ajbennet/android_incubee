package incubee.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import incubee.android.App;
import incubee.android.R;
import incubee.android.adaptors.SavedProjectsAdaptor;
import incubee.android.storage.DBFactory;
import incubee.android.storage.PrefManager;
import incubee.android.storage.model.Entitlement;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import services.ServiceProvider;
import services.models.AllLikesModel;
import services.models.IncubeeProfile;

/**
 * Created by Sanat.
 */
public class SavedProjects extends BaseFragment {

    private static final String TAG = "SavedProjects";
    private ListView mProjectListView;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private List<IncubeeProfile> mLikedIncubeeProfileList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.savedprojects_fragment, container, false);
        initializeViews(rootView);
        loadModelAndWireupWithView();
        return rootView;
    }

    private void initializeViews(View rootView) {
        mProjectListView = (ListView) rootView.findViewById(R.id.projects_list);
    }

    private void loadModelAndWireupWithView() {
        String userID = PrefManager.getUserID(getActivity());
        Entitlement entitlement = DBFactory.getEntitlementDB(getActivity().getApplicationContext())
                .getUserEntitlement(getActivity(), userID);

        String token = entitlement.getToken();

        mSubscriptions.add(
                ServiceProvider.getInstance().getUserService().getAllLikes(userID, token)
                        .subscribeOn(App.getIoThread())
                        .observeOn(App.getMainThread())
                        .subscribe(new Subscriber<AllLikesModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(AllLikesModel allLikesModel) {
                                if (allLikesModel != null) {
                                    List<String> incubeeList = allLikesModel.getIncubeeList();
                                    setupAdaptor(incubeeList);
                                }
                            }
                        })
        );
    }

    public void setupAdaptor(final List<String> incubeeIDList) {
        if (incubeeIDList != null) {
            mLikedIncubeeProfileList = new ArrayList<>();
            mSubscriptions.add(
                    ServiceProvider.getInstance().getUserService().getAllIncubees()
                            .subscribeOn(App.getIoThread())
                            .observeOn(App.getMainThread())
                            .subscribe(new Subscriber<ArrayList<IncubeeProfile>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(ArrayList<IncubeeProfile> incubeeProfiles) {
                                    if(incubeeProfiles == null) {
                                        Log.e(TAG, "incubeeProfiles returned is null");
                                        return;
                                    }
                                    for(IncubeeProfile incubeeProfile : incubeeProfiles) {
                                        if(incubeeIDList.contains(incubeeProfile.getId())) {
                                            mLikedIncubeeProfileList.add(incubeeProfile);
                                        }
                                    }

                                    if(mLikedIncubeeProfileList.size() > 0) {
                                        mProjectListView.setAdapter(new SavedProjectsAdaptor(getActivity(), mLikedIncubeeProfileList));
                                    }
                                }
                            })
            );
        }
    }
}
