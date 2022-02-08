package com.kunze.caisseenregistreuse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class ListHistoriqueCommandeAdapter extends ArrayAdapter<ObjectCommande> {

        private LayoutInflater mInflater = null;
        List<ObjectCommande> arrayList;

        Context mContext;

        public ListHistoriqueCommandeAdapter(Context context, int resource, List<ObjectCommande> objects) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
            arrayList=objects;

            mContext=context;
        }

        @Nullable
        @Override
        public ObjectCommande getItem(int position) {
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


    public ArrayList<ObjectCommande> getArrayList() {
        return (ArrayList<ObjectCommande>) arrayList;
    }



        @SuppressLint({"ResourceAsColor", "SetTextI18n"})
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewholderCom holder;
            if(convertView==null){

                convertView=mInflater.inflate(R.layout.listtoutescommandes,null);
                holder=new ViewholderCom();
                holder.numCommande=convertView.findViewById(R.id.textNumCommande);
                holder.dateCom=convertView.findViewById(R.id.textDate);
                holder.libelleCom=(TextView)convertView.findViewById(R.id.textCategorie);
                holder.categorieCom=convertView.findViewById(R.id.textCategorieCom);
                holder.tarifCom=(TextView)convertView.findViewById(R.id.texttarifCom);


                convertView.setTag(holder);
            }else{
                holder=(ViewholderCom)convertView.getTag();
            }

            ObjectCommande commande=getItem(position);
            Monnaie monnaie=new Monnaie();

            if (commande!=null){

                holder.numCommande.setText(commande.getNumero());
                holder.dateCom.setText(commande.getDateCommande());
                holder.categorieCom.setText(commande.getCategorieProduit());
                holder.libelleCom.setText(commande.getLibelleProduit());
                holder.tarifCom.setText(String.valueOf(commande.getTarifProduit())+" "+monnaie.getMonnaie(mContext));

            }

            return convertView;

        }
    }

