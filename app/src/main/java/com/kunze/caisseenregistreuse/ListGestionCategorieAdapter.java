package com.kunze.caisseenregistreuse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import androidx.appcompat.widget.AppCompatButton;

import java.util.List;

import top.defaults.colorpicker.ColorPickerPopup;


public class ListGestionCategorieAdapter extends ArrayAdapter<Categorie> {

        private LayoutInflater mInflater = null;
        Context mContext;
        ProduitDAO mBase;
        ListView mlistView;
        List<Categorie> mObjects;
        private int mDefaultcolor;

        public ListGestionCategorieAdapter(Context context, int resource, List<Categorie> objects, ProduitDAO base, ListView listView) {
            super(context, resource, objects);
            mInflater = LayoutInflater.from(context);
            mContext=context;
            mBase=base;
            mlistView=listView;
        }

        @Nullable
        @Override
        public Categorie getItem(int position) {
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

                convertView=mInflater.inflate(R.layout.listgestioncategorie,null);
                holder=new Viewholder3();
                holder.categorieGestion=(TextView)convertView.findViewById(R.id.textCategorie);
                holder.imageEdit=convertView.findViewById(R.id.imageButtonEdit);
                holder.imageDelete=convertView.findViewById(R.id.imageButtonDelete);

                convertView.setTag(holder);
            }else{
                holder=(Viewholder3)convertView.getTag();
            }

            Categorie c=getItem(position);
            if (c!=null){

                holder.categorieGestion.setText(c.getNom());
                holder.categorieGestion.setBackgroundColor(c.getColorCategorie());


            }

            holder.imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nomCat=c.getNom();
                    mDefaultcolor=c.getColorCategorie();

                    LayoutInflater factory = LayoutInflater.from(mContext);
                    final View alertDialogView = factory.inflate(R.layout.boite_de_dialogue_modifier_categorie, null);
                    final EditText modifLibell=alertDialogView.findViewById(R.id.editcategorieModif);
                    final AppCompatButton mPickerColorView=alertDialogView.findViewById(R.id.mcolorPickerView2);
                    mPickerColorView.setBackgroundColor(mDefaultcolor);
                    mPickerColorView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                    new ColorPickerPopup.Builder(mContext).initialColor(
                            Color.RED) // set initial color
                            // of the color
                            // picker dialog
                            .enableBrightness(
                                    true) // enable color brightness
                            // slider or not
                            .enableAlpha(
                                    true) // enable color alpha
                            // changer on slider or
                            // not
                            .okTitle(
                                    mContext.getResources().getString(R.string.chooseColor)) // this is top right
                            // Choose button
                            .cancelTitle(
                                    mContext.getResources().getString(R.string.annuler)) // this is top left
                            // Cancel button which
                            // closes the
                            .showIndicator(
                                    true) // this is the small box
                            // which shows the chosen
                            // color by user at the
                            // bottom of the cancel
                            // button
                            .showValue(
                                    true) // this is the value which
                            // shows the selected
                            // color hex code
                            // the above all values can be made
                            // false to disable them on the
                            // color picker dialog.
                            .build()
                            .show(
                                    v,
                                    new ColorPickerPopup.ColorPickerObserver() {
                                        @Override
                                        public void
                                        onColorPicked(int color) {
                                            // set the color
                                            // which is returned
                                            // by the color
                                            // picker
                                            mDefaultcolor= color;

                                            // now as soon as
                                            // the dialog closes
                                            // set the preview
                                            // box to returned
                                            // color
                                            mPickerColorView.setBackgroundColor(mDefaultcolor);
                                        }
                                    });
                        }
                    });
                    modifLibell.setText(nomCat);

                    //Création de l'AlertDialog
                    final AlertDialog.Builder adb = new AlertDialog.Builder(mContext);

                    //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
                    adb.setView(alertDialogView);

                    //On donne un titre à l'AlertDialog
                    adb.setTitle(mContext.getResources().getString(R.string.modifCategorie));

                    //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (modifLibell.getText().toString().matches("")){
                                Toast.makeText(mContext,mContext.getResources().getString(R.string.prodnonvide),Toast.LENGTH_LONG).show();
                           }else {

                                Categorie cNew = new Categorie(modifLibell.getText().toString(),mDefaultcolor);
                                mBase.mettreAjourUneCategorie(c, cNew);
                                mBase.mettreAjourlesProduits(c,cNew);
                                mObjects = mBase.getListeCategorie();
                                ListGestionCategorieAdapter adapter = new ListGestionCategorieAdapter(mContext, R.layout.listgestioncategorie, mObjects, mBase, mlistView);
                                mlistView.setAdapter(adapter);
                            }

                        }

                    });

                    //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
                    adb.setNegativeButton(mContext.getResources().getString(R.string.annuler), new DialogInterface.OnClickListener() {
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

                    String nomCat=c.getNom();
                    LayoutInflater factory = LayoutInflater.from(mContext);
                    final View alertDialogView = factory.inflate(R.layout.boite_de_dialogue_supprimer_produit, null);
                    final TextView text=(TextView)alertDialogView.findViewById(R.id.textViewSupp);
                    text.setText(mContext.getString(R.string.voulezvoussuppCate)+" "+nomCat +" ?");
                    //Création de l'AlertDialog
                    final AlertDialog.Builder adb = new AlertDialog.Builder(mContext);

                    //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
                    adb.setView(alertDialogView);

                    //On donne un titre à l'AlertDialog
                    adb.setTitle(mContext.getString(R.string.suppriCategorie));

                    //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            mBase.supprimerCategorie(nomCat);
                            Toast.makeText(alertDialogView.getContext(),mContext.getResources().getString(R.string.categorieaetesupp)+" "+nomCat+" !", Toast.LENGTH_SHORT).show();
                            mlistView.deferNotifyDataSetChanged();
                            mObjects=mBase.getListeCategorie();
                            ListGestionCategorieAdapter adapter=new ListGestionCategorieAdapter(mContext,R.layout.listgestioncategorie,mObjects,mBase,mlistView);
                            mlistView.setAdapter(adapter);
                        }

                    });

                    //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
                    adb.setNegativeButton(mContext.getString(R.string.annuler), new DialogInterface.OnClickListener() {
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
