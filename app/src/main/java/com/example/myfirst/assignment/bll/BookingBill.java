package com.example.myfirst.assignment.bll;

import com.example.myfirst.assignment.api.HotelAPI;
import com.example.myfirst.assignment.models.Booking;
import com.example.myfirst.assignment.responses.LoginResponses;
import com.example.myfirst.assignment.url.BaseUrl;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class BookingBill {
    boolean isSuccess = false;
    public boolean getRoomBooked(String Token,String date,String hotelid) {
        HotelAPI hotelAPI = BaseUrl.getInstance().create(HotelAPI.class);
        String userToken= "Bearer "+Token;
        Booking booking= new Booking(date,hotelid);
        Call<LoginResponses> usersCall = hotelAPI.getRoomBooked(userToken,booking);
        try {
            Response<LoginResponses> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("success")) {
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}

