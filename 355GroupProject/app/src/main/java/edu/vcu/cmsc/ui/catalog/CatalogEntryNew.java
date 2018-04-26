package edu.vcu.cmsc.ui.catalog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.CatalogEntryData;

/**
 * Created by Christine on 4/4/2018.
 */

// TODO: Link from main notification page

public class CatalogEntryNew extends Activity {
    private Button btnChoose, btnUpload;
    private ImageView imageView;

    private Uri filePath;
    ArrayList<ImageView> images;
    private final int PICK_IMAGE_REQUEST =1;
    int checkBool = 0;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_new);
        }

    public void check_for_sale(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            checkBool = 1;
        } else {
            checkBool = 0;
        }
    }

    private void selectImage(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture from Gallery"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ) {
            filePath = data.getData();
            try {
                ImageView catImg = findViewById(R.id.cat_img);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                catImg.setImageBitmap(bitmap);
                images = new ArrayList<ImageView>();
                images.add((ImageView) findViewById(R.id.cat_img));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            }
    }

    public void btn_make_entry(View v) {
        int correctInput = 1;
        Context context = getApplicationContext();
        CharSequence message = "Successfully made a new catalog entry.";
        int duration = Toast.LENGTH_SHORT;

        if (v.getId() == R.id.btn_upload_pic) {
            selectImage();
        }

        if (v.getId() == R.id.btn_upload_from_url) {
            //
        }

        if (v.getId() == R.id.btn_make_catalog_entry) {
            EditText titleField = (EditText)findViewById(R.id.field_artifact_title);
            EditText descriptionField = (EditText)findViewById(R.id.field_artifact_description);
            EditText priceField = (EditText)findViewById(R.id.field_artifact_price);
            String price = priceField.getText().toString();


            String title = titleField.getText().toString();
            String description = descriptionField.getText().toString();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
                CharSequence emptyField = "Error: Title or description field cannot be empty";
                final Toast toastBasic = Toast.makeText(context, emptyField, Toast.LENGTH_SHORT);
                toastBasic.show();
                correctInput = 0;
            }
            if (checkBool == 1 && TextUtils.isEmpty(price)) {
                    CharSequence noPrice = "If For Sale is checked you must enter a price!";
                    final Toast toastBasic = Toast.makeText(context, noPrice, Toast.LENGTH_SHORT);
                    toastBasic.show();
                    correctInput = 0;
                }

            if (correctInput == 1) {
                //CatalogEntryData c = new CatalogEntryData();
                //c.setTitle(title);
                //c.setDescription(description);
                //c.setPrice(price);


                final Toast toastBasic = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toastBasic.show();

                Intent intent = new Intent(this, CatalogActivity.class);
                startActivity(intent);
            }
        }


    }
}