package com.tominc.resultapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.tominc.resultapp.DataBase.DbContract;

/**
 * Created by shubham on 23/8/15.
 */
public class Tab2 extends Fragment {
    Spinner spinner,spinner2,spinner3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_year, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.year,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.branch,android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner3 = (Spinner) view.findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(),R.array.order,android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        Button submit = (Button) view.findViewById(R.id.year_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = spinner.getSelectedItemPosition() + 1;
                int branch = spinner2.getSelectedItemPosition() + 1;
                int order = spinner3.getSelectedItemPosition();
                String fin_order;
                if(order==0){
                    fin_order = "rank";
                } else{
                    fin_order = "roll";
                }
                ContentValues v=new ContentValues();
                v.put(DbContract.SEARCH_TABLE.TITLE,"Searched result of "+getData(R.array.year,year)+" "+getData(R.array.branch,branch)+" in order of "+fin_order);
                v.put(DbContract.SEARCH_TABLE.PARAMETER,year+" "+branch+" "+fin_order);
                v.put(DbContract.SEARCH_TABLE.TARGET_ACTIVITY,"ShowClassResult");
                getActivity().getContentResolver().insert(DbContract.insertHistory(),v);
                Intent in = new Intent(getActivity(), ShowClassResult.class);
                in.putExtra("year", String.valueOf(year));
                in.putExtra("branch", String.valueOf(branch));
                in.putExtra("order", fin_order);

                startActivity(in);
            }
        });



        return view;
    }

    private String getData(int array_id,int id){
        return getResources().getStringArray(array_id)[id];
    }
}
