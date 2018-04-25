package edu.vcu.cmsc.app;


public interface Catalog{
    void insertEntry(String title, String description, String price);

    void onInsertResult(int result);
	
	
}
