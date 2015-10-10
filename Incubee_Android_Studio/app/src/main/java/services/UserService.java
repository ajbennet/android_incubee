package services;

import java.util.List;

import rx.Observable;
import services.models.AllLikesModel;
import services.models.GetMsgResponseBody;
import services.models.IncubeeProfile;
import services.models.LoginRequest;
import services.models.LoginResponse;
import services.models.SendMessageBody;
import services.models.SignupRequest;
import services.models.StatusResponse;

/**
 * Created by sanattripathi on 9/9/15.
 */
public class UserService {
    private final static String TAG = "UserService";

    private static final String BASE_API_URL = "http://www.incub.ee";

    public Observable<LoginResponse> login(String name, String id, String image_url, String email, String token) {
        LoginRequest loginRequest = new LoginRequest(name, id, image_url, email, token);
        rx.Observable<LoginResponse> models = Service.getService(null, BASE_API_URL).login(loginRequest);
        return models;
    }


    public Observable<StatusResponse> signup(String name, String company_id, String url, String email, String token) {
        SignupRequest signupRequest = new SignupRequest(name, company_id, url, email, token);
        return Service.getService(null, BASE_API_URL).signup(signupRequest);
    }

    public Observable<IncubeeProfile> getIncubeeProfile(String incubee_id) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL).getIncubeeProfile(incubee_id);
    }

    public Observable<List<IncubeeProfile>> getAllIncubees() {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL).getAllIncubees();
    }

    public Observable<StatusResponse> like(String incubee_id, String userId) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL).like(incubee_id, userId);
    }

    public Observable<StatusResponse> customerLike(String incubee_id, String userId) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL).customerLike(incubee_id, userId);
    }

    public Observable<AllLikesModel> getAllLikes(String user_id) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL).getAllLikes(user_id);
    }

    public Observable<AllLikesModel> getAllCustomers(String incubee_id) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL).getAllCustomers(incubee_id);
    }

    public Observable<StatusResponse> sendMsg(String uid, SendMessageBody sendMessageBody) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL).sendMessage(uid, sendMessageBody);
    }

    public Observable<GetMsgResponseBody> getAllMsgs(String uid) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL).getAllMsgs(uid);
    }
}
