package com.example.empresa.agenda360.tareasAsincronicas;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.empresa.agenda360.AdapterAlumnos;
import com.example.empresa.agenda360.AdapterOpciones;
import com.example.empresa.agenda360.Constantes;
import com.example.empresa.agenda360.MainActivity;
import com.example.empresa.agenda360.R;
import com.example.reapro.myapplication.backend.dominio.alumnoApi.AlumnoApi;
import com.example.reapro.myapplication.backend.dominio.alumnoApi.model.Alumno;
import com.example.reapro.myapplication.backend.dominio.colegioApi.ColegioApi;
import com.example.reapro.myapplication.backend.dominio.notificacionApi.NotificacionApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dominio.OpcionGeneral;

/**
 * Created by REAPRO on 20/05/2015.
 */
public class AlumnoAsyncTask extends AsyncTask<ParametroServicio, Void, List<Alumno>> {
    private static AlumnoApi appService = null;

    private Context context;
    private PassObjectos passObjectos;


    public AlumnoAsyncTask(Context context, Fragment fragment) {
        this.context = context;
        if (fragment == null) {
            this.passObjectos = (PassObjectos) context;
        } else {
            this.passObjectos = (PassObjectos) fragment;
        }

    }

    public interface PassObjectos {
        public void retornarResultados(List<Alumno> alumnos);

        public void retornarResultado(Alumno alumno);
    }

    @Override
    protected List<Alumno> doInBackground(ParametroServicio... params) {

        ParametroServicio parametroServicio = params[0];

        if (appService == null) { // Only do this once
            inicializarAppService();
        }

        Log.d(Constantes.TAG_APPLICATION,parametroServicio.toString());
        try {
            switch (parametroServicio.getTipo()) {
                case Constantes.SRV_LISTA_ALUMNOS_BASICO:
                    return appService.list().setFields(parametroServicio.getFiltros()).setFamiliar(parametroServicio.getEmailFamiliar()).execute().getItems();


                case Constantes.SRV_ALUMNO_BASICO:

                    List<Alumno> alumnos = new ArrayList<Alumno>();
                    Log.d("11","idalumno::"+parametroServicio.getIdAlumno());
                    Alumno alumno= appService.get(parametroServicio.getIdAlumno()).setId(parametroServicio.getIdAlumno()).execute();
                    Log.d("11","apellidp "+alumno.getApellido());
                    alumnos.add(alumno);
                    return alumnos;

                case Constantes.SRV_ALUMNO_UPDATE:
                    appService.updateFoto(parametroServicio.getIdAlumno(),(Alumno)parametroServicio.getObjeto()).setFields(parametroServicio.getFiltros()).execute();


            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERRORNOTIFICACIONES", e.getMessage());
            return Collections.EMPTY_LIST;
        }
        return Collections.EMPTY_LIST;
    }


    @Override
    protected void onPostExecute(final List<Alumno> alumnos) {
        final MainActivity mainActivity = (MainActivity) context;

        passObjectos.retornarResultados(alumnos);
        super.onPostExecute(alumnos);
    }

    private void inicializarAppService() {

        AlumnoApi.Builder builder = new AlumnoApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                // .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                .setRootUrl("https://agendamovilitama.appspot.com/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        // end options for devappserver

        appService = builder.build();
    }
}
