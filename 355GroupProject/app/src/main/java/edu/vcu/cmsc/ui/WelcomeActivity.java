package edu.vcu.cmsc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.ui.catalog.CatalogActivity;

public class WelcomeActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //notifications loading

    }



    //menu buttons
    public void catalogClick(View v)
    {
        if (v. getId() == R.id.catalogButton)
        {
            Intent i = new Intent(this, CatalogActivity.class);
            startActivity(i);
        }
    }

    public void calendarClick(View v)
    {
        if (v. getId() == R.id.calendarButton)
        {
            //Intent i = new Intent(this, calendarMain.class);
            //startActivity(i);
        }
    }

    public void chatClick(View v)
    {
        if (v. getId() == R.id.chatButton)
        {
            //Intent i = new Intent(this, chatMain.class);
            //startActivity(i);
        }
    }

    public void mapClick(View v)
    {
        if (v. getId() == R.id.mapButton)
        {
            Intent i = new Intent(this, mapActivity.class);
            startActivity(i);
        }
    }

    public void settingsClick(View v)
    {
        if (v. getId() == R.id.settingsButton)
        {
            Intent i = new Intent(this, settingsActivity.class);
            startActivity(i);
        }
    }
}
