package edu.vcu.cmsc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends Activity
{
	private static final String TAG = "LOGIN";
	
	private AlertDialog popup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		boolean remember = pref.getBoolean("login.remember", false);
		((CheckBox) findViewById(R.id.check_login_remember)).setChecked(remember);
		
		if (remember)
		{
			String username = pref.getString("login.username", "");
			String password = pref.getString("login.password", "");
			
			((TextView) findViewById(R.id.field_login_username)).setText(username);
			((TextView) findViewById(R.id.field_login_password)).setText(password);
			
			login(username, password);
		}
		
	}
	
	public void btn_login(View view)
	{
		String username = ((TextView) findViewById(R.id.field_login_username)).getText().toString();
		String password = ((TextView) findViewById(R.id.field_login_password)).getText().toString();
		
		login(username, password);
		enableAll(false);
		
		AlertDialog.Builder d = new AlertDialog.Builder(this);
		d.setMessage("Logging in...");
		popup = d.create();
		popup.show();
	}
	
	public void btn_register(View view)
	{
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	
	private void enableAll(boolean enable)
	{
		findViewById(R.id.btn_login_login).setEnabled(enable);
		findViewById(R.id.btn_login_register).setEnabled(enable);
		findViewById(R.id.field_login_username).setEnabled(enable);
		findViewById(R.id.field_login_password).setEnabled(enable);
	}
	
	private void setResultMessage(String msg)
	{
		((TextView) findViewById(R.id.label_login_result)).setText(msg);
	}
	
	private void login(String username, String password)
	{
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		
		db.collection("login")
		  .whereEqualTo("username", username)
		  .whereEqualTo("password", password)
		  .get().addOnCompleteListener(
				task ->
				{
					boolean exists = false;
					boolean approved = false;
					if (task.isSuccessful())
					{
						for (DocumentSnapshot doc : task.getResult())
						{
							exists = true;
							approved = doc.getBoolean("approved");
						}
						if (popup != null)
						{
							popup.dismiss();
						}
						enableAll(true);
						
						if (!exists)
						{
							setPref(username, password, false);
							onLoginFailure();
						}
						else
						{
							setPref(username, password, true);
							if (approved)
							{
								onLoginSuccess();
							}
							else
							{
								onApprovalFailure();
							}
						}
					}
				});
	}
	
	private void setPref(String uname, String pass, boolean allowSet)
	{
		boolean remember = ((CheckBox) findViewById(R.id.check_login_remember)).isChecked();
		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean("login.remember", remember);
		if (remember && allowSet)
		{
			editor.putString("login.username", uname);
			editor.putString("login.password", pass);
		}
		else if (!remember)
		{
			editor.remove("login.remember");
			editor.remove("login.username");
			editor.remove("login.password");
		}
		editor.apply();
	}
	
	private void onLoginSuccess()
	{
		setResultMessage(" ");
		Intent intent = new Intent(this, WelcomeActivity.class);
		startActivity(intent);
	}
	
	public void onApprovalFailure()
	{
		setResultMessage("You haven't been approved yet!");
		new AlertDialog.Builder(this)
				.setTitle("You have not been approved!")
				.setMessage("Please wait for an administrator to approve your account.")
				.setPositiveButton("OK", (d, w) -> d.dismiss())
				.create()
				.show();
	}
	
	public void onLoginFailure()
	{
		setResultMessage("Invalid username or password!");
	}
}
