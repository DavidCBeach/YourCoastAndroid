package com.example.yourcoastandroid.AccessPointData;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.yourcoastandroid.MyItem;
import com.example.yourcoastandroid.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//recycler adapter
public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.myViewHolder> {

    private List<MyItem> jList = new ArrayList<>();
   // private List<MyItem> onScreenjList = new ArrayList<>();
    //private ArrayList<Integer> IDOfMarkers = new ArrayList<>();
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
    //set this to return jList.size() to return full array
    //TODO: paginate list to reduce startup time
    public int getItemCount() {
        //return 30;
        //return jList.size();
        if(jList.size() < 20)
            return jList.size();
        else
            return 20;
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

    @Override
    public void onBindViewHolder(@NonNull myViewHolder viewHolder, int i) {

        //Object obj = IDOfMarkers.get(i);
        //Object newList = jList.get(IDOfMarkers.get(i));
       // IDOfMarkers[i]
          //  Log.d("getID", String.valueOf(IDOfMarkers.get(i)));
//            for(int j = 0; j < jList.size(); j++){
//                if
//            }

        //Log.d("testIDOF", IDOfMarkers.get(i).toString());
        //Log.d("testjLISt", Integer.toString(jList.get(IDOfMarkers.get(i)).getID()) + " " + jList.get(IDOfMarkers.get(i)).getName());
        //Log.d("testNEWLIST", ((MyItem) newList).getName());
        //viewHolder.NameMobileWeb.setText(((MyItem) newList).getName());
       // viewHolder.DescriptionMobileWeb.setText(((MyItem) newList).getDescription());
        //String dis = Double.toString(((MyItem) newList).getDistance());
       // viewHolder.Distance.setText(dis + "mi");
        viewHolder.NameMobileWeb.setText(jList.get(i).getName());
        viewHolder.DescriptionMobileWeb.setText(jList.get(i).getDescription());
        viewHolder.Distance.setText(jList.get(i).getDistance() + "mi");
    }

}
