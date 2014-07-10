package com.momo.xmlparser;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.momo.model.meja;
import com.momo.model.pesanan;

public class MejaHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "pesanan";

	// Contacts table name
	private static final String TABLE = "meja";

	// Contacts Table Columns names
	private static final String KEY_ID = "no";
	private static final String KEY_NAMA = "nama";

	public MejaHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
				+ KEY_ID + " TEXT," + KEY_NAMA + " TEXT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	public void addMeja(meja m) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, m.getNomor());
		values.put(KEY_NAMA, m.getNama());
		
		// Inserting Row
		db.insert(TABLE, null, values);
		getAllMeja();
		db.close(); // Closing database connection
	}

	// Getting single contact
	meja getMeja(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE, new String[] { KEY_ID,KEY_NAMA}, null,null, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		meja m = new meja(cursor.getString(0),cursor.getString(1));
		// return contact
		return m;
	}
	
	// Getting All Contacts
	public ArrayList<meja> getAllMeja() {
		ArrayList<meja> mejaList = new ArrayList<meja>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				meja m = new meja(cursor.getString(0),cursor.getString(1));
				// Adding contact to list
				mejaList.add(m);
				Log.d("bookmark", cursor.getString(1));
			} while (cursor.moveToNext());
		}
		db.close();
		// return contact list
		return mejaList;
	}

	// Updating single contact
	public int updateMeja(meja m) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAMA, m.getNama());
		// updating row
		return db.update(TABLE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(m.getNomor()) });
	}

	public void deleteAllMeja(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE, null,null);
		db.close();
	}

	// Getting contacts Count
	public int getMejaCount() {
		String countQuery = "SELECT  * FROM " + TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
