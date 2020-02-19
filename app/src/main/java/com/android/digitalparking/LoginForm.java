package com.android.digitalparking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.digitalparking.BLL.LoginBLL;
import com.android.digitalparking.Dashboard2.Home;
import com.android.digitalparking.Model.AuthUser;
import com.android.digitalparking.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginForm extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    Button loginbtn, registerbtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Retrofit retrofit;
    UserClient userClient;
    Boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        initialize();
    }
    private void initialize(){

        email=findViewById(R.id.email_Et);
        password=findViewById(R.id.pwd_et);
        loginbtn=findViewById(R.id.login_Btn);
        registerbtn=findViewById(R.id.register_Btn);

        loginbtn.setOnClickListener(this);

        registerbtn.setOnClickListener(this);


    }


    private  boolean validate()
    {
        if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("Enter Email ");
            email.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password.getText().toString())){
            password.setError("Enter password");
            password.requestFocus();
            return false;
        }

        return true;
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_Btn:
                if(validate()) {
                    final LoginBLL loginBL = new LoginBLL(email.getText().toString(),password.getText().toString());
                    StrictMode();
                    if(loginBL.checkUser())
                    {
                        Toast.makeText(LoginForm.this, "Welcome"+LoginBLL._first_name_1, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginForm.this,Home.class);
                        preferences = getSharedPreferences("Userinfo",Context.MODE_PRIVATE);
                        editor = preferences.edit();
                        isLoggedIn = true;
                        editor.putString("token",LoginBLL.token_1).commit();
                        editor.putString("id",LoginBLL._id_1).commit();
                        editor.putString("first_name",LoginBLL._first_name_1).commit();
                        editor.putString("last_name",LoginBLL._last_name_1).commit();
                        editor.putString("email",LoginBLL._email_1).commit();
                        editor.putString("password",LoginBLL._password_1).commit();

                        startActivity(intent);
                        finish();


                    }
                    else {
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.register_Btn:
                Intent intent = new Intent(LoginForm.this, RegisterForm.class);
                startActivity(intent);
                break;
        }

    }


}
