package com.example.mazyi.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mazyi on 2015/7/10 0010.
 */
public class NoteAdapter extends BaseAdapter {

    private List<NoteItem> noteItems;
    private int resource;
    private Context context;
    private LayoutInflater inflator;
    private TextView noteTitle;
    private TextView noteTime;


    public NoteAdapter(Context context,List<NoteItem> noteItems,int resource){
        this.context = context;
        this.noteItems = noteItems;
        this.resource = resource;
    }


    @Override
    public int getCount() {
        return noteItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(resource, null);
            noteTitle = (TextView)convertView.findViewById(R.id.tv_item_title);
            noteTime =(TextView) convertView.findViewById(R.id.tv_item_time);
        }

        NoteItem noteItem = noteItems.get(position);
        noteTitle.setText(noteItem.getContent());
        noteTime.setText(noteItem.getTime());

        return convertView;
    }
}
