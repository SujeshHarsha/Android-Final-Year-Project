package com.example.myfirst.assignment.ui.registration;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import com.example.myfirst.assignment.LoginActivity;
import com.example.myfirst.assignment.R;
import com.example.myfirst.assignment.api.HotelAPI;
import com.example.myfirst.assignment.bll.RegistrationBll;
import com.example.myfirst.assignment.responses.ImageResponse;
import com.example.myfirst.assignment.strictMode.StrictModeClass;
import com.example.myfirst.assignment.url.BaseUrl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    CircleImageView profileImage;
    EditText uName,uPhone,uAddress,uEmail,uPass,uCPass;
    RadioButton uMale,uFemale,uOthers;
    Button register;
    TextView login;
    String Name,Phone,Address,Email,Cpass,Pass,Gender;
    String Profile;
    Uri ImageUri;
    public static String message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Gyro();
        Proximity();
        uName=findViewById(R.id.uname);
        uPhone=findViewById(R.id.number);
        uAddress=findViewById(R.id.address);
        uEmail=findViewById(R.id.email);
        uPass=findViewById(R.id.upassword);
        uCPass=findViewById(R.id.cpassword);

        uMale=findViewById(R.id.male);
        uFemale=findViewById(R.id.female);
        uOthers=findViewById(R.id.others);

        login=findViewById(R.id.ulogin);
        register=findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(uMale.isChecked()){
                        Gender="Male";
                    }else if(uFemale.isChecked()){
                        Gender="Female";
                    }else if(uOthers.isChecked()){
                        Gender="Others";
                    }
                    Name=uName.getText().toString().trim();
                    Phone=uPhone.getText().toString().trim();
                    Address=uAddress.getText().toString().trim();
                    Email=uEmail.getText().toString().trim();
                    Cpass=uCPass.getText().toString().trim();
                    Pass=uPass.getText().toString().trim();
                    if (TextUtils.isEmpty(Name)) {
                             uName.setError("Enter Username");
                        return;
                    }
                    else if (TextUtils.isEmpty(Phone)) {
                        uPhone.setError("Enter Phone Number");
                        return;
                    }
                   else if (TextUtils.isEmpty(Address)) {
                        uAddress.setError("Enter Address");
                        return;
                    }
                   else if (TextUtils.isEmpty(Email)) {
                        uEmail.setError("Enter Email");
                        return;
                    }
                   else if (TextUtils.isEmpty(Cpass)) {
                        uCPass.setError("Confirm Password ");
                        return;
                    }
                   else if (TextUtils.isEmpty(Pass)) {
                        uPass.setError("Enter Password");
                        return;
                    }
                   else if(!Pass.equals(Cpass)){
                        uCPass.setError("Password Won't match");
                        return;
                    }


                saveImageOnly();
                RegisterUser();


            }
        });


        profileImage=findViewById(R.id.profileImage);


        //check for permission
        checkPermission();


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu=new PopupMenu(RegistrationActivity.this,profileImage);
                popupMenu.getMenuInflater().inflate(R.menu.popup,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                     if(item.getTitle().toString().equals("Open Camera")){
                         loadCamera();
                     }
                        if(item.getTitle().toString().equals("Open Gallary")){
                            loadGallary();
                        }


                        return true;
                    }
                });
                popupMenu.show();


            }
        });


    }

    private void RegisterUser() {

        RegistrationBll registrationBll = new RegistrationBll();
        StrictModeClass.StrictMode();
        boolean res=registrationBll.registerUser(Name,Phone,Address,Email,Pass,Cpass,Gender,Profile);
        Toast.makeText(this, ""+res, Toast.LENGTH_SHORT).show();
        if (registrationBll.registerUser(Name,Phone,Address,Email,Pass,Cpass,Gender,Profile)) {
            Toast.makeText(RegistrationActivity.this, "You have sucessfully registered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            if (message.equals("")) {
                Toast.makeText(this, "Internal server Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
            }

        }


//        CreateUser user =new CreateUser(Name,Phone,Address,Email,Pass,Cpass,Gender,Profile);
//        HotelAPI hotelAPI= BaseUrl.getInstance().create(HotelAPI.class);
//        retrofit2.Call<Void> voidCall=hotelAPI.registerUser(user);
//        // Toast.makeText(RegistrationActivity.this, ""+profileImage, Toast.LENGTH_LONG).show();
//        voidCall.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                Toast.makeText(RegistrationActivity.this, "You have sucessfully registered", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(RegistrationActivity.this, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });





    }

    private void loadGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }



    private  void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            Manifest.permission.CAMERA
                    },0);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },0);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0 && resultCode==RESULT_OK){
            Bundle extras= data.getExtras();
            Bitmap imageBitmap=(Bitmap)extras.get("data");
            profileImage.setImageBitmap(imageBitmap);
            ImageUri= getImageUri();

        }
        if(requestCode==1 && resultCode==RESULT_OK) {

            if (data == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_LONG).show();
            }
            Uri uri = data.getData();
            profileImage.setImageURI(uri);
            ImageUri=data.getData();

        }
    }

    private Uri getImageUri() {
        Uri m_imgUri = null;
        File m_file;
        try {
            SimpleDateFormat m_sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String m_curentDateandTime = m_sdf.format(new Date());
            String m_imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + m_curentDateandTime + ".jpg";
            m_file = new File(m_imagePath);
            m_imgUri = Uri.fromFile(m_file);
        } catch (Exception p_e) {
        }
        return m_imgUri;
    }

    private  void loadCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,0);
        }

    }

    private  void saveImageOnly(){
        String image = getRealPathFromUri(ImageUri);
        File file = new File(image);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("profileImage", file.getName(), requestBody);
        BaseUrl baseUrl = new BaseUrl();

        Call<ImageResponse> call = baseUrl.getInstance().create(HotelAPI.class).uploadImage(body);
        StrictModeClass.StrictMode();
        try {
            Response<ImageResponse> imageModelResponse = call.execute();
            Profile=imageModelResponse.body().getFilename();
            Toast.makeText(this, ""+imageModelResponse.body().getFilename(), Toast.LENGTH_SHORT).show();

        } catch (IOException e) {

            e.printStackTrace();
            Toast.makeText(this, ""+ e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromUri(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_ind = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_ind);
        cursor.close();
        return result;
    }
    public void Gyro() {

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        SensorEventListener gyroEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[2] > 0.5f) {        // anticlockwise
                    Log.d("gyro", "tilted left");
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegistrationActivity.this, "tilted left", Toast.LENGTH_SHORT).show();
                } else if (event.values[2] < -0.5f) {     // clockwise
                    Toast.makeText(RegistrationActivity.this, "right tilted", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
//    register listener
        sensorManager.registerListener(gyroEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }


    public void Proximity() {

        SensorManager proximitySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor proximoty = proximitySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        SensorEventListener proximityEventListener = new SensorEventListener() {



            @Override
            public void onSensorChanged(SensorEvent event) {
                float distance = event.values[0];
                if(distance<=2){
                 //   Toast.makeText(RegistrationActivity.this, "Please Keep The Device Far From You", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
//    register listener
        proximitySensorManager.registerListener(proximityEventListener, proximoty, SensorManager.SENSOR_DELAY_NORMAL);

    }

}

