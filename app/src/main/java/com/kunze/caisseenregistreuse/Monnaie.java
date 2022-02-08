package com.kunze.caisseenregistreuse;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

public class Monnaie {


    public Monnaie() {

    }

    public String getMonnaie(Context context){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String monnaie=pref.getString("list_preference_1","€");

        return  monnaie;
    }
}
