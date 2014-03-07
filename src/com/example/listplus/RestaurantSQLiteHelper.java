package com.example.listplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RestaurantSQLiteHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "lunlist.db";
	private static final int SCHEMA_VERSION = 1;
	
	public RestaurantSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE restaurants (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, type TEXT, notes TEXT);");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		android.util.Log.w("LunchList", "Upgrading database, which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS restaurants");
		onCreate(db);
	}
	
	public Cursor getAll() {
		return (getReadableDatabase().rawQuery("SELECT _id, name, address, type, notes FROM restaurants ORDER BY name", null));
	}
	
	public Cursor getById(String id) {
		return (getReadableDatabase().rawQuery("SELECT _id, name, address, type, notes FROM restaurants WHERE _id ="+ id, null));
	}
	
	public void insert(String name, String address, String type, String notes) {
		ContentValues cv = new ContentValues();
		
		cv.put("name", name);
		cv.put("address", address);
		cv.put("type", type);			//Added type to database
		cv.put("notes", notes);			//Added notes to database

		getWritableDatabase().insert("restaurants", "name", cv);
	}
	
	public void update(String id, String name, String address, String type, String notes) {
		ContentValues cv = new ContentValues();
		cv.put("name", name);
		cv.put("address", address);
		cv.put("type", type);
		cv.put("notes", notes);
		
		getWritableDatabase().update("restaurants", cv, "_ID =" + id, null);
	}
	
	public String getName(Cursor c) {
		return (c.getString(1));
	}
	
	public String getAddress(Cursor c) {
		return (c.getString(2));
	}
	
	public String getType(Cursor c) {
		//Added return value for type
		return (c.getString(3));
	}
	
	public String getNotes(Cursor c) {
		//Added return value for notes
		return (c.getString(4));
	}
}
