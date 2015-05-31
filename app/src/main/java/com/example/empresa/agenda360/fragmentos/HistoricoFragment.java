package com.example.empresa.agenda360.fragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.empresa.agenda360.AdapterAlumnos;
import com.example.empresa.agenda360.Constantes;
import com.example.empresa.agenda360.R;
import com.example.empresa.agenda360.adapters.AdapterNotificaciones;
import com.example.empresa.agenda360.tareasAsincronicas.AlumnoAsyncTask;
import com.example.empresa.agenda360.tareasAsincronicas.EndpointsAsyncTask;
import com.example.empresa.agenda360.tareasAsincronicas.NotificacionAsyncTask;
import com.example.empresa.agenda360.tareasAsincronicas.ParametroServicio;
import com.example.reapro.myapplication.backend.dominio.alumnoApi.model.Alumno;
import com.example.reapro.myapplication.backend.dominio.notificacionApi.model.Notificacion;

import java.util.List;

/**
 * Created by REAPRO on 19/04/2015.
 */
public class HistoricoFragment extends Fragment implements AlumnoAsyncTask.PassObjectos,NotificacionAsyncTask.PassObjectos {

    public static final String  ARG_ALUMNO_ID ="ALUMNO_IDENTIFICADOR" ;



    private Comunicator comunicator;
    private Alumno alumno= new Alumno();
    private List<Notificacion> notificaciones;
    private ListView listViewNotif;
    private ImageView imgFotoAlumno;
    private Button btnDatosAlumno;

    @Override
    public void retornarResultados(List<Alumno> alumnos) {
        alumno=alumnos.get(0);
        byte[] byteArray=alumno.decodeBlobImage();
        Bitmap bitmap;
        if(byteArray==null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.camera);
        }else{
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }

        imgFotoAlumno.setImageBitmap(bitmap);
        btnDatosAlumno.setText(getString(R.string.datosde,alumno.getNombre()));
    }

    @Override
    public void retornarResultado(Alumno alumno) {

    }

    @Override
    public void retornarNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones=notificaciones;
        if(notificaciones!=null && !notificaciones.isEmpty()) {
            AdapterNotificaciones adapterNotificaciones = new AdapterNotificaciones(getActivity(), R.layout.listnotificaciones, notificaciones);
            listViewNotif.setAdapter(adapterNotificaciones);
        }




    }

    public interface Comunicator{
        public void redactarMensaje();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle= this.getArguments();
        if(bundle!=null){
            alumno.setId(bundle.getLong(ARG_ALUMNO_ID));
        }
        ParametroServicio parametroServicio= new ParametroServicio();
        parametroServicio.setTipo(Constantes.SRV_ALUMNO_BASICO);
        parametroServicio.setIdAlumno(alumno.getId());
        new AlumnoAsyncTask(getActivity(),this).execute(parametroServicio);


        ParametroServicio parametroServicio1= new ParametroServicio();
        parametroServicio1.setIdAlumno(alumno.getId());
        parametroServicio1.setTipo(Constantes.SRV_NOTIFICACIONES_X_ALUMNO);

        new NotificacionAsyncTask(getActivity(),this).execute(parametroServicio1);



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comunicator=(Comunicator)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentohistorianotificaciones, container, false);

        Button btnNotificaciones= (Button)view.findViewById(R.id.btnDatosAlumno);
        btnNotificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EndpointsAsyncTask(getActivity()).execute();
            }
        });

        listViewNotif= (ListView)view.findViewById(R.id.lstNotificaciones);
        btnDatosAlumno=(Button)view.findViewById(R.id.btnDatosAlumno);
        imgFotoAlumno=(ImageView)view.findViewById(R.id.imgFotoAlumno);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragmento,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_mensaje:
                comunicator.redactarMensaje();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
