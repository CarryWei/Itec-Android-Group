package com.example.mazyi.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mazyi on 2015/7/8 0008.
 */
public class EditActivity extends Activity{

    private EditText Edit_Content;
    private Button Save;
    private Button Cancel;
    private Button Delete;

    private String OriginContent;
    private String OriginTime;
    private String mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Edit_Content = (EditText) findViewById(R.id.edit_content);
        Save = (Button) findViewById(R.id.save);
        Cancel = (Button) findViewById(R.id.cancel);
        Delete = (Button) findViewById(R.id.delete);
        Save.setOnClickListener(mOnClickListener);
        Cancel.setOnClickListener(mOnClickListener);
        Delete.setOnClickListener(mOnClickListener);

        Intent intent = getIntent();
        OriginContent = intent.getStringExtra(MainActivity.sCONTENT);
        OriginTime = intent.getStringExtra(MainActivity.sTIME);
        mID = intent.getStringExtra(MainActivity.sID);

        if(!TextUtils.isEmpty(OriginContent)){
            Edit_Content.setText(OriginContent);
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.save:
                    if( saveNote() ){
                        Toast.makeText(EditActivity.this,"Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditActivity.this,"Opsss", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.cancel:
                    finish();
                    break;
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
        String content = Edit_Content.getText()+"";
        DBHelper dbHelper = new DBHelper(EditActivity.this);
        if(TextUtils.isEmpty(mID)) {		// 判断是插入，还是编辑笔记
            t.setToNow();
            String time = "" + t.year + "-" + t.month + "-" + t.monthDay;
            if( dbHelper.insertNote(content,time) == -1) {
                isSucceed = false;
            }
        }else{
            t.setToNow();
            String time = "" + t.year + "-" + t.month + "-" + t.monthDay;
            if( dbHelper.updateNote(mID,content,time) < 1) {
                isSucceed = false;
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

}
