package incubee.android.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.google.android.gms.plus.model.people.Person;

import incubee.android.App;
import incubee.android.R;
import incubee.android.storage.DBFactory;
import incubee.android.storage.EntitlementInterface;
import incubee.android.storage.PrefManager;
import incubee.android.storage.model.Entitlement;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import services.ServiceProvider;
import services.errors.UserAlreadyCreated;
import services.models.LoginResponse;
import services.models.StatusResponse;


/**
 * Login Screen. Connects to Google Play Services Framework to let the user
 * sign into the app using their Google Account.
 */
public class LoginActivity extends GSConnectionActivity implements
        View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private Person mPerson;
    private String mAccountName;
    private Context mContext;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mContext = this;
        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    protected void onUserSignedIn(Person person, String accountName) {
        Log.v(TAG + "/onUSI", "pe: " + person + " aN: " + accountName);

        // save user Id for Preference
        PrefManager.setUserID(getApplicationContext(), person.getId());

        mPerson = person;
        mAccountName = accountName;
        mSubscriptions.add(
                getToken(this, accountName)
                        .subscribeOn(App.getIoThread())
                        .observeOn(App.getMainThread())
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG + "/OUSI", "error");
                            }
                        })
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "/onUserSignedIninside subscriber: " + e.getMessage());
                                showLoginError();
                            }

                            @Override
                            public void onNext(String token) {
                                Log.d(TAG, "token: " + token);
                                signup(token);
                            }
                        })
        );
    }

    private void signup(final String token) {
        Log.d(TAG + "/signup", "called");
        mSubscriptions.add(
                ServiceProvider.getInstance().getUserService().signup(mPerson.getName().getGivenName(), mPerson.getId(), mPerson.getImage().getUrl(), mAccountName, token)
                        .subscribeOn(App.getIoThread())
                        .observeOn(App.getMainThread())
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG + "/signup", "doOnError");
                            }
                        })
                        .subscribe(new Subscriber<StatusResponse>() {
                            @Override
                            public void onCompleted() {
                                Log.v(TAG, "signup onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "/signup/inside subscriber: " + "signup onError");
                                if(e instanceof UserAlreadyCreated) {
                                    Log.d(TAG, "/user already created error");
                                    login(token);
                                } else {
                                    showLoginError();
                                }
                            }

                            @Override
                            public void onNext(StatusResponse statusResponse) {
                                Log.d(TAG, "/signup; about to signin, statusCode: " + statusResponse.getStatusCode() + " statusMessage: " + statusResponse.getStatusMessage());
                                login(token);
                            }
                        })
        );
    }

    private void showLoginError() {
            AlertDialog alertDialog = new AlertDialog.Builder(mContext).setTitle(R.string.signup_error_title)
                    .setMessage(R.string.signup_error_msg)
                    .setNeutralButton("OK", null)
                    .create();
            alertDialog.show();

    }

    private void login(String token) {
        Log.d(TAG + "/login", "called");
        mSubscriptions.add(
                ServiceProvider.getInstance().getUserService().login(mPerson.getName().getGivenName(), mPerson.getId(), mPerson.getImage().getUrl(), mAccountName, token)
                        .subscribeOn(App.getIoThread())
                        .observeOn(App.getMainThread())
                        .subscribe(new Subscriber<LoginResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "/login/inside subscriber: " + e.getMessage());
                                showLoginError();
                            }

                            @Override
                            public void onNext(LoginResponse loginResponse) {
                                if (loginResponse != null && loginResponse.getServicedata() != null && loginResponse.getServicedata().getCompany_id() != null) {
                                    Log.d(TAG + "/login", "companyId : " + loginResponse.getServicedata().getCompany_id());

                                    EntitlementInterface entitlementsDB = DBFactory.getEntitlementDB(getApplicationContext());
                                    Entitlement entitlement = new Entitlement();
                                    entitlement.setUserId(mPerson.getId());
                                    entitlement.setEmailId(mAccountName);
                                    entitlement.setDisplayName(mPerson.getName().getGivenName());
                                    entitlement.setCompanyId(loginResponse.getServicedata().getCompany_id());
                                    entitlementsDB.saveEntitlement(getApplicationContext(), entitlement);

                                    // write data to Preference :: this will read the next time user
                                    // logs in and chooses the same account
                                    PrefManager.setIncubeeID(getApplicationContext(),
                                            loginResponse.getServicedata().getCompany_id());

                                    navigateNextScreen();
                                } else if (loginResponse == null) {
                                    Log.e(TAG, "loginResponse is null");
                                } else if (loginResponse.getServicedata() == null) {
                                    Log.e(TAG, "loginResponse.getServiceData is null; User not found!");
                                } else {
                                    Log.e(TAG, "loginResponse.getServiceData.getCompanyId is null");
                                }
//                                showLoginError();
                                EntitlementInterface entitlementsDB = DBFactory.getEntitlementDB(getApplicationContext());
                                Entitlement entitlement = new Entitlement();
                                entitlement.setUserId(mPerson.getId());
                                entitlement.setEmailId(mAccountName);
                                entitlement.setDisplayName(mPerson.getName().getGivenName());
                                entitlement.setCompanyId("");
                                entitlementsDB.saveEntitlement(getApplicationContext(), entitlement);

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
