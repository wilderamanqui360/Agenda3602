package com.example.REAPRO.myapplication.backend.dominio;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by REAPRO on 25/04/2015.
 */
@Entity
public class Notificacion {
    @Id
    Long id;
    String descripcion;
    String fecha;
    String hora;
    Ref<TipoNotificacion> tipoNotificacion;
    @Index Ref<Alumno> alumno;
    String calificador;

    public String getCalificador() {
        return calificador;
    }

    public void setCalificador(String calificador) {
        this.calificador = calificador;
    }

    public Alumno getAlumno() {
        return alumno.get();
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = Ref.create(alumno);
    }

    public TipoNotificacion getTipoNotificacion() {
        return tipoNotificacion.get();
    }

    public void setTipoNotificacion(TipoNotificacion tipoNotificacion) {
        this.tipoNotificacion = Ref.create(tipoNotificacion);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
