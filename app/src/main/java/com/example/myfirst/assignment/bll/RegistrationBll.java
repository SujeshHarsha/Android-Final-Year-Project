package com.example.myfirst.assignment.bll;

import com.example.myfirst.assignment.api.HotelAPI;
import com.example.myfirst.assignment.models.CreateUser;
import com.example.myfirst.assignment.responses.SignUpResponse;
import com.example.myfirst.assignment.ui.registration.RegistrationActivity;
import com.example.myfirst.assignment.url.BaseUrl;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;


public class RegistrationBll {
    boolean isSuccess = false;

    public boolean registerUser(String Name, String Phone, String Address, String Email, String Pass, String Cpass, String Gender, String Profile) {

        CreateUser rg = new CreateUser(Name, Phone, Address, Email, Pass, Cpass, Gender, Profile);
        HotelAPI hotelAPI = BaseUrl.getInstance().create(HotelAPI.class);
        Call<SignUpResponse> usersCall = hotelAPI.registerUser(rg);
        try {
            Response<SignUpResponse> signUpResponseResponse = usersCall.execute();
            if (signUpResponseResponse.isSuccessful() &&
                    signUpResponseResponse.body().getStatus().equals("success")) {
                BaseUrl.Status=signUpResponseResponse.body().getStatus();
                isSuccess = true;
            }
            else if (signUpResponseResponse.isSuccessful() &&
                    signUpResponseResponse.body().getStatus().equals("error")) {
                RegistrationActivity.message = "Phone Number Already Exist, Please enter new one";
                BaseUrl.Status=signUpResponseResponse.body().getStatus();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}

