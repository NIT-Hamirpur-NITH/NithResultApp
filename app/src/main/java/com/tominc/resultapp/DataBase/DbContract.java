package com.tominc.resultapp.DataBase;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lenovo on 8/6/2016.
 */
public class DbContract {

    public static final String AUTHORITY="com.tominc.resultapp";
    public static final Uri BASE_URI=Uri.parse("content://"+AUTHORITY);
    public static final String DATABASE_NAME="SearchDb";
    public static final String TABLE_NAME="SearchTable";
    public static final int VERSION=1;
    public static final String INSERT="insert";
    public static final String READ="read";
    public static final String SUGGESTION="suggestion";

    public static class SEARCH_TABLE implements BaseColumns{
        public static final String TARGET_ACTIVITY="targetActivity";
        public static final String PARAMETER="parameter";
        public static final String SEARCHED_ON="created_on";
        public static final String TITLE="title";

    }

    public static Uri getSuggestion(){return BASE_URI.buildUpon().appendPath(SUGGESTION).build();}
    public static Uri insertHistory(){
        return BASE_URI.buildUpon().appendPath(INSERT).build();
    }
    public static Uri readHistory(){
        return BASE_URI.buildUpon().appendPath(READ).build();
    }
}
