package com.kunze.caisseenregistreuse;

public class ObjectCommande {

    String numero;
    String dateCommande;
    String libelleProduit;
    String categorieProduit;
    Double tarifProduit;

    public ObjectCommande(String numero, String dateCommande, String libelleProduit,String categorieProduit, Double tarifProduit) {
        this.numero = numero;
        this.dateCommande = dateCommande;
        this.libelleProduit = libelleProduit;
        this.categorieProduit=categorieProduit;
        this.tarifProduit = tarifProduit;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getLibelleProduit() {
        return libelleProduit;
    }

    public void setLibelleProduit(String libelleProduit) {
        this.libelleProduit = libelleProduit;
    }

    public Double getTarifProduit() {
        return tarifProduit;
    }

    public void setTarifProduit(Double tarifProduit) {
        this.tarifProduit = tarifProduit;
    }

    public String getCategorieProduit() {
        return categorieProduit;
    }

    public void setCategorieProduit(String categorieProduit) {
        this.categorieProduit = categorieProduit;
    }
}
