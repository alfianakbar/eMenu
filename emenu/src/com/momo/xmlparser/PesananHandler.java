package com.momo.xmlparser;

import java.util.ArrayList;

import com.momo.model.pesanan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class PesananHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "pesananmenu";

	// Contacts table name
	private static final String TABLE = "pesan";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAMA = "nama";
	private static final String KEY_QTY = "qty";
	private static final String KEY_SUB = "sub";
	private Context mCon;
	public PesananHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mCon=context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
				+ KEY_ID + " TEXT," + KEY_NAMA + " TEXT,"
				+ KEY_QTY + " TEXT," + KEY_SUB + " TEXT)";
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
	public void addContact(pesanan pesan) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, pesan.getIdMenu());
		values.put(KEY_NAMA, pesan.getNamaMenu()); // Contact Name
		values.put(KEY_QTY, pesan.getQty()); // Contact Phone
		values.put(KEY_SUB, pesan.getSubTotal());
		String id=pesan.getIdMenu();
		pesanan p=getPesanan(id);
		if(p==null){
			db.insert(TABLE, null, values);
		}else{
			updateContact(id, Integer.parseInt(p.getQty())+Integer.parseInt(pesan.getQty())+"");
		}
		// Inserting Row
		
		getAllContacts();
		db.close(); // Closing database connection
	}

	// Getting single contact
	pesanan getPesanan(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE, new String[] { KEY_ID,KEY_NAMA, KEY_QTY,KEY_SUB}, KEY_ID + "=?",new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		if(cursor.getCount()>0){
			pesanan pesan = new pesanan(cursor.getString(0),cursor.getString(1), cursor.getString(2),cursor.getString(3));	
			return pesan;
		}
		else{
			return null;
		}
	}
	
	// Getting All Contacts
	public ArrayList<pesanan> getAllContacts() {
		ArrayList<pesanan> pesanList = new ArrayList<pesanan>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				pesanan pesan = new pesanan();
				pesan.setIdMenu(cursor.getString(0));
				pesan.setNamaMenu(cursor.getString(1));
				pesan.setQty(cursor.getString(2));
				pesan.setSubTotal(cursor.getString(3));
				// Adding contact to list
				pesanList.add(pesan);
			} while (cursor.moveToNext());
		}
		db.close();
		// return contact list
		return pesanList;
	}

	// Updating single contact
	public int updateContact(String id,String qty) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_QTY, qty);
		// updating row
		return db.update(TABLE, values, KEY_ID + " = ?",
				new String[] { id });
	}

	// Deleting single contact
	public void deleteContact(pesanan pesan) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE, KEY_ID + " = ?",
				new String[] { String.valueOf(pesan.getIdMenu()) });
		db.close();
	}
	public void deleteAllPesanan(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE, null,null);
		db.close();
	}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
