package services.apis;

import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import services.models.AllCustomers;
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
public interface IHitRESTEndpoints {
    @POST("/rest/v1.0/login")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/rest/v1.0/signup")
    Observable<StatusResponse> signup(@Body SignupRequest signupRequest);

    @DELETE("/rest/v1.0/user")
    Response delete(@Query("uid") String uid);

    @GET("/rest/v1.0/{incubee_id}")
    Observable<IncubeeProfile> getIncubeeProfile(@Path("incubee_id") String incubee_id);

    @GET("/rest/v1.0/all")
    Observable<ArrayList<IncubeeProfile>> getAllIncubees();

    @POST("/rest/v1.0/like/{incubee_id}")
    Observable<StatusResponse> like(@Path("incubee_id") String incubee_id, @Query("uid") String uid);

    @GET("/rest/v1.0/like")
    Observable<AllLikesModel> getAllLikes(@Query("id") String user_id);

    @POST("/rest/v1.0/customer/{incubee_id}")
    Observable<StatusResponse> customerLike(@Path("incubee_id") String incubee_id, @Query("uid") String uid);

    @GET("/rest/v1.0/customer")
    Observable<AllCustomers> getAllCustomers(@Query("id") String incubee_id);

    @POST("/rest/v1.0/msg")
    Observable<StatusResponse> sendMessage(@Query("id") String user_id, @Body SendMessageBody sendMessageBody);

    @GET("/rest/v1.0/msg/all")
    Observable<GetMsgResponseBody> getAllMsgs(@Query("eid") String uid);
}
