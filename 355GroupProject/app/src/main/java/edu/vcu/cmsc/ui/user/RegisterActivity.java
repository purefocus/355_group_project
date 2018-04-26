package edu.vcu.cmsc.ui.user;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.UserData;

public class RegisterActivity extends Activity
{
	
	private static final String TAG = "REGISTER";
	
	private boolean hasErr = false;
	private boolean unameVerify = false;
	private boolean emailVerify = false;
	private boolean passVerify = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		findViewById(R.id.field_register_username).setOnFocusChangeListener(
				(v, hasFocus) ->
				{
					if (!hasFocus)
					{
						checkExists(v, "username", "Username exists!");
					}
				});
		
		findViewById(R.id.field_register_email).setOnFocusChangeListener(
				(v, hasFocus) ->
				{
					if (!hasFocus)
					{
						checkExists(v, "email", "Email exists!");
					}
				});
		
		findViewById(R.id.field_register_password2).setOnFocusChangeListener(
				(v, hasFocus) ->
				{
					if (!hasFocus)
					{
						String pw1 = ((TextView) findViewById(R.id.field_register_password))
								             .getText().toString();
						String pw2 = ((TextView) v).getText().toString();
						boolean match = pw2.equals(pw1);
						((TextView) v).setError(match ? null : "Passwords don't match!");
						passVerify = match;
					}
				});
	}
	
	private void checkExists(View view, String field, String errMsg)
	{
		TextView textView = (TextView) view;
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		String text = textView.getText().toString();
		db.collection("login").whereEqualTo(field, text).get()
		  .addOnCompleteListener(
				  task ->
				  {
					  if (!task.getResult().isEmpty())
					  {
						  textView.setError(errMsg);
						  if (field.equals("username"))
						  { unameVerify = false; }
						  else
						  { emailVerify = false; }
						
					  }
					  else
					  {
						  textView.setError(null);
						  if (field.equals("username"))
						  { unameVerify = true; }
						  else
						  { emailVerify = true; }
					  }
				  });
	}
	
	public void btn_register(View view)
	{
		hasErr = false;
		
		// do general field checkings
		String fname = verifyField(R.id.field_register_fname);
		String lname = verifyField(R.id.field_register_lname);
		String email = verifyField(R.id.field_register_email);
		String username = verifyField(R.id.field_register_username);
		String password = verifyField(R.id.field_register_password);
		
		TextView textView = findViewById(R.id.field_register_password2);
		if (!textView.getText().toString().equals(password))
		{
			textView.setError("Passwords don't match");
		}
		else
		{
			passVerify = true;
		}
		
		if (hasErr || !unameVerify || !emailVerify || !passVerify)
		{
			Log.d(TAG, "Error detected! " + hasErr + " : " + unameVerify + " : " + emailVerify + " : " + passVerify);
			return;
		}
//		enableAll(false);
		
		
		registerUser(fname, lname, username, email, password);
		
	}

//	private void enableAll(boolean en)
//	{
//		findViewById(R.id.field_register_fname).setEnabled(en);
//		findViewById(R.id.field_register_lname).setEnabled(en);
//		findViewById(R.id.field_register_email).setEnabled(en);
//		findViewById(R.id.field_register_username).setEnabled(en);
//		findViewById(R.id.field_register_password).setEnabled(en);
//		findViewById(R.id.field_register_password2).setEnabled(en);
//	}
	
	private String verifyField(int id)
	{
		TextView textView = findViewById(id);
		String text = textView.getText().toString();
		if (text.length() == 0)
		{
			textView.setError("Required Field");
			hasErr = true;
		}
		else
		{
			textView.setError(null);
		}
		
		return text;
	}
	
	private void registerUser(String fname, String lname, String username, String email, String password)
	{
		FirebaseAuth auth = FirebaseAuth.getInstance();
		auth.createUserWithEmailAndPassword(LoginActivity.usernameToEmail(username), password)
		    .addOnCompleteListener(
				    task ->
				    {
					    if (task.isSuccessful())
					    {
					    	
						    FirebaseFirestore db = FirebaseFirestore.getInstance();
						    UserData user = new UserData();
						    user.username = username;
						    user.firstName = fname;
						    user.lastName = lname;
						    user.email = email;
						    user.permissions = 0;
						    db.collection("users").add(user);
						    
						
//						    Map<String, Object> userData = new HashMap<>();
//						    userData.put("first_name", fname);
//						    userData.put("last_name", lname);
//						    userData.put("email", email);
//						    userData.put("username", username);
//						    userData.put(UserData)
						
//						    db.collection("users").add(userData);
//						    userData.clear();
//
//						    userData.put("username", username);
//						    userData.put("password", password);
//						    userData.put("approved", false);
//						    db.collection("login").add(userData);
//
						    finish();
					    }
					    else
					    {
						    task.getException().printStackTrace();
						    Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_LONG)
						         .show();
					    }
				    });
		
		
	}
}
