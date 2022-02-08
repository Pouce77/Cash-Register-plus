package com.kunze.caisseenregistreuse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class ListProduitAdapter extends ArrayAdapter<Produit> {

        private LayoutInflater mInflater = null;

        public ListProduitAdapter(Context context, int resource, List<Produit> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
        }

        @Nullable
        @Override
        public Produit getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @SuppressLint("ResourceAsColor")
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Viewholder2 holder;
            if(convertView==null){

                convertView=mInflater.inflate(R.layout.listproduitcommande,null);
                holder=new Viewholder2();
                holder.libelle=(TextView)convertView.findViewById(R.id.textCategorie);
                holder.tarif=(TextView)convertView.findViewById(R.id.texttarifCom);
                holder.layout=convertView.findViewById(R.id.layout);



                convertView.setTag(holder);
            }else{
                holder=(Viewholder2)convertView.getTag();
            }

            Produit p=getItem(position);
            Monnaie monnaie=new Monnaie();

            if (p!=null){

                holder.libelle.setText(p.getLibelle());
                holder.tarif.setText(String.valueOf(p.getTarif())+" "+monnaie.getMonnaie(getContext()));
                if (position % 2 == 0){
                    holder.layout.setBackgroundColor(Color.argb(70,179,179,179));

                }

            }


            return convertView;

        }
    }

