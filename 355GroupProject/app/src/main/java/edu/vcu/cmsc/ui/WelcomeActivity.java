package edu.vcu.cmsc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.ui.catalog.CatalogActivity;

public class WelcomeActivity extends Activity
{
	
	
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
			Intent i = new Intent(this, MapActivity.class);
			startActivity(i);
		}
	}
	
	public void settingsClick(View v)
	{
		if (v. getId() == R.id.settingsButton)
		{
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
		}
	}
}
