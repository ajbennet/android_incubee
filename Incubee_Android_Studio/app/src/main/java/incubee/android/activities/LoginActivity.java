package incubee.android.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person;

import incubee.android.R;


/**
 * Login Screen. Connects to Google Play Services Framework to let the user
 * sign into the app using their Google Account.
 */
public class LoginActivity extends GSConnectionActivity implements
        View.OnClickListener{

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        findViewById(R.id.sign_in_button).setOnClickListener(this);


    }

    @Override
    protected void onUserSignedIn(Person person) {
        Toast.makeText(getApplicationContext(),
                "User Signed In..", Toast.LENGTH_SHORT).show();

        navigateNextScreen();
    }


    private void navigateNextScreen() {

        HomeActivity.startActivity(this);

        finish();
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.sign_in_button:
                performSignIn();
                break;
            default:
                break;
        }

    }


}
