package com.example.pakingsystem.DB;

import java.util.ArrayList;
import java.util.List;

import com.example.pakingsystem.MODEL.Places;
import com.example.pakingsystem.MODEL.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlacesDB {
	public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_COUNT = "count";
    
   

    private static final String TAG = "PlacesDbAdapter";
    private DatabaseHelper mDbHelper;
    private static  SQLiteDatabase mDb;

    
    static final String SQLITE_TABLE = "PlacesTable";
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

    public PlacesDB(Context ctx) {
        this.mCtx = ctx;
    }

    public PlacesDB open() throws SQLException {

        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public static  long createPlaces(String name, String location, String count) {
        long createResult = 0;
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_LOCATION, location);
        initialValues.put(KEY_COUNT, count);
        try {
            createResult = mDb.insert(SQLITE_TABLE, null, initialValues);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return createResult;
    }

    public boolean deleteAllPlaces() {
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
    public static ArrayList<Places> fetchAll() {

        ArrayList<Places> allPlacesList = new ArrayList<Places>();
        Cursor mCursor = null;
        mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID, KEY_NAME,
        		KEY_LOCATION, KEY_COUNT }, null, null, null, null, null);
        if (mCursor.moveToFirst()) {
            do {
            	Places places = new Places();
            	places.setName(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_NAME)));
            	places.setLocation(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_LOCATION)));
            	places.setCount(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_COUNT)));
            	
            	allPlacesList.add(places);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return allPlacesList;
    }

    public Places queryUserByName(String name){
    	Places places = new Places();
    	 List<Places> placesList = fetchAll();
        placesList =fetchAll();
         for (int i = 0; i < placesList.size(); i++) {
	            if(placesList.get(i).getName().equals(name)){
	            	places.setName(placesList.get(i).getName());
	            	places.setLocation(placesList.get(i).getLocation());
	            	places.setCount(placesList.get(i).getCount());  
	            }
	        }
    	return places;
    }
//    public void updatePassword(String name,String password){
//    	mDb.execSQL("update UserTable set password=? where name=?", new Object[]{password, name});
//    }
//  //删除一条数据
//    public void deleteWaiter(String name) {
//       
//            mDb.execSQL("delete from UserTable where name=?", new Object[]{name});
//      
//    }
//   public void deleteAllWaiter() {
//        
//	   mDb.execSQL("delete from UserTable where tid=?", new Object[]{"1"});
//  
//    }
}
