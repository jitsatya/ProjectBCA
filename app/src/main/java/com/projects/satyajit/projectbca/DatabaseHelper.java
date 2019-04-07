package com.projects.satyajit.projectbca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "nutrition_search.db";
    public static final String SHOPPINGLIST = "Shopping_list_items";
    public static final String COMPARE = "compare_items";
    public static final String NBDNO = "NBDNO";
    public static final String NAME = "NAME";
    public static final String ENERGY = "ENERGY";
    public static final String PROTEIN = "PROTEIN";
    public static final String FAT = "FAT";
    public static final String CARBOHYDRATE = "CARBOHYDRATE";
        //creating the database
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }
    //ShoppingList creation SQL
    public static  final String SQL_CREATE_TABLE_SHOPPINGLIST = "create table " + SHOPPINGLIST + "(" + NBDNO + " INTEGER PRIMARY KEY, " +NAME+
            " TEXT, "+ENERGY+" INTEGER, "+PROTEIN+" INTEGER, "+FAT+" INTEGER, "+CARBOHYDRATE+" INTEGER )";

    //Compare creation SQL
    public static  final String SQL_CREATE_TABLE_COMPARE = "create table " + COMPARE + "(" + NBDNO + " INTEGER PRIMARY KEY, " +NAME+
            " TEXT, "+ENERGY+" INTEGER, "+PROTEIN+" INTEGER, "+FAT+" INTEGER, "+CARBOHYDRATE+" INTEGER )";
        //creating the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_COMPARE);
        db.execSQL(SQL_CREATE_TABLE_SHOPPINGLIST);
    }
        //updating the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //clear all data
                db.execSQL("DROP TABLE IF EXISTS " + SHOPPINGLIST);
                db.execSQL("DROP TABLE IF EXISTS " + COMPARE);
                //recreate the tables
                onCreate(db);

    }

    public boolean insertShoppingListData(String nbdno, String name, String energy, String protein, String fat, String carbohydrate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NBDNO, nbdno);
        contentValues.put(NAME, name);
        contentValues.put(ENERGY, energy);
        contentValues.put(PROTEIN, protein);
        contentValues.put(FAT, fat);
        contentValues.put(CARBOHYDRATE, carbohydrate);
        long result = db.insert(SHOPPINGLIST, null, contentValues);
        if(result == -1){
            return false;
        }
        else
            {return true;
        }
    }

    public boolean insertCompareData(String nbdno, String name, String energy, String protein, String fat, String carbohydrate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NBDNO, nbdno);
        contentValues.put(NAME, name);
        contentValues.put(ENERGY, energy);
        contentValues.put(PROTEIN, protein);
        contentValues.put(FAT, fat);
        contentValues.put(CARBOHYDRATE, carbohydrate);
        long result = db.insert(COMPARE, null, contentValues);
        if(result == -1){
            return false;
        }
        else
        {return true;
        }
    }
    public Cursor getFoodName(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+NAME+" from "+ SHOPPINGLIST, null);
        return res;
    }

    public Cursor getFoodDetails(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+NAME+" from "+ COMPARE, null);
        return res;
    }

    public Cursor removeFoodFromList(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("DELETE FROM "+ SHOPPINGLIST + " WHERE " + NAME + " = " +name, null);
        return res;
    }
}
