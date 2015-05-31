package com.example.empresa.agenda360.tareasAsincronicas;

import com.example.reapro.myapplication.backend.dominio.alumnoApi.model.Alumno;
import com.example.reapro.myapplication.backend.dominio.representanteApi.model.Representante;

/**
 * Created by REAPRO on 24/05/2015.
 */
public class ParametroServicio {

    private int tipo;
    private String emailFamiliar;
    private Long idAlumno;
    private String filtros;
    private Object objeto;


    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    @Override
    public String toString() {
        return "idAlumno: "+idAlumno+" filtros: "+filtros+" emailRep: "+emailFamiliar+" tipo:"+tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getFiltros() {
        return filtros;
    }

    public void setFiltros(String filtros) {
        this.filtros = filtros;
    }

    public String getEmailFamiliar() {
        return emailFamiliar;
    }

    public void setEmailFamiliar(String emailRepresentante) {
        this.emailFamiliar = emailRepresentante;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }
}
