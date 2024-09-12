package com.data_center_watchman.model;

import android.util.Log;

import com.data_center_watchman.Constants;
import com.data_center_watchman.ui.ValidateCrq;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemedyService {
    private static Retrofit retrofit;
    public static Retrofit getRemedyService(){
        if(retrofit == null){

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor(){
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization", "Bearer "+ ValidateCrq.token)
                            .method(original.method(), original.body())
                            .build();
                    Log.d("SEMA: ", request.url().toString());
                    Log.d("SEMA: ", ValidateCrq.token);

                    return chain.proceed(request);
                }
            });
            OkHttpClient okHttpClient = httpClient.build();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.REMEDY_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
