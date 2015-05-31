package com.example.empresa.agenda360;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import dominio.OpcionGeneral;

/**
 * Created by REAPRO on 19/04/2015.
 */
public class AdapterOpciones extends ArrayAdapter<OpcionGeneral> {

    private Context context;
    private int layout;
    private List<OpcionGeneral>opciones;

    public AdapterOpciones(Context context, int resource, List<OpcionGeneral> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layout=resource;
        this.opciones=objects;
    }


    static class OpcionHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        OpcionHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder = new OpcionHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgOpcion);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtOpcion);
            row.setTag(holder);
        }
        else
        {
            holder = (OpcionHolder)row.getTag();
        }

        OpcionGeneral opcionGeneral = opciones.get(position);
        holder.txtTitle.setText(opcionGeneral.getOpcion());
        holder.imgIcon.setImageResource(Integer.valueOf(opcionGeneral.getImagen()));

        return row;
    }
}
