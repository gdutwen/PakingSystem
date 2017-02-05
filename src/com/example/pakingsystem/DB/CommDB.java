package com.example.pakingsystem.DB;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CommDB {
	public static final String DATABASE_NAME = "myPaking"; //数据库名称
	 
    public static final int DATABASE_VERSION = 1;
    
 //创建该数据库下User的语句
   private static final String CREATE_TABLE_Users =
         "CREATE TABLE if not exists " + UserDB.SQLITE_TABLE + " (" +
        		 UserDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
        		 UserDB.KEY_NAME + "," +
        		 UserDB.KEY_PASSWORD + "," +
        		 UserDB.KEY_QUESTION + ","+
        		 UserDB.KEY_ANSWER +
        		 "," + UserDB.KEY_TID + ")";
 //创建该数据库下停车场的语句
   private static final String CREATE_TABLE_PLACES =
         "CREATE TABLE if not exists " + PlacesDB.SQLITE_TABLE + " (" +
        		 PlacesDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
        		 PlacesDB.KEY_NAME + "," +
        		 PlacesDB.KEY_LOCATION + "," +
        		 PlacesDB.KEY_COUNT  + ")";

 //创建该数据库下paking的语句
   private static final String CREATE_TABLE_Paking =
         "CREATE TABLE if not exists " + PakingDB.SQLITE_TABLE + " (" +
        		 PakingDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
        		 PakingDB.KEY_PLACES_NAME + "," +
        		 PakingDB.KEY_LICESE_NUMBER + "," +
        		 PakingDB.KEY_IN_TIME + "," + 
        		 PakingDB.KEY_OUT_TIME + "," +
        		 PakingDB.KEY_PLACES_NUMBER + "," +
        		 PakingDB.KEY_PRICE + "," +
        		 PakingDB.KEY_TOTAL + ")";
   private final Context context; 
   private DatabaseHelper DBHelper;
   private SQLiteDatabase db;
   /**
    * Constructor
    * @param ctx
    */
   public CommDB(Context ctx)
   {
       this.context = ctx;
       this.DBHelper = new DatabaseHelper(this.context);
   }

   private static class DatabaseHelper extends SQLiteOpenHelper 
   {
       DatabaseHelper(Context context) 
       {
           super(context, DATABASE_NAME, null, DATABASE_VERSION);
       }

       @Override
       public void onCreate(SQLiteDatabase db) 
       {
           
           db.execSQL(CREATE_TABLE_Users);//创建uesr表 
           db.execSQL(CREATE_TABLE_PLACES);//创建PLACES表 
           db.execSQL(CREATE_TABLE_Paking);//创建paking表 
       }

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, 
       int newVersion) 
       {               
           // Adding any table mods to this guy here
       }
   } 

  /**
    * open the db
    * @return this
   * @throws SQLException
    * return type: DBAdapter
*/
   public CommDB open() throws SQLException 
   {
       this.db = this.DBHelper.getWritableDatabase();
       return this;
   }
 
   /**
    * close the db 
    * return type: void
    */
   public void close() 
   {
       this.DBHelper.close();
   }

}
