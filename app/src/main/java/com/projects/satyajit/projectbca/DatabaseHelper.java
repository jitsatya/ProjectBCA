package com.projects.satyajit.projectbca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Database name
    public static final String DATABASE_NAME = "nutrition_search.db";
    //Table Names
    public static final String SHOPPINGLIST = "Shopping_list_items";
    public static final String COMPARE = "compare_items";
    public static final String USER_DETAILS = "user_details";
    //Table columns for SHOPPINGLIST and COMPARE
    public static final String NBDNO = "NBDNO";
    public static final String NAME = "NAME";
    public static final String ENERGY = "ENERGY";
    public static final String PROTEIN = "PROTEIN";
    public static final String FAT = "FAT";
    public static final String CARBOHYDRATE = "CARBOHYDRATE";
    //Table columns for USER_DETAILS
    public static final String USERNAME = "Username";
    public static final String F_NAME = "First_Name";
    public static final String L_NAME = "Last_Name";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String DATE_OF_BIRTH = "Date_Of_Birth";
    public static final String HEIGHT = "Height";
    public static final String WEIGHT = "Weight";


        //creating the database
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }
    //ShoppingList creation SQL
    public static  final String SQL_CREATE_TABLE_SHOPPINGLIST = "create table " + SHOPPINGLIST + "(" + NBDNO + " INTEGER PRIMARY KEY, " +NAME+
            " TEXT, "+ENERGY+" INTEGER, "+PROTEIN+" INTEGER, "+FAT+" INTEGER, "+CARBOHYDRATE+" INTEGER )";

    //Compare creation SQL
    public static  final String SQL_CREATE_TABLE_COMPARE = "create table " + COMPARE + "(" + NBDNO + " INTEGER PRIMARY KEY, " +NAME+
            " TEXT, "+ENERGY+" INTEGER, "+PROTEIN+" INTEGER, "+FAT+" INTEGER, "+CARBOHYDRATE+" INTEGER )";
    //user_details creation SQL
    public static  final String SQL_CREATE_TABLE_USER_DETAILS = "create table " + USER_DETAILS + "(" + USERNAME + " TEXT PRIMARY KEY, " +F_NAME+
            " TEXT, "+L_NAME+" TEXT, "+EMAIL+" TEXT, "+PASSWORD+" TEXT, "+DATE_OF_BIRTH+" TEXT, " + HEIGHT+" INTEGER, " +WEIGHT+" INTEGER )";
        //creating the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_COMPARE);
        db.execSQL(SQL_CREATE_TABLE_SHOPPINGLIST);
        db.execSQL(SQL_CREATE_TABLE_USER_DETAILS);
    }
        //updating the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //clear all data
                db.execSQL("DROP TABLE IF EXISTS " + SHOPPINGLIST);
                db.execSQL("DROP TABLE IF EXISTS " + COMPARE);
                //db.execSQL("DROP TABLE IF EXISTS " + USER_DETAILS);
                //recreate the tables
                onCreate(db);

    }

    public boolean insertUserData(String f_name, String l_name, String email, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(F_NAME, f_name);
        contentValues.put(L_NAME, l_name);
        contentValues.put(EMAIL, email);
        contentValues.put(USERNAME, username);
        contentValues.put(PASSWORD, password);
        long result = db.insert(USER_DETAILS, null, contentValues);
        if(result == -1){
            return false;
        }
        else
        {return true;
        }
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

    public boolean verifyUser(String username, String password){
        String selectQuery = "select * from "+ USER_DETAILS +
                " where "+ USERNAME +" = " + "'"+ username +"'" +" and " + PASSWORD + " = " +"'"+password+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            return true;
        }
        return false;
    }
    public Cursor getShoppingListFoodNbdno(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+NBDNO+" from "+ SHOPPINGLIST, null);
        return res;
    }

    public Cursor getCompareFoodNbdno(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+NBDNO+" from "+ COMPARE, null);
        return res;
    }

    public Cursor getCompareFoodData (String column, String nbdno){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+column+" from "+ COMPARE + " where NBDNO = " + nbdno, null);
        return  res;
    }

    public Cursor getShoppingListFoodData (String column, String nbdno){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+column+" from "+ SHOPPINGLIST + " where NBDNO = " + nbdno, null);
        return  res;
    }
    /*public Cursor removeFoodFromList(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("DELETE FROM "+ SHOPPINGLIST + " WHERE " + NAME + " = " +name, null);
        return res;
    }*/
}
