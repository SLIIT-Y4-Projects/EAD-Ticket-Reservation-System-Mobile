package com.example.ead;

import com.google.gson.annotations.SerializedName;

public class ReservationResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
