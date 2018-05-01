package edu.vcu.cmsc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.vcu.cmsc.App;
import edu.vcu.cmsc.R;
import edu.vcu.cmsc.ui.catalog.CatalogActivity;
import edu.vcu.cmsc.ui.chat.ChatThreadList;
import edu.vcu.cmsc.ui.user.LoginActivity;
import edu.vcu.cmsc.ui.user.UserApprovalActivity;

public abstract class BaseActivity extends AppCompatActivity
{
	
	private static final int REQUEST_LOGIN = 1;
	
	protected DrawerLayout mDrawer;
	protected NavigationView mNav;
	protected FrameLayout mContentView;
	
	protected FirebaseAuth mAuth;
	
	protected ActionBar mActionBar;
	
	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.base_activity_layout);
		
		mDrawer = findViewById(R.id.drawer_layout);
		mNav = findViewById(R.id.nav_view);
		mContentView = findViewById(R.id.content_frame);
		mAuth = FirebaseAuth.getInstance();
		
		mAuth = FirebaseAuth.getInstance();
		
		if (mAuth.getCurrentUser() == null || App.self == null)
		{
			Intent intent = new Intent(this, LoginActivity.class);
			
			startActivityForResult(intent, REQUEST_LOGIN);
		}
		
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
		
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View content = inflater.inflate(getContentLayout(), null, false);
		
		mContentView.addView(content, 0);
		
		mNav.setNavigationItemSelectedListener(
				item ->
				{
					item.setChecked(true);
					Intent i = null;
					switch (item.getItemId())
					{
						case R.id.nav_catalog:
							i = new Intent(this, CatalogActivity.class);
							startActivity(i);
							break;
						case R.id.nav_chat:
							i = new Intent(this, ChatThreadList.class);
							startActivity(i);
							break;
						case R.id.nav_trips:
							break;
						case R.id.nav_approve:
							i = new Intent(this, UserApprovalActivity.class);
							startActivity(i);
							break;
						case R.id.nav_settings:
							i = new Intent(this, SettingsActivity.class);
							startActivity(i);
							break;
						case R.id.nav_logout:
							mAuth.signOut();
							App.self = null;
							App.logged_out = true;
							
							Intent intent = new Intent(this, LoginActivity.class);
							startActivityForResult(intent, REQUEST_LOGIN);
							break;
						
					}
					return true;
				});
		
	}
	
	public void onStart()
	{
		super.onStart();
		
		
		if(App.self != null && App.self.permissions > App.Role.MODERATOR.perm)
		{
			mNav.getMenu().findItem(R.id.nav_approve).setVisible(true);
		}
		else
		{
			mNav.getMenu().findItem(R.id.nav_approve).setVisible(false);
		}
	}
	
	public void onActivityResult(int request, int result, Intent data)
	{
		if (request == REQUEST_LOGIN)
		{
			if (result == RESULT_OK)
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
	
	public abstract int getContentLayout();
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case 16908332:
				mDrawer.openDrawer(GravityCompat.START);
				return true;
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
