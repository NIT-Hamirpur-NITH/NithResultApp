package com.tominc.resultapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class ShowSemesterResult extends AppCompatActivity {
    ListView list;

    private String GET_URL = Config.BASEURL + "1.php";
    ArrayList<String> sub_name = new ArrayList<>();
    ArrayList<String> ob_cr = new ArrayList<>();
    ArrayList<String> t_cr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_semester_result);


        list = (ListView) findViewById(R.id.sem_result_list);

        Intent in = getIntent();
        final String roll  = in.getStringExtra("roll");
        final String semester = in.getStringExtra("semester");

        final SemAdapter adapter = new SemAdapter(sub_name, ob_cr, t_cr, getApplicationContext(), 2);
        list.setAdapter(adapter);

        final ProgressDialog dialog = new ProgressDialog(ShowSemesterResult.this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.show();

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray ar = jsonObject.getJSONArray("result");

                    for(int i=0;i<ar.length();i++){
                        JSONObject ob = ar.getJSONObject(i);
                        sub_name.add(ob.getString("subject"));
                        ob_cr.add(ob.getString("ocr"));
                        t_cr.add(ob.getString("tcr"));

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("roll", roll);
                params.put("semester", roll+semester);
                return params;
            }
        };

        rq.add(request);


    }
}
