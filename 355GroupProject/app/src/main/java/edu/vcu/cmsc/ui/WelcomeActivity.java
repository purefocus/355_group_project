package edu.vcu.cmsc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.ui.catalog.CatalogActivity;
import edu.vcu.cmsc.ui.chat.ChatThreadList;
import edu.vcu.cmsc.ui.user.LoginActivity;

public class WelcomeActivity extends Activity
{
	
	private static final int REQUEST_LOGIN = 1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		if(!LoginActivity.LOGGED_IN)
		{
			Intent intent = new Intent(this, LoginActivity.class);
			
			startActivityForResult(intent, REQUEST_LOGIN);
		}
		
		
		//notifications loading
		
	}
	
	
	public void onActivityResult(int request, int result, Intent data)
	{
		if(request == REQUEST_LOGIN)
		{
			if(result == RESULT_OK)
			{
				Toast.makeText(this, "Logged in!", Toast.LENGTH_LONG).show();
			}
			else
			{
				Intent intent = new Intent(this, LoginActivity.class);
				startActivityForResult(intent, REQUEST_LOGIN);
			}
		}
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
			Intent i = new Intent(this, ChatThreadList.class);
			startActivity(i);
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
