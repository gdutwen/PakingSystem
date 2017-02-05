package com.example.pakingsystem.DB;

import java.util.ArrayList;
import java.util.List;

import com.example.pakingsystem.MODEL.Paking;
import com.example.pakingsystem.MODEL.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PakingDB {
	public static final String KEY_ROWID = "_id";
    public static final String KEY_PLACES_NAME = "places_name";
    public static final String KEY_LICESE_NUMBER = "licese_number";
    public static final String KEY_IN_TIME = "in_time";
    public static final String KEY_OUT_TIME = "out_time";
    public static final String KEY_PLACES_NUMBER = "places_number";
    public static final String KEY_PRICE = "price";
    public static final String KEY_TOTAL = "total";

    private static final String TAG = "PakingDbAdapter";
    private DatabaseHelper mDbHelper;
    private static  SQLiteDatabase mDb;

    
    static final String SQLITE_TABLE = "PakingTable";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {

            super(context, CommDB.DATABASE_NAME, null, CommDB.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Log.w(TAG, DATABASE_CREATE);
            // db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public PakingDB(Context ctx) {
        this.mCtx = ctx;
    }

    public PakingDB open() throws SQLException {

        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public static  long createUser(String places_name, String licese_number, String in_time,String out_time,String places_number,String price,String total) {
        long createResult = 0;
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PLACES_NAME, places_name);
        initialValues.put(KEY_LICESE_NUMBER, licese_number);
        initialValues.put(KEY_IN_TIME, in_time);
        initialValues.put(KEY_OUT_TIME, out_time);
        initialValues.put(KEY_PLACES_NUMBER, places_number);
        initialValues.put(KEY_PRICE, price);
        initialValues.put(KEY_TOTAL, total);
        try {
            createResult = mDb.insert(SQLITE_TABLE, null, initialValues);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return createResult;
    }

    public boolean deleteAllPaking() {
        int doneDelete = 0;
        try {
            doneDelete = mDb.delete(SQLITE_TABLE, null, null);
            Log.w(TAG, Integer.toString(doneDelete));
            Log.e("doneDelete", doneDelete + "");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return doneDelete > 0;
    }


   // 扫描时进行判断本地数据库是否有此
    public static ArrayList<Paking> fetchAll() {

        ArrayList<Paking> allPakingList = new ArrayList<Paking>();
        Cursor mCursor = null;
        mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID, KEY_PLACES_NAME,
        		KEY_LICESE_NUMBER, KEY_IN_TIME,KEY_OUT_TIME,KEY_PLACES_NUMBER,KEY_PRICE,KEY_TOTAL }, null, null, null, null, null);
        if (mCursor.moveToFirst()) {
            do {
            	Paking paking = new Paking();
            	paking.setPlaces_name(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_PLACES_NAME)));
            	paking.setLicese_number(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_LICESE_NUMBER)));
            	paking.setIn_time(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_IN_TIME)));
            	paking.setOut_time(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_OUT_TIME)));
            	paking.setPlaces_number(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_PLACES_NUMBER)));
            	paking.setPrice(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_PRICE)));
            	paking.setTotal(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_TOTAL)));
            	allPakingList.add(paking);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return allPakingList;
    }
    public static ArrayList<Paking> fetchsome(String name) {

        ArrayList<Paking> somePakingList = new ArrayList<Paking>();
        Cursor mCursor = null;
        mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID, KEY_PLACES_NAME,
        		KEY_LICESE_NUMBER, KEY_IN_TIME,KEY_OUT_TIME,KEY_PLACES_NUMBER,KEY_PRICE,KEY_TOTAL }, "places_name = ?",  new String[]{name}, null, null, null);
        if (mCursor.moveToFirst()) {
            do {
            	Paking paking = new Paking();
            	paking.setPlaces_name(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_PLACES_NAME)));
            	paking.setLicese_number(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_LICESE_NUMBER)));
            	paking.setIn_time(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_IN_TIME)));
            	paking.setOut_time(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_OUT_TIME)));
            	paking.setPlaces_number(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_PLACES_NUMBER)));
            	paking.setPrice(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_PRICE)));
            	paking.setTotal(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_TOTAL)));
            	somePakingList.add(paking);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return somePakingList;
    }

    public Paking queryPakingByName(String name){
    	Paking paking = new Paking();
    	 List<Paking> pkList = fetchAll();
         pkList =fetchAll();
         for (int i = 0; i < pkList.size(); i++) {
	            if(pkList.get(i).getPlaces_name().equals(name)){
	            	paking.setPlaces_name(pkList.get(i).getPlaces_name());
	            	paking.setLicese_number(pkList.get(i).getLicese_number());
	            	paking.setIn_time(pkList.get(i).getIn_time());
	            	paking.setOut_time(pkList.get(i).getOut_time());
	            	paking.setPlaces_number(pkList.get(i).getPlaces_number()); 
	            	paking.setPrice(pkList.get(i).getPrice());
	            	paking.setTotal(pkList.get(i).getTotal());
	            }
	        }
    	return paking;
    }
    public Paking queryPakingByLName(String name){
    	Paking paking = new Paking();
    	 List<Paking> pkList = fetchAll();
         pkList =fetchAll();
         for (int i = 0; i < pkList.size(); i++) {
	            if(pkList.get(i).getLicese_number().equals(name)){
	            	paking.setPlaces_name(pkList.get(i).getPlaces_name());
	            	paking.setLicese_number(pkList.get(i).getLicese_number());
	            	paking.setIn_time(pkList.get(i).getIn_time());
	            	paking.setOut_time(pkList.get(i).getOut_time());
	            	paking.setPlaces_number(pkList.get(i).getPlaces_number()); 
	            	paking.setPrice(pkList.get(i).getPrice());
	            	paking.setTotal(pkList.get(i).getTotal());
	            }
	        }
    	return paking;
    }
    public void updateCar(String name,String out_time){
    	mDb.execSQL("update PakingTable set out_time=? where licese_number=?", new Object[]{out_time, name});
    }
    public void updatePrice(String name,String price){
    	mDb.execSQL("update PakingTable set price=? where places_name=?", new Object[]{price, name});
    }

}
