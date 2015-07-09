package com.example.mazyi.note;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.app.ActionBar;



public class MainActivity extends Activity {

    static final public String sCONTENT = "content";
    static final public String sID = "_id";
    static final public String sTIME = "time";
    static final public String sDATE = "date";

    private ListView mlist;
    private SimpleCursorAdapter mAdapter = null;
    private SimpleCursorAdapter eAdapter = null;
    private Cursor mCursor = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();

        mlist = (ListView) findViewById(R.id.list);
        mlist.setOnItemClickListener(mOnItemClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        createSimpleCursorAdapter();
    }

    public void createSimpleCursorAdapter() {
        DBHelper db = new DBHelper(this);
        mCursor = db.selectNotes();
        if (mCursor.moveToFirst()) {
            String[] from = {DBHelper.NOTE_CONTENT, DBHelper.NOTE_DATE};
            int[] to = {R.id.Item_Title, R.id.Item_Time};
            mAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.item, mCursor, from, to, 2);
            mlist.setAdapter(mAdapter);
        }
        else {
            mlist.setAdapter(eAdapter);
        }

    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.list) {
                Intent intent = new Intent();
                mCursor.moveToPosition(position);
                intent.setClass(MainActivity.this, EditActivity.class);
                intent.putExtra(DBHelper.NOTE_ID, mCursor.getString(mCursor.getColumnIndexOrThrow(DBHelper.NOTE_ID)));
                intent.putExtra(DBHelper.NOTE_CONTENT, mCursor.getString(mCursor.getColumnIndexOrThrow(DBHelper.NOTE_CONTENT)));
                intent.putExtra(DBHelper.NOTE_TIME, mCursor.getString(mCursor.getColumnIndexOrThrow(DBHelper.NOTE_TIME)));
                intent.putExtra(DBHelper.NOTE_DATE, mCursor.getString(mCursor.getColumnIndexOrThrow(DBHelper.NOTE_DATE)));
                startActivity(intent);
            }
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EditActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


