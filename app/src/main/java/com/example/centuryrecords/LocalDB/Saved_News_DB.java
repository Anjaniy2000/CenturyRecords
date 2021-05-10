package com.example.centuryrecords.LocalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.centuryrecords.Models.NewsModelClass;

import java.util.ArrayList;

public class Saved_News_DB extends SQLiteOpenHelper {

    //DATABASE_NAME:
    public static final String DATABASE_NAME = "Saved_News.db";
    //TABLE_NAME:
    public static final String TABLE_NAME = "Saved_News_Articles";
    //COLUMNS:
    public static final String COL_1 = "URL";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "AUTHOR";
    public static final String COL_4 = "DATE_TIME";
    public static final String COL_5 = "IMAGE_URL";



    //CONSTRUCTOR: -
    public Saved_News_DB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //METHODS: -
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (URL TEXT PRIMARY KEY , TITLE TEXT , AUTHOR TEXT , DATE_TIME TEXT , IMAGE_URL TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    //SAVE - OPERATION: -
    public int Save_Article(String title,String author,String url,String image_url,String date_time){

        String url_column = "null";

        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {

                url_column = cursor.getString(0);

            }while (cursor.moveToNext());
        }

        cursor.close();

        if(url_column.equals(url)){
            return(2);
        }


        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,url);
        contentValues.put(COL_2,title);
        contentValues.put(COL_3,author);
        contentValues.put(COL_4,date_time);
        contentValues.put(COL_5,image_url);

        long Check = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        if(Check == -1){
            return(0);
        }
        else{
            return(1);
        }
    }

    //DISPLAY - OPERATION: -
    public ArrayList<NewsModelClass> Display_Saved_Articles(){

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<NewsModelClass> objects = new ArrayList<NewsModelClass>();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                String url = cursor.getString(0);
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                String date_time = cursor.getString(3);
                String image_url = cursor.getString(4);
                objects.add(new NewsModelClass(title,author,url,image_url,date_time));
            }while(cursor.moveToNext());
        }

        cursor.close();
        return objects;
    }

    //DELETE - OPERATION: -

    public Integer Delete_Saved_Article(String url){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return(sqLiteDatabase.delete(TABLE_NAME,"URL = ?",new String[]{url}));

    }

}
