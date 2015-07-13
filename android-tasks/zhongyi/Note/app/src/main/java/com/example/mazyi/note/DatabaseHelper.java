package com.example.mazyi.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mazyi on 2015/7/8 0008.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "Note";
    private final static int DATABASE_VERSION = 1;

    private final static String TABLE_NAME = "note";

    public final static String NOTE_ID = "_id";
    public final static String NOTE_TIME = "time";
    public final static String NOTE_DATE = "date";
    public final static String NOTE_CONTENT = "content";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String sql = "create table "
                + TABLE_NAME + " ("
                + NOTE_ID + " integer primary key autoincrement, "
                + NOTE_TIME + " text, "
                + NOTE_DATE + " date, "
                + NOTE_CONTENT + " text )";

        database.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public Cursor selectNotes(){
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    public long insertNote(String content,String date,String time){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put( NOTE_CONTENT, content);
        cv.put( NOTE_DATE, date);
        cv.put( NOTE_TIME, time);

        long rowId = database.insert(TABLE_NAME, null, cv);
        database.close();

        return rowId;
    }

    public boolean deleteNote(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String where = NOTE_ID + "=?";
        String[] whereValues = {
                id
        };

        boolean is =  (database.delete( TABLE_NAME, where, whereValues) > 0);
        database.close();

        return is;
    }

    public int updateNote(String id, String content,String date,String time){
        SQLiteDatabase database = this.getWritableDatabase();
        String where = NOTE_ID +"=?";
        String[] whereValues = {
                id
        };

        ContentValues cv = new ContentValues();
        cv.put( NOTE_CONTENT, content);
        cv.put( NOTE_DATE, date);
        cv.put( NOTE_TIME, time);

        int numRow = database.update( TABLE_NAME, cv, where, whereValues);
        database.close();

        return numRow;
    }

    public List<NoteItem> getAllNote() {
        SQLiteDatabase database = this.getReadableDatabase();

        if(database.isOpen()){

            //罗列出要选出的字段
            String[] columns = { NOTE_ID, NOTE_DATE, NOTE_TIME, NOTE_CONTENT};

            Cursor cursor = database.query( TABLE_NAME, columns, null, null, null, null, null);
            List<NoteItem> NoteList = new ArrayList<NoteItem>();

            if(cursor!=null && cursor.getCount()>0){
                String content;
                String time;
                while(cursor.moveToNext()){
                    content = cursor.getString(3);
                    time = cursor.getString(1);
                    NoteItem noteItem = new NoteItem(content,time);
                    NoteList.add(noteItem);
                }
            }

            cursor.close();
            database.close();
            return NoteList;

        }

        return null;
    }

}