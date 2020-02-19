package com.android.digitalparking.Model;

import java.util.Date;

public class Booking { private String _id;
    private String Vehicle_Number;
    private String Vehicle_Type;
    private String Start_Time;
    private String End_Time;
    private String   Date;
    private String userid;
    private String first_name;
    private String last_name;
    private String email;

    public Booking(String _id, String vehicle_Number, String vehicle_Type, String start_Time, String end_Time, String date, String userid, String first_name, String last_name, String email) {
        this._id = _id;
        Vehicle_Number = vehicle_Number;
        Vehicle_Type = vehicle_Type;
        Start_Time = start_Time;
        End_Time = end_Time;
        Date = date;
        this.userid = userid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getVehicle_Number() {
        return Vehicle_Number;
    }

    public void setVehicle_Number(String vehicle_Number) {
        Vehicle_Number = vehicle_Number;
    }

    public String getVehicle_Type() {
        return Vehicle_Type;
    }

    public void setVehicle_Type(String vehicle_Type) {
        Vehicle_Type = vehicle_Type;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(String start_Time) {
        Start_Time = start_Time;
    }

    public String getEnd_Time() {
        return End_Time;
    }

    public void setEnd_Time(String end_Time) {
        End_Time = end_Time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
