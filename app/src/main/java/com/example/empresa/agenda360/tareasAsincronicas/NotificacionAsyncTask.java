package com.example.empresa.agenda360.tareasAsincronicas;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.empresa.agenda360.Constantes;
import com.example.empresa.agenda360.MainActivity;

import com.example.reapro.myapplication.backend.dominio.notificacionApi.NotificacionApi;
import com.example.reapro.myapplication.backend.dominio.notificacionApi.model.Notificacion;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by REAPRO on 20/05/2015.
 */
public class NotificacionAsyncTask extends AsyncTask<ParametroServicio, Void, List<Notificacion>> {
    private static NotificacionApi appService = null;

    private Context context;
    private PassObjectos passObjectos;


    public NotificacionAsyncTask(Context context, Fragment fragment) {
        this.context = context;
        if (fragment == null) {
            this.passObjectos = (PassObjectos) context;
        } else {
            this.passObjectos = (PassObjectos) fragment;
        }

    }

    public interface PassObjectos {
        public void retornarNotificaciones(List<Notificacion> alumnos);

    }

    @Override
    protected List<Notificacion> doInBackground(ParametroServicio... params) {

        ParametroServicio parametroServicio = params[0];

        if (appService == null) { // Only do this once
            inicializarAppService();
        }


        try {
            switch (parametroServicio.getTipo()) {
                case Constantes.SRV_NOTIFICACIONES_X_ALUMNO:
                    return appService.list().setFields(parametroServicio.getFiltros()).setIdAlumno(parametroServicio.getIdAlumno()).execute().getItems();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERRORNOTIFICACIONES", e.getMessage());
            return Collections.EMPTY_LIST;
        }
        return Collections.EMPTY_LIST;
    }


    @Override
    protected void onPostExecute(final List<Notificacion> alumnos) {
        final MainActivity mainActivity = (MainActivity) context;

        passObjectos.retornarNotificaciones(alumnos);
        super.onPostExecute(alumnos);
    }

    private void inicializarAppService() {

        NotificacionApi.Builder builder = new NotificacionApi.Builder(AndroidHttp.newCompatibleTransport(),
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
