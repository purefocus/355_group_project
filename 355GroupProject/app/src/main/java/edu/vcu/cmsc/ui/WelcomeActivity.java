package edu.vcu.cmsc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.vcu.cmsc.App;
import edu.vcu.cmsc.R;
import edu.vcu.cmsc.ui.catalog.CatalogActivity;
import edu.vcu.cmsc.ui.chat.ChatThreadList;
import edu.vcu.cmsc.ui.user.LoginActivity;
import edu.vcu.cmsc.ui.user.UserApprovalActivity;

public class WelcomeActivity extends BaseActivity
{
	
	private static final int REQUEST_LOGIN = 1;
	
	private FirebaseAuth mAuth;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_welcome);
		
//		mAuth = FirebaseAuth.getInstance();
//
//		if (mAuth.getCurrentUser() == null || App.self == null)
//		{
//			Intent intent = new Intent(this, LoginActivity.class);
//
//			startActivityForResult(intent, REQUEST_LOGIN);
//		}
	}
	
	@Override
	public int getContentLayout()
	{
		return R.layout.activity_welcome;
	}
	
//
//	public void onStart()
//	{
//		super.onStart();
//
//		if(App.self != null && App.self.permissions > App.Role.MODERATOR.perm)
//		{
//			findViewById(R.id.btn_user_app).setVisibility(View.VISIBLE);
//			findViewById(R.id.btn_user_app).setEnabled(true);
//		}
//		else
//		{
//			findViewById(R.id.btn_user_app).setVisibility(View.INVISIBLE);
//			findViewById(R.id.btn_user_app).setEnabled(false);
//		}
//	}
//
//
//	public void onActivityResult(int request, int result, Intent data)
//	{
//		if (request == REQUEST_LOGIN)
//		{
//			if (result == RESULT_OK)
//			{
//				Toast.makeText(this, "Logged in!", Toast.LENGTH_LONG).show();
//			}
//			else
//			{
//				Intent intent = new Intent(this, LoginActivity.class);
//				startActivityForResult(intent, REQUEST_LOGIN);
//			}
//		}
//	}
//
//	//menu buttons
//	public void catalogClick(View v)
//	{
//		if (v.getId() == R.id.catalogButton)
//		{
//			Intent i = new Intent(this, CatalogActivity.class);
//			startActivity(i);
//		}
//	}
//
//	public void calendarClick(View v)
//	{
//		if (v.getId() == R.id.calendarButton)
//		{
//			//Intent i = new Intent(this, calendarMain.class);
//			//startActivity(i);
//		}
//	}
//
//	public void chatClick(View v)
//	{
//		if (v.getId() == R.id.chatButton)
//		{
//			Intent i = new Intent(this, ChatThreadList.class);
//			startActivity(i);
//		}
//	}
//
//	public void mapClick(View v)
//	{
//		if (v.getId() == R.id.mapButton)
//		{
//			Intent i = new Intent(this, MapActivity.class);
//			startActivity(i);
//		}
//	}
//
//	public void settingsClick(View v)
//	{
//		if (v.getId() == R.id.settingsButton)
//		{
//			Intent i = new Intent(this, SettingsActivity.class);
//			startActivity(i);
//		}
//	}
//
//	public void btn_approve(View view)
//	{
//		Intent intent = new Intent(this, UserApprovalActivity.class);
//		startActivity(intent);
//	}
//
//	public void btn_logout(View view)
//	{
//		mAuth.signOut();
//		App.self = null;
//		App.logged_out = true;
//
//		Intent intent = new Intent(this, LoginActivity.class);
//		startActivityForResult(intent, REQUEST_LOGIN);
//
//	}
}
