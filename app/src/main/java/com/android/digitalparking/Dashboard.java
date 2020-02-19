package com.android.digitalparking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recycleview);
        List<Container> containerList = new ArrayList<>();
        containerList.add(new Container(R.drawable.ic_menu_camera, "Total Space", 23,"#ffc0eb"));
        containerList.add(new Container(R.drawable.ic_menu_camera, "Total Space", 24,"#fffc0eb"));
        containerList.add(new Container(R.drawable.ic_menu_camera, "Total Space", 23,"#ffffff"));
        containerList.add(new Container(R.drawable.ic_menu_camera, "Total Space", 23,"#ffffff"));

        // pass LIst to the Adapter class
        ContainerAdapter containerAdapter = new ContainerAdapter(this,containerList);
        recyclerView.setAdapter(containerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, '2'));
    }
}
