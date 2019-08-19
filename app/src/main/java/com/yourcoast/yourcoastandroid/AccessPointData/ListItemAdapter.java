package com.yourcoast.yourcoastandroid.AccessPointData;

import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yourcoast.yourcoastandroid.MyItem;
import com.yourcoast.yourcoastandroid.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//recycler adapter
public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.myViewHolder> {
private onItemListener onItemListener;
    private List<MyItem> jList = new ArrayList<>();

    public ListItemAdapter(List<MyItem> jList, onItemListener onItemListener){
        this.jList = jList;
        this.onItemListener = onItemListener;

        if(jList.size() == 0){
            //TODO: replace with bottomsheet fragment!
            System.out.println("empty!");
            MyItem e = new MyItem(0.0, 0.0, "", "", 0, "Nothing to see here!", "Tap to return to California", null);
            jList.add(e);
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_view,viewGroup,false);
        return new myViewHolder(view, onItemListener);
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


    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView NameMobileWeb, DescriptionMobileWeb, Distance;
        onItemListener onItemListener;
        public myViewHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);
            NameMobileWeb = (TextView)itemView.findViewById(R.id.nameMobileWeb);
            DescriptionMobileWeb = (TextView)itemView.findViewById(R.id.descriptionMobileWeb);
            Distance = (TextView)itemView.findViewById(R.id.distance);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            onItemListener.onClick(getAdapterPosition());
            v.setBackgroundColor(Color.parseColor("#f0f0f0"));
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    v.setBackgroundColor(Color.WHITE);
                }
            }, 100);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder viewHolder, int i) {
        viewHolder.NameMobileWeb.setText(jList.get(i).getName());
        viewHolder.DescriptionMobileWeb.setText(jList.get(i).getDescription());
        String distanceS = jList.get(i).getDistance() + "mi";
        if(distanceS.equals("nullmi"))
            viewHolder.Distance.setText("");
        else
        viewHolder.Distance.setText(distanceS);
    }

    public interface onItemListener{
        void onClick(int position);
    }
}
