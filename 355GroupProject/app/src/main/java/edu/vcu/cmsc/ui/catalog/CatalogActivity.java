package edu.vcu.cmsc.ui.catalog;

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
import edu.vcu.cmsc.ui.BaseActivity;

public class CatalogActivity extends BaseActivity implements CatalogAdapter.CatalogItemSelectedListener
{
	
	RecyclerView mRecyclerView;
	
	private Query mQuery;
	
	private CatalogAdapter mAdapter;
	
	private FirebaseFirestore mFirestore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_catalog);
		
		mRecyclerView = findViewById(R.id.cat_item_list);
		
		mFirestore = FirebaseFirestore.getInstance();
		mQuery = mFirestore.collection(CatalogEntryTable.TABLE_TITLE);
		
		mAdapter = new CatalogAdapter(mQuery, this);
		
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
		return R.layout.activity_catalog;
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
	
	public void btn_new(View view)
	{
		//TODO: Add new catalog entry activity here
		Intent intent = new Intent(this, CatalogEntryNew.class);
		startActivity(intent);
	}
	
	@Override
	public void onCatalogItemSelected(DocumentSnapshot item)
	{
		Intent intent = new Intent(this, CatalogEntryActivity.class);
		intent.putExtra("entryKey", item.getId());
		
		startActivity(intent);
	}
}
