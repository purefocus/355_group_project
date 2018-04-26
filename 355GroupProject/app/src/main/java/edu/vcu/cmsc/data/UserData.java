package edu.vcu.cmsc.data;


import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import edu.vcu.cmsc.App;

@IgnoreExtraProperties
public class UserData
{
	
	public int permissions;
	
	public String username;
	public String email;
	
	public String firstName;
	public String lastName;
	
	public boolean approved;
	
	@Exclude
	public boolean loggedIn;
	
	@Exclude
	public App.Role role;
	
	public UserData()
	{
	
	}
	
}
