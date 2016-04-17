package services.models;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import incubee.android.IncubeeRobolectricTestRunner;

@RunWith(IncubeeRobolectricTestRunner.class)
public class LoginResponseTest {

    private LoginResponse mLoginResponse;
    private final String statusMsg = "statusMessage";
    private final String statusCode = "400";
    private final String companyID = "company_id";
    private final String userType = "I";

    private ServiceData serviceData = new ServiceData(companyID, userType);

    @Before
    public void setup() {
        mLoginResponse = new LoginResponse(statusMsg, statusCode, serviceData);
    }

    @Test
    public void testLoginResponseModel() {
        Assert.assertEquals(companyID, mLoginResponse.getServicedata().getCompany_id());
        Assert.assertEquals(statusCode, mLoginResponse.getStatusCode());
        Assert.assertEquals(statusMsg, mLoginResponse.getStatusMessage());
    }

    @After
    public void cleanup() {
        mLoginResponse = null;
    }
}
