package edu.vcu.cmsc.ui.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.Date;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.ChatPostData;
import edu.vcu.cmsc.ui.FirestoreAdapter;


public class ThreadAdapter extends FirestoreAdapter<ThreadAdapter.ViewHolder>
{
	
	public interface OnThreadClickedListener
	{
		void onThreadClicked(DocumentSnapshot item);
		
	}
	
	private OnThreadClickedListener mListener;
	
	public ThreadAdapter(Query query, OnThreadClickedListener listener)
	{
		super(query);
		mListener = listener;
	}
	
	@Override
	public ThreadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		
		return new ThreadAdapter.ViewHolder(inflater.inflate(R.layout.thread_item, parent, false));
	}
	
	@Override
	public void onBindViewHolder(ThreadAdapter.ViewHolder holder, int position)
	{
		holder.bind(getSnapshot(position), mListener);
	}
	
	static class ViewHolder extends RecyclerView.ViewHolder
	{
		
		private TextView mAuthor;
		private TextView mDate;
		private TextView mTitle;
		
		public ViewHolder(View itemView)
		{
			super(itemView);
			mAuthor = itemView.findViewById(R.id.thread_author);
			mDate = itemView.findViewById(R.id.thread_date);
			mTitle = itemView.findViewById(R.id.thread_title);
			
		}
		
		public void bind(final DocumentSnapshot snapshop, final OnThreadClickedListener listener)
		{
			ChatPostData entry = snapshop.toObject(ChatPostData.class);
			
			mAuthor.setText(entry.author);
			mDate.setText(new Date(entry.time).toString());
			mTitle.setText(entry.message);


			itemView.setOnClickListener(
					v ->
					{
						if (listener != null)
						{
							listener.onThreadClicked(snapshop);
						}
					});
		
		
		}
	}
}