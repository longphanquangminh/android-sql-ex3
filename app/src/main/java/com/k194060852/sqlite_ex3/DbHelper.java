package com.k194060852.sqlite_ex3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "warranty.sqlite";
    public static final int DB_VERSION = 1;

    public static final String TBL_NAME = "Warranty";
    public static final String COL_CODE = "WarrantyCode";
    public static final String COL_NAME = "WarrantyName";
    public static final String COL_DES = "WarrantyDescription";
    public static final String COL_PHOTO = "WarrantyPhoto";


    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " (" + COL_CODE +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME +
                " VARCHAR(30), " + COL_DES + " VARCHAR(50), " + COL_PHOTO + " BLOB)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getData(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_NAME, null);
    }

    public void insertData(String name, String des, byte[] photo){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO " + TBL_NAME + " VALUES(null,?,?,?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, name);
        statement.bindString(2, des);
        statement.bindBlob(3, photo);

        statement.executeInsert();
    }
}
