package incubee.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import incubee.android.PlaceHolderFragment;
import incubee.android.R;

public class HomeActivity extends GSConnectionActivity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceHolderFragment()).commit();

        }
    }

    @Override
    protected void onUserSignedOut() {
        Toast.makeText(getApplicationContext(),
                "User Signed out..", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_signout) {
            performSignOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getIntent(Context context) {

        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    public static void startActivity(Activity activity) {
        activity.startActivity(HomeActivity.getIntent(activity.getApplicationContext()));
    }

}
