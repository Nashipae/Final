package com.data_center_watchman.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;
//    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    public static final int CONNECTION_TIMEOUT = 60000;
    public static final int FETCH_TIMEOUT = 60000;
    public static final int INPUT_TIMEOUT = 60000;

    public static Retrofit getClient(String baseUrl, OkHttpClient client) {

        Gson gson = new GsonBuilder().setLenient().create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofit;
    }
    public static OkHttpClient getUnsafeOkHttpClient(String CONSUMER_KEY, String CONSUMER_SECRET) {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(INPUT_TIMEOUT, TimeUnit.SECONDS)
//                    .sslSocketFactory(new TLSSocketFactory())
                    .readTimeout(FETCH_TIMEOUT, TimeUnit.SECONDS)
//                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new AccessTokenInterceptor(CONSUMER_KEY, CONSUMER_SECRET))
                    .sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static OkHttpClient getUnsafeCRQStatusOkHttpClient(String token) {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(INPUT_TIMEOUT, TimeUnit.SECONDS)
//                    .sslSocketFactory(new TLSSocketFactory())
                    .readTimeout(FETCH_TIMEOUT, TimeUnit.SECONDS)
//                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new AuthInterceptor(token))
                    .sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
