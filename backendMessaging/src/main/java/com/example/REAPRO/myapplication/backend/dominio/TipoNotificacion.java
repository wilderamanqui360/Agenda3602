package com.example.REAPRO.myapplication.backend.dominio;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by REAPRO on 25/04/2015.
 */
@Entity
public class TipoNotificacion {
    @Id
    Long id;
    String nombre;
    String descripcion;
    String tipo; //nombre de imagen
    String calificadores;

    public String getCalificadores() {
        return calificadores;
    }

    public void setCalificadores(String calificadores) {
        this.calificadores = calificadores;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
