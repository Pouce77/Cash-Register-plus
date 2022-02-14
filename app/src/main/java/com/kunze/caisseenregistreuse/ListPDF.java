package com.kunze.caisseenregistreuse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class ListPDF extends AppCompatActivity {

    ListView listPDF;
    FloatingActionButton fabHome;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_csv);

        listPDF= findViewById(R.id.listCSV);
        fabHome = findViewById(R.id.fabHomePDF);
        arrayList = new ArrayList<>();

        File folder = new File(String.valueOf(getExternalFilesDir(null)));

        File[] file = folder.listFiles();

        if (file != null) {
            for (int i = 0; i < file.length; i++) {
                if (file[i].getName().endsWith("pdf")) {
                    arrayList.add(file[i].getName());
                    Log.e("File",file[i].getName());

                }

            }
        }

        ListPDFAdapter listPDFAdapter=new ListPDFAdapter(ListPDF.this,R.layout.listfichierpdf,arrayList);
        listPDF.setAdapter(listPDFAdapter);

        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent (ListPDF.this,MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);

                finish();

            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(ListPDF.this,MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);
        finish();    }
}