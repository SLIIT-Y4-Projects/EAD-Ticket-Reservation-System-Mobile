package com.example.ead;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentApi {
    @GET("api/students")
    Call<List<Student>> getStudents();

    @DELETE("api/students/{id}")
    Call<Void> deleteStudent(@Path("id") String studentId);

    @POST("api/BackOfficeUser/login")
    Call<Void> login(@Body Student student);

    @POST("api/BackOfficeUser/register")
    Call<Void> register(@Body Student student);

    @PUT("api/students/{id}")
    Call<Void> updateStudent(@Path("id") String studentId, @Body Student student);



}
