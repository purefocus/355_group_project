package edu.vcu.cmsc.ui.chat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.ui.BaseActivity;

public class ThreadPostActivity extends BaseActivity
{

	private ThreadFragment mthread;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);


		setContentView(R.layout.activity_thread_post);

		String author = getIntent().getStringExtra("author");

		long posttime = getIntent().getLongExtra("time",0);

		String message = getIntent().getStringExtra("message");
		((TextView)findViewById(R.id.thread_post_author)).setText(author);
		((TextView)findViewById(R.id.thread_post_time)).setText(new Date(posttime).toString());
		((TextView)findViewById(R.id.thread_post_message)).setText(message);


	}

	@Override
	public int getContentLayout()
	{
		return R.layout.activity_thread_post;
	}
}
