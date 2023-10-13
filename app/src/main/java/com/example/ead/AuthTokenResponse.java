package com.example.ead;

import com.google.gson.annotations.SerializedName;

public class AuthTokenResponse {
    @SerializedName("token")
    private String token;

    private String id;

    public AuthTokenResponse(String token, String id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }
}
