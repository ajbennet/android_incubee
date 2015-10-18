import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import retrofit.RetrofitError;
import services.ServiceProvider;
import services.models.AllLikesModel;
import services.models.GetMsgResponseBody;
import services.models.IncubeeProfile;
import services.models.LoginResponse;
import services.models.SendMessageBody;
import services.models.StatusResponse;

/**
 * Created by sanat.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class IntegrationTest {

    private ServiceProvider mServiceProvider = ServiceProvider.getInstance();

    private static final String NAME = "Abinath Bennet";
    private static final String USER_ID = "110489314263267697974";
    private static final String IMAGE_URL = "https://lh4.googleusercontent.com/-CL6coBFm9VE/AAAAAAAAAAI/AAAAAAAAHCk/ngCxGax3Tcc/s96-c/photo.jpg";
    private static final String EMAIL = "abinathab@gmail.com";
    private static final String TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRhNjYyNWIzNmJjMDlkMzAwMzUzYjI4YTc0MWNlMTc1MjVhNGMzM2IifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwic3ViIjoiMTEwNDg5MzE0MjYzMjY3Njk3OTc0IiwiYXpwIjoiMTA3OTIxODM2OTc1My0zZmc5c291NDBrZHJqYjVoc2ZubTBvbzlqajBkb2s5YS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImVtYWlsIjoiYWJpbmF0aGFiQGdtYWlsLmNvbSIsImF0X2hhc2giOiJIRVBZblI1UEN6N2JtenR2Qk5HOU13IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF1ZCI6IjEwNzkyMTgzNjk3NTMtM2ZnOXNvdTQwa2RyamI1aHNmbm0wb285amowZG9rOWEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJpYXQiOjE0Mzc0NjI1NjUsImV4cCI6MTQzNzQ2NjE2NSwibmFtZSI6IkFiaW5hdGhhYiBCZW5uZXQiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDQuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1DTDZjb0JGbTlWRS9BQUFBQUFBQUFBSS9BQUFBQUFBQUhDay9uZ0N4R2F4M1RjYy9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiQWJpbmF0aGFiIiwiZmFtaWx5X25hbWUiOiJCZW5uZXQiLCJsb2NhbGUiOiJlbiJ9.iLVRfNtK_MZVQtgJFyS-0hk6iJ8JjAT9vr0bg1iwYpryhi2-y2kBF4-qKCM3k_wmYqFh4JJDgbS-_AktT01Wo3kvL7atkQBs3kN8jq9YhTZi5NWkafuQPHB3q4xE8ict_xMjCozfxxAquyDMZzymt_qOP_vDERbU0mrJR2FOLqJNENF29GBUCZjiGxJGEDDP6lnk57ZRLLbc_XpzouowlrOiw2x8u0txXE5fBe6xqa1TDV1Xfa9_eSGSv7azPZPkvS3OUew2KDuCTe6WxwOCnFeiAA5rMOwci_zwyvcDj4bsS8vw-LM-LQs_zXSP2gKxIrM2fn1sestAxtfIonNy2A";
    public static final String INCUBEE_ID = "inc_952745e0-ea2e-4365-83b3-cd379072ce57";

    @Test(expected = RetrofitError.class)
    public void testSignupService()  {
        StatusResponse signupResponse = mServiceProvider.getUserService().signup(NAME, USER_ID, IMAGE_URL, EMAIL, TOKEN).toBlocking().single();
        System.out.println("StatusCode: "+signupResponse.getStatusCode());
        System.out.println("StatusMessage: "+signupResponse.getStatusMessage());
    }

    @Test
    public void testLoginService() {
        LoginResponse loginResponse = mServiceProvider.getUserService().login(NAME, USER_ID, IMAGE_URL, EMAIL, TOKEN).toBlocking().single();
        System.out.println("StatusCode: "+loginResponse.getStatusCode());
        System.out.println("StatusMessage: "+loginResponse.getStatusMessage());
        System.out.println("Company_Id: "+loginResponse.getServicedata().getCompany_id());
    }

    @Test
    public void testGetIncubeeProfileAPI() {
        IncubeeProfile incubeeProfile = mServiceProvider.getUserService().getIncubeeProfile(INCUBEE_ID).toBlocking().single();
        System.out.println(incubeeProfile.getCompany_name());
    }

    @Test
    public void testGetAllIncubeesAPI() {
        List<IncubeeProfile> incubeeProfiles = mServiceProvider.getUserService().getAllIncubees().toBlocking().single();
        System.out.println(incubeeProfiles.get(0).getId());
        System.out.println(incubeeProfiles.get(0).getId());
    }

    @Test
    public void testGetAllCustomersAPI() {
        AllLikesModel allCustomerProfiles = mServiceProvider.getUserService().getAllCustomers(INCUBEE_ID).toBlocking().single();
        System.out.println(allCustomerProfiles.getIncubeeList().get(0));
    }

    @Test
    public void testDeleteAPI() {
        mServiceProvider.getUserService().delete("111766196999845439854");
    }

    /*
    * Following 2 APIs have some issues, hence ignored
    * */
    @Ignore
    @Test
    public void testIncubeeLikeAPI() {
        StatusResponse statusResponse = mServiceProvider.getUserService().like("inc_e14651b7-1f65-460f-8841-5cb716236704", USER_ID).toBlocking().single();
        System.out.println(statusResponse.getStatusCode());
    }


    @Test
    public void testIncubeeCustomerLikeAPI() {
        StatusResponse statusResponse = mServiceProvider.getUserService().customerLike("inc_e14651b7-1f65-460f-8841-5cb716236704", USER_ID).toBlocking().single();
        System.out.println(statusResponse.getStatusCode());
    }

    @Test
    public void testIncubeeAllLikesAPI() {
        AllLikesModel allLikesModel = mServiceProvider.getUserService().getAllLikes(USER_ID).toBlocking().single();
        System.out.println(allLikesModel.getStatusCode());
        System.out.println(allLikesModel.getIncubeeList().get(1));
    }

    @Ignore
    @Test
    public void testSendMsgAPI() {
        //Need other persons uid to send messages.
        SendMessageBody sendMessageBody = new SendMessageBody("hi, I like this idea", USER_ID, "Pawan tr", "TO_UID", 914, 323, "USR");
        mServiceProvider.getUserService().sendMsg(USER_ID, sendMessageBody);
    }

    @Test
    public void testGetAllMsgsAPI() {
        GetMsgResponseBody getMsgResponseBody = mServiceProvider.getUserService().getAllMsgs(USER_ID).toBlocking().single();
        System.out.println(getMsgResponseBody.getMessages().get(0).getBody());
    }
}
