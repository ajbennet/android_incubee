package incubee.android.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person;

import incubee.android.R;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import services.ServiceProvider;
import services.models.LoginResponse;


/**
 * Login Screen. Connects to Google Play Services Framework to let the user
 * sign into the app using their Google Account.
 */
public class LoginActivity extends GSConnectionActivity implements
        View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private Person mPerson;
    private String mAccountName;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    protected void onUserSignedIn(Person person, String accountName) {
        mPerson = person;
        mAccountName = accountName;
        mSubscriptions.add(
                getToken(this, accountName)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "/onUserSignedIninside subscriber: " + e.getMessage());
                            }

                            @Override
                            public void onNext(String token) {
                                Log.d(TAG, "token: " + token);
                                login(token);
                            }
                        })
        );
    }

    private void login(String token) {
        Toast.makeText(getApplicationContext(),
                "User Signed In..", Toast.LENGTH_SHORT).show();
        mSubscriptions.add(
                ServiceProvider.getInstance().getUserService().login(mPerson.getName().getGivenName(), mPerson.getId(), mPerson.getImage().getUrl(), mAccountName, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<LoginResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "/login/inside subscriber: " + e.getMessage());
                            }

                            @Override
                            public void onNext(LoginResponse loginResponse) {
                                if(loginResponse != null && loginResponse.getServicedata() != null && loginResponse.getServicedata().getCompany_id() != null) {
                                    Log.d(TAG + "/login", "companyId : " + loginResponse.getServicedata().getCompany_id());
                                } else if(loginResponse == null){
                                    Log.e(TAG, "loginResponse is null");
                                } else if(loginResponse.getServicedata() == null) {
                                    Log.e(TAG, "loginResponse.getServiceData is null");
                                } else {
                                    Log.e(TAG, "loginResponse.getServiceData.getCompanyId is null");
                                }
                                navigateNextScreen();
                            }
                        })
        );
    }


    private void navigateNextScreen() {

        HomeActivity.startActivity(this);

        finish();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sign_in_button:
                performSignIn();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }
}
