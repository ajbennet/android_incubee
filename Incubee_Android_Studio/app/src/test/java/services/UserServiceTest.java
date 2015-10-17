package services;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import incubee.android.IncubeeRobolectricTestRunner;
import rx.Observable;
import services.models.LoginResponse;
import services.models.ServiceData;

import static org.mockito.Mockito.when;

@RunWith(IncubeeRobolectricTestRunner.class)
public class UserServiceTest {

    @Mock
    UserService mUserService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        // String jsonResponse ="{\"statusMessage\":\"Success\",\"statusCode\":\"LOG_1000\",\"servicedata\":{\"company_id\":\"inc_952745e0-ea2e-4365-83b3-cd379072ce57\\n\"}}";
        Observable<LoginResponse> loginResponseObservable = Observable.just(new LoginResponse("Success", "LOG_1000", new ServiceData("inc_952745e0-ea2e-4365-83b3-cd379072ce57")));
        when(mUserService.login(Matchers.<String>any(),Matchers.<String>any(),Matchers.<String>any(),Matchers.<String>any(),Matchers.<String>any())).thenReturn(loginResponseObservable);
    }

    @Test
    public void testLogin() {
        Observable<LoginResponse> models = mUserService.login("name", "id", "asdasd", "email", "token");
        LoginResponse loginResponse = models.toBlocking().first();
        Assert.assertEquals("Success", loginResponse.getStatusMessage());
        Assert.assertEquals("LOG_1000", loginResponse.getStatusCode());
        Assert.assertEquals("inc_952745e0-ea2e-4365-83b3-cd379072ce57", loginResponse.getServicedata().getCompany_id());
    }

    @After
    public void cleanup() {
        mUserService = null;
    }

}
