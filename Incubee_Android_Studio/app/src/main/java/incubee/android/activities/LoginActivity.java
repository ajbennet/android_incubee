package incubee.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        findViewById(R.id.no_thanks).setOnClickListener(this);
        TextView welcomeText = (TextView) findViewById(R.id.welcome_text);

        String bannerText = "<font color='red'>Login</font> if you already uploaded " +
                "your work at <font color='#07947a'>incub.ee</font>";
        welcomeText.setText(Html.fromHtml(bannerText), TextView.BufferType.SPANNABLE);
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
                                Log.e(TAG, "/onUserSignedIninside subscriber: " + e.getMessage(), e);
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
                                } else {
                                    showLoginError();
                                }
                                login(token);
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
                    .setPositiveButton("OK", null)
                    .create();
            alertDialog.show();

    }

    private void login(final String token) {
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
                                if (loginResponse != null) {
                                    Log.d(TAG, "Received login Response");

                                    EntitlementInterface entitlementsDB = DBFactory.getEntitlementDB(getApplicationContext());
                                    Entitlement entitlement = new Entitlement();
                                    entitlement.setUserId(mPerson.getId());
                                    entitlement.setEmailId(mAccountName);
                                    entitlement.setDisplayName(mPerson.getName().getGivenName());
                                    entitlement.setToken(token);

                                    if (loginResponse.getServicedata() != null) {
                                        entitlement.setCompanyId(loginResponse.getServicedata().getCompany_id());
                                        // write data to Preference :: this will read the next time user
                                        // logs in and chooses the same account
                                        PrefManager.setIncubeeID(getApplicationContext(),
                                                loginResponse.getServicedata().getCompany_id());
                                    }

                                    entitlementsDB.saveEntitlement(getApplicationContext(), entitlement);


                                    navigateNextScreen();
                                } else {
                                    Log.e(TAG, "loginResponse is null");
                                }
                                showLoginError();

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
            case R.id.no_thanks:
                finish();
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

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }
}
