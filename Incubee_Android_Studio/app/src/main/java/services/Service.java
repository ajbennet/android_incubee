package services;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import services.apis.IHitRESTEndpoints;
import services.errors.ServerUnreachable;

/**
 * Created by sanattripathi on 9/9/15.
 */
public class Service {

    public static IHitRESTEndpoints getService(Client client, final String baseApiURL, ErrorHandler errorHandler) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseApiURL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Content-Type", "application/json");
                    }
                })
                .setErrorHandler(errorHandler);
        if(client == null) {
            builder.setClient(new DefaultClient());
        } else {
            builder.setClient(client);
        }
        return builder.build().create(IHitRESTEndpoints.class);
    }

    public static IHitRESTEndpoints getServiceWithNoInterceptor(Client client, final String baseURL, ErrorHandler errorHandler) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseURL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        System.out.println("RestAdapter log: "+message);
                    }
                });

        if(errorHandler == null) {
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public Throwable handleError(RetrofitError cause) {
                    return new ServerUnreachable();
                }
            });
        } else {
            builder.setErrorHandler(errorHandler);
        }

        if(client == null) {
            builder.setClient(new DefaultClient());
        } else {
            builder.setClient(client);
        }
        return builder.build().create(IHitRESTEndpoints.class);
    }
}
