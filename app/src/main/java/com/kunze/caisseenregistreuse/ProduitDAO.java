package com.kunze.caisseenregistreuse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProduitDAO {

    protected final static int VERSION = 2;
    protected final static String NAME = "baseCaisseenregistreuse.db";
    protected SQLiteDatabase eDb = null;
    protected DBhelper eHandler = null;

    public static final String NOM_TABLE_PRODUITS = "produits";
    public static final String NOM_TABLE_COMMANDES = "commandes";
    public static final String NOM_TABLE_CATEGORIES = "categories";

    public static final String KEY = "_id";
    public static final String LIBELLE = "libelle";
    public static final String CATEGORIE = "categorie";
    public static final String COLOR = "color";
    public static final String TARIF = "tarif";
    public static final String COMMANDE = "commande";
    public static final String DATE = "date";
    public static final String VENTE="vente";



    public ProduitDAO(Context context) {
        eHandler = new DBhelper(context, NAME, null, VERSION);
    }

    public void openDb() {
        eDb = eHandler.getWritableDatabase();
    }

    public void closeDb() {
        eDb.close();
    }

    public void ajoutCategorie(Categorie c) {

        ContentValues value = new ContentValues();
        value.put(CATEGORIE, c.getNom());
        value.put(COLOR, c.getColorCategorie());

        eDb.insert(NOM_TABLE_CATEGORIES, null, value);
    }

    public void ajoutProduit(Produit p) {

        ContentValues value = new ContentValues();
        value.put(LIBELLE, p.getLibelle());
        value.put(CATEGORIE, p.getCategorie());
        value.put(TARIF, p.getTarif());

        eDb.insert(NOM_TABLE_PRODUITS, null, value);
    }

    public ArrayList<Categorie> getListeCategorie() {

        Cursor c = eDb.rawQuery("select distinct categorie, color from " + NOM_TABLE_CATEGORIES + ";", null);
        ArrayList<Categorie> el = new ArrayList<>();

        while (c.moveToNext()) {
            String categorie = c.getString(0);
            int color = c.getInt(1);

            Categorie cat = new Categorie();

            cat.setNom(categorie);
            cat.setColorCategorie(color);
            el.add(cat);
        }

        c.close();
        return el;
    }


    public ArrayList<String> getlistNomCategorie() {
        ArrayList<String> liste = new ArrayList<>();
        Cursor c = eDb.rawQuery("select distinct categorie from " + NOM_TABLE_CATEGORIES + ";", null);
        while (c.moveToNext()) {
            liste.add(c.getString(0));
        }
        c.close();
        return liste;
    }

    public ArrayList<Produit> getListeProduit(String nomCategorie) {

        Cursor c = eDb.rawQuery("select distinct libelle,categorie,tarif,vente from " + NOM_TABLE_PRODUITS + " where " + CATEGORIE + "=? order by libelle", new String[]{String.valueOf(nomCategorie)});
        ArrayList<Produit> el = new ArrayList<>();

        while (c.moveToNext()) {
            String libelle = c.getString(0);
            String categorie = c.getString(1);
            Double tarif=c.getDouble(2);
            int vente=c.getInt(3);

            Produit p = new Produit(libelle,categorie,tarif,vente);

            el.add(p);
        }

        c.close();
        return el;
    }

    public ArrayList<String> getListeAllCommande() {

        Cursor c = eDb.rawQuery("select distinct commande from " + NOM_TABLE_COMMANDES,null);
        ArrayList<String> el = new ArrayList<>();

        while (c.moveToNext()) {
            String commande = c.getString(0);
            el.add(commande);

        }

        c.close();
        return el;
    }
    public ArrayList<String> getListeDate(){
        ArrayList<String> liste=new ArrayList<>();
        Cursor c = eDb.rawQuery("select distinct date from " + NOM_TABLE_COMMANDES,null);
        while (c.moveToNext()) {
            String date = c.getString(0);
            liste.add(date);
        }
        return liste;
    }

    public ArrayList<ObjectCommande> getListCommande(){
        ArrayList<ObjectCommande> liste=new ArrayList<>();

        Cursor c = eDb.rawQuery("select commande,date,libelle,categorie,tarif from " + NOM_TABLE_COMMANDES , null);

        while (c.moveToNext()) {
            String numero=c.getString(0);
            String date=c.getString(1);
            String libelle = c.getString(2);
            String categorie = c.getString(3);
            Double tarif=c.getDouble(4);


            ObjectCommande objectCommande = new ObjectCommande(numero,date,libelle,categorie,tarif);

            liste.add(objectCommande);
        }

        return liste;
    }

    public ArrayList<ObjectCommande> getListCommandeParDate(String date){
        ArrayList<ObjectCommande> liste=new ArrayList<>();

        Cursor c = eDb.rawQuery("select commande,date,libelle,categorie,tarif from " + NOM_TABLE_COMMANDES + " where " + DATE+ "=?", new String[]{String.valueOf(date)});

        while (c.moveToNext()) {
            String numero=c.getString(0);
            String datecom=c.getString(1);
            String libelle = c.getString(2);
            String categorie = c.getString(3);
            Double tarif=c.getDouble(4);


            ObjectCommande objectCommande = new ObjectCommande(numero,datecom,libelle,categorie,tarif);

            liste.add(objectCommande);
        }

        return liste;

    }
    public ArrayList<ObjectCommande> getListCommandeParNumero(String numero){
        ArrayList<ObjectCommande> liste=new ArrayList<>();

        Cursor c = eDb.rawQuery("select commande,date,libelle,categorie,tarif from " + NOM_TABLE_COMMANDES + " where " + COMMANDE+ "=?", new String[]{String.valueOf(numero)});

        while (c.moveToNext()) {
            String numeroCom=c.getString(0);
            String datecom=c.getString(1);
            String libelle = c.getString(2);
            String categorie = c.getString(3);
            Double tarif=c.getDouble(4);


            ObjectCommande objectCommande = new ObjectCommande(numeroCom,datecom,libelle,categorie,tarif);

            liste.add(objectCommande);
        }

        return liste;

    }
    public ArrayList<ObjectCommande> getListCommandeParCategorie(String categorie){
        ArrayList<ObjectCommande> liste=new ArrayList<>();

        Cursor c = eDb.rawQuery("select commande,date,libelle,categorie,tarif from " + NOM_TABLE_COMMANDES + " where " + CATEGORIE+ "=?", new String[]{String.valueOf(categorie)});

        while (c.moveToNext()) {
            String numeroCom=c.getString(0);
            String datecom=c.getString(1);
            String libelle = c.getString(2);
            String categorieCom= c.getString(3);
            Double tarif=c.getDouble(4);


            ObjectCommande objectCommande = new ObjectCommande(numeroCom,datecom,libelle,categorieCom,tarif);

            liste.add(objectCommande);
        }

        return liste;

    }

    public  void supprimerProduit(String libelle){

        eDb.delete(NOM_TABLE_PRODUITS, "libelle=?", new String[]{libelle});

    }

    public void mettreAjourUnProduit (Produit p, Produit pNew){

        String libelle=p.getLibelle();
        String categorie=p.getCategorie();


        eDb.delete(NOM_TABLE_PRODUITS, "libelle=? and categorie=?", new String[]{libelle,categorie});

        ContentValues value = new ContentValues();
        value.put(LIBELLE, pNew.getLibelle());
        value.put(CATEGORIE, pNew.getCategorie());
        value.put (TARIF,pNew.getTarif());
        eDb.insert(NOM_TABLE_PRODUITS, null, value);
    }

    public String getNumeroCommande(){
        String n="";
        int i=0;
        Cursor c = eDb.rawQuery("select distinct commande from " + NOM_TABLE_COMMANDES,null);
        i=c.getCount();
        n=String.valueOf(i);
      return n;
    }

    public void ajoutCommande(String nomCommande,Produit p) {

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        ContentValues value = new ContentValues();
        value.put(COMMANDE,nomCommande);
        value.put(DATE,timeStamp);
        value.put(LIBELLE, p.getLibelle());
        value.put(CATEGORIE, p.getCategorie());
        value.put(TARIF, p.getTarif());

        eDb.insert(NOM_TABLE_COMMANDES, null, value);
    }

    public void supprimerCommande(String numero){

        eDb.delete(NOM_TABLE_COMMANDES,"commande=?",new String[]{numero});

    }
    public void supprimerToutesCommandes(){

        eDb.execSQL("delete from "+NOM_TABLE_COMMANDES+" ");

    }

    public void supprimerCategorie(String libelle){

        eDb.delete(NOM_TABLE_CATEGORIES, "categorie=?", new String[]{libelle});

    }

    public void mettreAjourUneCategorie (Categorie c, Categorie cNew){

        String categorie=c.getNom();
        int couleur=c.getColorCategorie();

        eDb.delete(NOM_TABLE_CATEGORIES, "categorie=?", new String[]{categorie});

        ContentValues value = new ContentValues();

        value.put(CATEGORIE, cNew.getNom());
        value.put (COLOR,cNew.getColorCategorie());
        eDb.insert(NOM_TABLE_CATEGORIES, null, value);
    }

    public void mettreAjourlesProduits (Categorie c, Categorie cNew){

        Cursor cursor = eDb.rawQuery("select libelle,categorie,tarif,vente from " + NOM_TABLE_PRODUITS + " where " + CATEGORIE+ "=?", new String[]{String.valueOf(c.getNom())});

        while (cursor.moveToNext()) {
            String libelle=cursor.getString(0);
            Log.e("Message",libelle);
            eDb.execSQL("update "+NOM_TABLE_PRODUITS+" set categorie='"+cNew.getNom()+"' where libelle=?", new String[]{libelle});
        }

    }

    public void ajoutVenteProduit(ArrayList<Produit> arrayList){

        for(int i=0;i<arrayList.size();i++){

            Produit p=arrayList.get(i);
            p.setVente(p.getVente()+1);
            eDb.execSQL("update "+NOM_TABLE_PRODUITS+" set vente="+p.getVente()+" where libelle=?",new String[]{p.getLibelle()});
        }

    }

    public ArrayList<Produit> getListeProduitParVente(String nomCategorie) {

        Cursor c = eDb.rawQuery("select distinct libelle,categorie,tarif,vente from " + NOM_TABLE_PRODUITS + " where " + CATEGORIE + "=? order by vente desc", new String[]{String.valueOf(nomCategorie)});
        ArrayList<Produit> el = new ArrayList<>();

        while (c.moveToNext()) {
            String libelle = c.getString(0);
            String categorie = c.getString(1);
            Double tarif=c.getDouble(2);
            int vente=c.getInt(3);

            Produit p = new Produit(libelle,categorie,tarif,vente);

            el.add(p);
        }

        c.close();
        return el;
    }


}

