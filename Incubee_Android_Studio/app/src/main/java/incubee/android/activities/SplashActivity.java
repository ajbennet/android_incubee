package incubee.android.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import incubee.android.R;
import incubee.android.storage.DBFactory;
import incubee.android.storage.EntitlementInterface;
import incubee.android.storage.PrefManager;
import incubee.android.storage.model.Entitlement;
import incubee.android.views.SecretTextView;


/**
 * Entry point of the app; Launcher Activity
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        SecretTextView logoText = (SecretTextView) findViewById(R.id.splash_logo_text);
        logoText.setDuration(3500);
        logoText.hide();

        SecretTextView secText = (SecretTextView) findViewById(R.id.logo_sec);
        secText.setDuration(3000);
        secText.hide();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String userId = PrefManager.getUserID(getApplicationContext());

                if (!TextUtils.isEmpty(userId)) {
                    EntitlementInterface entitlementDB = DBFactory.getEntitlementDB(getApplicationContext());
                    Entitlement user = entitlementDB.getUserEntitlement(getApplicationContext(), userId);

                    if(user != null && !TextUtils.isEmpty(user.getToken())) {
                        HomeActivity.startActivity(SplashActivity.this);
                        finish();
                        return;
                    }


                }

                LoginActivity.startActivity(SplashActivity.this);



                finish();

            }
        }, 3000L);


    }

}
