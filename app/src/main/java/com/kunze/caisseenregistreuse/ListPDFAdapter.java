package com.kunze.caisseenregistreuse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;

public class ListPDFAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater = null;

    public ListPDFAdapter(@NonNull Context context, int resource, ArrayList <String> arrayList) {
        super(context, resource, arrayList);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public String getItem(int position) {
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


    @SuppressLint("WrongViewCast")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viewholder2 holder;
        if(convertView==null){

            convertView=mInflater.inflate(R.layout.listfichierpdf,null);
            holder=new Viewholder2();
            holder.nomFichier=(TextView)convertView.findViewById(R.id.nomFichier);
            holder.buttonOuvrir= (AppCompatImageButton) convertView.findViewById(R.id.buttonOuvrir);
            holder.buttonPartager= (AppCompatImageButton) convertView.findViewById(R.id.buttonPartager);
            holder.buttonPrint=convertView.findViewById(R.id.buttonPrint);
            holder.nomFichier.setText(getItem(position));

            holder.buttonOuvrir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fichier=holder.nomFichier.getText().toString();
                    File folder = new File(String.valueOf(getContext().getExternalFilesDir(null)));
                    File fi=new File(folder,fichier);
                    Uri uri= FileProvider.getUriForFile(getContext(),"com.kunze.caisseenregistreuse.provider",fi);

                    Intent j=new Intent(Intent.ACTION_VIEW);
                    j.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    if (fichier.endsWith("csv")) {
                        j.setDataAndType(uri,"text/csv");
                    }else{
                        j.setDataAndType(uri,"application/pdf");
                    }
                    getContext().startActivity(j);

                }
            });

            holder.buttonPartager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fichier=holder.nomFichier.getText().toString();
                    File folder = new File(String.valueOf(getContext().getExternalFilesDir(null)));
                    File fi=new File(folder,fichier);
                    Uri uri= FileProvider.getUriForFile(getContext(),"com.kunze.caisseenregistreuse.provider",fi);

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    if (fichier.endsWith("csv")) {
                        sharingIntent.setType("text/csv");
                    }else{
                        sharingIntent.setType("application/pdf");
                    }
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    getContext().startActivity(sharingIntent);

                }
            });

            holder.buttonPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fichier=holder.nomFichier.getText().toString();
                    File folder = new File(String.valueOf(getContext().getExternalFilesDir(null)));
                    File fi=new File(folder,fichier);
                    PrintManager printManager=(PrintManager) getContext().getSystemService(Context.PRINT_SERVICE);
                    try
                    {

                        PrintDocumentAdapter printAdapter = new PdfDocumentAdapter(getContext(),fi.getAbsolutePath());
                        assert printManager != null;
                        printManager.print("Document", printAdapter,new PrintAttributes.Builder().build());
                    }
                    catch (Exception e)
                    {

                    }

                }
            });

            convertView.setTag(holder);
        }else{
            holder=(Viewholder2)convertView.getTag();
        }

        return convertView;

    }

}
