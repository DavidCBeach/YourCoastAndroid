package com.example.yourcoastandroid.AccessPointData;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.yourcoastandroid.R;

import java.util.ArrayList;

//recycler adapter (may need to change name not sure if this is the correct file to implement this)
public class CCCDataClient extends RecyclerView.Adapter<CCCDataClient.myViewHolder> {

    private ArrayList<CCCAccPtDataStructure> list = new ArrayList<>();

    CCCDataClient(ArrayList<CCCAccPtDataStructure> list){
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder viewHolder, int i) {
        viewHolder.ID.setText(Integer.toString(list.get(i).getID()));
        viewHolder.DISTRICT.setText(list.get(i).getDISTRICT());
    }

    @Override
    public int getItemCount() {
        return 10;//list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView ID, DISTRICT;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ID = (TextView)itemView.findViewById(R.id.id);
            DISTRICT = (TextView)itemView.findViewById(R.id.district);
        }
    }
}
