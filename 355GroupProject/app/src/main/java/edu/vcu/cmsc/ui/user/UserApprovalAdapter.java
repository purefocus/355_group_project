package edu.vcu.cmsc.ui.user;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.UserData;
import edu.vcu.cmsc.ui.FirestoreAdapter;

public class UserApprovalAdapter extends FirestoreAdapter<UserApprovalAdapter.ViewHolder>
{
	
	public interface UserButtonListener
	{
		void onChatPostSelected(DocumentSnapshot item, boolean approved);
	}
	
	private UserApprovalAdapter.UserButtonListener mListener;
	
	public UserApprovalAdapter(Query query, UserApprovalAdapter.UserButtonListener listener)
	{
		super(query);
		mListener = listener;
	}
	
	@Override
	public UserApprovalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		
		return new UserApprovalAdapter.ViewHolder(inflater.inflate(R.layout.user_approval_item, parent, false));
	}
	
	@Override
	public void onBindViewHolder(UserApprovalAdapter.ViewHolder holder, int position)
	{
		holder.bind(getSnapshot(position), mListener);
	}
	
	static class ViewHolder extends RecyclerView.ViewHolder
	{
		private ImageButton mApprove;
		private ImageButton mDeny;
		private TextView mUsername;
		private TextView mInfo;
		
		private String key;
		
		public ViewHolder(View itemView)
		{
			super(itemView);
			mApprove = itemView.findViewById(R.id.btn_approve);
			mDeny = itemView.findViewById(R.id.btn_deny);
			mUsername = itemView.findViewById(R.id.text_approve_uname);
			mInfo = itemView.findViewById(R.id.text_approve_desc);
		}
		
		public void bind(final DocumentSnapshot snapshop, final UserApprovalAdapter.UserButtonListener listener)
		{
			UserData entry = snapshop.toObject(UserData.class);
			
			
			mUsername.setText(entry.username);
			String info = entry.firstName + " " + entry.lastName;
			
			mInfo.setText(info);
			
			mApprove.setOnClickListener(
					v ->
					{
						if (listener != null)
						{
							listener.onChatPostSelected(snapshop, true);
						}
					});
			
			mDeny.setOnClickListener(
					v ->
					{
						if (listener != null)
						{
							listener.onChatPostSelected(snapshop, false);
						}
					});
			
			
		}
	}
}