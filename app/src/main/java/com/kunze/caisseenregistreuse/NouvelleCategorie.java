package com.kunze.caisseenregistreuse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import top.defaults.colorpicker.ColorPickerPopup;

public class NouvelleCategorie extends AppCompatActivity {

    EditText editCategorie;
    AppCompatButton buttonChangerColor, buttonEnregistrer;
    View mColorPickerView;
    private int mDefaultColor;
    FloatingActionButton fab;
    ListView listCategorie;

    ProduitDAO base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_categorie);

        editCategorie=findViewById(R.id.editNomCategorie);
        buttonChangerColor=findViewById(R.id.buttonChangercolor);
        buttonEnregistrer=findViewById(R.id.buttonEnregistrerCategorie);
        listCategorie=findViewById(R.id.listViewCategorie);
        fab=findViewById(R.id.fabCat);

        mColorPickerView=findViewById(R.id.mcolorPickerView2);
        mDefaultColor=Color.BLUE;
        base=new ProduitDAO(NouvelleCategorie.this);
        base.openDb();

        buttonChangerColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(NouvelleCategorie.this).initialColor(
                        Color.RED) // set initial color
                        // of the color
                        // picker dialog
                        .enableBrightness(
                                true) // enable color brightness
                        // slider or not
                        .enableAlpha(
                                true) // enable color alpha
                        // changer on slider or
                        // not
                        .okTitle(
                                getResources().getString(R.string.chooseColor)) // this is top right
                        // Choose button
                        .cancelTitle(
                                getResources().getString(R.string.annuler)) // this is top left
                        // Cancel button which
                        // closes the
                        .showIndicator(
                                true) // this is the small box
                        // which shows the chosen
                        // color by user at the
                        // bottom of the cancel
                        // button
                        .showValue(
                                true) // this is the value which
                        // shows the selected
                        // color hex code
                        // the above all values can be made
                        // false to disable them on the
                        // color picker dialog.
                        .build()
                        .show(
                                v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        // set the color
                                        // which is returned
                                        // by the color
                                        // picker
                                        mDefaultColor = color;

                                        // now as soon as
                                        // the dialog closes
                                        // set the preview
                                        // box to returned
                                        // color
                                        mColorPickerView.setBackgroundColor(mDefaultColor);

                                    }
                                });

            }
        });

        buttonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editCategorie.getText().toString().matches("")|| editCategorie.getText().toString()==null) {

                    Toast.makeText(NouvelleCategorie.this,getResources().getString(R.string.vousDevezentrernomCat),Toast.LENGTH_LONG).show();

                }else{
                    Categorie c = new Categorie(editCategorie.getText().toString(), mDefaultColor);
                    base.ajoutCategorie(c);
                    ArrayList<Categorie> arrayList=new ArrayList<>();
                    arrayList=base.getListeCategorie();
                    ListGestionCategorieAdapter listGestionCategorieAdapter=new ListGestionCategorieAdapter(NouvelleCategorie.this,R.layout.listgestioncategorie,arrayList,base,listCategorie);
                    listCategorie.setAdapter(listGestionCategorieAdapter);
                    Toast.makeText(NouvelleCategorie.this,getResources().getString(R.string.vouavezenregilaCat)+" "+editCategorie.getText().toString()+".",Toast.LENGTH_LONG).show();

                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(NouvelleCategorie.this,MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);

                finish();
            }
        });

        ArrayList<Categorie> arrayList=new ArrayList<>();
        arrayList=base.getListeCategorie();
        ListGestionCategorieAdapter listGestionCategorieAdapter=new ListGestionCategorieAdapter(NouvelleCategorie.this,R.layout.listgestioncategorie,arrayList,base,listCategorie);
        listCategorie.setAdapter(listGestionCategorieAdapter);

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

            Intent i= new Intent(NouvelleCategorie.this,Commande.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideright, R.anim.slideoutleft);

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        base.closeDb();
    }
}