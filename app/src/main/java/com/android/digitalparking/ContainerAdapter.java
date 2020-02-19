package com.android.digitalparking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContainerAdapter extends RecyclerView.Adapter<ContainerAdapter.ContainerViewHolder>
{
    Context mContext;


    List<Container> containerList;

    public ContainerAdapter(Context mContext, List<Container> containerList){
        this.mContext=mContext;
        this.containerList=containerList;
    }

    @NonNull
    @Override
    public ContainerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_container,null);
        return  new ContainerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ContainerViewHolder containerViewHolder, int i) {
        Container container = containerList.get(i);

        containerViewHolder.imgid.setImageResource(container.getImageid());
        containerViewHolder.title.setText(container.getTitle());
        containerViewHolder.data.setText(""+container.getData());
    }

    @Override
    public int getItemCount() {
        return containerList.size();
    }

    public class ContainerViewHolder extends RecyclerView.ViewHolder{
        ImageView imgid;
         TextView title, data;


         public ContainerViewHolder(@NonNull View itemView) {
             super(itemView);
             imgid=itemView.findViewById(R.id.imgid);
             title=itemView.findViewById(R.id.total_space);
             data=itemView.findViewById(R.id.number);
         }
     }
}
