package com.android.digitalparking.Model;

public class Feedback {
    private String userid;
    private String first_name;
    private String last_name;
    private String email;
    private String desc;

    public Feedback(String userid, String first_name, String last_name, String email, String desc) {
        this.userid = userid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
