package com.example.myfirst.assignment.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.example.myfirst.assignment.DashboardActivity;
import com.example.myfirst.assignment.R;
import com.example.myfirst.assignment.bll.BookingBill;
import com.example.myfirst.assignment.strictMode.StrictModeClass;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.myfirst.assignment.url.BaseUrl.Token;

public class RoomActivity extends AppCompatActivity {
    CircleImageView hotelImage;
    Button book, back;
    TextView date;
    TextView address, phone, bedNo, desc, hotelName;
    String path = "http://10.0.2.2:3012/profile/";
    DatePickerDialog picker;
    public  static String bookdate=null;
    public  static String id=null;
    public static  boolean res =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        hotelImage = findViewById(R.id.roomImage);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        bedNo = findViewById(R.id.bedNo);
        desc = findViewById(R.id.desc);
        hotelName = findViewById(R.id.hotelName);
        book = findViewById(R.id.book);
        back = findViewById(R.id.back);
        date = findViewById(R.id.date);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(RoomActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();

            }
        });
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Picasso.get().load(path + bundle.getString("Image")).resize(120, 60).into(hotelImage);
            address.setText(bundle.getString("Address"));
            phone.setText(bundle.getString("Phone"));
            bedNo.setText(bundle.getString("BedNo"));
            desc.setText(bundle.getString("Desc"));
            hotelName.setText(bundle.getString("hotelName"));
            id = bundle.getString("Id");

            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookdate=date.getText().toString();
                    Booked();
           }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

    }
    private void Booked() {

        if (TextUtils.isEmpty(bookdate)) {
            date.setError("Please enter Date");
            return;
        }
        BookingBill bookingBill = new BookingBill();
        StrictModeClass.StrictMode();
        res=bookingBill.getRoomBooked(Token,bookdate,id);
        Toast.makeText(RoomActivity.this, ""+res, Toast.LENGTH_SHORT).show();
        if (res){
            Toast.makeText(RoomActivity.this, "Room is booked", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(RoomActivity.this, "Room cannot be booked", Toast.LENGTH_SHORT).show();

        }
    }

}
