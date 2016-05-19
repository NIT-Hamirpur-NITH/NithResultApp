package com.tominc.resultapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by avani on 2/4/16.
 */
public class SemAdapter extends BaseAdapter {
    ArrayList<String> sems;
    ArrayList<String> sgpis;
    ArrayList<String> cgpis;
    ArrayList<String> year_ranks;
    ArrayList<String> college_ranks;
    Context c;
    int type;

    public SemAdapter(ArrayList<String> sems, ArrayList<String> sgpis, ArrayList<String> cgpis, Context c, int type) {
        this.sems = sems;
        this.sgpis = sgpis;
        this.cgpis = cgpis;
        this.c = c;
        this.type = type;
    }

    public SemAdapter(ArrayList<String> sems, ArrayList<String> sgpis, ArrayList<String> cgpis, ArrayList<String> year_ranks, ArrayList<String> college_ranks, Context c, int type) {
        this.sems = sems;
        this.sgpis = sgpis;
        this.cgpis = cgpis;
        this.c = c;
        this.type = type;
        this.college_ranks=college_ranks;
        this.year_ranks=year_ranks;
    }

    @Override
    public int getCount() {
        return sems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.sem_item, null, false);
        } else{
            row = convertView;
        }

        TextView text1 = (TextView) row.findViewById(R.id.sem_sem);
        TextView text2 = (TextView) row.findViewById(R.id.sem_sgpi);
        TextView text3 = (TextView) row.findViewById(R.id.sem_cgpi);
        TextView text4 = (TextView) row.findViewById(R.id.sem_year_rank);
        TextView text5 = (TextView) row.findViewById(R.id.sem_college_rank);

        if(type==1){
            text1.setText("Semester: " + sems.get(position));
            text2.setText("SGPI: " + sgpis.get(position));
            text3.setText("CGPI: " + cgpis.get(position));
        } else if(type==2){
            text1.setText("Subject: " + sems.get(position));
            text2.setText("Obtainted Credit: " + sgpis.get(position));
            text3.setText("Total Credit: " + cgpis.get(position));
        } else if(type==3){
            text1.setText(String.valueOf(position+1) + ": " + sems.get(position));
            text2.setText(sgpis.get(position));
            text3.setText("CGPI: " + cgpis.get(position));
            text4.setText("YEAR RANK: " + year_ranks.get(position));
            text5.setText("COLLEGE RANK: " + college_ranks.get(position));
        } else if(type==4){
            text1.setText(sems.get(position));
            text2.setText(sgpis.get(position));
            text3.setText("CGPI: " + cgpis.get(position));
            text4.setText("YEAR RANK: " + year_ranks.get(position));
            text5.setText("COLLEGE RANK: " + college_ranks.get(position));
        }

        return row;
    }
}
