package com.kunze.caisseenregistreuse;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NouveauProduit extends AppCompatActivity {

    Spinner spinner;
    AppCompatButton enregistrer;
    EditText editLibelle,editTarif;
    ListView listviewProduit;
    ProduitDAO base;
    ArrayList<Produit> listProduit;
    ListGestionProduitAdapter adapter;
    ArrayList<String>arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_produit);

        base=new ProduitDAO(this);
        spinner=findViewById(R.id.spinnerCategorie);
        enregistrer=findViewById(R.id.buttonEnregistrerProduit);
        editLibelle=findViewById(R.id.editTextLibelle);
        editTarif=findViewById(R.id.editTextPrix);
        listviewProduit=findViewById(R.id.listProduits);

        base.openDb();
        arrayList=new ArrayList<>();
        arrayList=base.getlistNomCategorie();

        if (arrayList==null||arrayList.isEmpty()){
            Toast.makeText(NouveauProduit.this,getResources().getString(R.string.pasdecategorie),Toast.LENGTH_LONG).show();
        }else {

            ArrayAdapter<String> dadapter = new ArrayAdapter<String>(NouveauProduit.this, android.R.layout.simple_spinner_item, arrayList);
            dadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dadapter);


            if (spinner.getSelectedItem() != null) {
                listProduit = base.getListeProduit(spinner.getSelectedItem().toString());
            }

            adapter = new ListGestionProduitAdapter(NouveauProduit.this, R.layout.listproduit, listProduit, base, listviewProduit);
            listviewProduit.setAdapter(adapter);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //On déclare quelle categorie est visible

                listProduit = base.getListeProduitParVente(spinner.getSelectedItem().toString());
                adapter = new ListGestionProduitAdapter(NouveauProduit.this,R.layout.listproduit,listProduit,base,listviewProduit);
                listviewProduit.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayList == null || arrayList.isEmpty()) {
                    Toast.makeText(NouveauProduit.this,getResources().getString(R.string.veuillezaumoinsune),Toast.LENGTH_LONG).show();
                } else {
                    if (editLibelle.getText().toString().matches("")) {
                        Toast.makeText(NouveauProduit.this, getResources().getString(R.string.prodnonvide), Toast.LENGTH_LONG).show();

                    } else if (editTarif.getText().toString().matches("")) {
                        Toast.makeText(NouveauProduit.this, getResources().getString(R.string.entrprixprod), Toast.LENGTH_LONG).show();

                    } else if (editTarif.getText().toString().matches("") || Float.parseFloat(editTarif.getText().toString()) == 0.0) {
                        Toast.makeText(NouveauProduit.this, getResources().getString(R.string.entreprixdiffde0), Toast.LENGTH_LONG).show();

                    } else {
                        String libelle = editLibelle.getText().toString();
                        String categorie = spinner.getSelectedItem().toString();
                        Double prix = Double.parseDouble(editTarif.getText().toString());
                        int vente=0;
                        Produit p = new Produit(libelle, categorie, prix,vente);
                        base.ajoutProduit(p);
                        editLibelle.setText("");
                        editTarif.setText("0.00");
                        Toast.makeText(NouveauProduit.this, getResources().getString(R.string.enregiSucces), Toast.LENGTH_LONG).show();
                        mettreAjourlist();
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gestion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.newCommande) {

            Intent i= new Intent(NouveauProduit.this,Commande.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideright, R.anim.slideoutleft);

            finish();

            return true;
        }

        if (id == R.id.home) {

            Intent i= new Intent(NouveauProduit.this,MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void mettreAjourlist(){

        listProduit = base.getListeProduitParVente(spinner.getSelectedItem().toString());
        adapter = new ListGestionProduitAdapter(NouveauProduit.this,R.layout.listproduit,listProduit,base,listviewProduit);
        listviewProduit.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        base.closeDb();
    }
}