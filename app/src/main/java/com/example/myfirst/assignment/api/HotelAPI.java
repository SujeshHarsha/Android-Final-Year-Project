package com.example.myfirst.assignment.api;

import com.example.myfirst.assignment.models.Booking;
import com.example.myfirst.assignment.models.CreateUser;
import com.example.myfirst.assignment.responses.ImageResponse;
import com.example.myfirst.assignment.responses.JSONResponse;
import com.example.myfirst.assignment.responses.LoginResponses;
import com.example.myfirst.assignment.responses.SignUpResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface HotelAPI {


    //Create user
    @POST("users/signup")
    Call<SignUpResponse> registerUser(@Body CreateUser usr);

    //login user
    @POST("users/signin")
    Call<LoginResponses>login(@Body CreateUser user);

    @Multipart
    @POST("users/profile")
    Call<ImageResponse>uploadImage(@Part MultipartBody.Part image);

    @GET("users")
    Call<CreateUser> getUserDetails(@Header("Authorization")String token);

    @PUT("users/")
    Call<LoginResponses> updatePassword(@Header("Authorization") String token, @Body CreateUser user);

    @GET("hotel/rooms")
    Call<JSONResponse> getHotelRooms();

    @POST("hotel/rooms/book")
    Call<LoginResponses> getRoomBooked(@Header("Authorization")String token,@Body Booking booking);

    @DELETE("users/")
    Call<LoginResponses> getUserDelete(@Header("Authorization")String token);

    @PUT("users/forget")
    Call<LoginResponses>forgetPassword(@Body CreateUser user);


}
