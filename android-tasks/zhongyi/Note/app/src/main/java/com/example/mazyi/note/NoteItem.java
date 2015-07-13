package com.example.mazyi.note;

import java.lang.ref.SoftReference;

/**
 * Created by mazyi on 2015/7/10 0010.
 */
public class NoteItem {

        private String time;
        private String content;

        public NoteItem(String content, String time) {
            super();
            this.content = content;
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public String getContent() {
            return content;
        }

}
