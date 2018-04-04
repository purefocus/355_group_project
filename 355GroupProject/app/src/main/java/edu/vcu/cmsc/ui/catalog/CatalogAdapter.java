package edu.vcu.cmsc.ui.catalog;


import android.content.res.Resources;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.CatalogEntryData;
import edu.vcu.cmsc.ui.FirestoreAdapter;

public class CatalogAdapter extends FirestoreAdapter<CatalogAdapter.ViewHolder>
{
	
	public interface CatalogItemSelectedListener
	{
		void onCatalogItemSelected(DocumentSnapshot item);
	}
	
	private CatalogItemSelectedListener mListener;
	
	public CatalogAdapter(Query query, CatalogItemSelectedListener listener)
	{
		super(query);
		mListener = listener;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		
		return new ViewHolder(inflater.inflate(R.layout.catalog_list_entry_item, parent, false));
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		holder.bind(getSnapshot(position), mListener);
	}
	
	static class ViewHolder extends RecyclerView.ViewHolder
	{
		
		private ImageView imageView;
		private TextView titleView;
		private TextView descView;
		
		public ViewHolder(View itemView)
		{
			super(itemView);
			imageView = itemView.findViewById(R.id.cat_item_img);
			titleView = itemView.findViewById(R.id.cat_item_title);
			descView = itemView.findViewById(R.id.cat_item_desc);
			
		}
		
		public void bind(final DocumentSnapshot snapshop, final CatalogItemSelectedListener listener)
		{
			CatalogEntryData entry = snapshop.toObject(CatalogEntryData.class);
			
			if (entry.images != null && !entry.images.isEmpty())
			{
				//Glide.with(imageView.getContext()).load(entry.images[0]).into(imageView);
				
				FirebaseStorage storage = FirebaseStorage.getInstance();
				StorageReference ref = storage.getReference("catalog_images")
				                              .child(entry.images.get(0));
				
				Glide.with(imageView.getContext())
				     .using(new FirebaseImageLoader())
				     .load(ref)
				     .into(imageView);
			}
			
			
			titleView.setText(entry.title);
			descView.setText(entry.brief);
			
			
			itemView.setOnClickListener(
					v ->
					{
						if (listener != null)
						{
							listener.onCatalogItemSelected(snapshop);
						}
					});
			
			
		}
	}
}
