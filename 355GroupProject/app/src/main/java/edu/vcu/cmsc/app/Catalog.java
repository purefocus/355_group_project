package edu.vcu.cmsc.app;


import android.widget.ImageView;

import java.util.ArrayList;

public interface Catalog{
    void insertEntry(String title, String description, String price, ArrayList<ImageView> images);

    void onInsertResult(int result);
	
	
}
