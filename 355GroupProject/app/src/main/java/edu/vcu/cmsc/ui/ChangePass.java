package edu.vcu.cmsc.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.ui.catalog.CatalogActivity;
import edu.vcu.cmsc.ui.chat.ChatThreadList;

/**
 * Created by Christine on 4/25/2018.
 */

public class ChangePass extends Activity {
    int successInput = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_pass);
    }

    public void changePassClick(View v) {
        if (v.getId() == R.id.btn_submit_new_pass) {
            if (successInput == 1) {

                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
            }
        }
    }
}