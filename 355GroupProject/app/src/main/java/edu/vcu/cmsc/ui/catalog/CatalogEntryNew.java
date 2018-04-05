package edu.vcu.cmsc.ui.catalog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

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

    /*public void checkbox_for_sale_checked(View view) {
        boolean checked == ((CheckBox) view).isChecked();

        if (checked) {
            EditText priceField = (EditText)findViewById(R.id.field_artifact_price);
            double price = Double.parseDouble(String.valueOf(priceField.getText()));
        }
    }*/

    public void btn_make_entry(View v) { // On click of "Submit Values" button on "Enter Values" screen
        if (v.getId() == R.id.btn_make_catalog_entry) {
            /*EditText titleField = (EditText)findViewById(R.id.field_artifact_title);
            EditText descriptionField = (EditText)findViewById(R.id.field_artifact_description);

            String title = titleField.getText().toString();
            String description = descriptionField.getText().toString();

            CatalogEntryData c = new CatalogEntryData();*/
            //c.SETTITLEDATABASEFUNCTION(title);
            //c.SETDESCRIPTIONDATABASEFUNCTION(description);

            //helper.INSERTINTODATABASEFUNCTION(w);*/
            //Intent i = new Intent(CatalogEntryNew.this, /*GO BACK TO CATALOG MAIN PAGE*/.class);
            //startActivity(i);

        }


    }
}