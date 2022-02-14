package com.kunze.caisseenregistreuse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class ListeCSV extends AppCompatActivity {

    ListView listCSV;
    FloatingActionButton fabHome;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_csv);

        listCSV = findViewById(R.id.listCSV);
        fabHome = findViewById(R.id.fabHomePDF);
        arrayList = new ArrayList<>();

        File folder = new File(String.valueOf(getExternalFilesDir(null)));

        File[] file = folder.listFiles();

        if (file != null) {
            for (int i = 0; i < file.length; i++) {
                if (file[i].getName().endsWith("csv")) {
                    arrayList.add(file[i].getName());

                }

            }
        }

        ListCSVAdapter listCSVAdapter=new ListCSVAdapter(ListeCSV.this,R.layout.listfichiercsv,arrayList);
        listCSV.setAdapter(listCSVAdapter);

        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent (ListeCSV.this,MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);

                finish();

            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(ListeCSV.this,MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);
        finish();    }
}