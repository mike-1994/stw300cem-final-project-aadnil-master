package com.android.digitalparking;

public class Container {
    private  int imageid;
    private String title;
    private int data;
    private String color;

    public Container(int imageid, String title, int data, String color) {
        this.imageid = imageid;
        this.title = title;
        this.data = data;
        this.color = color;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = title;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
