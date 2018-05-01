package edu.vcu.cmsc.ui.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.ui.BaseActivity;

public class ThreadPostActivity extends BaseActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread_post);
	}
	
	@Override
	public int getContentLayout()
	{
		return R.layout.activity_thread_post;
	}
}
