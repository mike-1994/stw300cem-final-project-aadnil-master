package com.android.digitalparking;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.digitalparking.Model.Booking;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView firstname, lastname, bemail;
    EditText StartTime, EndTime, Vehicle_Number, Vehicle_Type, Date;
    Button btn_book;
   // Button btn_cancel;
    Retrofit retrofit;
    UserClient userClient;
    ActionBar actionBar;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        init();
    }


    private void init() {
        firstname = findViewById(R.id.b_f_name);
        lastname = findViewById(R.id.b_l_name);
        bemail = findViewById(R.id.b_email);
        StartTime = findViewById(R.id.start_time);
        EndTime = findViewById(R.id.end_time);
        Vehicle_Number = findViewById(R.id.v_number);
        Vehicle_Type = findViewById(R.id.v_type);
        Date = findViewById(R.id.date);
        btn_book=findViewById(R.id.book_btn);
       // btn_cancel=findViewById(R.id.cancel_btn);

        btn_book.setOnClickListener(this);
     // btn_cancel.setOnClickListener(this);


        preferences = getApplicationContext().getSharedPreferences("Userinfo", Context.MODE_PRIVATE);

        String userid  = preferences.getString("id","");
        String b_first_name = preferences.getString("first_name", "");
    String b_last_name = preferences.getString("last_name", "");
      String b_email = preferences.getString("email", "");


       firstname.setText(b_first_name);
        lastname.setText(b_last_name);
      bemail.setText(b_email);


    }


    private void createInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userClient = retrofit.create(UserClient.class);

    }

private void addBooking() {
        createInstance();
       Call<Void> voidCall = userClient.addBooking
              (new Booking
                       ("",Vehicle_Number.getText().toString(),Vehicle_Type.getText().toString(),StartTime.getText().toString(),EndTime.getText().toString(),
                               Date.getText().toString(),"",firstname.getText().toString(),lastname.getText().toString(),bemail.getText().toString()));
        voidCall.enqueue(new Callback<Void>() {
            @Override public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(BookingActivity.this, "THankyou for your booking", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BookingActivity.this, "Error while booking", Toast.LENGTH_SHORT).show();
            }
       });
    }

    private boolean validate() {


        if (TextUtils.isEmpty(firstname.getText().toString())) {
            firstname.setError("Enter First Name");
            firstname.requestFocus();
            return false;
       }
        if (TextUtils.isEmpty(lastname.getText().toString())) {
            lastname.setError("Enter last name ");
            lastname.requestFocus();
            return false;
      }
         if (TextUtils.isEmpty(bemail.getText().toString())) {
            bemail.setError("Enter your email");
          bemail.requestFocus();
           return false;
        }
        if (TextUtils.isEmpty(StartTime.getText().toString())) {
            StartTime.setError("Enter Your Start Time");
            StartTime.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(EndTime.getText().toString())) {
            EndTime.setError("Enter Your End Time");
            EndTime.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(Vehicle_Number.getText().toString())) {
            Vehicle_Number.setError("Enter Your Vehicle Number");
            Vehicle_Number.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(Vehicle_Type.getText().toString())) {
            Vehicle_Type.setError("Enter Your Vehicle Type");
            Vehicle_Type.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(Date.getText().toString())) {
            Date.setError("Enter Date");
            Date.requestFocus();
            return false;
        }

        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_btn:
                if (validate()) {
                    addBooking();
                }
                break;
        }

    }

}

