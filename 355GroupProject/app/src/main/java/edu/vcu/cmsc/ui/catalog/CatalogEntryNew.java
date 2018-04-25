package edu.vcu.cmsc.ui.catalog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import edu.vcu.cmsc.R;
import edu.vcu.cmsc.data.CatalogEntryData;

/**
 * Created by Christine on 4/4/2018.
 */

// TODO: Link from main notification page

public class CatalogEntryNew extends Activity {
    int checkBool = 0;

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

    public void btn_make_entry(View v) {
        int correctInput = 1;
        Context context = getApplicationContext();
        CharSequence message = "Successfully made a new catalog entry.";
        int duration = Toast.LENGTH_SHORT;

        //if (v.getId() == R.id.btn_upload_pic) {
        //}


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