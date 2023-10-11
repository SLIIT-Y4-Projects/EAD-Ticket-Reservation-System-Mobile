package com.example.ead;

import com.google.gson.annotations.SerializedName;


    public class StudentResponse {
        @SerializedName("message")
        private String message;

        public String getMessage() {
            return message;
        }
}
