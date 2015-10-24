package incubee.android.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import incubee.android.R;
import incubee.android.storage.DBFactory;
import incubee.android.storage.EntitlementInterface;
import incubee.android.storage.PrefManager;
import incubee.android.storage.model.Entitlement;


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


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String userId = PrefManager.getUserID(getApplicationContext());

                if (!TextUtils.isEmpty(userId)) {
                    EntitlementInterface entitlementDB = DBFactory.getEntitlementDB(getApplicationContext());
                    Entitlement user = entitlementDB.getUserEntitlement(getApplicationContext(), userId);

                    if(!TextUtils.isEmpty(user.getToken())) {
                        HomeActivity.startActivity(SplashActivity.this);
                        finish();
                        return;
                    }


                }

                LoginActivity.startActivity(SplashActivity.this);



                finish();

            }
        }, 1000L);


    }

}
