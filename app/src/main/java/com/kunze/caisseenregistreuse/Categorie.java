package com.kunze.caisseenregistreuse;

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
