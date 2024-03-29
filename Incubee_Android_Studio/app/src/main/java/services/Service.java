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
class Service {

    static IHitRESTEndpoints getService(Client client, final String baseApiURL, ErrorHandler errorHandler) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseApiURL)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Content-Type", "application/json");
                    }
                })
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        System.out.println(message);
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

    static IHitRESTEndpoints getServiceWithNoInterceptor(Client client, final String baseURL, ErrorHandler errorHandler) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseURL)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        System.out.println("RestAdapter log: " + message);
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

    static IHitRESTEndpoints getServiceWithNoInterceptorButHeader(Client client, final String baseURL, ErrorHandler errorHandler, final String authToken) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseURL)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("token", authToken);
                    }
                })
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        System.out.println("RestAdapter log: " + message);
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
