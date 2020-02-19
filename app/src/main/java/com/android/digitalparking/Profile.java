package com.android.digitalparking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.digitalparking.Dashboard2.Home;
import com.android.digitalparking.Model.User;
import com.android.digitalparking.Model.UserImg;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity implements View.OnClickListener{
    EditText first_name , email, last_name, password, imagename;

    ImageView image;
    Button btn_ImageUpload, btn_BrowseImage, btn_Update;
    Retrofit retrofit;
    UserClient userClient;
    Uri uri;
    Bitmap bitmap;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String userImageName;

    static final int REQUEST_IMAGE_CAPTURE =1;
    static final int PICK_IMAGE =2;
    String userimagename2;

    //For Sensor
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDevice mShakeDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //Shake Sensor
        mSensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDevice=new ShakeDevice();
        mShakeDevice.setOnShakeListner(new ShakeDevice.OnShakeListener() {
            @Override
            public void onshake(int count) {
                Intent intent = new Intent(Profile.this,Home.class);
                startActivity(intent);
                mSensorManager.unregisterListener(mShakeDevice);
            }
        });

        first_name = findViewById(R.id.u_fname);
        email = findViewById(R.id.u_email);
        last_name = findViewById(R.id.u_lname);
        password = findViewById(R.id.u_password);
        image = findViewById(R.id.image);

        btn_ImageUpload = findViewById(R.id.btn_ImageUpload);
        btn_ImageUpload.setOnClickListener(this);

        btn_Update = findViewById(R.id.btn_Update);
        btn_Update.setOnClickListener(this);

        btn_BrowseImage = findViewById(R.id.btn_BrowseImage);
        btn_BrowseImage.setOnClickListener(this);


        preferences = getApplicationContext().getSharedPreferences("Userinfo", Context.MODE_PRIVATE);

        String userid  = preferences.getString("id","");
        String fname = preferences.getString("first_name","");
        String last_names = preferences.getString("last_name","");
        String emails = preferences.getString("email","");
        String passwords = preferences.getString("password","");
        String userimagename = preferences.getString("imagename","");


        String url ="http://10.0.2.2:3000/uploads/"+userimagename;
        Picasso.get().load(url).into(image);

        first_name.setText(fname);
        email.setText(emails);
        last_name.setText(last_names);
        password.setText(passwords);

    }
    private void opengallery()
    {

    }

    private void opencamera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) !=null)
        {
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);

        }
    }

    private void createInstance()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userClient = retrofit.create(UserClient.class);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode)
        {

            case REQUEST_IMAGE_CAPTURE:
                if(resultCode==RESULT_OK) {
                    Bundle extras = data.getExtras();
                    if (extras == null) {
                        return;

                    }
                    bitmap = (Bitmap) extras.get("data");
                    image.setImageBitmap(bitmap);
                }
                break;


            case PICK_IMAGE:
                if(resultCode==RESULT_OK) {
                    if (data == null) {
                        Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
                    }
                    uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        image.setImageBitmap(bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void addImage(Bitmap bm)
    {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytes =stream.toByteArray();
        try{
            File file = new File(this.getCacheDir(),"image.jpeg");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();

            RequestBody rb = RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",file.getName(),rb);
            createInstance();
            Call<UserImg> imgCall = userClient.uploadImage(body);
            StrictMode();
            try {
                Response<UserImg> imageModelResponse = imgCall.execute();
                userImageName = imageModelResponse.body().getImage();
                //imagename.setText(userImageName);

            }
            catch (IOException e)
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void  updateUser()
    {
        addImage(bitmap);
        String uid =  preferences.getString("id","");
        String ufirst_name =first_name.getText().toString();
        String  ulast_name = last_name.getText().toString();
        String uemail = email.getText().toString();
        String  upassword = password.getText().toString();

        createInstance();

        final User user = new User(ufirst_name,uemail,ulast_name,upassword,userImageName);
        Call<Void> voidCall = userClient.updateUser(uid,user);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(Profile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                preferences = getApplicationContext().getSharedPreferences("Userinfo",Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("first_name",user.getFirst_name()).commit();
                editor.putString("last_name",user.getLast_name()).commit();
                editor.putString("email",user.getEmail()).commit();
                editor.putString("password",user.getPassword()).commit();
                editor.putString("imagename",user.getImage()).commit();


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Profile.this, "Update Failed"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent myintent = new Intent(getApplicationContext(), Home.class);
        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myintent,0);
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_BrowseImage:
                Intent gallery =   new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"Choose image"),PICK_IMAGE);
                break;

            case R.id.btn_ImageUpload:
                opencamera();
                break;

            case R.id.btn_Update:
                if (bitmap!=null){
                    updateUser();
                    Intent intent = new Intent(Profile.this,Home.class);
                    finish();
                    startActivity(intent);

                }
                else{
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    // for Sensor
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDevice,mAccelerometer,SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDevice);
        super.onPause();


    }
    //End of Sensor
    }

