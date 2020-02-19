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

import com.android.digitalparking.Model.Feedback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedbackActivity extends AppCompatActivity  implements View.OnClickListener {
    TextView f_fname, f_lname, f_email;
    EditText f_desc;
    Button btn_post,btn_cancel;
    Retrofit retrofit;
    UserClient userClient;
    ActionBar actionBar;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackactivity);
        init();
    }
    private void init() {
        f_fname = findViewById(R.id.f_fname);
        f_lname = findViewById(R.id.f_lname);
        f_email = findViewById(R.id.f_email);
        f_desc=findViewById(R.id.f_desc);
        btn_post = findViewById(R.id.post);
      ////  btn_cancel=findViewById(R.id.feedback_cancel);

        btn_post.setOnClickListener(this);
      //  btn_cancel.setOnClickListener(this);


        preferences = getApplicationContext().getSharedPreferences("Userinfo", Context.MODE_PRIVATE);

        // String userid  = preferences.getString("id","");
        String first_name = preferences.getString("first_name", "");
        String last_name = preferences.getString("last_name", "");
        String email = preferences.getString("email", "");
        String feeddesc = preferences.getString("feedback_desc", "");

        f_fname.setText(first_name);
        f_lname.setText(last_name);
        f_email.setText(email);
        f_desc.setText(feeddesc);


    }


    private void createInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userClient = retrofit.create(UserClient.class);

    }

    private void giveFeedback() {
        createInstance();
        Call<Void> voidCall = userClient.addFeedback(new Feedback("",f_fname.getText().toString(),f_lname.getText().toString(),f_email.getText().toString(),f_desc.getText().toString()));
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(FeedbackActivity.this, "THankyou for you feedback", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FeedbackActivity.this, "Error while sending feedback", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validate() {


        if (TextUtils.isEmpty(f_fname.getText().toString())) {
            f_fname.setError("Enter First Name");
            f_fname.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(f_lname.getText().toString())) {
            f_lname.setError("Enter last name ");
            f_lname.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(f_email.getText().toString())) {
            f_email.setError("Enter your email");
            f_email.requestFocus();
            return false;
        }



        if (TextUtils.isEmpty(f_desc.getText().toString())) {
            f_desc.setError("Enter Your FeedbackActivity");
            f_desc.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post:
                if (validate()) {
                    giveFeedback();
                }
                break;
        }

    }
}
