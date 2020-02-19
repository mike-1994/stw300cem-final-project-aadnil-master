package com.android.digitalparking.Dashboard2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.digitalparking.About_Us;
//import com.android.digitalparking.BookingActivity;
import com.android.digitalparking.BookingActivity;
import com.android.digitalparking.FeedbackActivity;
import com.android.digitalparking.LoginForm;
import com.android.digitalparking.Model.Booking;
import com.android.digitalparking.MyBooking;
import com.android.digitalparking.NotificationBroadCast.NotificationBroadCastReceiver;
import com.android.digitalparking.Profile;
import com.android.digitalparking.R;
import com.android.digitalparking.createchannel.CreateChannel;

public class Home extends AppCompatActivity implements MenuListener{
//for sliding panel
    SlidingPaneLayout paneLayout;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    boolean isLoggedIn;
    //for notification
    NotificationBroadCastReceiver notificationBroadCastReceiver= new NotificationBroadCastReceiver(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        paneLayout = findViewById(R.id.sp);
        paneLayout.setPanelSlideListener(new Panelistner());

        // channel for notfication
        CreateChannel channel = new CreateChannel(this);
        channel.createChannel();
    }
    @Override
    protected void onStart() {
        super.onStart();
       IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
       registerReceiver(notificationBroadCastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(notificationBroadCastReceiver);
    }


   //toast message
    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

      //for Click
    @Override
    public void onMenuclickListener(int position) {
        Log.d("Home", "::: LEFT PANE MENU ITEM CLICKED POSITION ::: " + position);
        this.showToast("CLICKED POSITION = " + position);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch(position){
            case 0:
                Intent intent = new Intent(Home.this, BookingActivity.class);
                startActivity(intent);
                //do smthing like intent to open specifiec activity.
                //no. of case equal to number of munu item on left panel/sidenav

            break;
                case 1:{
              Intent intent1 = new Intent(Home.this,MyBooking.class);
              startActivity(intent1);
                   // transaction.replace(R.id.fra)
            }
            break;
                 case 2:
                    /* Intent intent3 = new Intent(Home.this, ParkingDetails.class);
               startActivity(intent3);*/

            break;
            case 3:

             Intent intent4 = new Intent(Home.this, FeedbackActivity.class);
             startActivity(intent4);


            break;
                case 4:
                    Intent intent5 = new Intent(Home.this, Profile.class);
                    startActivity(intent5);


            break;
                case  5:
                Intent intent6 = new Intent(Home.this, About_Us.class);
                startActivity(intent6);

            break;
            case 6:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Parenting")
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Home.this, LoginForm.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                                preferences = getSharedPreferences("Userinfo", Context.MODE_PRIVATE);
                                editor = preferences.edit();
                                isLoggedIn = false;
                                editor.putBoolean("isLoggedIn", isLoggedIn).commit();
                                startActivity(intent);
                                finish();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                  builder.show();


        }

    }


    public class Panelistner implements SlidingPaneLayout.PanelSlideListener {
        @Override
        public void onPanelSlide(@NonNull View view, float v) {
           // System.out.println("Panel side");
            getSupportFragmentManager().findFragmentById(R.id.leftpane).setHasOptionsMenu(false);
            getSupportFragmentManager().findFragmentById(R.id.rightpane).setHasOptionsMenu(true);
        }

        @Override
        public void onPanelOpened(@NonNull View view) {
           // System.out.println("Panel opened");
            getSupportFragmentManager().findFragmentById(R.id.leftpane).setHasOptionsMenu(true);
            getSupportFragmentManager().findFragmentById(R.id.rightpane).setHasOptionsMenu(false);

        }

        @Override
        public void onPanelClosed(@NonNull View view) {
           // System.out.println("Panel closed");

        }
}}
