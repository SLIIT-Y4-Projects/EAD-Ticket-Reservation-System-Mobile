package com.example.ead;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

public interface StudentApi {
    @GET("api/students")
    Call<List<Student>> getStudents();

    @DELETE("api/students/{id}")
    Call<Void> deleteStudent(@retrofit2.http.Path("id") String studentId);


    @POST("api/BackOfficeUser/login")
    Call<Void> login(@Body Student student
    );

    @POST("api/BackOfficeUser/register") // Use the correct endpoint
    Call<Void> register(@Body Student student);

}
