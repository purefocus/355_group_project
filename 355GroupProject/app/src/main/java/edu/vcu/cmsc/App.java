package edu.vcu.cmsc;

import edu.vcu.cmsc.data.UserData;

public class App
{
	
	public enum UserResult
	{
		FAILED, SUCCESS, UNAUTHORIZED;
		
		UserResult()
		{
		
		}
	}
	
	public enum Role
	{
		NEW_USER, MEMBER, MODERATOR, ADMINISTRATOR;
		
		Role()
		{
		
		}
	}
	
	public static UserData self;
	public static boolean isLoggedIn;
	
	
}
