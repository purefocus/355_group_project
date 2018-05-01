package edu.vcu.cmsc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.vcu.cmsc.R;

public class SettingsActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
    }
    
    @Override
    public int getContentLayout()
    {
        return R.layout.activity_settings;
    }
    
    public void settingsClick(View v)
    {
        if (v. getId() == R.id.btn_change_pass) {
            Intent i = new Intent(this, ChangePass.class);
            startActivity(i);
        }
    }
}
