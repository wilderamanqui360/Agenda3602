package com.example.empresa.agenda360.tareasAsincronicas;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.example.reapro.myapplication.backend.dominio.colegioApi.ColegioApi;
import com.example.reapro.myapplication.backend.dominio.colegioApi.model.Colegio;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by REAPRO on 19/04/2015.
 */
public class EndpointsAsyncTask extends AsyncTask<Void, Void, List<Colegio>> {
    private static ColegioApi appService = null;

    private Context context;

    public EndpointsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Colegio> doInBackground(Void... params) {
        if (appService == null) { // Only do this once
            ColegioApi.Builder builder = new ColegioApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
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

        try {
            return appService.list().execute().getItems();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERRORNOTIFICACIONES",e.getMessage());
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Colegio> result) {
        Log.d("onPostExecute","result::" +result.size());
        for (Colegio q : result) {
            Toast.makeText(context, q.getNombre() + " : " + q.getDireccion(), Toast.LENGTH_LONG).show();
        }
    }
}
