package com.tominc.resultapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by shubham on 23/8/15.
 */
public class Tab1 extends Fragment{
    EditText roll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fragment_roll_no, container, false);

        Button submit = (Button) root.findViewById(R.id.submit);
        roll = (EditText) root.findViewById(R.id.rollno);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(roll.getText().toString());
            }
        });
        return root;
    }

    // What is the need of the View v Parameter??
    public void next(String s_roll)
    {
        if(!s_roll.isEmpty()){
            if(Utility.checkRollno(s_roll)){
                Intent i = new Intent(getActivity(), Semwise_result.class);
                i.putExtra("roll", s_roll);
                startActivity(i);
            }
            else {
                Toast.makeText(getActivity(), "Invalid Roll Number", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Roll No empty", Toast.LENGTH_SHORT).show();
        }
    }
}
