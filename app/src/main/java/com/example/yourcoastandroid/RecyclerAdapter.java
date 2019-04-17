package com.example.yourcoastandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yourcoastandroid.AccessPointData.CCCAccPtDataStructure;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<MyItem> list;
    private static List<CCCAccPtDataStructure> filteredList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.location);

        }
    }

    RecyclerAdapter(List<MyItem> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewTyped) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CCCAccPtDataStructure item = filteredList.get(position);
        holder.name.setText(String.valueOf(item.getNameMobileWeb()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if(charString.isEmpty()){
//                    filteredList = list;
//                }else{
//                    List<CCCAccPtDataStructure> filtered = new ArrayList<>();
//                    for(CCCAccPtDataStructure item : list){
//                        //name match condition
//                        if(item.getNameMobileWeb().toLowerCase().contains(charString.toLowerCase())){
//                            filtered.add(item);
//                        }
//                    }
//                    filteredList = filtered;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filteredList = (ArrayList<CCCAccPtDataStructure>) filterResults.values;
//
//                //refresh the list with filtered data
//                notifyDataSetChanged();
//            }
//        };
//    }

}
