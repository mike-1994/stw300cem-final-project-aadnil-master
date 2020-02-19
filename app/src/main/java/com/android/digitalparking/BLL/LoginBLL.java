package com.android.digitalparking.BLL;

import com.android.digitalparking.Model.AuthUser;
import com.android.digitalparking.Model.User;
import com.android.digitalparking.UserClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginBLL {
    Retrofit retrofit;
    UserClient userclient;

    private  String email;
    private String password;

    boolean isSuccess = false;
    public static String _id_1,_first_name_1,_last_name_1,_email_1,_password_1,token_1,message_1;

    public LoginBLL(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void createInstance()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.111:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userclient = retrofit.create(UserClient.class);
    }
    public boolean checkUser()
    {
        createInstance();
        User user = new User("","",email,password,"");
        Call<AuthUser> userCall = userclient.validateUser(user);

        try {
            Response<AuthUser> authUserResponse = userCall.execute();
            if(authUserResponse.body().getToken()!=null ){
                isSuccess = true;
                _id_1 = authUserResponse.body().getId();
                _first_name_1 = authUserResponse.body().getFirst_name();
                _last_name_1 = authUserResponse.body().getLast_name();
                _email_1 = authUserResponse.body().getEmail();
                _password_1 = authUserResponse.body().getPassword();



            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return isSuccess;

    }
}




