package com.kunze.caisseenregistreuse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class ListCommandeAdapter extends ArrayAdapter<Produit> {

        private LayoutInflater mInflater = null;
        List<Produit> arrayList;
        ListView mlistView;
        Context mContext;
        TextView textPrix;

        public ListCommandeAdapter(Context context, int resource, List<Produit> objects, ListView listView,TextView textView) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
            arrayList=objects;
            mlistView=listView;
            textPrix=textView;

            mContext=context;

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

                convertView=mInflater.inflate(R.layout.listcommande,null);
                holder=new Viewholder2();
                holder.libelle=(TextView)convertView.findViewById(R.id.textCategorie);
                holder.tarif=(TextView)convertView.findViewById(R.id.texttarifCom);
                holder.imageButton=convertView.findViewById(R.id.deleteligneCommande);

                convertView.setTag(holder);
            }else{
                holder=(Viewholder2)convertView.getTag();
            }

            Produit p=getItem(position);
            Monnaie monnaie=new Monnaie();


            if (p!=null){

                holder.libelle.setText(p.getLibelle());
                holder.tarif.setText(String.valueOf(p.getTarif())+" "+monnaie.getMonnaie(mContext));

            }

            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {

                           arrayList.remove(p);
                           Double prix =Double.parseDouble(textPrix.getText().toString().replace(monnaie.getMonnaie(mContext),"" ));
                           prix=arrondi(prix-p.getTarif(),2);
                           textPrix.setText(String.valueOf(prix)+" "+monnaie.getMonnaie(mContext));
                           ListCommandeAdapter listCommandeAdapter=new ListCommandeAdapter(mContext,R.layout.listcommande,arrayList,mlistView,textPrix);
                           mlistView.setAdapter(listCommandeAdapter);

                }
            });


            return convertView;

        }

    public static double arrondi(Double A, int B) {

        return (double) (((int) (A * Math.pow(10, B) + .5)) / Math.pow(10, B));
    }
    }


