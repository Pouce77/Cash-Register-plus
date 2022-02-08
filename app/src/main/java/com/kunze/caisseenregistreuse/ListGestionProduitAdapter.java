package com.kunze.caisseenregistreuse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class ListGestionProduitAdapter extends ArrayAdapter<Produit> {

        private LayoutInflater mInflater = null;
        Context mContext;
        ProduitDAO mBase;
        ListView mlistView;
        List<Produit> mObjects;

        public ListGestionProduitAdapter(Context context, int resource, List<Produit> objects, ProduitDAO base, ListView listView) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
            mContext=context;
            mBase=base;
            mlistView=listView;
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

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Viewholder3 holder;
            if(convertView==null){

                convertView=mInflater.inflate(R.layout.listproduit,null);
                holder=new Viewholder3();
                holder.libelle=(TextView)convertView.findViewById(R.id.textCategorie);
                holder.tarif=(TextView)convertView.findViewById(R.id.texttarifCom);
                holder.nbrVentes=convertView.findViewById(R.id.nbrVentes);
                holder.imageEdit=convertView.findViewById(R.id.imageButtonEdit);
                holder.imageDelete=convertView.findViewById(R.id.imageButtonDelete);

                convertView.setTag(holder);
            }else{
                holder=(Viewholder3)convertView.getTag();
            }

            Produit p=getItem(position);
            Monnaie monnaie=new Monnaie();
            if (p!=null){

                holder.libelle.setText(p.getLibelle());
                holder.tarif.setText(String.valueOf(p.getTarif())+" "+monnaie.getMonnaie(mContext));
                holder.nbrVentes.setText(getContext().getResources().getString(R.string.nbreVente)+" "+String.valueOf(p.getVente()));

            }

            holder.imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String libelle=p.getLibelle();
                    String tarif=String.valueOf(p.getTarif());
                    LayoutInflater factory = LayoutInflater.from(mContext);
                    final View alertDialogView = factory.inflate(R.layout.boit_de_dialogue_modifier_produit, null);
                    final EditText modifLibell=alertDialogView.findViewById(R.id.editcategorieModif);
                    final EditText modifTarif=alertDialogView.findViewById(R.id.editTarifModif);
                    modifLibell.setText(libelle);
                    modifTarif.setText(tarif);
                    //Création de l'AlertDialog
                    final AlertDialog.Builder adb = new AlertDialog.Builder(mContext);

                    //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
                    adb.setView(alertDialogView);

                    //On donne un titre à l'AlertDialog
                    adb.setTitle(getContext().getResources().getString(R.string.ModifProd));

                    //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (modifLibell.getText().toString().matches("")){
                                Toast.makeText(mContext,getContext().getResources().getString(R.string.prodnonvide),Toast.LENGTH_LONG).show();
                            }else  if(modifTarif.getText().toString().matches("")){
                                Toast.makeText(mContext,getContext().getResources().getString(R.string.entrprixprod),Toast.LENGTH_LONG).show();
                            }else {

                                Produit pNew = new Produit(modifLibell.getText().toString(), p.getCategorie(), Double.parseDouble(modifTarif.getText().toString()),p.getVente());
                                mBase.mettreAjourUnProduit(p, pNew);
                                mObjects = mBase.getListeProduit(p.getCategorie());
                                ListGestionProduitAdapter adapter = new ListGestionProduitAdapter(mContext, R.layout.listproduit, mObjects, mBase, mlistView);
                                mlistView.setAdapter(adapter);
                            }

                        }

                    });

                    //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
                    adb.setNegativeButton(getContext().getResources().getString(R.string.annuler), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Lorsque l'on cliquera sur annuler on quittera l'application
                            dialog.dismiss();
                        }
                    });
                    adb.show();

                }
            });

            holder.imageDelete.setOnClickListener(new View.OnClickListener() {


                public void onClick(View v) {

                    String libelle=p.getLibelle();
                    LayoutInflater factory = LayoutInflater.from(mContext);
                    final View alertDialogView = factory.inflate(R.layout.boite_de_dialogue_supprimer_produit, null);
                    final TextView text=(TextView)alertDialogView.findViewById(R.id.textViewSupp);
                    text.setText(getContext().getResources().getString(R.string.supprimProduit)+" "+libelle +" ?");
                    //Création de l'AlertDialog
                    final AlertDialog.Builder adb = new AlertDialog.Builder(mContext);

                    //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
                    adb.setView(alertDialogView);

                    //On donne un titre à l'AlertDialog
                    adb.setTitle(R.string.deletProduct);

                    //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            mBase.supprimerProduit(libelle);
                            Toast.makeText(alertDialogView.getContext(),getContext().getResources().getString(R.string.leprodaetesupp), Toast.LENGTH_SHORT).show();
                            mlistView.deferNotifyDataSetChanged();
                            mObjects=mBase.getListeProduit(p.getCategorie());
                            ListGestionProduitAdapter adapter=new ListGestionProduitAdapter(mContext,R.layout.listproduit,mObjects,mBase,mlistView);
                            mlistView.setAdapter(adapter);
                        }

                    });

                    //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
                    adb.setNegativeButton(getContext().getResources().getString(R.string.annuler), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Lorsque l'on cliquera sur annuler on quittera l'application
                            dialog.dismiss();
                        }
                    });
                    adb.show();


                }
            });

            return convertView;

        }
    }

