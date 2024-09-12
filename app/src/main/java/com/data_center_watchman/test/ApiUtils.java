package com.data_center_watchman.test;

import com.data_center_watchman.Constants;
import com.data_center_watchman.adapter.VisitorAdapter;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

import static com.data_center_watchman.test.NetworkClient.CONNECTION_TIMEOUT;
import static com.data_center_watchman.test.NetworkClient.FETCH_TIMEOUT;
import static com.data_center_watchman.test.NetworkClient.INPUT_TIMEOUT;

public class ApiUtils {

    public static VisitorAdapter getAuthenticationAPI(String CONSUMER_KEY, String CONSUMER_SECRET, String BASE_URL) {


        //Get unsafe
        OkHttpClient client=NetworkClient.getUnsafeOkHttpClient(CONSUMER_KEY,CONSUMER_SECRET);
        return NetworkClient.getClient(BASE_URL,client).create(VisitorAdapter.class);
    }

    public static VisitorAdapter getCRQStatus(String token) {


        //Get unsafe
        OkHttpClient client=NetworkClient.getUnsafeCRQStatusOkHttpClient(token);
        return NetworkClient.getClient(Constants.REMEDY_URL,client).create(VisitorAdapter.class);
    }

}
