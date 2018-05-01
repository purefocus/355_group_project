package edu.vcu.cmsc.ui.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Map;

import edu.vcu.cmsc.App;
import edu.vcu.cmsc.R;
import edu.vcu.cmsc.database.tables.UserTable;
import edu.vcu.cmsc.ui.BaseActivity;

public class UserApprovalActivity extends BaseActivity implements UserApprovalAdapter.UserButtonListener
{
	
	RecyclerView mRecyclerView;
	
	private Query mQuery;
	
	private UserApprovalAdapter mAdapter;
	
	private FirebaseFirestore mFirestore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_user_approval);
		
		mRecyclerView = findViewById(R.id.user_approval_list);
		
		mFirestore = FirebaseFirestore.getInstance();
		
		mQuery = mFirestore.collection(UserTable.USERS_TABLE)
		                   .whereEqualTo(UserTable.COL_PERMISSION, App.Role.NEW_USER.perm);
		
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mAdapter = new UserApprovalAdapter(mQuery, this);
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
		return R.layout.activity_user_approval;
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
	public void onChatPostSelected(DocumentSnapshot item, boolean approved)
	{
		Map<String, Object> data = item.getData();
		data.put(UserTable.COL_PERMISSION, approved ? App.Role.MEMBER.perm : App.Role.DENIED.perm);
		item.getReference().update(data);
	}
}
