package com.kunze.caisseenregistreuse;

public class Produit {

    String libelle;
    String categorie;
    Double tarif;
    int vente;

    public Produit(String libelle, String categorie, Double tarif,int vente) {
        this.libelle = libelle;
        this.categorie = categorie;
        this.tarif = tarif;
        this.vente=vente;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public int getVente() {
        return vente;
    }

    public void setVente(int vente) {
        this.vente = vente;
    }
}
