package com.android.digitalparking.Dashboard2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.digitalparking.R;

import java.util.ArrayList;
import java.util.List;

public class LeftPanelFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        if (!(context instanceof MenuListener))
            throw new ClassCastException();
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.left_panel_fragment, container, true);
        ListView listView = v.findViewById(R.id.booklist);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, this.getmenuitem());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MenuListener) getActivity()).onMenuclickListener(position);


            }
        });
        setHasOptionsMenu(true);
        return v;
    }

    private List<String> getmenuitem() {
        List<String> menu = new ArrayList<>();
        menu.add("Booking");  //case 1
        menu.add("View Booking"); //case 2
        menu.add("Parking Details"); //case 3
        menu.add("FeedbackActivity"); //case 4
        menu.add("My Profile"); //case 5
        menu.add("Contact Us"); //case 6
        menu.add("Logout");//case 7 in Home_activty.
        return menu;
    }
}
