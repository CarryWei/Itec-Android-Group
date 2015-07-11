package com.example.mazyi.note;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;


public class MainActivity extends Activity {

    static final public String MAIN_CONTENT = "content";
    static final public String MAIN_ID = "_id";
    static final public String MAIN_TIME = "time";
    static final public String MAIN_DATE = "date";

    private ListView list;
    private Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.lv_main_list);
        list.setOnItemClickListener(ListOnItemClickListener);
        list.setOnItemLongClickListener(ListOnItemLongClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //createSimpleCursorAdapter();
        setNoteAdapter();
    }

    private void setNoteAdapter() {

        DatabaseHelper database = new DatabaseHelper(this);
        cursor = database.selectNotes();
        cursor.moveToFirst();
        List<NoteItem> noteItems;

        noteItems = database.getAllNote();
        NoteAdapter noteAdapter = new NoteAdapter(this, noteItems, R.layout.item);
        list.setAdapter(noteAdapter);
    }

    public void createSimpleCursorAdapter() {
        DatabaseHelper db = new DatabaseHelper(this);
        cursor = db.selectNotes();

        if (cursor.moveToFirst()) {
            String[] from = {DatabaseHelper.NOTE_CONTENT, DatabaseHelper.NOTE_DATE};
            int[] to = {R.id.tv_item_title, R.id.tv_item_time};
            SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.item, cursor, from, to, 2);
            list.setAdapter(mAdapter);
        } else {
            list.setAdapter(null);
        }

    }

    private AdapterView.OnItemClickListener ListOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.lv_main_list) {
                cursor.moveToPosition(position);

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EditActivity.class);
                intent.putExtra(DatabaseHelper.NOTE_ID, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NOTE_ID)));
                intent.putExtra(DatabaseHelper.NOTE_CONTENT, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NOTE_CONTENT)));
                intent.putExtra(DatabaseHelper.NOTE_TIME, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NOTE_TIME)));
                intent.putExtra(DatabaseHelper.NOTE_DATE, cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NOTE_DATE)));
                startActivity(intent);
            }
        }
    };

    private AdapterView.OnItemLongClickListener ListOnItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            cursor.moveToPosition(position);
            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
            databaseHelper.deleteNote(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NOTE_ID)));
            onResume();
            return true;
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


