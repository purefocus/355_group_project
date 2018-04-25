package edu.vcu.cmsc.ui.chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import edu.vcu.cmsc.R;

/**
 * Created by Christine on 4/25/2018.
 */

public class ChatThreadList extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_thread_list);
    }


    public void btn_make_entry(View v) { // On click of "Submit Values" button on "Enter Values" screen

        if (v.getId() == R.id.btn_create_thread) {


        }


    }
}