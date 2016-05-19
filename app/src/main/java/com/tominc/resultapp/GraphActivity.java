package com.tominc.resultapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    GraphView graphView;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        text = (TextView) findViewById(R.id.graph_des);

        Intent in = getIntent();
        String type = in.getStringExtra("type");

        if(type.equals("sem")){
            text.setText("SGPI vs Semester");
        } else {
            text.setText("CGPI vs Class");
        }

        ArrayList<String> sgpi = new ArrayList<>();
        sgpi = in.getStringArrayListExtra("sgpi");

        double total = 0;

        DataPoint[] ar = new DataPoint[sgpi.size()];
        for(int i=0;i<sgpi.size();i++){
            DataPoint temp = new DataPoint(i+1, Double.valueOf(sgpi.get(i)));
            ar[i] = temp;
            total+=Double.valueOf(sgpi.get(i));
        }

        double avg = total/sgpi.size();

        DataPoint[] ar2 = new DataPoint[sgpi.size()];
        for(int i=0;i<ar2.length;i++){
            DataPoint temp = new DataPoint(i+1, avg);
            ar2[i]=temp;
        }



        graphView = (GraphView) findViewById(R.id.indi_graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(ar);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(ar2);

        series2.setColor(Color.parseColor("#F0810F"));
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxX(sgpi.size() + 1);
        graphView.getViewport().setMaxY(10);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMinY(0);


        graphView.addSeries(series);
        graphView.getViewport().setScrollable(true);
        if(!type.equals("sem")){
            series.setColor(Color.parseColor("#b2b2ff"));
            graphView.addSeries(series2);
            graphView.setTitle("Average:"+new DecimalFormat("##.##").format(avg));
        }


    }
}
