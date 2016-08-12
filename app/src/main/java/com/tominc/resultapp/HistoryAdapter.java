package com.tominc.resultapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 8/6/2016.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder> {
    private ArrayList<HistoryModel> list = new ArrayList<>();

    public void refresh(ArrayList<HistoryModel> list){
        this.list=list;
        notifyItemRangeChanged(0,list.size());
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
     if(!list.get(position).getTitle().isEmpty()){
         holder.textView.setText(list.get(position).getTitle());
     }
        if(!list.get(position).getCreated_on().isEmpty()){
            holder.timeview.setText(Utility.getTimestamp(list.get(position).getCreated_on()+" GMT"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView textView,timeview;

        public viewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_item_history);
            timeview = (TextView) itemView.findViewById(R.id.text_time_history);
        }
    }
}
