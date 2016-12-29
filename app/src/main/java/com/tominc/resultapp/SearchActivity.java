package com.tominc.resultapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
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
import com.tominc.resultapp.DataBase.DbContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    public static final String SEARCH_KEYWORD = "key";
    private static final int SUGGESTION_ID = 12;
    Toolbar toolbar;
    private final String GET_URL = Config.BASEURL + "4.php";
    ListView list;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> rollno = new ArrayList<>();
    ArrayList<String> cgpis = new ArrayList<>();
    ArrayList<String> year_rank = new ArrayList<>();
    ArrayList<String> college_rank = new ArrayList<>();
    SemAdapter adapter;
    private android.support.v7.widget.SearchView searchView;
    private SimpleCursorAdapter suggestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);

        searchView = (android.support.v7.widget.SearchView) toolbar.findViewById(R.id.search_view);
        list = (ListView) findViewById(R.id.search_list);

        searchView.setIconified(false);
        searchView.setQueryHint("Search");
        android.support.v7.widget.SearchView.SearchAutoComplete searchAutoComplete = (android.support.v7.widget.SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        adapter = new SemAdapter(names, rollno, cgpis, year_rank, college_rank, getApplicationContext(), 4);
        list.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                ContentValues v = new ContentValues();
                v.put(DbContract.SEARCH_TABLE.TITLE, "Searched Keyword " + q);
                v.put(DbContract.SEARCH_TABLE.PARAMETER, q);
                v.put(DbContract.SEARCH_TABLE.TARGET_ACTIVITY, "SearchActivity");
                getContentResolver().insert(DbContract.insertHistory(), v);
                requestSearch(q);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSuggestion(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    doExitAnim();
                }
                return false;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = rollno.get(i);
                ContentValues v = new ContentValues();
                v.put(DbContract.SEARCH_TABLE.TITLE, "Searched " + s);
                v.put(DbContract.SEARCH_TABLE.PARAMETER, s);
                v.put(DbContract.SEARCH_TABLE.TARGET_ACTIVITY, "Semwise_result");
                getContentResolver().insert(DbContract.insertHistory(), v);
                Log.d("d", s);
                Intent in = new Intent(SearchActivity.this, Semwise_result.class);
                in.putExtra("roll", s);
                startActivity(in);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(SEARCH_KEYWORD))
                requestSearch(i.getStringExtra(SEARCH_KEYWORD));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            doEnterAnim();
        }

        suggestionAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null,new String[]{DbContract.SEARCH_TABLE.PARAMETER},new int[]{android.R.id.text1});
        searchView.setSuggestionsAdapter(suggestionAdapter);
    }

    private void requestSearch(final String search) {
        final ProgressDialog dialog = new ProgressDialog(SearchActivity.this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.show();

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                list.setVisibility(View.VISIBLE);
                try {
                    names.clear();
                    rollno.clear();
                    cgpis.clear();
                    year_rank.clear();
                    college_rank.clear();
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray ar = jsonObject.getJSONArray("result");

                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject ob = ar.getJSONObject(i);
                        names.add(ob.getString("Name"));
                        cgpis.add(ob.getString("CGPI"));
                        rollno.add(ob.getString("roll_no"));
                        year_rank.add(ob.getString("Year"));
                        college_rank.add(ob.getString("College"));
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
                params.put("name", search);
                return params;
            }
        };

        rq.add(request);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doEnterAnim() {
        // Fade in a background scrim as this is a floating window. We could have used a
        // translucent window background but this approach allows us to turn off window animation &
        // overlap the fade with the reveal animation – making it feel snappier.
        View scrim = findViewById(R.id.scrim);
        scrim.animate()
                .alpha(1f)
                .setDuration(500L)
                .setInterpolator(
                        AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in))
                .start();

        // Next perform the circular reveal on the search panel
        final View searchPanel = findViewById(R.id.search_panel);
        if (searchPanel != null) {
            // We use a view tree observer to set this up once the view is measured & laid out
            searchPanel.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            searchPanel.getViewTreeObserver().removeOnPreDrawListener(this);
                            // As the height will change once the initial suggestions are delivered by the
                            // loader, we can't use the search panels height to calculate the final radius
                            // so we fall back to it's parent to be safe
                            int revealRadius = ((ViewGroup) searchPanel.getParent()).getHeight();
                            // Center the animation on the top right of the panel i.e. near to the
                            // search button which launched this screen.
                            Animator show = ViewAnimationUtils.createCircularReveal(searchPanel,
                                    searchPanel.getRight(), searchPanel.getTop(), 0f, revealRadius);
                            show.setDuration(250L);
                            show.setInterpolator(AnimationUtils.loadInterpolator(SearchActivity.this,
                                    android.R.interpolator.fast_out_slow_in));
                            show.start();
                            return false;
                        }
                    });
        }
    }

    /**
     * On Lollipop+ perform a circular animation (a contracting circular mask) when hiding the
     * search panel.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doExitAnim() {
        final View searchPanel = findViewById(R.id.search_panel);
        // Center the animation on the top right of the panel i.e. near to the search button which
        // launched this screen. The starting radius therefore is the diagonal distance from the top
        // right to the bottom left
        int revealRadius = (int) Math.sqrt(Math.pow(searchPanel.getWidth(), 2)
                + Math.pow(searchPanel.getHeight(), 2));
        // Animating the radius to 0 produces the contracting effect
        Animator shrink = ViewAnimationUtils.createCircularReveal(searchPanel,
                searchPanel.getRight(), searchPanel.getTop(), revealRadius, 0f);
        shrink.setDuration(200L);
        shrink.setInterpolator(AnimationUtils.loadInterpolator(SearchActivity.this,
                android.R.interpolator.fast_out_slow_in));
        shrink.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                searchPanel.setVisibility(View.INVISIBLE);
                ActivityCompat.finishAfterTransition(SearchActivity.this);
            }
        });
        shrink.start();

        // We also animate out the translucent background at the same time.
        findViewById(R.id.scrim).animate()
                .alpha(0f)
                .setDuration(200L)
                .setInterpolator(
                        AnimationUtils.loadInterpolator(SearchActivity.this,
                                android.R.interpolator.fast_out_slow_in))
                .start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            doExitAnim();
        }
    }

    private void getSuggestion(String s) {
        Cursor c = getContentResolver().query(DbContract.getSuggestion(),null, DbContract.SEARCH_TABLE.TITLE + " like "+"\"%"+s+"%\"",null, null);
        if (c != null) {
            if(c.moveToFirst())
            suggestionAdapter.changeCursor(c);
        }
    }
}
