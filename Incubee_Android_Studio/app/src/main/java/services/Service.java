package services;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Client;
import services.apis.ISendLoginRequests;

/**
 * Created by sanattripathi on 9/9/15.
 */
public class Service {

    public static ISendLoginRequests getLoginService(Client client, final String baseApiURL) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseApiURL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Content-Type", "application/json");
                    }
                });
        if(client == null) {
            builder.setClient(new DefaultClient());
        } else {
            builder.setClient(client);
        }
        return builder.build().create(ISendLoginRequests.class);
    }
}
