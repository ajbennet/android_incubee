package services;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;

/**
 * Created by sanattripathi on 9/9/15.
 */
public class DefaultClient extends UrlConnectionClient{

    private int mConnectTimeoutMillis = 30 * 1000;
    private int mReadTimeoutMillis = 30 * 1000;

    public DefaultClient() {
        super();
    }

    public DefaultClient(int connectTimeoutMillis, int readTimeoutMillis) {
        mConnectTimeoutMillis = connectTimeoutMillis;
        mReadTimeoutMillis = readTimeoutMillis;
    }

    @Override
    protected HttpURLConnection openConnection(Request request) throws IOException {
        HttpURLConnection connection = super.openConnection(request);
        connection.setConnectTimeout(mConnectTimeoutMillis);
        connection.setReadTimeout(mReadTimeoutMillis);
        return connection;
    }
}
