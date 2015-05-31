package com.example.empresa.agenda360;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;

import android.os.Bundle;

import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.empresa.agenda360.dominio.AlumnoAndroid;
import com.example.empresa.agenda360.fragmentos.ContactosFragment;
import com.example.empresa.agenda360.fragmentos.HistoricoFragment;
import com.example.empresa.agenda360.fragmentos.RegistroMensajeFragment;
import com.example.empresa.agenda360.tareasAsincronicas.AlumnoAsyncTask;
import com.example.empresa.agenda360.tareasAsincronicas.GcmRegistrationAsyncTask;
import com.example.empresa.agenda360.tareasAsincronicas.ParametroServicio;

import com.example.reapro.myapplication.backend.dominio.alumnoApi.model.Alumno;
import com.example.reapro.myapplication.backend.dominio.alumnoApi.model.Colegio;
import com.example.reapro.myapplication.backend.dominio.familiarApi.model.Familiar;
import com.example.reapro.myapplication.backend.dominio.representanteApi.model.Representante;
import com.example.reapro.myapplication.backend.registration.model.RegistrationRecord;
import com.google.identitytoolkit.GitkitClient;
import com.google.identitytoolkit.GitkitUser;
import com.google.identitytoolkit.IdToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dominio.OpcionGeneral;

public class MainActivity extends Activity implements ContactosFragment.OnFragmentInteractionListener, HistoricoFragment.Comunicator, RegistroMensajeFragment.OnFragmentInteractionListener, AlumnoAsyncTask.PassObjectos, GcmRegistrationAsyncTask.PassObjectos {

    private DrawerLayout drawerLayout;
    private ListView listOpciones;
    private ListView listaAlumnos;
    private int posicionAlumno;
    private ImageView imgAlumno;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ProgressDialog progressDialog;
    private GitkitClient client;


    private Familiar familiar = new Familiar();

    public int getPosicionAlumno() {
        return posicionAlumno;
    }

    public void setPosicionAlumno(int posicionAlumno) {
        this.posicionAlumno = posicionAlumno;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = GitkitClient.newBuilder(this, new GitkitClient.SignInCallbacks() {
            @Override
            public void onSignIn(IdToken idToken, GitkitUser user) {
                Log.d("USUARIOLOGUEADO", "" + idToken + user.getEmail() + "/" + user.getDisplayName() + "/" + user.getPhotoUrl() + "/" + user.getIdProvider());
                familiar.setEmail(user.getEmail());
                familiar.setNombre(user.getDisplayName());
                validarRegistroGCM(familiar);

                //new LoadViewTask().execute();
            }

            @Override
            public void onSignInFailed() {
                Toast.makeText(MainActivity.this, "Sign in failed", Toast.LENGTH_LONG).show();
            }
        }).build();
        client.startSignIn();


    }

    private boolean validarRegistroGCM(Familiar familiar) {

        ParametroServicio parametroServicio = new ParametroServicio();
        parametroServicio.setEmailFamiliar(familiar.getEmail());
        new GcmRegistrationAsyncTask(this, null).execute(parametroServicio);
        return false;
    }

    private void cargarActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    private void cargarActionBarDrawerToggle() {

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav, R.string.mensaje) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                Log.d("A", "Close");
                super.onDrawerClosed(view);
                // getActionBar().setTitle("titulo cerrado");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                Log.d("A", "Abierto");
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle("titulo abierto");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (actionBarDrawerToggle != null) {
            actionBarDrawerToggle.syncState();
        }
    }


    private void cargarListaOpciones() {
        listOpciones = (ListView) findViewById(R.id.listaOpciones);
        //View header = getLayoutInflater().inflate(R.layout.headeralumnos, null);


        // listOpciones.addHeaderView(header);
        listOpciones.setHeaderDividersEnabled(true);
        listOpciones.setDividerHeight(2);

        //ListView listaAlumnos = (ListView) findViewById(R.id.listaalumnos);
        String[] opcionesGeneralesData = getResources().getStringArray(R.array.opciones);
        List<OpcionGeneral> opcionesGenerales = new ArrayList<OpcionGeneral>();
        for (String op : opcionesGeneralesData) {
            OpcionGeneral opcionGeneral = new OpcionGeneral();
            opcionGeneral.setOpcion(op);
            opcionGeneral.setImagen("" + R.drawable.ic_action_email);
            opcionesGenerales.add(opcionGeneral);
        }

        AdapterOpciones adapterOpciones = new AdapterOpciones(this, R.layout.listopcion, opcionesGenerales);

        // AdapterOpciones adapterAlumnos = new AdapterOpciones(this, R.layout.listopcion, opcionesGenerales);
        listaAlumnos=(ListView) findViewById(R.id.listaalumnos);
        listOpciones.setAdapter(adapterOpciones);
        listOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case Constantes.OP_Comunicados:
                        break;
                    case Constantes.OP_Contactar:
                        llamarFragmentoContacto();
                        break;
                    case Constantes.OP_Ajustes:
                        break;
                    case Constantes.OP_Informacion:
                        break;
                }
            }
        });
        // listaAlumnos.setAdapter(adapterAlumnos);
        ParametroServicio parametroServicio = new ParametroServicio();
        parametroServicio.setFiltros(Constantes.FILTROS_BASICO_ALUMNO);
        parametroServicio.setEmailFamiliar(familiar.getEmail());
        parametroServicio.setTipo(Constantes.SRV_LISTA_ALUMNOS_BASICO);
        new AlumnoAsyncTask(this, null).execute(parametroServicio);
    }


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;

    private void cargarPantalla() {

        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        cargarListaOpciones();
        cargarActionBarDrawerToggle();
        cargarActionBar();
        drawerLayout.openDrawer(Gravity.START);

    }


    //OPCIONES DE MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //INVOCACION A FRAGMENTOS
    private void llamarFragmentoContacto() {

        Fragment fragment = ContactosFragment.newInstance(null, null);
        getFragmentManager().beginTransaction().replace(R.id.contentFrame, fragment).commit();
        Log.d("llamarhistorico", "inciando fragmento contacto");
        // update selected item and title, then close the drawer


        //drawerLayout.closeDrawer(listOpciones.getRootView());
        drawerLayout.closeDrawers();
    }

    public void llamarhistorico(Alumno alumno) {

        Fragment fragment = new HistoricoFragment();
        Bundle args = new Bundle();
        args.putLong(HistoricoFragment.ARG_ALUMNO_ID, alumno.getId());


        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFrame, fragment).commit();
        Log.d("llamarhistorico", "inciando fragmento Historico");

        // update selected item and title, then close the drawer

        setTitle(alumno.getNombre());

        //drawerLayout.closeDrawer(listOpciones.getRootView());
        drawerLayout.closeDrawers();

    }

    @Override
    public void redactarMensaje() {
        Fragment fragment = RegistroMensajeFragment.newInstance("", "");
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFrame, fragment).addToBackStack(null).commit();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public List<Colegio> obtenerColegios() {
        return getColegios();
    }


    // AUTENTICACION

    // Step 3: Override the onActivityResult method.
    // When a result is returned to this activity, it is maybe intended for GitkitClient. Call
    // GitkitClient.handleActivityResult to check the result. If the result is for GitkitClient,
    // the method returns true to indicate the result has been consumed.


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (!client.handleActivityResult(requestCode, resultCode, intent)) {


            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    // Image captured and saved to fileUri specified in the Intent
                    //  Toast.makeText(this, "Image saved to:\n" +                            intent.getData(), Toast.LENGTH_LONG).show();

                    AdapterAlumnos adapterAlumnos = (AdapterAlumnos) listaAlumnos.getAdapter();
                    Bitmap bp = (Bitmap) intent.getExtras().get("data");

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    AlumnoAndroid alumno = adapterAlumnos.getItem(posicionAlumno);
                    alumno.setImgAlumno(bp);
                    String s = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    //alumno.getAlumno().encodeBlobImage(byteArray);
                    alumno.getAlumno().setBlobImage(s);
                    //alumno.setNombre("CAMBIOOOOO");
                    adapterAlumnos.notifyDataSetChanged();

                    ParametroServicio parametroServicio = new ParametroServicio();
                    parametroServicio.setIdAlumno(alumno.getAlumno().getId());
                    parametroServicio.setTipo(Constantes.SRV_ALUMNO_UPDATE);
                    parametroServicio.setObjeto(alumno.getAlumno());
                    new AlumnoAsyncTask(this, null).execute(parametroServicio);


                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
            }
            super.onActivityResult(requestCode, resultCode, intent);
        }

    }

    // Step 4: Override the onNewIntent method.
    // When the app is invoked with an intent, it is possible that the intent is for GitkitClient.
    // Call GitkitClient.handleIntent to check it. If the intent is for GitkitClient, the method
    // returns true to indicate the intent has been consumed.

    @Override
    protected void onNewIntent(Intent intent) {
        if (!client.handleIntent(intent)) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void retornarResultados(final List<Alumno> alumnos) {
        List<AlumnoAndroid> alumnoAndroids = new ArrayList<AlumnoAndroid>();
        for (Alumno a : alumnos) {

            a.setApellido("" + R.drawable.camera);
            Log.d("qq", "sexo:: " + a.getSexo());
            AlumnoAndroid alumnoAndroid = new AlumnoAndroid();
            alumnoAndroid.setAlumno(a);

            // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.camera);
            byte[] byteArray = a.decodeBlobImage();
            Bitmap bitmap;
            if (byteArray == null) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.camera);
            } else {
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            }

            alumnoAndroid.setImgAlumno(bitmap);
            alumnoAndroids.add(alumnoAndroid);


        }

        AdapterAlumnos adapterAlumnos = new AdapterAlumnos(this, R.layout.listalumnos, alumnoAndroids);
        listaAlumnos.setAdapter(adapterAlumnos);
        listaAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                llamarhistorico(alumnos.get(position));
            }
        });


    }

    @Override
    public void retornarResultado(Alumno alumno) {

    }

    @Override
    public void retornarGCM(List<RegistrationRecord> registrations) {
        cargarPantalla();
    }


    //CARGADOR

    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
        //Before running code in separate thread
        @Override
        protected void onPreExecute() {

            // new GcmRegistrationAsyncTask(MainActivity.this).execute();
            //Create a new progress dialog
            progressDialog = new ProgressDialog(MainActivity.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //Set the dialog title to 'Loading...'
            progressDialog.setTitle("Loading...");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Loading application View, please wait...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(false);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            //The maximum number of items is 100
            progressDialog.setMax(100);
            //Set the current progress to zero
            progressDialog.setProgress(0);
            //Display the progress dialog
            progressDialog.show();
        }

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params) {
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */
            try {
                //Get the current thread's token
                synchronized (this) {
                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while (counter <= 4) {
                        //Wait 850 milliseconds
                        this.wait(850);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
                        publishProgress(counter * 25);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values) {
            //set the current progress of the progress dialog
            progressDialog.setProgress(values[0]);
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result) {
            //close the progress dialog
            progressDialog.dismiss();
            //initialize the View
            //cargarPantalla();

        }
    }

    public List<Colegio> getColegios() {
        List<AlumnoAndroid> alumnos = ((AdapterAlumnos) listaAlumnos.getAdapter()).getAlumnos();
        boolean indicadorIgual = false;
        List<Colegio> colegios = new ArrayList<Colegio>();
        Long indColegio = new Long(0);
        for (AlumnoAndroid a : alumnos) {
            Colegio colegio = a.getAlumno().getAula().getProfesor().getColegio();
            if (!colegios.contains(colegio)) {
                colegios.add(colegio);
            }
        }
        return colegios;

    }


}
