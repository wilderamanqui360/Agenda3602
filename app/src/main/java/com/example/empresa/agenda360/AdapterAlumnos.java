package com.example.empresa.agenda360;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.empresa.agenda360.dominio.AlumnoAndroid;
import com.example.reapro.myapplication.backend.dominio.alumnoApi.model.Alumno;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dominio.OpcionGeneral;

/**
 * Created by REAPRO on 19/04/2015.
 */
public class AdapterAlumnos extends ArrayAdapter<AlumnoAndroid> {

    private Context context;
    private int layout;
    private List<AlumnoAndroid>alumnos;

    public AdapterAlumnos(Context context, int resource, List<AlumnoAndroid> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layout=resource;
        this.alumnos=objects;
    }

    public List<AlumnoAndroid> getAlumnos() {

        return alumnos;
    }

    public void setAlumnos(List<AlumnoAndroid> alumnos) {
        this.alumnos = alumnos;
    }

    static class OpcionHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        OpcionHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder = new OpcionHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgAlumno);


            holder.imgIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  // fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                   // intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                  //  intent.putExtra("ID_ALUMNO", position);


                    // start the image capture Intent
                            ((Activity) context).startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    ((MainActivity)context).setPosicionAlumno(position);
                }


            });
            holder.txtTitle = (TextView)row.findViewById(R.id.txtAlumno);
            row.setTag(holder);
        }
        else
        {
            holder = (OpcionHolder)row.getTag();
        }

        AlumnoAndroid alumnoAndroid = alumnos.get(position);
        Alumno alumno=alumnoAndroid.getAlumno();
        holder.txtTitle.setText(alumno.getNombre());
        holder.imgIcon.setImageBitmap(alumnoAndroid.getImgAlumno());



        return row;
    }



    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

}
