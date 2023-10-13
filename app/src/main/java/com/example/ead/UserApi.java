package com.example.ead;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @GET("api/students")
    Call<List<Student>> getStudents();

    @GET("api/Reservation")
    Call<List<Reservation>> getReservation();
    @DELETE("api/students/{id}")
    Call<Void> deleteStudent(@Path("id") String studentId);


    @POST("api/TravellerUser/login")
    Call<AuthTokenResponse> login(@Body Traveller traveller);

    @POST("api/TravellerUser/register")
    Call<Void> register(@Body Traveller traveller);

    @PUT("api/students/{id}")
    Call<Void> updateStudent(@Path("id") String studentId, @Body Student student);



}
