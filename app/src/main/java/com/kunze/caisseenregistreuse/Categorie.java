package com.kunze.caisseenregistreuse;

import android.widget.Button;
import android.widget.CalendarView;

import javax.security.auth.login.LoginException;

public class Categorie {

    String nom;
    int colorCategorie;

    public Categorie() {
    }


    public Categorie(String nom, int colorCategorie) {
        this.nom = nom;
        this.colorCategorie = colorCategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getColorCategorie() {
        return colorCategorie;
    }

    public void setColorCategorie(int colorCategorie) {
        this.colorCategorie = colorCategorie;
    }
}
