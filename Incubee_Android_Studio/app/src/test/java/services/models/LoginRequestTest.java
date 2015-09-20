package services.models;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import incubee.android.IncubeeRobolectricTestRunner;

/**
 * Created by sanattripathi
 */
@RunWith(IncubeeRobolectricTestRunner.class)
public class LoginRequestTest {

    private LoginRequest mLoginRequest;
    private final String name = "san";
    private final String id = "id";
    private final String url = "https://imageurl.com";
    private final String email = "san@image.com";
    private final String token = "token";


    @Before
    public void setup() {
        mLoginRequest = new LoginRequest(name,id,url,email,token);
    }

    @Test
    public void testLoginRequestModel() {
        Assert.assertEquals(name, mLoginRequest.getName());
        Assert.assertEquals(id, mLoginRequest.getId());
        Assert.assertEquals(url, mLoginRequest.getImage_url());
        Assert.assertEquals(email, mLoginRequest.getEmail());
        Assert.assertEquals(token, mLoginRequest.getToken());
    }

    @After
    public void cleanup() {
        mLoginRequest = null;
    }
}
