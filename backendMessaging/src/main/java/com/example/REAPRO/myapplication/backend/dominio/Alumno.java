package com.example.REAPRO.myapplication.backend.dominio;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreLoad;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Generated;

/**
 * Created by REAPRO on 19/04/2015.
 */

@Entity
public class Alumno {
    @Id

    Long id;
    String nombre;
    String apellido;
    String fechanacimiento;
    String sexo;
     @Index
     @Load
    List<Ref<Familiar>> familiares ;

    private Blob blobImage;



    Ref<Aula> aula;

    public Aula getAula() {
        return aula.get();
    }

    public void setAula(Aula aula) {
        this.aula = Ref.create(aula);
    }

    public byte[] getBlobImage() {
        if(blobImage==null)
            return null;
        return blobImage.getBytes();
    }

    public void setBlobImage(byte[] bytes) {
        this.blobImage = new Blob(bytes);
    }

    public List<Familiar> getFamiliares() {
        System.out.println("getFamiliares");
        List<Familiar> familiares = new ArrayList<Familiar>();
        ListIterator<Ref<Familiar>> iterator=this.familiares.listIterator();
        while (iterator.hasNext()) {
            Familiar a = iterator.next().get();
            familiares.add(a);
        }
        System.out.println("getFamiliares:: "+familiares.size());
        return familiares;
    }

    public void setFamiliares(List<Familiar> familiares) {
        System.out.println("seetFamiliares");
        if(this.familiares==null){
            this.familiares=new ArrayList<Ref<Familiar>>();
        }
        for (Familiar a : familiares) {
            Ref<Familiar> b = Ref.create(a);
            this.familiares.add(b);
        }

    }

    /*List<Ref<Notificacion>> notificaciones;
    public List<Notificacion> getNotificaciones() {
        System.out.println("getNotificaciones");
        List<Notificacion> notificacions = new ArrayList<Notificacion>();
        ListIterator<Ref<Notificacion>> iterator=this.notificaciones.listIterator();
        while (iterator.hasNext()) {
            Notificacion a = iterator.next().get();
            notificacions.add(a);
        }
        System.out.println("getFamiliares:: "+familiares.size());
        return notificacions;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        System.out.println("setNotificaciones");
        if(this.notificaciones==null){
            this.notificaciones=new ArrayList<Ref<Notificacion>>();
        }
        for (Notificacion a : notificaciones) {
            Ref<Notificacion> b = Ref.create(a);
            this.notificaciones.add(b);
        }

    }
*/

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

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


}
