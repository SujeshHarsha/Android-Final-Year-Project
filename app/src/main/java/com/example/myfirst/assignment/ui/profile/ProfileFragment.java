package com.example.myfirst.assignment.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.example.myfirst.assignment.LoginActivity;
import com.example.myfirst.assignment.R;
import com.example.myfirst.assignment.api.HotelAPI;
import com.example.myfirst.assignment.bll.UpdatePasswordBll;
import com.example.myfirst.assignment.models.CreateUser;
import com.example.myfirst.assignment.strictMode.StrictModeClass;
import com.example.myfirst.assignment.url.BaseUrl;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.myfirst.assignment.url.BaseUrl.Token;

public class ProfileFragment extends Fragment {
    CircleImageView imgProfile;
    TextView tvName,tvPhone,tvEmail,tvGender,logout,delete;
    EditText etUPassword;
    Button btnUpdate;
    String url="http://10.0.2.2:3012/profile/";
    public static  boolean res =false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        imgProfile= root.findViewById(R.id.imgProfile);
        tvName= root.findViewById(R.id.tvName);
        tvPhone= root.findViewById(R.id.tvPhone);
        tvEmail= root.findViewById(R.id.tvEmail);
        tvGender= root.findViewById(R.id.tvGender);
        logout= root.findViewById(R.id.logout);
        etUPassword= root.findViewById(R.id.etUPassword);
        btnUpdate= root.findViewById(R.id.btnUpdate);
        delete=root.findViewById(R.id.delete);

        getUser();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Token=null;
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeleteActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              update();
            }
        });
        return root;
    }

    private void update() {
        String pass=etUPassword.getText().toString().trim();

        if (TextUtils.isEmpty(pass)) {
            etUPassword.setError("Please enter password");
            return;
        }
        UpdatePasswordBll updatePasswordBll = new UpdatePasswordBll();
        StrictModeClass.StrictMode();
         res=updatePasswordBll.updatePassword(Token, pass);
        if (updatePasswordBll.updatePassword(Token, pass)) {
            Toast.makeText(getActivity(), "Password Is Updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), "Password cannot be updated", Toast.LENGTH_SHORT).show();

        }
    }

    private void getUser() {
        StrictModeClass.StrictMode();
        HotelAPI hotelAPI= BaseUrl.getInstance().create(HotelAPI.class);
        String userToken= "Bearer "+Token;
        Call<CreateUser> usersCall = hotelAPI.getUserDetails(userToken);
        try {
            Response<CreateUser> createUserResponse = usersCall.execute();
            if (createUserResponse.isSuccessful() &&
                    !createUserResponse.body().getPhone().isEmpty()) {
                Picasso.get().load(url+createUserResponse.body().getProfileImage()).resize(400, 110).centerCrop().into(imgProfile);
                tvName.setText(createUserResponse.body().getUsername());
                tvPhone.setText(createUserResponse.body().getPhone());
                tvEmail.setText(createUserResponse.body().getEmail());
                tvGender.setText(createUserResponse.body().getGender());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}