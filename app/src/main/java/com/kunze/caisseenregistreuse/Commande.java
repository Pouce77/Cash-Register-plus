package com.kunze.caisseenregistreuse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Commande extends AppCompatActivity {

    ListView listCategorie, listProduit,listViewCommande;
    ArrayList<Categorie> arrayCategorie;
    ArrayList<Produit> arrayProduit;
    ArrayList<Produit> arrayCommande;
    ProduitDAO base;
    ListAdapter arrayAdapter;
    ListProduitAdapter listProduitAdapter;
    TextView total,categorieSelect,textTaxe;
    Double prix=0.0;
    AppCompatButton enregistrerCommande;
    String nomCommande;
    AppCompatButton effacerCommande;
    FloatingActionButton fab;
    Monnaie monnaie;
    Facture facture;
    MyView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        listCategorie=findViewById(R.id.listCategorie);
        listProduit=findViewById(R.id.listProduits);
        listViewCommande=findViewById(R.id.listCommande);
        total=findViewById(R.id.textTarif);
        categorieSelect=findViewById(R.id.textCategorieselectionnee);
        base=new ProduitDAO(Commande.this);
        base.openDb();
        arrayCategorie=base.getListeCategorie();
        arrayCommande=new ArrayList<>();
        enregistrerCommande=findViewById(R.id.enregistrerCommande);
        effacerCommande=findViewById(R.id.effacerCommande);
        textTaxe=findViewById(R.id.textTaxe);
        monnaie=new Monnaie();
        total.setText(monnaie.getMonnaie(Commande.this)+" 0.00");

        arrayAdapter=new ListAdapter(Commande.this,R.layout.listcategorie,arrayCategorie);
        listCategorie.setAdapter(arrayAdapter);

        listCategorie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                arrayProduit= base.getListeProduitParVente(arrayAdapter.getItem(position).getNom());
                categorieSelect.setText(arrayAdapter.getItem(position).getNom());
                if (arrayProduit.isEmpty()){
                    Toast.makeText(Commande.this,"Vous n'avez pas encore enregistré de produit pour cette catégorie !",Toast.LENGTH_LONG).show();
                }else {
                    listProduitAdapter = new ListProduitAdapter(Commande.this, R.layout.listproduitcommande, arrayProduit);
                    listProduit.setAdapter(listProduitAdapter);
                }
            }
        });

        listProduit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Produit p=listProduitAdapter.getItem(position);
                arrayCommande.add(p);
                ListCommandeAdapter adapter=new ListCommandeAdapter(Commande.this,R.layout.listcommande,arrayCommande,listViewCommande,total);
                listViewCommande.setAdapter(adapter);
                prix =Double.parseDouble(total.getText().toString().replace(monnaie.getMonnaie(Commande.this),"" ));
                prix=prix+p.getTarif();
                prix=arrondi(prix,2);
                total.setText(monnaie.getMonnaie(Commande.this)+" "+String.valueOf(prix));
                textTaxe.setText("Dont taxes ("+String.valueOf(getTaxe())+" %) : "+String.valueOf((arrondi(prix*getTaxe()/100,2)))+" "+monnaie.getMonnaie(Commande.this));
            }
        });

        enregistrerCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nomCommande = ajoutnomCommande();
                for (int i = 0; i < arrayCommande.size(); i++) {

                    base.ajoutCommande(nomCommande, arrayCommande.get(i));
                }

                base.ajoutVenteProduit(arrayCommande);

                Toast.makeText(Commande.this, getResources().getString(R.string.ajoutcommande) + " " + nomCommande, Toast.LENGTH_LONG).show();

                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(Commande.this);
                String coordo=pref.getString("edit_text_preference_2","[empty contact details]");
                String date = new SimpleDateFormat("dd/MM/yyyy  hh:mm", Locale.getDefault()).format(new Date());
                ArrayList<String>arrayList=new ArrayList<>();
                ArrayList<String>arraytarif=new ArrayList<>();
                for(int i=0;i<arrayCommande.size();i++){
                    arrayList.add(arrayCommande.get(i).libelle);
                }
                for(int i=0;i<arrayCommande.size();i++){
                    arraytarif.add(String.valueOf(arrayCommande.get(i).tarif));
                }


                LayoutInflater factory = LayoutInflater.from(Commande.this);
                final View alertDialogView = factory.inflate(R.layout.boite_de_dialogue_enregistrer_pdf, null);

                EditText editAdress=alertDialogView.findViewById(R.id.editAdresseFacture);

                //Création de l'AlertDialog
                final AlertDialog.Builder adb = new AlertDialog.Builder(Commande.this);

                //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
                adb.setView(alertDialogView);

                //On donne un titre à l'AlertDialog
                adb.setTitle(alertDialogView.getContext().getResources().getString(R.string.titreboiteimprim));


                //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
                adb.setPositiveButton("Save only", (dialog, which) -> {
                    facture=new Facture(Commande.this,nomCommande,coordo,editAdress.getText().toString(),date,(arrondi(prix*getTaxe()/100,2)),String.valueOf(prix),arrayList,arraytarif);
                    view=new MyView(Commande.this,facture);

                    facture.enregistrerFacture(view,nomCommande);
                    Toast.makeText(alertDialogView.getContext(),alertDialogView.getContext().getResources().getString(R.string.savedInvoice)+" "+nomCommande+".pdf.",Toast.LENGTH_LONG).show();
                    total.setText(monnaie.getMonnaie(Commande.this)+" 0.00");
                    prix=0.0;
                    textTaxe.setText("Taxes ("+String.valueOf(getTaxe())+" %) : "+String.valueOf((arrondi(prix*getTaxe()/100,2)))+monnaie.getMonnaie(Commande.this));

                });

                adb.setNeutralButton("Print invoice", (dialog, which) -> {
                    facture=new Facture(Commande.this,nomCommande,coordo,editAdress.getText().toString(),date,(arrondi(prix*getTaxe()/100,2)),String.valueOf(prix),arrayList,arraytarif);
                    view=new MyView(Commande.this,facture);

                    facture.enregistrerFacture(view,nomCommande);
                    File file=new File(String.valueOf(getExternalFilesDir(null)),nomCommande+".pdf");
                    facture.imprimerFacture(file);
                    total.setText(monnaie.getMonnaie(Commande.this)+" 0.00");
                    prix=0.0;
                    textTaxe.setText("Taxes ("+String.valueOf(getTaxe())+" %) : "+String.valueOf((arrondi(prix*getTaxe()/100,2)))+monnaie.getMonnaie(Commande.this));
                    Toast.makeText(alertDialogView.getContext(),alertDialogView.getContext().getResources().getString(R.string.savedInvoice)+" "+nomCommande+".pdf.",Toast.LENGTH_LONG).show();

                });

                //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement

                adb.setNegativeButton(getResources().getString(R.string.annuler), (dialog, which) -> {
                    dialog.dismiss();
                    total.setText(monnaie.getMonnaie(Commande.this)+" 0.00");
                    prix=0.0;
                    textTaxe.setText("Taxes ("+String.valueOf(getTaxe())+" %) : "+String.valueOf((arrondi(prix*getTaxe()/100,2)))+monnaie.getMonnaie(Commande.this));

                });

                adb.show();

                arrayCommande.clear();
                ListCommandeAdapter adapter=new ListCommandeAdapter(Commande.this,R.layout.listcommande,arrayCommande,listViewCommande,total);
                listViewCommande.setAdapter(adapter);

            }
        });

        effacerCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayCommande.clear();
                ListCommandeAdapter adapter=new ListCommandeAdapter(Commande.this,R.layout.listcommande,arrayCommande,listViewCommande,total);
                listViewCommande.setAdapter(adapter);
                total.setText(monnaie.getMonnaie(Commande.this)+" 0.00");
                prix=0.0;
                textTaxe.setText("Taxes ("+String.valueOf(getTaxe())+" %) : "+String.valueOf((arrondi(prix*getTaxe()/100,2)))+monnaie.getMonnaie(Commande.this));

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commande, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ajoutCategorie) {

            Intent i= new Intent(Commande.this,NouvelleCategorie.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);
            finish();
            return true;
        }
        if (id == R.id.ajoutProduit) {

            Intent i= new Intent(Commande.this,NouveauProduit.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);
            finish();
            return true;
        }
        if (id == R.id.settings) {

            Intent i= new Intent(Commande.this,Settings.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);
            finish();
            return true;
        }

        if (id == R.id.home) {

            Intent i= new Intent(Commande.this,MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public String ajoutnomCommande(){
        String nom="";
        String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
        nom="CE"+timeStamp+base.getNumeroCommande();
        return nom;
    }

    public static double arrondi(Double A, int B) {

        return (double) (((int) (A * Math.pow(10, B) + .5)) / Math.pow(10, B));
    }

    public Double getTaxe(){
        Double taxe=0.0;
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(Commande.this);
        String taxeStringFormat=(pref.getString("edit_text_preference_1","20.0")).replace(",",".");
        String taxeFinish=taxeStringFormat.replace("%","");
        if (taxeFinish.matches("[0-9]*.?[0-9]*")&&!taxeFinish.matches("")){
            taxe=Double.parseDouble(taxeFinish);
        }
        Log.e("taxefinish",taxeFinish);
        Log.e("Taxe",String.valueOf(taxe));
        return taxe;
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(Commande.this,MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);
        finish();    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        base.closeDb();
    }
}