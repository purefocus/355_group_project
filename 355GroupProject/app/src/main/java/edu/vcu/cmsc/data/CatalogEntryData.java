package edu.vcu.cmsc.data;


import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class CatalogEntryData
{
	public String title;
	public String description;
	public String owner;
	public double price;
	
	public String brief;
	public float lat;
	public float lon;
	public List<String> images;
	
	public String commentsKey;
	
	@Exclude
	public String key;
	
	public CatalogEntryData()
	{
	
	}
	
	
}
