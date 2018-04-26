package edu.vcu.cmsc.ui.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.Date;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.ChatPostData;
import edu.vcu.cmsc.ui.FirestoreAdapter;

public class ChatAdapter extends FirestoreAdapter<ChatAdapter.ViewHolder>
{
	
	public interface ChatPostSelected
	{
		void onChatPostSelected(DocumentSnapshot item);
	}
	
	private ChatAdapter.ChatPostSelected mListener;
	
	public ChatAdapter(Query query, ChatAdapter.ChatPostSelected listener)
	{
		super(query);
		mListener = listener;
	}
	
	@Override
	public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		
		return new ChatAdapter.ViewHolder(inflater.inflate(R.layout.frag_thread_post, parent, false));
	}
	
	@Override
	public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position)
	{
		holder.bind(getSnapshot(position), mListener);
	}
	
	static class ViewHolder extends RecyclerView.ViewHolder
	{
		
		private TextView mAuthor;
		private TextView mTime;
		private TextView mMessage;
		
		public ViewHolder(View itemView)
		{
			super(itemView);
			mAuthor = itemView.findViewById(R.id.post_author);
			mTime = itemView.findViewById(R.id.post_time);
			mMessage = itemView.findViewById(R.id.post_message);
			
		}
		
		public void bind(final DocumentSnapshot snapshop, final ChatAdapter.ChatPostSelected listener)
		{
			ChatPostData entry = snapshop.toObject(ChatPostData.class);
			
			mAuthor.setText(entry.author);
			mTime.setText(new Date(entry.time).toString());
			mMessage.setText(entry.message);
			
			
//			itemView.setOnClickListener(
//					v ->
//					{
//						if (listener != null)
//						{
//							listener.onChatPostSelected(snapshop);
//						}
//					});
			
			
		}
	}
}
