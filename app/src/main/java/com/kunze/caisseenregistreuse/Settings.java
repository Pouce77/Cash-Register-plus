package com.kunze.caisseenregistreuse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, new SettingsFragment()).commit();

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
       pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
           @Override
           public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
               String monnaie=pref.getString(key,null);
               if(key.matches("edit_text_preference_1")){
                   Toast.makeText(Settings.this,"Vous avez changé la valeur des taxes en : "+monnaie+" %",Toast.LENGTH_LONG).show();

               }else if(key.matches("edit_text_preference_2")) {
                   Toast.makeText(Settings.this,"Vous avez changé les coordonnées en :\n "+monnaie,Toast.LENGTH_LONG).show();

               }else{
                     Toast.makeText(Settings.this,"Vous avez choisi la monnaie : "+monnaie,Toast.LENGTH_LONG).show();
              }
           }
       });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {

            Intent i= new Intent(Settings.this,MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideleft, R.anim.slideoutright);

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}