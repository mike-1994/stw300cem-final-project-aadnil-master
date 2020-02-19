package com.android.digitalparking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.digitalparking.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterForm extends AppCompatActivity implements View.OnClickListener {
    EditText email, firstname, lastname, password, repassword;
    Button registerbtn;
    Retrofit retrofit;
    UserClient userClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        email = findViewById(R.id.email_Et);

        firstname = findViewById(R.id.reg_fname);
        lastname = findViewById(R.id.reg_lname);
        password = findViewById(R.id.pwd_et);
        repassword = findViewById(R.id.reg_repwd);
        registerbtn = findViewById(R.id.register_Btn);
        registerbtn.setOnClickListener(this);

    }

    private void createInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userClient = retrofit.create(UserClient.class);

    }

    private void addUser() { createInstance();
        Call<Void> addCall = userClient.createUser(
                new User(
                        firstname.getText().toString(),
                        email.getText().toString(),
                        lastname.getText().toString(),
                        password.getText().toString(),
                        repassword.getText().toString()

                )
        );
        addCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(RegisterForm.this, "RegisterSuccessfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterForm.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });


    }
    private  boolean validate()
    {


        if(TextUtils.isEmpty(firstname.getText().toString())){
            firstname.setError("Enter your first name");
            firstname.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(lastname.getText().toString())){
            lastname.setError("Enter your last name ");
            lastname.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(email.getText().toString())){
            email.setError("Enter your email");
            email.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())){
            password.setError("Enter your password");
            password.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(repassword.getText().toString())){
            repassword.setError("Enter your Re-password");
            repassword.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
switch (view.getId())
{

    case R.id.register_Btn:
        if(validate())
        {
            addUser();
        }
}
    }
}
