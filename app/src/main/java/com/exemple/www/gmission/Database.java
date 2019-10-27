package com.exemple.www.gmission;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import models.Employe;


public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "employes.db";
    private static final String TABLE = "employe";
    private static final String KEY_CIN = "cin";
    private static final String KEY_NOM = "nom";
    private static final String KEY_PRENOM = "prenom";
    private static final String KEY_FONCTION= "fonction";
    private static final String KEY_GRADE = "grade";
    private static final String KEY_TYPE = "type";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create = "create table " + TABLE + "(" + KEY_CIN + " TEXT PRIMARY KEY," + KEY_NOM + " TEXT" +
                "," + KEY_PRENOM + " TEXT" +
                "," + KEY_FONCTION + " TEXT"+
                "," + KEY_GRADE + " TEXT" +
                "," + KEY_TYPE + " TEXT" +")";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
    public void Del()
    { SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

    }

    public  void addClient(Employe employe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, employe.getNom());
        values.put(KEY_PRENOM, employe.getPrenom());
        values.put(KEY_CIN, employe.getCin());
        values.put(KEY_FONCTION, employe.getFonction());
        values.put(KEY_GRADE, employe.getGrade());
        values.put(KEY_TYPE, employe.getType());
        db.insert(TABLE, null, values);
        db.close();
    }

    public Employe getEmploye()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from "+TABLE, null);
        try{

            if (cursor.moveToFirst())
            {
                Employe employe = new Employe(cursor.getString(0)
                        , cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),Integer.parseInt(cursor.getString(5)));
                return employe;
            }
        }catch(Exception e)
        {
            Toast.makeText(MainActivity.getAppContext(),"ex"+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return null;
    }
    public void deleteClient(Employe employe) {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE,  null,null);
        db.close();
    }

}
