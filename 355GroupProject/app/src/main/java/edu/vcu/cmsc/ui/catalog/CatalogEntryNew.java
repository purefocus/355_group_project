package edu.vcu.cmsc.ui.catalog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_new);
    }

    public void check_for_sale(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            //EditText priceField = (EditText)findViewById(R.id.field_artifact_price);
            //double price = Double.parseDouble(String.valueOf(priceField.getText()));
        }
    }

    public void btn_make_entry(View v) { // On click of "Submit Values" button on "Enter Values" screen
        Context context = getApplicationContext();
        CharSequence message = "Successfully made a new catalog entry.";
        int duration = Toast.LENGTH_SHORT;

        if (v.getId() == R.id.btn_make_catalog_entry) {
            EditText titleField = (EditText)findViewById(R.id.field_artifact_title);
            EditText descriptionField = (EditText)findViewById(R.id.field_artifact_description);

            String title = titleField.getText().toString();
            String description = descriptionField.getText().toString();

            CatalogEntryData c = new CatalogEntryData();
            //c.SETTITLEDATABASEFUNCTION(title);
            //c.SETDESCRIPTIONDATABASEFUNCTION(description);

            //helper.INSERTINTODATABASEFUNCTION(w);*/

            final Toast toastBasic = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastBasic.show();

            Intent intent = new Intent(this, CatalogActivity.class);
            startActivity(intent);
        }


    }
}