package edu.vcu.cmsc.app;


public interface LoginPage
{
	
	
	void attemptLogin(String username, String password);
	
	void onLoginResult(int result);
	
	
}
