package com.tominc.resultapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Semwise_result extends AppCompatActivity {
    ListView listViewSMS;
    ArrayList<String> sems = new ArrayList<>();
    ArrayList<String> sgpis = new ArrayList<>();
    ArrayList<String> cgpis = new ArrayList<>();
    TextView t_name, t_roll,year_rank,college_rank;
    Button graph;

    private String GET_URL = Config.BASEURL + "semester.php";

   // private String GET_URL = "http://192.168.43.155/php/3.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semwise_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent in = getIntent();
        final String roll = in.getStringExtra("roll");

        Log.e("Result", roll);

        t_name = (TextView) findViewById(R.id.result_name);
        t_roll = (TextView) findViewById(R.id.result_roll);
        year_rank = (TextView) findViewById(R.id.year_rank);
        college_rank = (TextView) findViewById(R.id.college_rank);

        t_roll.setText(roll);

        listViewSMS=(ListView)findViewById(R.id.sem);
        final SemAdapter adapter = new SemAdapter(sems, sgpis, cgpis, getApplicationContext(), 1);
        listViewSMS.setAdapter(adapter);

        listViewSMS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(Semwise_result.this, ShowSemesterResult.class);
                in.putExtra("roll", roll);
                in.putExtra("semester", String.valueOf(position + 1));
                startActivity(in);
            }
        });

      /*  graph = (Button) findViewById(R.id.make_graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Semwise_result.this, GraphActivity.class);
                in.putStringArrayListExtra("sgpi", sgpis);
                startActivity(in);
            }
        });
*/

        final ProgressDialog dialog = new ProgressDialog(Semwise_result.this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.show();

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String name = jsonObject.getString("name");
                    t_name.setText(name);
                    String yrank = jsonObject.getString("Year");
                    year_rank.setText("Year Rank: " + yrank);
                    String crank = jsonObject.getString("College");
                    college_rank.setText("College Rank: "+crank);
                    JSONArray ar = jsonObject.getJSONArray("result");

                    for(int i=0;i<ar.length();i++){
                        JSONObject ob = ar.getJSONObject(i);
                        sgpis.add(ob.getString("SGPI"));
                        cgpis.add(ob.getString("CGPI"));
                        sems.add(String.valueOf(i+1));
                    }
                    dialog.dismiss();
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("VolleyError", volleyError.toString());
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("roll", roll);
                return params;
            }
        };

        rq.add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_plot) {
            Intent in = new Intent(Semwise_result.this, GraphActivity.class);
            in.putStringArrayListExtra("sgpi", sgpis);
            in.putExtra("type", "sem");
            startActivity(in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
