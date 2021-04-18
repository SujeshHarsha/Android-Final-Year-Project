package com.example.myfirst.assignment.bll;

import com.example.myfirst.assignment.api.HotelAPI;
import com.example.myfirst.assignment.models.CreateUser;
import com.example.myfirst.assignment.responses.LoginResponses;
import com.example.myfirst.assignment.url.BaseUrl;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.myfirst.assignment.url.BaseUrl.Token;

public class LoginBll {

        boolean isSuccess = false;

    public boolean checkUser(String user, String pass) {
           String Name="",Address="",Email="",Cpass="",Gender="";
           CreateUser rg =new CreateUser(Name,user,Address,Email,pass,Cpass,Gender,"Profile");
           HotelAPI hotelAPI= BaseUrl.getInstance().create(HotelAPI.class);
            Call<LoginResponses> usersCall = hotelAPI.login(rg);
            try {
                Response<LoginResponses> loginResponse = usersCall.execute();
                if (loginResponse.isSuccessful() &&
                         loginResponse.body().getStatus().equals("Success")) {
                         Token = loginResponse.body().getUsertoken();
                         isSuccess = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return isSuccess;
        }
    }

