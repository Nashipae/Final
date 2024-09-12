package com.data_center_watchman.model;

import com.data_center_watchman.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitorService {
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
