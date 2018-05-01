package edu.vcu.cmsc.ui.chat;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import edu.vcu.cmsc.App;
import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.ChatPostData;
import edu.vcu.cmsc.database.tables.CatalogEntryTable;
import edu.vcu.cmsc.database.tables.ThreadTable;
import edu.vcu.cmsc.ui.catalog.CatalogAdapter;

public class ThreadFragment extends Fragment implements View.OnClickListener
{
	
	private RecyclerView mRecyclerView;
	private TextView mCommentView;
	
	private String mThreadKey;
	
	private Query mQuery;
	
	private ChatAdapter mAdapter;
	
	private FirebaseFirestore mFirestore;
	
	public ThreadFragment()
	{
	
	}
	
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		
	}
	
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		mRecyclerView = view.findViewById(R.id.thread_posts);
		mCommentView = view.findViewById(R.id.text_msg_thread);
		
		view.findViewById(R.id.btn_thread_reply)
		    .setOnClickListener(this);
		
		mFirestore = FirebaseFirestore.getInstance();
		
		mQuery = mFirestore.collection(ThreadTable.TABLE_TITLE);
		if (getActivity().getIntent().hasExtra("entryKey"))
		{
			mThreadKey = getActivity().getIntent().getStringExtra("entryKey");
			mQuery = mQuery.whereEqualTo("threadKey", mThreadKey);
		}
		
		mQuery.orderBy(ThreadTable.COL_TIME, Query.Direction.ASCENDING);
		
		mAdapter = new ChatAdapter(mQuery, null);
		
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
	public void onStop()
	{
		super.onStop();
		if (mAdapter != null)
		{
			mAdapter.stopListening();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.frag_thread, container, false);
	}
	
	@Override
	public void onClick(View v)
	{
		String msg = mCommentView.getText().toString().trim();
		if (msg.length() > 1)
		{
			ChatPostData post = new ChatPostData();
			post.time = System.currentTimeMillis();
			post.author = App.self.username;
			post.message = msg;
			post.threadKey = mThreadKey;
			
			mCommentView.setEnabled(false);
			
			
			mFirestore.collection(ThreadTable.TABLE_TITLE).add(post)
			          .addOnCompleteListener(
					          task ->
					          {
						          if (task.isSuccessful())
						          {
							          mCommentView.setText("");
						          }
						          else
						          {
							          Toast
									          .makeText(getActivity(), "Unable to make post!", Toast.LENGTH_LONG)
									          .show();
						          }
						          mCommentView.setEnabled(true);
					          });
		}
	}
}
