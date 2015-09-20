package services;

import rx.Observable;
import services.models.LoginRequest;
import services.models.LoginResponse;

/**
 * Created by sanattripathi on 9/9/15.
 */
public class UserService {
    private final static String TAG = "UserService";

    private static final String BASE_API_URL = "http://www.incub.ee";

    public Observable<LoginResponse> login(String name, String id, String image_url, String email, String token) {
        LoginRequest loginRequest = new LoginRequest(name, id, image_url, email, token);
        rx.Observable<LoginResponse> models = Service.getLoginService(null, BASE_API_URL).login(loginRequest);
//        models.subscribe(new Subscriber<LoginResponse>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted called!");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("error called: "+e.getMessage());
//            }
//
//            @Override
//            public void onNext(LoginResponse responseModel) {
//                Log.e(TAG, "responseModel : "+responseModel);
//                if(responseModel == null) {
//                    return;
//                }
//            }
//        });
        return models;
    }
}
