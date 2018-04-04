package edu.vcu.cmsc.ui;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public abstract class FirestoreAdapter<VH extends RecyclerView.ViewHolder>
		extends RecyclerView.Adapter<VH>
		implements EventListener<QuerySnapshot>
{
	
	private static final String TAG = "FirestoreAdapter";
	
	private Query mQuery;
	private ListenerRegistration mRegistration;
	
	private ArrayList<DocumentSnapshot> mSnapshots;
	
	public FirestoreAdapter(Query query)
	{
		mQuery = query;
		mSnapshots = new ArrayList<>();
	}
	
	@Override
	public int getItemCount()
	{
		return mSnapshots.size();
	}
	
	public void startListening()
	{
		if (mQuery != null && mRegistration == null)
		{
			mRegistration = mQuery.addSnapshotListener(this);
		}
	}
	
	public void stopListening()
	{
		if (mRegistration != null)
		{
			mRegistration.remove();
			mRegistration = null;
		}
		
		mSnapshots.clear();
		notifyDataSetChanged();
	}
	
	public void setQuery(Query query)
	{
		stopListening();
		
		mQuery = query;
		
		startListening();
	}
	
	protected DocumentSnapshot getSnapshot(int index)
	{
		return mSnapshots.get(index);
	}
	
	@Override
	public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e)
	{
		if (e != null)
		{
			Log.w(TAG, "onEvent:error", e);
			return;
		}
		
		Log.d(TAG, "onEvent:numChanges:" + documentSnapshots.getDocumentChanges().size());
		for (DocumentChange change : documentSnapshots.getDocumentChanges())
		{
			switch (change.getType())
			{
				case ADDED:
					mSnapshots.add(change.getNewIndex(), change.getDocument());
					notifyItemInserted(change.getNewIndex());
					break;
				case MODIFIED:
					if (change.getOldIndex() == change.getNewIndex())
					{
						mSnapshots.set(change.getOldIndex(), change.getDocument());
						notifyItemChanged(change.getOldIndex());
					}
					else
					{
						mSnapshots.remove(change.getOldIndex());
						mSnapshots.add(change.getNewIndex(), change.getDocument());
						
						notifyItemMoved(change.getOldIndex(), change.getNewIndex());
					}
					break;
				case REMOVED:
					mSnapshots.remove(change.getOldIndex());
					notifyItemRemoved(change.getOldIndex());
					break;
			}
		}
	}
}
