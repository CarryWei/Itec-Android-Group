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

    /*构造函数*/
    public DBHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*创建数据库*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 定义SQL语句
        String sql = "create table "+TABLE_NAME+" ("
                +NOTE_ID+" integer primary key autoincrement, "
                +NOTE_TIME+" text, "
                +NOTE_CONTENT+" text )";
        // 直接执行 sql 语句
        db.execSQL(sql);
    }

    /*更新数据库*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 暂无操作
    }

    /**查询记事本表中的内容
     */
    public Cursor selectNotes(){
        // 实例化一个 SQLiteDatabase 对象
        SQLiteDatabase db = this.getReadableDatabase();
        // 获取一个指向数据库的游标，用来查询数据库
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }



    /**插入记事
     * */
    public long insertNote(String content,String time){
        // 实例化一个 SQLiteDatabase 对象
        SQLiteDatabase db = this.getWritableDatabase();
		/*
		 * 将需要修改的数据放在 ContentValues 对象中
		 * ContentValues 是以键值对形式储存数据，其中键是数据库的列名，值是列名对应的数据
		 * */
        ContentValues cv = new ContentValues();
        cv.put(NOTE_CONTENT, content);
        cv.put(NOTE_TIME, time);
        // insert()方法：插入数据，成功返回行数，否则返回-1
        long rowid = db.insert(TABLE_NAME, null, cv);
        db.close();
        return rowid;
    }

    /**删除记事
     * @param id
     * _id字段
     * */
    public boolean deleteNote(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = NOTE_ID + "=?";
        String[] whereValues = {id};
        // delete方法：根据条件删除数据，where表示删除的条件
        boolean is =  (db.delete(TABLE_NAME, where, whereValues) > 0);
        db.close();
        return is;
    }

    /**更新记事
     * @param id
     * _id字段
     * */
    public int updateNote(String id, String content,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = NOTE_ID+"=?";
        String[] whereValues = {id};
        ContentValues cv = new ContentValues();
        cv.put(NOTE_CONTENT, content);
        cv.put(NOTE_TIME, time);
        // update()方法：根据条件更新数据库，cv保存更新后的数据，where为更新条件
        int numRow = db.update(TABLE_NAME, cv, where, whereValues);
        db.close();
        return numRow;
    }
}