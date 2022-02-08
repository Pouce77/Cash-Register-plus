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

import java.util.List;

public class ListAdapter extends ArrayAdapter<Categorie> {


    private LayoutInflater mInflater = null;

    public ListAdapter(Context context, int resource, List<Categorie> objects) {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(context);
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




    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viewholder holder;
        if(convertView==null){

            convertView=mInflater.inflate(R.layout.listcategorie,null);
            holder=new Viewholder();
            holder.categorie=(TextView)convertView.findViewById(R.id.textcategorie);


            convertView.setTag(holder);
        }else{
            holder=(Viewholder)convertView.getTag();
        }

        Categorie c=getItem(position);
        holder.categorie.setText(c.getNom());
        holder.categorie.setBackgroundColor(c.getColorCategorie());

        return convertView;

    }
}