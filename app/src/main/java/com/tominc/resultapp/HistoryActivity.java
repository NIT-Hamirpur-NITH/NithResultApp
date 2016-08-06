package com.tominc.resultapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tominc.resultapp.DataBase.DbContract;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        historyAdapter = new HistoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(historyAdapter);
        getData(getContentResolver().query(DbContract.readHistory(), null, null, null, null));


    }

    private void getData(Cursor cursor) {
        ArrayList<HistoryModel> list = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    HistoryModel model = new HistoryModel(cursor.getString(cursor.getColumnIndex(DbContract.SEARCH_TABLE.TITLE)), cursor.getString(cursor.getColumnIndex(DbContract.SEARCH_TABLE.PARAMETER)), cursor.getString(cursor.getColumnIndex(DbContract.SEARCH_TABLE.SEARCHED_ON)), cursor.getString(cursor.getColumnIndex(DbContract.SEARCH_TABLE.TARGET_ACTIVITY)));
                    list.add(model);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }
        historyAdapter.refresh(list);
    }
}
