package com.android.digitalparking.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.digitalparking.Adapter.MyBookingAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {
    UserClient userClient;
    Retrofit retrofit;
    RecyclerView recyclerView;
    SharedPreferences preferences;


    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        recyclerView = view.findViewById(R.id.booking_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        getMyPurchase();
        return view;
    }

    public void getMyPurchase() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userClient = retrofit.create(UserClient.class);

        preferences = getActivity().getSharedPreferences("Userinfo", Context.MODE_PRIVATE);
        String useremail = preferences.getString("email", "");


        Call<List<Booking>> bookingcall = userClient.getMyBooking(useremail);
        bookingcall.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Error" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                } else if (response.body() != null) {
                    List<Booking> mybookinglist = response.body();
                    recyclerView.setAdapter(new MyBookingAdapter(mybookinglist, getActivity().getApplicationContext()));
                    Toast.makeText(getActivity(), "Booking Details", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Something is wrong ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}



