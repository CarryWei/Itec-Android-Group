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

/**
 * Created by mazyi on 2015/7/8 0008.
 */
public class EditActivity extends Activity {

    private EditText Edit_Content;
    private TextView date;
    private Button Delete;

    private String OriginContent;
    private String OriginTime;
    private String OriginDate;
    private String mID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        Edit_Content = (EditText) findViewById(R.id.edit_content);

        date = (TextView) findViewById(R.id.date);

        Delete = (Button) findViewById(R.id.delete);
        Delete.setOnClickListener(mOnClickListener);

        Intent intent = getIntent();
        OriginContent = intent.getStringExtra(MainActivity.sCONTENT);
        OriginDate = intent.getStringExtra(MainActivity.sDATE);
        OriginTime = intent.getStringExtra(MainActivity.sTIME);
        mID = intent.getStringExtra(MainActivity.sID);

        if(!TextUtils.isEmpty(OriginContent)){
            Edit_Content.setText(OriginContent);
            Edit_Content.setSelection(Edit_Content.length());
        }
        if(!TextUtils.isEmpty(OriginDate)){
            date.setText(OriginDate + " " + OriginTime);
        }

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete:
                    if(!deleteNote()){
                        Toast.makeText(EditActivity.this,"Opsss", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 保存笔记
     *
     * @return
     * 	保存是否成功
     */
    private boolean saveNote() {
        Time t = new Time();
        boolean isSucceed = true;
        String content = Edit_Content.getText() + "";
        if(content.length() == 0){
            deleteNote();
        }
        else{
            DBHelper dbHelper = new DBHelper(EditActivity.this);
            if(TextUtils.isEmpty(mID)) {		// 判断是插入，还是编辑笔记
                t.setToNow();
                String date = "" + t.year + "-" + (t.month+1) + "-" + t.monthDay;
                String time = "" + t.hour + ":" + t.minute + ":" + t.second;
                if( dbHelper.insertNote(content,date,time) == -1) {
                    isSucceed = false;
                }
            }else{
                t.setToNow();
                String date = "" + t.year + "-" + (t.month+1) + "-" + t.monthDay;
                String time = "" + t.hour + ":" + t.minute + ":" + t.second;
                if( dbHelper.updateNote(mID, content, date,time) < 1) {
                    isSucceed = false;
                }
            }
        }
        return isSucceed;
    }


    private boolean deleteNote() {
        boolean isSucceed = true;

        DBHelper dbHelper = new DBHelper(EditActivity.this);

        if( ! TextUtils.isEmpty(mID) ){
            isSucceed = dbHelper.deleteNote(mID);
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
                if( saveNote() ){
                    Toast.makeText(EditActivity.this,"Saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditActivity.this,"Opsss", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
