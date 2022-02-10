package com.kunze.caisseenregistreuse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Historique extends AppCompatActivity {

    Spinner spinnerTrie, spinnerCommandeTriee,spinnerTriFin;
    TextView choix,tarifHistoComm,textFin;
    ListView listeCommande;
    AppCompatButton enregistrerCSV;
    ProduitDAO base;
    ArrayList<String> arrayCommande;
    int choice=0;
    String totalCommande;
    ArrayList<ObjectCommande> arraylistCommande;
    RadioButton radioProduit;
    RadioButton radioCategorie,radioButton;
    FloatingActionButton fab;

    ArrayList<String> arrayFin;
    ArrayList<ObjectCommande>arrayForCSV;
    ListHistoriqueCommandeAdapter listHistoriqueCommandeAdapter;
    RadioGroup radioGroup;
    ArrayList<String>arrayList1;
    String numero="";
    Monnaie monnaie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        spinnerCommandeTriee=findViewById(R.id.spinnerCommmandeTriee);
        spinnerTrie=findViewById(R.id.spinnerTri);
        spinnerTriFin=findViewById(R.id.spinnerTriFin);
        textFin=findViewById(R.id.textFin);
        choix=findViewById(R.id.choix);
        tarifHistoComm=findViewById(R.id.tarifhistoComm);
        listeCommande=findViewById(R.id.listTriee);
        enregistrerCSV=findViewById(R.id.enregistrerCSV);

        radioProduit=findViewById(R.id.radioButtonProduit);
        radioCategorie=findViewById(R.id.radioButtonCategorie);
        radioGroup=findViewById(R.id.radioHisto);

        base=new ProduitDAO(Historique.this);
        base.openDb();

        arrayList1=new ArrayList<>();
        arrayList1.add(getResources().getString(R.string.aucunTri));
        arrayList1.add(getResources().getString(R.string.numeroComm));
        arrayList1.add(getResources().getString(R.string.datetri));
        arrayList1.add(getResources().getString(R.string.categorietri));
        arrayCommande=new ArrayList<>();
        arrayFin=new ArrayList<>();

        monnaie=new Monnaie();

        ArrayAdapter adapter=new ArrayAdapter(Historique.this, android.R.layout.simple_spinner_dropdown_item,arrayList1);
        spinnerTrie.setAdapter(adapter);

        spinnerTrie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tri=spinnerTrie.getSelectedItem().toString();
                if (tri.matches(getResources().getString(R.string.numeroComm))){
                    choix.setText(getResources().getString(R.string.choixnum));
                    choice=1;
                    arrayCommande = base.getListeAllCommande();
                    miseAjourSpinner(arrayCommande);
                }else if(tri.matches(getResources().getString(R.string.datetri))){
                    choix.setText(getResources().getString(R.string.choixdate));
                    arrayCommande=base.getListeDate();
                    choice=2;
                    miseAjourSpinner(arrayCommande);
                }else if(tri.matches(getResources().getString(R.string.categorietri))){
                    choix.setText(getResources().getString(R.string.choixcategorie));
                    choice=3;
                    arrayCommande=base.getlistNomCategorie();
                    miseAjourSpinner(arrayCommande);
                }else if (tri.matches(getResources().getString(R.string.aucunTri))){
                    arrayCommande.clear();
                    arrayCommande.add(getResources().getString(R.string.toutesCommandes));
                    choice=0;
                    choix.setText("");
                    miseAjourSpinner(arrayCommande);
                    ArrayList<ObjectCommande> arraylistCommande=base.getListCommande();
                    ListHistoriqueCommandeAdapter listHistoriqueCommandeAdapter=new ListHistoriqueCommandeAdapter(Historique.this,R.layout.listtoutescommandes,arraylistCommande);
                    listeCommande.setAdapter(listHistoriqueCommandeAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCommandeTriee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 if (choice==1){

                    arraylistCommande=base.getListCommandeParNumero(spinnerCommandeTriee.getItemAtPosition(position).toString());
                    miseaJourspinnerFin();
                     miseAjourList(arraylistCommande);
                    totalCommande=getTotalCommande(arraylistCommande);
                    tarifHistoComm.setText(totalCommande);

                }else if (choice==2){

                     arraylistCommande=base.getListCommandeParDate(spinnerCommandeTriee.getItemAtPosition(position).toString());
                     miseaJourspinnerFin();
                     miseAjourList(arraylistCommande);
                     totalCommande=getTotalCommande(arraylistCommande);
                     tarifHistoComm.setText(totalCommande);

                 }else if (choice==3){

                     arraylistCommande=base.getListCommandeParCategorie(spinnerCommandeTriee.getItemAtPosition(position).toString());
                     miseaJourspinnerFin();
                     miseAjourList(arraylistCommande);
                     totalCommande=getTotalCommande(arraylistCommande);
                     tarifHistoComm.setText(totalCommande);

                 }else if (choice==0){

                     arraylistCommande=base.getListCommande();
                     miseaJourspinnerFin();
                     miseAjourList(arraylistCommande);
                     totalCommande=getTotalCommande(arraylistCommande);
                     tarifHistoComm.setText(totalCommande);
                 }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTriFin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spinnerTriFin.getSelectedItem().toString().matches(getResources().getString(R.string.tousProduits))||spinnerTriFin.getSelectedItem().toString().matches(getResources().getString(R.string.tousCategorie))){
                    miseAjourList(arraylistCommande);
                    totalCommande=getTotalCommande(arraylistCommande);
                    tarifHistoComm.setText(totalCommande);
                }else{
                    ArrayList<ObjectCommande>objectliste=new ArrayList<>();
                    for(int i=0;i<arraylistCommande.size();i++){
                        if(arraylistCommande.get(i).libelleProduit.matches(spinnerTriFin.getSelectedItem().toString())||arraylistCommande.get(i).categorieProduit.matches(spinnerTriFin.getSelectedItem().toString())){
                            objectliste.add(arraylistCommande.get(i));
                        }
                    }
                    miseAjourList(objectliste);
                    totalCommande=getTotalCommande(objectliste);
                    tarifHistoComm.setText(totalCommande);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    enregistrerCSV.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LayoutInflater factory = LayoutInflater.from(Historique.this);
            final View alertDialogView = factory.inflate(R.layout.boite_de_dialogue_enregistrer_csv, null);

            //Création de l'AlertDialog
            final AlertDialog.Builder adb = new AlertDialog.Builder(Historique.this);

            //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
            adb.setView(alertDialogView);

            //On donne un titre à l'AlertDialog
            adb.setTitle(alertDialogView.getContext().getResources().getString(R.string.donnnomfichier));
            EditText editTextNom=alertDialogView.findViewById(R.id.editNomFichier);

            //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
            adb.setPositiveButton("Ok", (dialog, which) -> {

                arrayForCSV=listHistoriqueCommandeAdapter.getArrayList();
                String data="";
                String nomFichier="";
                String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(new Date());
                if (editTextNom.getText().toString().matches("")){
                    nomFichier=timeStamp+base.getNumeroCommande()+".csv";
                }else {
                    nomFichier = editTextNom.getText().toString() + ".csv";
                }
                data=alertDialogView.getContext().getResources().getString(R.string.data);
                for(int i=0;i<arrayForCSV.size();i++){
                    data=data+arrayForCSV.get(i).getNumero()+";"+
                            arrayForCSV.get(i).getDateCommande()+";"+
                            arrayForCSV.get(i).getCategorieProduit()+";"+
                            arrayForCSV.get(i).getLibelleProduit()+";"+
                            arrayForCSV.get(i).getTarifProduit()+"\n";
                }

                ecrireDansCSV(data,nomFichier);
                Toast.makeText(Historique.this,nomFichier+" "+alertDialogView.getContext().getResources().getString(R.string.aeteenregistrerdossier)+" "+String.valueOf(getExternalFilesDir(null)),Toast.LENGTH_LONG).show();

            });

            //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
            adb.setNegativeButton(getResources().getString(R.string.annuler), (dialog, which) -> dialog.dismiss());

            adb.show();

        }
    });



    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            radioButton=findViewById(checkedId);

            miseaJourspinnerFin();

        }
    });


    }
    public void miseaJourspinnerFin() {


        arrayFin = new ArrayList<>();
        if (radioButton==radioProduit) {
            arrayFin.add(getResources().getString(R.string.tousProduits));
            for (int i = 0; i < arraylistCommande.size(); i++) {
                if (!arrayFin.contains(arraylistCommande.get(i).getLibelleProduit())) {
                    arrayFin.add(arraylistCommande.get(i).getLibelleProduit());
                }
                ArrayAdapter arrayadapterFin = new ArrayAdapter(Historique.this, android.R.layout.simple_spinner_dropdown_item, arrayFin);
                spinnerTriFin.setAdapter(arrayadapterFin);
            }
        } else if (radioButton==radioCategorie) {
            arrayFin.add(getResources().getString(R.string.tousCategorie));
            for (int i = 0; i < arraylistCommande.size(); i++) {
                if (!arrayFin.contains(arraylistCommande.get(i).getCategorieProduit())) {
                    arrayFin.add(arraylistCommande.get(i).getCategorieProduit());
                }
                ArrayAdapter arrayadapterFin = new ArrayAdapter(Historique.this, android.R.layout.simple_spinner_dropdown_item, arrayFin);
                spinnerTriFin.setAdapter(arrayadapterFin);

            }
        }
    }

    public void miseAjourSpinner(ArrayList<String> tri){

            ArrayAdapter arrayAdapter = new ArrayAdapter(Historique.this, android.R.layout.simple_spinner_dropdown_item, tri);
            spinnerCommandeTriee.setAdapter(arrayAdapter);

    }

    public void miseAjourList(ArrayList<ObjectCommande> objectCommandes){

       listHistoriqueCommandeAdapter=new ListHistoriqueCommandeAdapter(Historique.this,R.layout.listtoutescommandes,objectCommandes);
        listeCommande.setAdapter(listHistoriqueCommandeAdapter);
    }

    public String getTotalCommande(ArrayList<ObjectCommande> objectCommandes){
        Double total=0.0;
        String tarif;

        for(int i=0;i<objectCommandes.size();i++){

            total=total+objectCommandes.get(i).tarifProduit;

        }
          tarif=String.valueOf(arrondi(total,2))+" "+monnaie.getMonnaie(Historique.this);

        return tarif;

    }

    public static double arrondi(Double A, int B) {

        return (double) (((int) (A * Math.pow(10, B) + .5)) / Math.pow(10, B));
    }

    public void ecrireDansCSV(String data, String filename) {

        File folder= new File(String.valueOf(getExternalFilesDir(null)));
        if(!folder.exists()){

            folder.mkdir();
        }

        File file=new File(folder,filename);
        int n=0;
        try{
            FileOutputStream out=new FileOutputStream(file);
            out.write(data.getBytes());

            if (out != null)
                out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        base.closeDb();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historique, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.supprimerCommande) {

            LayoutInflater factory = LayoutInflater.from(Historique.this);
            final View alertDialogView = factory.inflate(R.layout.boite_de_diaogue_supprimer_commande, null);

            //Création de l'AlertDialog
            final AlertDialog.Builder adb = new AlertDialog.Builder(Historique.this);

            //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
            adb.setView(alertDialogView);

            //On donne un titre à l'AlertDialog
            adb.setTitle(alertDialogView.getContext().getResources().getString(R.string.suppressComm));

            Spinner spinner=alertDialogView.findViewById(R.id.spinnerDatesupp);
            ListView listViewsup=alertDialogView.findViewById(R.id.listCommandesupp);
            ArrayList<String>arrayDate=new ArrayList<>();
            arrayDate=base.getListeDate();
            ArrayAdapter<String> adapter=new ArrayAdapter(alertDialogView.getContext(), android.R.layout.simple_spinner_dropdown_item,arrayDate);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ArrayList<ObjectCommande>arrayList=new ArrayList<>();
                    arrayList=base.getListCommandeParDate(spinner.getItemAtPosition(position).toString());
                    ArrayList<String>arrayNumero=new ArrayList<>();
                    for(int i=0;i<arrayList.size();i++){

                        String num=arrayList.get(i).getNumero();
                        if(!arrayNumero.contains(num)){
                            arrayNumero.add(num);
                        }

                    }
                    ArrayAdapter<String> adapterlist=new ArrayAdapter(alertDialogView.getContext(), android.R.layout.simple_list_item_1,arrayNumero);
                    listViewsup.setAdapter(adapterlist);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            listViewsup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    numero=parent.getItemAtPosition(position).toString();

                }
            });

            //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
            adb.setPositiveButton("Ok", (dialog, which) -> {


                base.supprimerCommande(numero);
                arraylistCommande=base.getListCommande();
                ArrayAdapter adapter1=new ArrayAdapter(Historique.this, android.R.layout.simple_spinner_dropdown_item,arrayList1);
                spinnerTrie.setAdapter(adapter1);
                miseAjourList(arraylistCommande);
                Toast.makeText(alertDialogView.getContext(),alertDialogView.getContext().getResources().getString(R.string.vousavezsuppcommnum)+numero+" !",Toast.LENGTH_LONG).show();


            });

            //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
            adb.setNegativeButton(getResources().getString(R.string.annuler), (dialog, which) -> dialog.dismiss());

            adb.show();

            return true;
        }
        if (id == R.id.supprimerToutesLesCommandes) {

            LayoutInflater factory = LayoutInflater.from(Historique.this);
            final View alertDialogView = factory.inflate(R.layout.boite_de_dialogue_supprimer_toutes_les_commandes, null);

            //Création de l'AlertDialog
            final AlertDialog.Builder adb = new AlertDialog.Builder(Historique.this);

            //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
            adb.setView(alertDialogView);

            //On donne un titre à l'AlertDialog
            adb.setTitle(alertDialogView.getContext().getResources().getString(R.string.supprallcomm));

            //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
            adb.setPositiveButton("Ok", (dialog, which) -> {

                base.supprimerToutesCommandes();
                arraylistCommande=base.getListCommande();
                ArrayAdapter adapter1=new ArrayAdapter(Historique.this, android.R.layout.simple_spinner_dropdown_item,arrayList1);
                spinnerTrie.setAdapter(adapter1);
                miseAjourList(arraylistCommande);
                Toast.makeText(alertDialogView.getContext(),alertDialogView.getContext().getResources().getString(R.string.vousavezsupptoutescomm),Toast.LENGTH_LONG).show();


            });

            //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
            adb.setNegativeButton(getResources().getString(R.string.annuler), (dialog, which) -> dialog.dismiss());

            adb.show();

            return true;
        }
        if (id == R.id.printInvoice) {

            LayoutInflater factory = LayoutInflater.from(Historique.this);
            final View alertDialogView = factory.inflate(R.layout.boite_de_dialogue_print_invoice, null);

            //Création de l'AlertDialog
            final AlertDialog.Builder adb = new AlertDialog.Builder(Historique.this);
            Spinner spinner=alertDialogView.findViewById(R.id.spinnerfacturePrint);
            ArrayList<String>arrayListComm=base.getListeAllCommande();
            ArrayAdapter arrayAdapter=new ArrayAdapter(alertDialogView.getContext(), android.R.layout.simple_spinner_dropdown_item,arrayListComm);
            spinner.setAdapter(arrayAdapter);
            EditText biling=alertDialogView.findViewById(R.id.editTextbilingAdress);

            //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
            adb.setView(alertDialogView);

            //On donne un titre à l'AlertDialog
            adb.setTitle(alertDialogView.getContext().getResources().getString(R.string.printInvoice));

            //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
            adb.setPositiveButton("Ok", (dialog, which) -> {

            String numero=spinner.getSelectedItem().toString();
            ArrayList<ObjectCommande>objectCommandeArrayList=base.getListCommandeParNumero(numero);
                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(alertDialogView.getContext());
                String coordo=pref.getString("edit_text_preference_2","[empty contact details]");
                String taxe=pref.getString("edit_text_preference_1","5.5");
                String date=objectCommandeArrayList.get(0).getDateCommande();
                ArrayList<String>arrayProduit=new ArrayList<>();
                ArrayList<String>arraytarifs=new ArrayList<>();
                Double totalTTC=0.0;
                for(int i=0;i<objectCommandeArrayList.size();i++){
                    arrayProduit.add(objectCommandeArrayList.get(i).getLibelleProduit());
                    arraytarifs.add(String.valueOf(objectCommandeArrayList.get(i).getTarifProduit()));
                    totalTTC=totalTTC+objectCommandeArrayList.get(i).getTarifProduit();
                }

                Facture facture=new Facture(alertDialogView.getContext(), numero,coordo,biling.getText().toString(),date,Double.parseDouble(taxe),String.valueOf(totalTTC),arrayProduit,arraytarifs);
                MyView view=new MyView(alertDialogView.getContext(),facture);

                facture.enregistrerFacture(view,numero);
                File file=new File(String.valueOf(getExternalFilesDir(null)),numero+".pdf");
                facture.imprimerFacture(file);

            });

            //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
            adb.setNegativeButton(getResources().getString(R.string.annuler), (dialog, which) -> dialog.dismiss());

            adb.show();

        return true;
        }

        if (id == R.id.home) {

            Intent i= new Intent(Historique.this,MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}