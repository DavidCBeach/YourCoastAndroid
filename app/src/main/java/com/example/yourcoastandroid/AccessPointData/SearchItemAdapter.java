package com.example.yourcoastandroid.AccessPointData;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.example.yourcoastandroid.MyItem;
import com.example.yourcoastandroid.R;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.MyViewHolder> {

    private List<MyItem> list = new ArrayList<>();
    private List<MyItem> filteredList = new ArrayList<>();
    private String distance = "mi";

    public SearchItemAdapter(List<MyItem> list){
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView NameMobileWeb, Distance;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            NameMobileWeb = (TextView)itemView.findViewById(R.id.location);
            Distance = (TextView)itemView.findViewById(R.id.Distance);
        }
    }

    @NonNull
    @Override
    public SearchItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.NameMobileWeb.setText(list.get(i).getName());
        viewHolder.Distance.setText(String.valueOf(list.get(i).getDistance()) + distance);
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public void setFilter(List<MyItem> itemList){
        list=new ArrayList<>();
        list.addAll(itemList);
        notifyDataSetChanged();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    filteredList = list;
                }else{
                    List<MyItem> filtered = new ArrayList<>();
                    for(MyItem item : list){
                        //name match condition
                        if(item.getName().toLowerCase().contains(charString.toLowerCase())){
                            filtered.add(item);
                        }
                    }
                    filteredList = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<MyItem>) filterResults.values;

                //refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
