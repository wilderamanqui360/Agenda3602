package com.example.empresa.agenda360.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.empresa.agenda360.R;
import com.example.reapro.myapplication.backend.dominio.alumnoApi.model.Alumno;
import com.example.reapro.myapplication.backend.dominio.notificacionApi.model.Notificacion;

import java.util.List;

/**
 * Created by REAPRO on 19/04/2015.
 */
public class AdapterNotificaciones extends ArrayAdapter<Notificacion> {

    private Context context;
    private int layout;
    private List<Notificacion>notificacions;

    public AdapterNotificaciones(Context context, int resource, List<Notificacion> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layout=resource;
        this.notificacions=objects;
    }


    static class OpcionHolder
    {
        ImageView imgTipoNotificacion;
        TextView txtDescripcion;
        TextView txtHora;
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
            holder.imgTipoNotificacion = (ImageView)row.findViewById(R.id.imgTipoNotificacion);
            holder.txtHora = (TextView)row.findViewById(R.id.txtHoraNotificacion);
            holder.txtDescripcion=(TextView)row.findViewById(R.id.txtDescripcionNotificacion);
            row.setTag(holder);
        }
        else
        {
            holder = (OpcionHolder)row.getTag();
        }

        Notificacion notificacion = notificacions.get(position);
        holder.txtDescripcion.setText(notificacion.getDescripcion());
        holder.txtHora.setText("" + notificacion.getHora());

        holder.imgTipoNotificacion.setImageResource(context.getResources().getIdentifier(notificacion.getTipoNotificacion().getTipo(),"drawable",context.getPackageName()));

        return row;
    }
}
