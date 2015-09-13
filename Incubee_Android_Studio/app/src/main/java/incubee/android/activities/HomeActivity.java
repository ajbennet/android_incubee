package incubee.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import incubee.android.PlaceHolderFragment;
import incubee.android.R;

public class HomeActivity extends AppCompatActivity implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {


	private static final String TAG = "HomeActivity";

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;

	/* Is there a ConnectionResult resolution in progress? */
	private boolean mIsResolving = false;

	/* Should we automatically resolve ConnectionResults when possible? */
	private boolean mShouldResolve = false;
	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceHolderFragment()).commit();


			// Build GoogleApiClient with access to basic profile
			mGoogleApiClient = new GoogleApiClient.Builder(this)
					.addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this)
					.addApi(Plus.API)
					.addScope(new Scope(Scopes.PROFILE))
					.build();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();

		mGoogleApiClient.disconnect();
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
		onSignOutClicked();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConnected(Bundle bundle) {
		// onConnected indicates that an account was selected on the device, that the selected
		// account has granted any requested permissions to our app and that we were able to
		// establish a service connection to Google Play services.
		Log.d(TAG, "onConnected:" + bundle);
		mShouldResolve = false;
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// Could not connect to Google Play Services.  The user needs to select an account,
		// grant permissions or resolve an error in order to sign in. Refer to the javadoc for
		// ConnectionResult to see possible error codes.
		Log.d(TAG, "onConnectionFailed:" + connectionResult);

		if (!mIsResolving && mShouldResolve) {
			if (connectionResult.hasResolution()) {
				try {
					connectionResult.startResolutionForResult(this, RC_SIGN_IN);
					mIsResolving = true;
				} catch (IntentSender.SendIntentException e) {
					Log.e(TAG, "Could not resolve ConnectionResult.", e);
					mIsResolving = false;
					mGoogleApiClient.connect();
				}
			} else {
				// Could not resolve the connection result, show the user an
				// error dialog.
				showErrorDialog(connectionResult);


			}
		} else {
			// Show the signed-out UI
			showSignedOutUI();
		}

	}

	private void showSignedOutUI() {
		Toast.makeText(getApplicationContext(),
				"User Signed out..", Toast.LENGTH_SHORT).show();

		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);

		finish();
	}

	private void showErrorDialog(ConnectionResult connectionResult) {
		Toast.makeText(getApplicationContext(),
				connectionResult.toString(), Toast.LENGTH_SHORT).show();
	}

	private void onSignOutClicked() {
		// Clear the default account so that GoogleApiClient will not automatically
		// connect in the future.
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
		}

		showSignedOutUI();
	}

	public static Intent getIntent(Context context){

		Intent intent = new Intent(context, HomeActivity.class);
		return intent;
	}

	public static void startActivity(Activity activity){
		activity.startActivity(HomeActivity.getIntent(activity.getApplicationContext()));
	}
	
}
