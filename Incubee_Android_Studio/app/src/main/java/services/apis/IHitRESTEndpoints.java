package services.apis;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
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
public interface IHitRESTEndpoints {
    @POST("/rest/login")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/rest/signup")
    Observable<StatusResponse> signup(@Body SignupRequest signupRequest);

    @DELETE("/rest/user?uid={uid}")
    void delete(@Path("uid") String uid);

    @GET("/rest/{incubee_id}")
    Observable<IncubeeProfile> getIncubeeProfile(@Path("incubee_id") String incubee_id);

    @GET("/rest/all")
    Observable<List<IncubeeProfile>> getAllIncubees();

    @POST("/rest/like/{incubee_id}")
    Observable<StatusResponse> like(@Path("incubee_id") String incubee_id, @Query("uid") String uid);

    @GET("/rest/like")
    Observable<AllLikesModel> getAllLikes(@Query("id") String user_id);

    @POST("/rest/customer/{incubee_id}")
    Observable<StatusResponse> customerLike(@Path("incubee_id") String incubee_id, @Query("uid") String uid);

    @GET("/rest/customer")
    Observable<AllLikesModel> getAllCustomers(@Query("id") String incubee_id);

    @POST("/rest/msg")
    Observable<StatusResponse> sendMessage(@Query("id") String user_id, @Body SendMessageBody sendMessageBody);

    @GET("/rest/msg/all")
    Observable<GetMsgResponseBody> getAllMsgs(@Query("eid") String uid);
}
