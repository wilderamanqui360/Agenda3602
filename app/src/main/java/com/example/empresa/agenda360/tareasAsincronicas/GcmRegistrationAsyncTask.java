package com.example.empresa.agenda360.tareasAsincronicas;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.empresa.agenda360.Constantes;
import com.example.reapro.myapplication.backend.registration.Registration;
import com.example.reapro.myapplication.backend.registration.model.RegistrationRecord;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by REAPRO on 19/04/2015.
 */
public class GcmRegistrationAsyncTask extends AsyncTask<ParametroServicio, Void, String> {

    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;

    private PassObjectos passObjectos;


    public GcmRegistrationAsyncTask(Context context, Fragment fragment) {
        this.context = context;
        if (fragment == null) {
            this.passObjectos = (PassObjectos) context;
        } else {
            this.passObjectos = (PassObjectos) fragment;
        }

    }

    public interface PassObjectos {
        public void retornarGCM(List<RegistrationRecord> registrations);

    }

    @Override
    protected String doInBackground(ParametroServicio... params) {
        ParametroServicio parametroServicio = params[0];
        Log.d(Constantes.TAG_APPLICATION, parametroServicio.toString());
        inicializarGCM();
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            String regId = gcm.register(Constantes.SENDER_ID);
            msg = "Device registered, registration ID=" + regId;

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            regService.register(regId, parametroServicio.getEmailFamiliar()).execute();

        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }


    @Override
    protected void onPostExecute(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
        passObjectos.retornarGCM(null);
    }

    private void inicializarGCM() {
        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                    // otherwise they can be skipped
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl("https://agendamovilitama.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end of optional local run code

            regService = builder.build();
        }
    }
}
