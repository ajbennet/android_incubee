package services;

import java.util.ArrayList;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import rx.Observable;
import services.errors.UserAlreadyCreated;
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
        rx.Observable<LoginResponse> models = Service.getService(null, BASE_API_URL, new ErrorHandler() {
            @Override
            public Throwable handleError(RetrofitError cause) {
                return handleKnownError(cause);
            }
        }).login(loginRequest);
        return models;
    }

    public void delete(String uid) {
        Service.getServiceWithNoInterceptor(null, BASE_API_URL, null).delete(uid);
    }

    public Observable<StatusResponse> signup(String name, String company_id, String url, String email, String token) {
        SignupRequest signupRequest = new SignupRequest(name, company_id, url, email, token);
        return Service.getService(null, BASE_API_URL, new ErrorHandler() {
            @Override
            public Throwable handleError(RetrofitError cause) {
                return handleKnownError(cause);
            }
        }).signup(signupRequest);
    }

    private Throwable handleKnownError(RetrofitError cause) {
        if (cause != null) {
            switch (cause.getResponse().getStatus()) {
                case 409:
                    return new UserAlreadyCreated();
            }
        }
        return cause;
    }

    public Observable<IncubeeProfile> getIncubeeProfile(String incubee_id) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL, null).getIncubeeProfile(incubee_id);
    }

    public Observable<ArrayList<IncubeeProfile>> getAllIncubees() {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL, null).getAllIncubees();
    }

    public Observable<StatusResponse> like(String incubee_id, String userId) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL, null).like(incubee_id, userId);
    }

    public Observable<StatusResponse> customerLike(String incubee_id, String userId) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL, null).customerLike(incubee_id, userId);
    }

    public Observable<AllLikesModel> getAllLikes(String user_id) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL, null).getAllLikes(user_id);
    }

    public Observable<AllLikesModel> getAllCustomers(String incubee_id) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL, null).getAllCustomers(incubee_id);
    }

    public Observable<StatusResponse> sendMsg(String uid, SendMessageBody sendMessageBody) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL, null).sendMessage(uid, sendMessageBody);
    }

    public Observable<GetMsgResponseBody> getAllMsgs(String uid) {
        return Service.getServiceWithNoInterceptor(null, BASE_API_URL, null).getAllMsgs(uid);
    }
}
