package com.example.mazyi.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mazyi on 2015/7/8 0008.
 */
public class DBHelper extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "Note";
    private final static int DATABASE_VERSION = 1;

    private final static String TABLE_NAME = "note";
    public final static String NOTE_ID = "_id";
    public final static String NOTE_TIME = "time";
    public final static String NOTE_CONTENT = "content";

    /*���캯��*/
    public DBHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*�������ݿ�*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        // ����SQL���
        String sql = "create table "+TABLE_NAME+" ("
                +NOTE_ID+" integer primary key autoincrement, "
                +NOTE_TIME+" text, "
                +NOTE_CONTENT+" text )";
        // ֱ��ִ�� sql ���
        db.execSQL(sql);
    }

    /*�������ݿ�*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // ���޲���
    }

    /**��ѯ���±����е�����
     */
    public Cursor selectNotes(){
        // ʵ����һ�� SQLiteDatabase ����
        SQLiteDatabase db = this.getReadableDatabase();
        // ��ȡһ��ָ�����ݿ���α꣬������ѯ���ݿ�
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }



    /**�������
     * */
    public long insertNote(String content,String time){
        // ʵ����һ�� SQLiteDatabase ����
        SQLiteDatabase db = this.getWritableDatabase();
		/*
		 * ����Ҫ�޸ĵ����ݷ��� ContentValues ������
		 * ContentValues ���Լ�ֵ����ʽ�������ݣ����м������ݿ��������ֵ��������Ӧ������
		 * */
        ContentValues cv = new ContentValues();
        cv.put(NOTE_CONTENT, content);
        cv.put(NOTE_TIME, time);
        // insert()�������������ݣ��ɹ��������������򷵻�-1
        long rowid = db.insert(TABLE_NAME, null, cv);
        db.close();
        return rowid;
    }

    /**ɾ������
     * @param id
     * _id�ֶ�
     * */
    public boolean deleteNote(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = NOTE_ID + "=?";
        String[] whereValues = {id};
        // delete��������������ɾ�����ݣ�where��ʾɾ��������
        boolean is =  (db.delete(TABLE_NAME, where, whereValues) > 0);
        db.close();
        return is;
    }

    /**���¼���
     * @param id
     * _id�ֶ�
     * */
    public int updateNote(String id, String content,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = NOTE_ID+"=?";
        String[] whereValues = {id};
        ContentValues cv = new ContentValues();
        cv.put(NOTE_CONTENT, content);
        cv.put(NOTE_TIME, time);
        // update()���������������������ݿ⣬cv������º�����ݣ�whereΪ��������
        int numRow = db.update(TABLE_NAME, cv, where, whereValues);
        db.close();
        return numRow;
    }
}