package com.example.myfirst.assignment.bll;

import com.example.myfirst.assignment.api.HotelAPI;
import com.example.myfirst.assignment.responses.LoginResponses;
import com.example.myfirst.assignment.url.BaseUrl;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class UserDeleteBll {
    boolean isSuccess = false;

    public boolean delete() {
        HotelAPI hotelAPI = BaseUrl.getInstance().create(HotelAPI.class);
        String userToken= "Bearer "+BaseUrl.Token;
        Call<LoginResponses> usersCall = hotelAPI.getUserDelete(userToken);
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

