package com.kunze.caisseenregistreuse;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.preference.PreferenceManager;

import com.kunze.caisseenregistreuse.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    Button nouvelleCommande;
    Button historique;
    Button listCSV,test;

    public static final int CODE_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODE_REQUEST);
        }

        nouvelleCommande=findViewById(R.id.nouvelleCommande);
        test=findViewById(R.id.buttonPDF);

        historique=findViewById(R.id.historique);
        listCSV=findViewById(R.id.listeCSV);

        nouvelleCommande.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                Intent i= new Intent(MainActivity.this,Commande.class);
                startActivity(i);
                overridePendingTransition(R.anim.slideright, R.anim.slideoutleft);
                finish();
            }
        });

        historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(MainActivity.this,Historique.class);
                startActivity(i);
                overridePendingTransition(R.anim.slideright, R.anim.slideoutleft);
                finish();
            }
        });

        listCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MainActivity.this,ListeCSV.class);
                startActivity(i);
                overridePendingTransition(R.anim.slideright, R.anim.slideoutleft);
                finish();
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MainActivity.this,ListPDF.class);
                startActivity(i);
                overridePendingTransition(R.anim.slideright, R.anim.slideoutleft);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            Intent i= new Intent(MainActivity.this,NouvelleCategorie.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideright, R.anim.slideoutleft);
            finish();
            return true;
        }
        if (id == R.id.ajoutProduit) {

            Intent i= new Intent(MainActivity.this,NouveauProduit.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideright, R.anim.slideoutleft);
            finish();
            return true;
        }
        if (id == R.id.settings) {

            Intent i= new Intent(MainActivity.this,Settings.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideright, R.anim.slideoutleft);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permissions Granted !", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Permission denied !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}