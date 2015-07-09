package com.example.mazyi.note;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {

    static final public String sCONTENT = "content";
    static final public String sID = "_id";
    static final public String sTIME = "time";


    private ListView mlist;
    private SimpleCursorAdapter mAdapter = null;
    private SimpleCursorAdapter eAdapter = null;
    private Cursor mCursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlist = (ListView) findViewById(R.id.list);
        mlist.setOnItemClickListener(mOnItemClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        createSimpleCursorAdapter();
        Log.wtf("Resume","End");
    }

    public void createSimpleCursorAdapter() {
        DBHelper db = new DBHelper(this);
        mCursor = db.selectNotes();
        if (mCursor.moveToFirst()) {
            String[] from = {DBHelper.NOTE_CONTENT, DBHelper.NOTE_TIME};
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
                startActivity(intent);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.add:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}


