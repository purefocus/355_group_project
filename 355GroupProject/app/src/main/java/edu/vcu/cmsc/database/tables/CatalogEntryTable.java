package edu.vcu.cmsc.database.tables;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.vcu.cmsc.data.CatalogEntryData;

/**
 * Created by kinex on 3/20/2018.
 */

public class CatalogEntryTable
{
	public static final String TABLE_TITLE = "catalog";
	public static final String COL_TITLE = "title";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_BRIEF = "brief";
	public static final String COL_PRICE = "price";
	
	
	public static Task<DocumentReference> insert(CatalogEntryData data)
	{
		FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//		Map<String, Object> userData = new HashMap<>();
//		userData.put(COL_TITLE, data.title);
//		userData.put(COL_DESCRIPTION, data.description);
//		userData.put(COL_PRICE, data.price);
//		userData.put(COL_BRIEF, data.brief);
//		db.collection(TABLE_TITLE).add(data);
		return db.collection(TABLE_TITLE).add(data);
	}
	
	public static void uploadImages(Context context, String key, List<Uri> imgList)
	{
		if (!imgList.isEmpty())
		{
			StorageReference ref = FirebaseStorage.getInstance()
			                                      .getReference("catalog_images")
			                                      .child(key);
			UploadTask all = null;
			for (Uri img : imgList)
			{
				System.out.println("Uploading img: " + img.toString());
				UploadTask t = ref.child(img.getLastPathSegment()).putFile(img);
				if(all == null)
				{
					all = t;
				}
				else
				{
//					all.continueWith()
				}
			}
			
			all.addOnCompleteListener(
					task ->
					{
						if (task.isSuccessful())
						{
							System.out.println("successful upload!");
						}
						else
						{
							System.out.println("error");
						}
					});
		}
	}
	
}

