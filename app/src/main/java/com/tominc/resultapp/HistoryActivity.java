package com.tominc.resultapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.tominc.resultapp.DataBase.DbContract;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_HISTORY =1 ;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryModel> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        historyAdapter = new HistoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(historyAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openActivity(list.get(position).getParameter().trim(),list.get(position).getTarget());
            }
        }));
        getLoaderManager().initLoader(LOADER_HISTORY,null,this);


    }

    private void getData(Cursor cursor) {
        ArrayList<HistoryModel> list_data = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    HistoryModel model = new HistoryModel(cursor.getString(cursor.getColumnIndex(DbContract.SEARCH_TABLE.TITLE)), cursor.getString(cursor.getColumnIndex(DbContract.SEARCH_TABLE.PARAMETER)), cursor.getString(cursor.getColumnIndex(DbContract.SEARCH_TABLE.SEARCHED_ON)), cursor.getString(cursor.getColumnIndex(DbContract.SEARCH_TABLE.TARGET_ACTIVITY)));
                    list_data.add(model);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }

        list=list_data;
        historyAdapter.refresh(list);
    }

    private void openActivity(String parameter,String Target){
        Intent i;
        ArrayList<String> list=Spiltparameter(parameter);
        switch (Target){
            case "Semwise_result":
            if(list.size()==1){
                i=new Intent(this,Semwise_result.class);
                i.putExtra("roll",list.get(0));
                startActivity(i);
            }
                break;
            case "ShowClassResult":
                i=new Intent(this,ShowClassResult.class);
                i.putExtra("year", list.get(0));
                i.putExtra("branch",list.get(1));
                i.putExtra("order",list.get(2));
                startActivity(i);
                break;
            case  "SearchActivity":
                if(list.size()==1){
                    i=new Intent(this,SearchActivity.class);
                    i.putExtra(SearchActivity.SEARCH_KEYWORD,list.get(0));
                    startActivity(i);
                }
                break;
        }


    }

    private ArrayList<String> Spiltparameter(String parameter){
        ArrayList<String> list=new ArrayList<>();
        for(String s:parameter.split(" ")){
            Log.d("s",s);
        list.add(s);}
        return list;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, DbContract.readHistory(),null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
         getData(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
