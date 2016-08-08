package com.tominc.resultapp.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.tominc.resultapp.DataBase.DbContract.SEARCH_TABLE.PARAMETER;
import static com.tominc.resultapp.DataBase.DbContract.SEARCH_TABLE.SEARCHED_ON;
import static com.tominc.resultapp.DataBase.DbContract.SEARCH_TABLE.TARGET_ACTIVITY;
import static com.tominc.resultapp.DataBase.DbContract.SEARCH_TABLE.TITLE;
import static com.tominc.resultapp.DataBase.DbContract.SEARCH_TABLE._ID;
import static com.tominc.resultapp.DataBase.DbContract.TABLE_NAME;
import static com.tominc.resultapp.DataBase.DbContract.VERSION;

/**
 * Created by lenovo on 8/6/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private final String CREATE_TABLE = "Create Table " + TABLE_NAME + "( " + _ID + " integer auto_increment primary key ," + TITLE + " text not null , " + PARAMETER + " text not null , " + TARGET_ACTIVITY + " text , " + SEARCHED_ON + " timestamp default current_timestamp);";

    public DbHelper(Context context) {
        super(context, DbContract.DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
