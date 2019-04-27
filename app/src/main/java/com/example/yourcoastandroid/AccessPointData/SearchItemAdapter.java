package com.example.yourcoastandroid.AccessPointData;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yourcoastandroid.MyItem;
import com.example.yourcoastandroid.R;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.MyViewHolder> {

    private List<MyItem> list = new ArrayList<>();
    private String distance = "mi";
    private onItemListener onItemListener;

    public SearchItemAdapter(List<MyItem> list, onItemListener onItemListener){
        this.list = list;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public SearchItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item,viewGroup,false);
        return new MyViewHolder(view, onItemListener);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView NameMobileWeb, Distance;
        onItemListener onItemListener;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);

            NameMobileWeb = (TextView)itemView.findViewById(R.id.location);
            Distance = (TextView)itemView.findViewById(R.id.Distance);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v){
            onItemListener.onClick(getAdapterPosition());
            v.setBackgroundColor(Color.parseColor("#f0f0f0"));
            v.postDelayed(new Runnable() {
                @Override
                public void run() { v.setBackgroundColor(Color.WHITE); }
            },100);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
            Log.d("list", list.toString());
            Log.d("list", "index: " + i + " id: " + list.get(i).getID() + " name: " + list.get(i).getName());
            viewHolder.NameMobileWeb.setText(list.get(i).getName());
            viewHolder.Distance.setText(new StringBuilder().append(String.valueOf(list.get(i).getDistance())).append(distance).toString());
    }

    @Override
    public int getItemCount() {
        if(list != null) {return list.size(); }
        else{return 0;}
    }

    public interface onItemListener{
        void onClick(int position);
    }
}
