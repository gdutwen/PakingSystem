package com.example.pakingsystem.DB;

import java.util.ArrayList;
import java.util.List;

import com.example.pakingsystem.MODEL.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDB {
	public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_ANSWER = "answer";
    public static final String KEY_TID = "tid";
   

    private static final String TAG = "UserDbAdapter";
    private DatabaseHelper mDbHelper;
    private static  SQLiteDatabase mDb;

    
    static final String SQLITE_TABLE = "UserTable";
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

    public UserDB(Context ctx) {
        this.mCtx = ctx;
    }

    public UserDB open() throws SQLException {

        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public static  long createUser(String name, String password, String question,String answer,String tid) {
        long createResult = 0;
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_PASSWORD, password);
        initialValues.put(KEY_QUESTION, question);
        initialValues.put(KEY_ANSWER, answer);
        initialValues.put(KEY_TID, tid);
        try {
            createResult = mDb.insert(SQLITE_TABLE, null, initialValues);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return createResult;
    }

    public boolean deleteAllUsers() {
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
    public static ArrayList<User> fetchAll() {

        ArrayList<User> allUserList = new ArrayList<User>();
        Cursor mCursor = null;
        mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID, KEY_NAME,
        		KEY_PASSWORD, KEY_QUESTION,KEY_ANSWER,KEY_TID }, null, null, null, null, null);
        if (mCursor.moveToFirst()) {
            do {
            	User user = new User();
            	user.setUsername(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_NAME)));
            	user.setPassword(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_PASSWORD)));
            	user.setQuestion(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_QUESTION)));
            	user.setAnswer(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_ANSWER)));
            	user.setType(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_TID)));
            	allUserList.add(user);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return allUserList;
    }

    public User queryUserByName(String name){
    	User user = new User();
    	 List<User> usList = fetchAll();
         usList =fetchAll();
         for (int i = 0; i < usList.size(); i++) {
	            if(usList.get(i).getUsername().equals(name)){
	            	user.setUsername(usList.get(i).getUsername());
	            	user.setPassword(usList.get(i).getPassword());
	            	user.setQuestion(usList.get(i).getQuestion());
	            	user.setAnswer(usList.get(i).getAnswer());
	            	user.setType(usList.get(i).getType());  
	            }
	        }
    	return user;
    }
    public void updatePassword(String name,String password){
    	mDb.execSQL("update UserTable set password=? where name=?", new Object[]{password, name});
    }
  //删除一条数据
    public void deleteWaiter(String name) {
       
            mDb.execSQL("delete from UserTable where name=?", new Object[]{name});
      
    }
   public void deleteAllWaiter() {
        
	   mDb.execSQL("delete from UserTable where tid=?", new Object[]{"1"});
  
    }

}
