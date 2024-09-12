package com.data_center_watchman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OauthToken {
    @SerializedName("access_token")
    @Expose
    private String token;

    public OauthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
