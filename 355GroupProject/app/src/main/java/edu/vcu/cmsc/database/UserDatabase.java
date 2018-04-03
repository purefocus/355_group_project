package edu.vcu.cmsc.database;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import edu.vcu.cmsc.App;

import static edu.vcu.cmsc.database.tables.LoginTable.*;
import static edu.vcu.cmsc.database.tables.UserTable.*;

public class UserDatabase
{
	
	
	public void attemptLogin(String username, String password, OnLoginAttempt callback)
	{
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		
		db.collection(TABLE_TITLE)
		  .whereEqualTo(COL_USERNAME, username)
		  .whereEqualTo(COL_PASSWORD, password)
		  .get().addOnCompleteListener(
				task ->
				{
					if (task.isSuccessful())
					{
						QuerySnapshot snapshot = task.getResult();
						if (snapshot.isEmpty())
						{
							callback.onLoginAttempt(username, App.UserResult.FAILED);
						}
						else
						{
							DocumentSnapshot doc = snapshot.getDocuments().get(0);
							String perms = doc.getString(COL_PERMISSIONS);
							App.Role role = App.Role.valueOf(perms);
							
						}
					}
				});
		
	}
	
	public void loadUserInfo(String username)
	{
		
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		db.collection(USERS_TABLE)
		  .whereEqualTo(COL_USERNAME, username)
		  .get().addOnCompleteListener(
				task ->
				{
					
				});
		
	}
	
	public interface OnLoginAttempt
	{
		
		void onLoginAttempt(String username, App.UserResult result);
		
	}
	
}
