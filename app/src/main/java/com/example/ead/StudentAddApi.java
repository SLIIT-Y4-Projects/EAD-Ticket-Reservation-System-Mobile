package com.example.ead;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StudentAddApi {

    @POST("/api/students")
    Call<StudentResponse> addStudent(@Body Student student);

}