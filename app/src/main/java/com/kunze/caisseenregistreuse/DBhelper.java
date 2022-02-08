package com.kunze.caisseenregistreuse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Julien on 20/01/2017.
 */

public class DBhelper extends SQLiteOpenHelper {

    public static final String NOM_TABLE_PRODUITS = "produits";
    public static final String NOM_TABLE_COMMANDES = "commandes";
    public static final String NOM_TABLE_CATEGORIES= "categories";
    public static final String KEY = "_id";
    public static final String LIBELLE = "libelle";
    public static final String CATEGORIE ="categorie";
    public static final String COLOR="color";
    public static final String TARIF="tarif";
    public static final String COMMANDE="commande";
    public static final String DATE="date";
    public static final String VENTE="vente";


    public static final String TABLE_CREATE = "CREATE TABLE " + NOM_TABLE_PRODUITS + " ("+ KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LIBELLE+ " TEXT, "+CATEGORIE + " TEXT, " + TARIF + " FLOAT, "+VENTE+" INTEGER);";
    public static final String TABLE_CREATE_COMMANDE = "CREATE TABLE " + NOM_TABLE_COMMANDES + " ("+ KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COMMANDE+ " TEXT, "+DATE + " TEXT,"+ LIBELLE+ " TEXT, "+CATEGORIE + " TEXT, " + TARIF + " DOUBLE);";
    public static final String TABLE_CREATE_CATEGORIE = "CREATE TABLE " + NOM_TABLE_CATEGORIES + " ("+ KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CATEGORIE+ " TEXT, " + COLOR + " INTEGER);";


    public static final String TABLE_DROP= "DROP TABLE IF EXISTS " + NOM_TABLE_PRODUITS + ";";
    public static final String TABLE_DROP_COMMANDE= "DROP TABLE IF EXISTS " + NOM_TABLE_COMMANDES + ";";
    public static final String TABLE_DROP_CATEGORIE= "DROP TABLE IF EXISTS " + NOM_TABLE_CATEGORIES + ";";

    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_COMMANDE);
        db.execSQL(TABLE_CREATE_CATEGORIE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP);
        db.execSQL(TABLE_DROP_COMMANDE);
        db.execSQL(TABLE_DROP_CATEGORIE);
        onCreate(db);
    }

}