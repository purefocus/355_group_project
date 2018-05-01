package edu.vcu.cmsc.ui.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.database.tables.CatalogEntryTable;
import edu.vcu.cmsc.database.tables.ThreadTable;
import edu.vcu.cmsc.ui.BaseActivity;
import edu.vcu.cmsc.ui.catalog.CatalogAdapter;
import edu.vcu.cmsc.ui.catalog.CatalogEntryActivity;

/**
 * Created by Christine on 4/25/2018.
 */

public class ChatThreadList extends BaseActivity implements ThreadAdapter.OnThreadClickedListener
{
	
	RecyclerView mRecyclerView;
	
	private Query mQuery;
	
	private ThreadAdapter mAdapter;
	
	private FirebaseFirestore mFirestore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_chat_thread_list);
		
		mRecyclerView = findViewById(R.id.list_threads);
		
		mFirestore = FirebaseFirestore.getInstance();
		mQuery = mFirestore.collection(ThreadTable.TABLE_TITLE_ALL);
		
		mAdapter = new ThreadAdapter(mQuery, this);
		
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(mAdapter);
	}
	
	
	@Override
	public void onStart()
	{
		super.onStart();
		if (mAdapter != null)
		{
			mAdapter.startListening();
		}
	}
	
	@Override
	public int getContentLayout()
	{
		return R.layout.activity_chat_thread_list;
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		if (mAdapter != null)
		{
			mAdapter.stopListening();
		}
	}
	
	public void btn_make_entry(View v)
	{ // On click of "Submit Values" button on "Enter Values" screen
		
		if (v.getId() == R.id.btn_create_thread)
		{
		
		
		
		}
		
	}
	
	@Override
	public void onThreadClicked(DocumentSnapshot item)
	{
		Intent intent = new Intent(this, ThreadPostActivity.class);
		intent.putExtra("entryKey", item.getId());
		
		startActivity(intent);
	}
}
