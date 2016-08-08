package com.tominc.resultapp.DataBase;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by lenovo on 8/6/2016.
 */
public class DBProvider extends ContentProvider {
    private SQLiteDatabase sqLiteDatabase;
    private DbHelper dbHelper;
    private static final int INSERT = 1;
    private static final int READ = 2;
    private static UriMatcher uriMatcher = uriMatcher();

    private static UriMatcher uriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.INSERT, INSERT);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.READ, READ);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
       sqLiteDatabase=dbHelper.getReadableDatabase();
        Cursor cursor=null;
        if(uriMatcher.match(uri)==READ){
            cursor=sqLiteDatabase.query(DbContract.TABLE_NAME,null,null,null,null,null,DbContract.SEARCH_TABLE.SEARCHED_ON+" desc");
            if(cursor!=null){
                cursor.setNotificationUri(getContext().getContentResolver(),uri);
                return  cursor;
            }
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return  ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+DbContract.AUTHORITY;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        sqLiteDatabase=dbHelper.getWritableDatabase();
        long id=0;
        if(uriMatcher.match(uri)==INSERT){
            id = sqLiteDatabase.insert(DbContract.TABLE_NAME, null, contentValues);
            if (id > 0) {
                Log.d("insert","insert");
                Uri _uri = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(_uri, null);
                sqLiteDatabase.close();
                return _uri;
            }

        }
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
