package com.kaushlendraprajapati.internshalaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public  class DatabaseClass extends SQLiteOpenHelper {

    private static final String Database_Name="notes.db";
    private static final String Table_Name="MyNotes";
     static  final String Col_1= "id";
     static final String Col_2= "title";
     static final String Col_3= "content";


    public DatabaseClass(Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Table_Name+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        onCreate(db);
    }

    public boolean isInsert(String title,String content){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2,title);
        contentValues.put(Col_3,content);

        long data = database.insert(Table_Name,null,contentValues);
        if (data==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor readData()
    {
        SQLiteDatabase database= this.getReadableDatabase();
        Cursor result = database.rawQuery("select * from "+Table_Name,null);
        return result;
    }

    public boolean isUpdate(String id,String title,String content){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1,id);
        contentValues.put(Col_2,title);
        contentValues.put(Col_3,content);
        database.update(Table_Name,contentValues,"id=?",new String[]{id});
        return true;
    }

    public boolean deleteNote(String id){
        SQLiteDatabase database = this.getWritableDatabase();
         database.delete(Table_Name,"id=?",new String[]{id});
         return true;
    }
}
