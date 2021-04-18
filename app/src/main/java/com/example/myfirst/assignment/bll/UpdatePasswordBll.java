package com.example.myfirst.assignment.bll;

import com.example.myfirst.assignment.api.HotelAPI;
import com.example.myfirst.assignment.models.CreateUser;
import com.example.myfirst.assignment.responses.LoginResponses;
import com.example.myfirst.assignment.url.BaseUrl;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class UpdatePasswordBll {
    boolean isSuccess = false;

    public boolean updatePassword(String Token,String Password) {
        HotelAPI hotelAPI = BaseUrl.getInstance().create(HotelAPI.class);
        String userToken= "Bearer "+Token;
        String Name="",Address="",Email="",Cpass="",Gender="",user="",Profile="";
        CreateUser rg =new CreateUser(Name,user,Address,Email,Password,Cpass,Gender,Profile);
        Call<LoginResponses> usersCall = hotelAPI.updatePassword(userToken,rg);
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

