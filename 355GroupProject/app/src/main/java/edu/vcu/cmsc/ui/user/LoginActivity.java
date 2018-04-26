package edu.vcu.cmsc.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import edu.vcu.cmsc.App;
import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.UserData;
import edu.vcu.cmsc.database.tables.LoginTable;
import edu.vcu.cmsc.ui.WelcomeActivity;


public class LoginActivity extends Activity
{
	private static final String TAG = "LOGIN";
	
	
	private AlertDialog popup;
	
	private FirebaseAuth mAuth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		boolean remember = pref.getBoolean("login.remember", false);
		((CheckBox) findViewById(R.id.check_login_remember)).setChecked(remember);
		
		mAuth = FirebaseAuth.getInstance();
		
		if (remember)
		{
			String username = pref.getString("login.username", "");
			String password = pref.getString("login.password", "");
			
			((TextView) findViewById(R.id.field_login_username)).setText(username);
			((TextView) findViewById(R.id.field_login_password)).setText(password);

			login(username, password);
		}
		
	}
	
	@Override
	public void onBackPressed()
	{
		// don't allow the user to back out of login screen
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
		
		mAuth.signInWithEmailAndPassword(usernameToEmail(username), password).addOnCompleteListener(
				task ->
				{
					if(popup != null)
					{
						popup.dismiss();
					}
					enableAll(true);
					if (task.isSuccessful())
					{
						setPref(username, password, true);
						onLoginSuccess(username);
					}
					else
					{
						setPref(username, password, false);
						onLoginFailure();
//						Toast.makeText(this, "Invalid email/password", Toast.LENGTH_LONG).show();
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
	
	private void onLoginSuccess(String uname)
	{
		setResultMessage(" ");
//		Intent intent = new Intent(this, WelcomeActivity.class);
//		startActivity(intent);
		
		FirebaseFirestore.getInstance().collection("users")
		                 .whereEqualTo(LoginTable.COL_USERNAME, uname)
		                 .get().addOnCompleteListener(
				task ->
				{
					if (task.isSuccessful())
					{
						for (DocumentSnapshot snap : task.getResult().getDocuments())
						{
							App.self = snap.toObject(UserData.class);
							if(App.self.approved)
							{
								onApprovalFailure();
							}
							else
							{
								App.isLoggedIn = true;
								finish();
							}
						}
					}
					else
					{
						App.isLoggedIn = false;
						Toast.makeText(this, "Error logging in?", Toast.LENGTH_LONG).show();
					}
				});
		
		setResult(RESULT_OK);
		finish();
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
	
	public static String usernameToEmail(String username)
	{
		if(!username.contains("@"))
		{
			return username + "@meh.com";
		}
		return username;
	}
	
	public void onLoginFailure()
	{
		setResultMessage("Invalid username or password!");
	}
}
