/**
 * 
 */
package com.tl.baccarat.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Tom.Tang
 *
 */
public class GameOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "baccarat";
    private static final int DATABASE_VERSION = 2;
    private static final String GAME_TABLE_CREATE =
                "CREATE TABLE BACCARAT (_id INTEGER PRIMARY KEY AUTOINCREMENT, type SMALLINT, score SMALLINT)";

    public GameOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GAME_TABLE_CREATE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}