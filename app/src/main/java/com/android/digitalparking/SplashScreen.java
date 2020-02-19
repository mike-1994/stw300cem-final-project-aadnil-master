package com.android.digitalparking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences preferences;
    Boolean checkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        preferences = getSharedPreferences("Userinfo", Context.MODE_PRIVATE);
        checkLogin = preferences.getBoolean("isLoggedIn",false);
        if(checkLogin==true)
        {
            final Intent intent = new Intent(SplashScreen.this, Dashboard.class);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            },3000);
        }

        else if(checkLogin== false)
        {
            final Intent intent = new Intent(SplashScreen.this, LoginForm.class);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            },3000);
        }


        }
    }

