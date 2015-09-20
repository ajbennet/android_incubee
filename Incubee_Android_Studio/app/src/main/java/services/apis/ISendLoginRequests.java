package services.apis;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;
import services.models.LoginRequest;
import services.models.LoginResponse;

/**
 * Created by sanattripathi on 9/9/15.
 */
public interface ISendLoginRequests {
    @POST("/rest/login")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);
}
