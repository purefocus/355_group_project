package edu.vcu.cmsc.ui.catalog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.vcu.cmsc.App;
import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.CatalogEntryData;
import edu.vcu.cmsc.database.tables.CatalogEntryTable;

/**
 * Created by Christine on 4/4/2018.
 */

// TODO: Link from main notification page

public class CatalogEntryNew extends Activity
{
	boolean forSale = false;
	private List<Uri> imgList;
	private Uri filePath;

	private static final int PICK_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalog_new);

		imgList = new ArrayList<>();
	}

	public void check_for_sale(View view)
	{
		forSale = ((CheckBox) view).isChecked();
	}


	public void btn_upload(View v)
	{
		Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
		getIntent.setType("image/*");

		Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		pickIntent.setType("image/*");

		Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

		startActivityForResult(chooserIntent, PICK_IMAGE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == PICK_IMAGE && resultCode == RESULT_OK)
		{
			if (data == null)
			{
				return;
			}
			filePath = data.getData();
			imgList.add(filePath);
			try {
				ImageView catImg = findViewById(R.id.cat_img2);
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
				catImg.setImageBitmap(bitmap);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void btn_make_entry(View v)
	{
		boolean correctInput = true;
		Context context = getApplicationContext();
		CharSequence message = "Successfully made a new catalog entry.";
		int duration = Toast.LENGTH_SHORT;



		if (v.getId() == R.id.btn_make_catalog_entry)
		{
			EditText titleField = (EditText) findViewById(R.id.field_artifact_title);
			EditText descriptionField = (EditText) findViewById(R.id.field_artifact_description);
			EditText priceField = (EditText) findViewById(R.id.field_artifact_price);
			EditText coordinatesField = (EditText) findViewById(R.id.field_coordinates);
			ImageView catImgView = findViewById(R.id.cat_img2);

			String price = priceField.getText().toString();
			String title = titleField.getText().toString();
			String description = descriptionField.getText().toString();
			String coordinates = coordinatesField.getText().toString();
			String searchCoordinates = ", ";

			if ( coordinates.contains(searchCoordinates) == false ) {

				CharSequence emptyField = "Incorrect format. Example location is 37.5466151, -77.4532558";
				final Toast toastBasic = Toast.makeText(context, emptyField, Toast.LENGTH_SHORT);
				toastBasic.show();
				correctInput = false;
			}

			String[] coordinateParts = coordinates.split(", ", 2);
			String latString = coordinateParts[0];
			String lonString = coordinateParts[1];
			float lat = Float.parseFloat(latString);
			float lon = Float.parseFloat(lonString);


			if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(coordinates) || catImgView.getDrawable() == null)
			{
				CharSequence emptyField = "Error: Empty title/description/coordinates field or no picture added";
				final Toast toastBasic = Toast.makeText(context, emptyField, Toast.LENGTH_SHORT);
				toastBasic.show();
				correctInput = false;
			}

			if (forSale && TextUtils.isEmpty(price))
			{
				CharSequence noPrice = "If For Sale is checked you must enter a price!";
				final Toast toastBasic = Toast.makeText(context, noPrice, Toast.LENGTH_SHORT);
				toastBasic.show();
				correctInput = false;
			}

			if (correctInput)
			{

				CatalogEntryData c = new CatalogEntryData();
				c.title = title;
				c.description = description;
				c.brief = description.substring(0, Math.min(200, description.length()));
				c.price = forSale ? Double.parseDouble(price) : 0D;
				if (App.self != null)
				{
					c.owner = App.self.username;
				}
				if (!imgList.isEmpty())
				{
					c.images = new ArrayList<>();
					for (Uri img : imgList)
					{
						c.images.add(img.getLastPathSegment());
					}
				}

				CatalogEntryTable.insert(c).addOnCompleteListener(
						task ->
						{
							if (task.isSuccessful())
							{
								//finish();
								c.key = task.getResult().getId();
								uploadAllImages(c.key);
								Toast.makeText(this, "Uploading images...", Toast.LENGTH_LONG)
										.show();
							}
							else
							{
								Toast.makeText(this, "Error adding entry!", Toast.LENGTH_LONG)
										.show();
							}
						});

//				final Toast toastBasic = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//				toastBasic.show();


			}
		}


	}

	private int upCount;
	private AlertDialog dialog;

	private void uploadAllImages(String key)
	{

		dialog = new AlertDialog.Builder(this)
				.setTitle("Creating new Post...")
				.setMessage(String.format("Uploading Images ... (%d/%d)", 1, imgList.size()))
				.setCancelable(false)
				.create();

		dialog.show();

		StorageReference ref = FirebaseStorage.getInstance()
				.getReference("catalog_images")
				.child(key);

		UploadTask all = null;
		upCount = imgList.size();
		for (Uri img : imgList)
		{
			ref.child(img.getLastPathSegment())
					.putFile(img)
					.addOnCompleteListener(
							task ->
							{
								upCount--;
								if (task.isSuccessful())
								{
									if (upCount == 0)
									{
										dialog.dismiss();
										finish();
									}
								}
								else
								{
									System.out.println("error");
								}
							});
		}
	}

}