package incubee.android;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowHandler;

import incubee.android.activities.LoginActivity;

/**
 * Test against LoginActivity.
 */
@RunWith(IncubeeRobolectricTestRunner.class)
@Config(sdk = 21)
public class LoginActivityTest {

    @Test
    public void testSignInButton() {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        loginActivity.getWindow().findViewById(R.id.sign_in_button).performClick();
        ShadowHandler.idleMainLooper();
//        Assert.assertEquals(ShadowToast.getTextOfLatestToast(), "User Signed out..");
    }
}
