package com.example.playergenre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by saidu on 01-Sep-18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG="Database helper";

    public DatabaseHelper(Context context)
    {
        super(context,"all_songs",null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable="CREATE TABLE all_songs(songname text primary key,songlocation text,singer text,genreselected text,genre text,timesplayed integer)";
        db.execSQL(createtable);
        String createtable2="CREATE TABLE temp_playlist(songname text primary key,songlocation text,singer text,genreselected text,genre text,timesplayed integer)";
        db.execSQL(createtable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String s2="drop table if exists all_songs";
        db.execSQL(s2);
        onCreate(db);
        String s3="drop table if exists all_songs";
        db.execSQL(s3);
        onCreate(db);

    }

    public  void droptemptable()
    {   SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from temp_playlist");

    }

    public void createtemptable()
    {SQLiteDatabase db = this.getWritableDatabase();
        String createtable2="CREATE TABLE temp_playlist(songname text primary key,songlocation text,singer text,genreselected text,genre text,timesplayed integer)";
        db.execSQL(createtable2);
    }


    long result;
    public boolean adddata(String sname,String sloc,String ssinger)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("songname", sname);
        contentValues.put("songlocation", sloc);
        contentValues.put("singer", ssinger);
        contentValues.put("genreselected", "no");
        contentValues.put("genre", "");
        contentValues.put("timesplayed", 0);


        Log.d(TAG, "adding data" + sname + sloc);
        try {
            result = db.insert("all_songs", null, contentValues);
        }catch (SQLiteConstraintException ignore)
        {

        }
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Cursor getyorn(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Select genreselected from all_songs where songname=\""+name+"\"";
        Cursor data=db.rawQuery(query,null);

        return  data;
    }

    public Cursor getpath(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Select songlocation from all_songs where songname=\""+name+"\"";
        Cursor data=db.rawQuery(query,null);

        return  data;
    }



    public Cursor getallsong()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Select * from temp_playlist";
        Cursor data=db.rawQuery(query,null);

        return  data;
    }

    public Cursor getsongnamesinger(String path)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Select * from all_songs where songlocation like \"%"+path+"%\"";
        Cursor data=db.rawQuery(query,null);

        return  data;
    }

    public void updategenre(String path,String genres)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="update all_songs set genre='"+genres+"' where songlocation like \"%"+path+"%\"";
        db.execSQL(query);
        String query2="update all_songs set genreselected='yes' where songlocation like \"%"+path+"%\"";
        db.execSQL(query2);
    }
    public Cursor getgenreplaylist2(String genre) {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Select * from all_songs where genre like \"%"+genre+"%\"";
        Cursor data=db.rawQuery(query,null);

        return  data;
    }
    long result2;
    public boolean adddata2(String a,String b,String c,String d,String e,String f)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("songname", a);
        contentValues.put("songlocation", b);
        contentValues.put("singer", c);
        contentValues.put("genreselected", d);
        contentValues.put("genre", e);
        contentValues.put("timesplayed", f);


        Log.e(TAG, a);
        try {
            result2 = db.insert("temp_playlist", null, contentValues);
        }catch (SQLiteConstraintException ignore)
        {

        }
        if (result2 == -1) {
            return false;
        } else {
            return true;
        }

    }
}

