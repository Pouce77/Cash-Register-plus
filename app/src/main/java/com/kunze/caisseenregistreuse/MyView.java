package com.kunze.caisseenregistreuse;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.View;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;


public class MyView extends View {

    String coordonees;
    String date;
    String numero;
    ArrayList<String>listProduit;
    ArrayList<String>tarif;
    String total;
    String taxe;
    String adresseFacture;
    Paint paint;
    TextPaint textPaint;
    Rect rect;
    Facture facture;
    SharedPreferences pref;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context,Facture facture){
        super(context);

        this.facture=facture;

        pref= PreferenceManager.getDefaultSharedPreferences(getContext());

        numero=facture.getNumero();
        coordonees=pref.getString("edit_text_preference_2","[empty contact details]");
        date=facture.getDate();
        listProduit= facture.getListProduit();
        tarif= facture.getTarif();
        total=facture.getTotalTTC();
        taxe=String.valueOf(facture.getTaxe());
        adresseFacture=facture.getAdresseFacture();

        paint=new Paint();
        textPaint=new TextPaint();
        rect=new Rect(10,10,565,797);

        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(android.R.color.black));
        textPaint.setTextSize(12.0F);
        textPaint.setStrokeWidth(5);
        textPaint.setColor(getResources().getColor(android.R.color.black));
        Typeface var5 = Typeface.createFromAsset(context.getAssets(), "arial.ttf");
        textPaint.setTypeface(var5);
        setMinimumWidth(600);
        setMinimumHeight(800);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(rect,paint);

        int a = 30, b = 40;
        for (String line: coordonees.split("\n")) {
            canvas.drawText(line, a, b, textPaint);
            b += textPaint.descent() - textPaint.ascent();
        }
        String adresse=getContext().getResources().getString(R.string.bilingadress)+" :\n"+adresseFacture;
        int c=260,d=40;
        for (String line: adresse.split("\n")) {
            canvas.drawText(line, c, d, textPaint);
            d += textPaint.descent() - textPaint.ascent();
        }
        canvas.drawLine(245,15,245,b,paint);
        canvas.drawLine(20,b+3,555,b+3,paint);
        canvas.drawText(date,200,b+20,textPaint);

        canvas.drawLine(20,b+25,555,b+25,paint);
        canvas.drawText("Commande n°"+numero,30,b+45,textPaint);
        canvas.drawLine(20,b+55,555,b+55,paint);

        int y=b+100;
        int g=b+100;

        for(int i=0;i<listProduit.size();i++) {

            canvas.drawText(listProduit.get(i), 30, y, textPaint);
            y += textPaint.descent() - textPaint.ascent();
        }
        Monnaie monnaie=new Monnaie();
        for(int i=0;i<tarif.size();i++) {

            canvas.drawText(tarif.get(i)+" "+monnaie.getMonnaie(getContext()), 300, g, textPaint);
            g += textPaint.descent() - textPaint.ascent();
        }

        String taxepref=pref.getString("edit_text_preference_1","5.5");
        canvas.drawLine(20,y,555,y,paint);
        canvas.drawText(total+" "+monnaie.getMonnaie(getContext()) ,300,y+20,textPaint);
        canvas.drawText("Taxes ("+taxepref+"%) : "+taxe+" "+monnaie.getMonnaie(getContext()),30,y+20,textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(200,200);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



}
