package com.yourcoast.yourcoastandroid.AccessPointData;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yourcoast.yourcoastandroid.R;

import java.util.ArrayList;
import java.util.List;

//recycler adapter (may need to change name not sure if this is the correct file to implement this)
public class CCCDataClient extends RecyclerView.Adapter<CCCDataClient.myViewHolder> {

    private List<CCCAccPtDataStructure> list = new ArrayList<>();

    public CCCDataClient(List<CCCAccPtDataStructure> list){
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_view,viewGroup,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder viewHolder, int i) {
        viewHolder.ID.setText(Integer.toString(list.get(i).getID()));
        Log.d("ID", Integer.toString(list.get(i).getID()));
        viewHolder.DISTRICT.setText(list.get(i).getDISTRICT());
        viewHolder.NameMobileWeb.setText(list.get(i).getNameMobileWeb());
    }

    @Override
    public int getItemCount() {
        return 30; //list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView ID, DISTRICT, NameMobileWeb;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ID = (TextView)itemView.findViewById(R.id.id);
            DISTRICT = (TextView)itemView.findViewById(R.id.district);
            NameMobileWeb = (TextView)itemView.findViewById(R.id.nameMobileWeb);
        }
    }
}
