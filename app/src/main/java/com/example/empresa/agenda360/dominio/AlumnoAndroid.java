package com.example.empresa.agenda360.dominio;

import android.graphics.Bitmap;

import com.example.reapro.myapplication.backend.dominio.alumnoApi.model.Alumno;

/**
 * Created by REAPRO on 25/05/2015.
 */
public class AlumnoAndroid {
    private Alumno alumno;
    private Bitmap imgAlumno;


    public Alumno getAlumno() {
        return alumno;
    }



    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Bitmap getImgAlumno() {
        return imgAlumno;
    }

    public void setImgAlumno(Bitmap imgAlumno) {
        this.imgAlumno = imgAlumno;
    }
}
