package com.tominc.resultapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class ShowClassResult extends AppCompatActivity {
    ProgressDialog dialog;
    SemAdapter adapter;
    ListView list;
    ArrayList<String> rollno = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> cgpis = new ArrayList<>();
    ArrayList<String> college_rank = new ArrayList<>();
    ArrayList<String> year_rank = new ArrayList<>();

    private final String GET_URL = Config.BASEURL + "2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_class_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent in = getIntent();
        final String year = in.getStringExtra("year");
        final String branch = in.getStringExtra("branch");
        final String order = in.getStringExtra("order");


        Log.e("ShowClassResult", order);

        list = (ListView) findViewById(R.id.class_result);


        adapter = new SemAdapter(rollno, names, cgpis, year_rank, college_rank, getApplicationContext(), 3);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(ShowClassResult.this, Semwise_result.class);
                in.putExtra("roll", rollno.get(i));
                startActivity(in);
            }
        });

        dialog = new ProgressDialog(ShowClassResult.this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.show();

        getClassResult(year, branch, order);
    }


    public void getClassResult(final String year, final String branch, final String order) {
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray ar = jsonObject.getJSONArray("result");

                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject ob = ar.getJSONObject(i);
                        rollno.add(ob.getString("rollno"));
                        names.add(ob.getString("name"));
                        cgpis.add(ob.getString("CGPI"));
                        college_rank.add(ob.getString("College"));
                        year_rank.add(ob.getString("Year"));

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
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("year", year);
                params.put("branch", branch);
                params.put("order", order);
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
            Intent in = new Intent(ShowClassResult.this, GraphActivity.class);
            in.putStringArrayListExtra("sgpi", cgpis);
            in.putExtra("type", "none");
            startActivity(in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
