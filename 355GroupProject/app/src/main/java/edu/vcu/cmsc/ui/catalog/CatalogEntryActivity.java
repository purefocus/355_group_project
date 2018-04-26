package edu.vcu.cmsc.ui.catalog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.CatalogEntryData;

public class CatalogEntryActivity extends Activity implements OnMapReadyCallback
{
	
	private int imagePosition;
	private ImageView mImageView;
	
	private CatalogEntryData mCatalogData;
	private FirebaseStorage mStorage;
	
	private String mKey;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalog_entry);
		
		mKey = getIntent().getStringExtra("entryKey");
		
		mImageView = findViewById(R.id.cat_img);
		
		
		MapFragment mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map_frag);
		
		FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
		mStorage = FirebaseStorage.getInstance();
		
		DocumentReference mEntryRef = mFirestore.collection("catalog").document(mKey);
		mEntryRef.get().addOnCompleteListener(
				task ->
				{
					mCatalogData = task.getResult().toObject(CatalogEntryData.class);
					
					((TextView) findViewById(R.id.cat_title)).setText(mCatalogData.title);
					((TextView) findViewById(R.id.cat_description))
							.setText(mCatalogData.description);
					
					btn_img(null);
					
					
					mapFrag.getMapAsync(this);
					
					// download all images
					for(String img : mCatalogData.images)
					{
						StorageReference ref = mStorage.getReference("catalog_images").child(mKey)
						                               .child(img);
						
						Glide.with(this).using(new FirebaseImageLoader()).load(ref)
						     .downloadOnly(mImageView.getWidth(), mImageView.getHeight());
					}
				});
		
		
	}
	
	public void btn_img(View view)
	{
		if (mCatalogData != null && mCatalogData.images != null && !mCatalogData.images.isEmpty())
		{
			if (view != null)
			{
				if (view.getId() == R.id.cat_img_prev)
				{
					if (--imagePosition < 0)
					{
						imagePosition = mCatalogData.images.size() - 1;
					}
				}
				else
				{
					if (++imagePosition >= mCatalogData.images.size())
					{
						imagePosition = 0;
					}
				}
			}
			
			String img = mCatalogData.images.get(imagePosition);
			
			StorageReference ref = mStorage.getReference("catalog_images").child(mKey).child(img);
			
			Glide.with(this).using(new FirebaseImageLoader()).load(ref).into(mImageView);
		}
		
	}
	
	@Override
	public void onMapReady(GoogleMap map)
	{
		LatLng sydney = new LatLng(mCatalogData.lat, mCatalogData.lon);
		map.addMarker(new MarkerOptions().position(sydney).title("Marker"));
		map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
	}
}
