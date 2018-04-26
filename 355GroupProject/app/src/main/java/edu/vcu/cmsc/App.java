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
		NEW_USER(0), DENIED(1), MEMBER(2), MODERATOR(3), ADMINISTRATOR(4);
		
		public final int perm;
		
		Role(int permLevel)
		{
			this.perm = permLevel;
		}
	}
	
	public static UserData self;
	public static boolean logged_out;
	
	
}
