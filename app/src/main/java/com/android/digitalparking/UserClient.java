package com.android.digitalparking;

import com.android.digitalparking.Model.AuthUser;
import com.android.digitalparking.Model.Booking;
import com.android.digitalparking.Model.Feedback;
import com.android.digitalparking.Model.RegisterModel;
import com.android.digitalparking.Model.User;
import com.android.digitalparking.Model.UserImg;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserClient {
    //to register user
@POST ("users/adduser")
    Call<Void> createUser (@Body User user);


@POST ("users/login")
    Call<AuthUser> validateUser(@Body User user);

    @Multipart
    @POST("users/addimages")
    Call<UserImg> uploadImage(@Part MultipartBody.Part Body);
@GET("Booking")
    Call<List<Booking>> getAllBooking();

@POST("bookings/addbooking")
Call<Void>addBooking(@Body Booking booking);

    @DELETE("bookings/deletebooking/{id}")
    Call<Void> deleteBooking(@Path("id") String id);
    // to view all the products

@GET("FeedbackActivity")
    Call<List<Feedback>> getAllFeedback();

    @GET("bookings/booking/{email}")
    Call<List<Booking>> getMyBooking(@Path("email") String email);


    @PUT("users/updateuserAndroid/{id}")
    Call<Void> updateUser(@Path("id") String id, @Body User user);

@POST("feedbacks/addfeedback")
    Call<Void> addFeedback(@Body Feedback feedback);
}
