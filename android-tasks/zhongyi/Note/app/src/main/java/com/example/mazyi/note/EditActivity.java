package com.example.mazyi.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends Activity {

    private EditText editTextContent;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        String originContent;
        String originTime;
        String originDate;

        editTextContent = (EditText) findViewById(R.id.edtTxt_edit_content);
        TextView date = (TextView) findViewById(R.id.tv_edit_date);
        Button delete = (Button) findViewById(R.id.btn_edit_delete);
        delete.setOnClickListener(ButtonOnClickListener);

        Intent intent = getIntent();
        originContent = intent.getStringExtra(MainActivity.MAIN_CONTENT);
        originDate = intent.getStringExtra(MainActivity.MAIN_DATE);
        originTime = intent.getStringExtra(MainActivity.MAIN_TIME);
        id = intent.getStringExtra(MainActivity.MAIN_ID);

        if (!TextUtils.isEmpty(originContent)){
            editTextContent.setText(originContent);
            editTextContent.setSelection(editTextContent.length());
        }
        if (!TextUtils.isEmpty(originDate)){
            date.setText(originDate + " " + originTime);
        }

    }

    private View.OnClickListener ButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_edit_delete:
                    if(!deleteNote()) {
                        Toast.makeText(EditActivity.this,"Opsss", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    break;
                default:
            }
        }
    };

    private boolean saveNote() {
        Time t = new Time();
        boolean isSucceed = true;
        String content = editTextContent.getText() + "";

        if (content.length() == 0){
            deleteNote();
        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(EditActivity.this);
            if (TextUtils.isEmpty(id)) {
                t.setToNow();

                String date = "" + t.year + "-" + (t.month+1) + "-" + t.monthDay;
                String time = "" + t.hour + ":" + t.minute + ":" + t.second;

                if (databaseHelper.insertNote(content,date,time) == -1) {
                    isSucceed = false;
                }
            } else {
                t.setToNow();

                String date = "" + t.year + "-" + (t.month+1) + "-" + t.monthDay;
                String time = "" + t.hour + ":" + t.minute + ":" + t.second;

                if (databaseHelper.updateNote(id, content, date,time) < 1) {
                    isSucceed = false;
                }
            }
        }
        return isSucceed;
    }


    private boolean deleteNote() {
        boolean isSucceed = true;

        DatabaseHelper databaseHelper = new DatabaseHelper(EditActivity.this);

        if( ! TextUtils.isEmpty(id) ) {
            isSucceed = databaseHelper.deleteNote(id);
        }
        return isSucceed;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if (saveNote()) {
                    Toast.makeText(EditActivity.this,"Saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else{
                    Toast.makeText(EditActivity.this,"Opsss", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
