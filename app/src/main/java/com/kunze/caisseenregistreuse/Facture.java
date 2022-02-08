package com.kunze.caisseenregistreuse;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Facture {

    String coordonnees;
    String date;
    Double taxe;
    String totalTTC;
    ArrayList<String> listProduit,tarif;
    PdfDocument pdfDocument;
    PdfDocument.PageInfo pageInfo;
    Rect contentRect;
    Context mContext;
    String numero;
    String adresseFacture;


    public Facture(Context context,String numero,String coordonnees, String adresseFacture,String date, Double taxe, String totalTTC, ArrayList<String> listProduit,ArrayList<String>tarif) {

        this.mContext=context;
        this.coordonnees = coordonnees;
        this.date = date;
        this.taxe = taxe;
        this.totalTTC = totalTTC;
        this.listProduit = listProduit;
        this.tarif=tarif;
        this.numero=numero;
        this.adresseFacture=adresseFacture;
    }

    public ArrayList<String> getTarif() {
        return tarif;
    }

    public void setTarif(ArrayList<String> tarif) {
        this.tarif = tarif;
    }

    public String getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(String coordonnees) {
        this.coordonnees = coordonnees;
    }

    public String getAdresseFacture() {
        return adresseFacture;
    }

    public void setAdresseFacture(String adresseFacture) {
        this.adresseFacture = adresseFacture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTaxe() {
        return taxe;
    }

    public void setTaxe(Double taxe) {
        this.taxe = taxe;
    }

    public String getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(String totalTTC) {
        this.totalTTC = totalTTC;
    }

    public ArrayList<String> getListProduit() {
        return listProduit;
    }

    public void setListProduit(ArrayList<String> listProduit) {
        this.listProduit = listProduit;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void enregistrerFacture(View view, String numCom){


        contentRect=new Rect(20,20,585,822);
        pdfDocument=new PdfDocument();
        pageInfo=new PdfDocument.PageInfo.Builder(595,842,1).setContentRect(contentRect).create();

        PdfDocument.Page page=pdfDocument.startPage(pageInfo);
        view.draw(page.getCanvas());
        pdfDocument.finishPage(page);

        File folder = new File(String.valueOf(mContext.getExternalFilesDir(null)));

        if(!folder.exists()){

            folder.mkdir();
        }

        File file=new File(folder,numCom+".pdf");

        int n=0;
        try{
            FileOutputStream out=new FileOutputStream(file);
            pdfDocument.writeTo(out);

            if (out != null)
                out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();

    }



    public void imprimerFacture(File file){

        PrintManager printManager=(PrintManager) mContext.getSystemService(Context.PRINT_SERVICE);
        try
        {

            PrintDocumentAdapter printAdapter = new PdfDocumentAdapter(mContext,file.getAbsolutePath());
            assert printManager != null;
            printManager.print("Document", printAdapter,new PrintAttributes.Builder().build());
        }
        catch (Exception e)
        {

        }

    }

}
