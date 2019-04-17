package com.example.yourcoastandroid.AccessPointData;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.yourcoastandroid.MyItem;
import com.example.yourcoastandroid.R;
import java.util.ArrayList;
import java.util.List;

//recycler adapter
public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.myViewHolder> {

    private List<MyItem> jList = new ArrayList<>();
    public ListItemAdapter(List<MyItem> jList){
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
        viewHolder.NameMobileWeb.setText(jList.get(i).getName());
        viewHolder.DescriptionMobileWeb.setText(jList.get(i).getDescription());
        viewHolder.Distance.setText(jList.get(i).getDistance() + "mi");
    }

    @Override
    //set this to return jList.size() to return full array
    //TODO: paginate list to reduce startup time
    public int getItemCount() {
        return 30;
        //return jList.size();
    }


    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView NameMobileWeb, DescriptionMobileWeb, Distance;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            NameMobileWeb = (TextView)itemView.findViewById(R.id.nameMobileWeb);
            DescriptionMobileWeb = (TextView)itemView.findViewById(R.id.descriptionMobileWeb);
            Distance = (TextView)itemView.findViewById(R.id.distance);
        }
    }
}
