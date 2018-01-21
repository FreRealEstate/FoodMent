package com.example.czareg.foodment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kamil on 13.01.2018.
 */


public class Database extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "FoodMent.db";
    public static final String TABLE_FRIDGE = "Lodówka";
    public static final String ID_PRODUCT = "IDProduktu";
    public static final String PRODUCT_NAME = "NazwaProduktu";
    public static final String QUANTITY = "Ilość";
    public static final String UNIT = "Jednostka";
    public static final String DATE = "DataPrzydatności";

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_FRIDGE + "(" +
                ID_PRODUCT + " INTEGER PRIMARY KEY  AUTOINCREMENT " +
                PRODUCT_NAME + " TEXT " +
                QUANTITY + " INTEGER " +
                UNIT + " TEXT " +
                DATE + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIDGE);
        onCreate(db);
    }

    public void addProduct(Rzecz rzecz){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, rzecz.getNazwa());
        values.put(QUANTITY, rzecz.getIlosc());
        values.put(UNIT, rzecz.getJednostka());
        values.put(DATE, rzecz.getDataPrzydatnosci());
        db.insert(TABLE_FRIDGE, null, values);
        //db.close();
    }

    public void deleteProduct(String productName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FRIDGE + " WHERE " + PRODUCT_NAME + "=\"" + productName + "\";");
    }

    public String databaseToString(){
        SQLiteDatabase db = this.getWritableDatabase();
        String dbString = "";
        String query = "SELECT * FROM " + TABLE_FRIDGE + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("NazwaProduktu"))!=null){
                dbString += c.getString(c.getColumnIndex("NazwaProduktu")) + "\t";
                //dbString += c.getString(c.getColumnIndex("Ilość")) + "\t";
                //dbString += c.getString(c.getColumnIndex("Jednostka")) + "\t";
                //dbString += c.getString(c.getColumnIndex("DataPrzydatności"));
                dbString += "\n";
            }
        }
        //db.close();
        return dbString;
    }
}
