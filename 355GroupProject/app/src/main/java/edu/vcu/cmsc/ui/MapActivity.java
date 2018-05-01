package edu.vcu.cmsc.ui;

import android.app.Activity;
import android.os.Bundle;

import edu.vcu.cmsc.R;

public class MapActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //notifications loading

    }
    
    @Override
    public int getContentLayout()
    {
        return R.layout.activity_map;
    }
}
