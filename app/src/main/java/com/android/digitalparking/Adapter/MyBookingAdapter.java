package com.android.digitalparking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.digitalparking.Dashboard2.Home;
import com.android.digitalparking.Model.Booking;
import com.android.digitalparking.R;
import com.android.digitalparking.URL.URL;
import com.android.digitalparking.UserClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyBookingHolder> {

    public List<Booking> bookingList;
    Context context;
    String pid;
    Retrofit retrofit;
    UserClient userClient;

    public MyBookingAdapter(List<Booking> purchaseList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyBookingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sample_booking_view,viewGroup,false);
        return new MyBookingHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingHolder myBookingHolder, int i) {
        final  Booking booking = bookingList.get(i);
        pid=booking.get_id();
        myBookingHolder.sample_userid.setText(booking.getUserid());
        myBookingHolder.sample_firstname.setText(booking.getFirst_name());
        myBookingHolder.sample_lastname.setText(booking.getLast_name());
        myBookingHolder.sample_email.setText("email:"+booking.getEmail());
        myBookingHolder.sample_starttime.setText("Start Time :"+booking.getStart_Time() );
        myBookingHolder.sample_endtime.setText("Email: "+booking.getEnd_Time());
        myBookingHolder.sample_vehicle_no.setText("Vehicle Number: "+booking.getVehicle_Number());
        myBookingHolder.sample_vehicle_type.setText("Vehicle Type: "+booking.getVehicle_Type());
        myBookingHolder.sample_date.setText("Date: "+booking.getDate());

        myBookingHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBooking();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }


    public class MyBookingHolder extends RecyclerView.ViewHolder{


        public TextView sample_userid, sample_firstname, sample_lastname, sample_email,sample_starttime,sample_endtime,sample_vehicle_no,sample_vehicle_type,sample_date;
        public Button btn_Update, btn_cancel;


        public MyBookingHolder(@NonNull View itemView) {
            super(itemView);
            sample_userid = itemView.findViewById(R.id.sample_b_id);
            sample_firstname = itemView.findViewById(R.id.sample_b_f_name);
            sample_lastname = itemView.findViewById(R.id.sample_b_l_name);
            sample_email = itemView.findViewById(R.id.sample_b_email);
            sample_starttime = itemView.findViewById(R.id.sample_start_time);
            sample_endtime = itemView.findViewById(R.id.sample_end_time);
            sample_vehicle_no = itemView.findViewById(R.id.sample_v_number);
            sample_vehicle_type = itemView.findViewById(R.id.sample_v_type);
            sample_date = itemView.findViewById(R.id.sample_date);


            // btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btn_cancel = itemView.findViewById(R.id.sample_cancel_btn);
        }
    }

    public void deleteBooking(){
        retrofit=new Retrofit.Builder()
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userClient = retrofit.create(UserClient.class);

        Call<Void> deleteCall=userClient.deleteBooking(pid);
        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, "Error:"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context, Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

}
