package com.data_center_watchman.model;

public class Authenticate {
    private String apiKey;
    private String secret;

    public Authenticate(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
