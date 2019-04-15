package com.example.yourcoastandroid.AccessPointData;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yourcoastandroid.R;

import java.util.ArrayList;
import java.util.List;

//recycler adapter
public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.myViewHolder> {

    private List<ListItemStructure> jList = new ArrayList<>();

    public ListItemAdapter(List<ListItemStructure> jList){
        this.jList = jList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_view,viewGroup,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder viewHolder, int i) {
       // viewHolder.ID.setText(Integer.toString(jList.get(i).getID()));
       // Log.d("ID", Integer.toString(list.get(i).getID()));
        viewHolder.NameMobileWeb.setText(jList.get(i).getName());
        viewHolder.DescriptionMobileWeb.setText(jList.get(i).getDescription());

    }

    @Override
    public int getItemCount() {
        return jList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView ID, NameMobileWeb, DescriptionMobileWeb;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ID = (TextView)itemView.findViewById(R.id.id);
            NameMobileWeb = (TextView)itemView.findViewById(R.id.nameMobileWeb);
            DescriptionMobileWeb = (TextView)itemView.findViewById(R.id.descriptionMobileWeb);

        }
    }

    public List<ListItemStructure> getjList() {
        return jList;
    }
}
