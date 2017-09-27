package com.example.apaolantonio.tomaestado.activities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import watcher.EstadoWatcher;
import model.AppDataBaseHelper;
import model.EstadoDataBaseAdapter;
import model.GeolocalizacionDataBaseAdapter;
import model.MedidorDataBaseAdapter;
import model.MensajeDataBaseAdapter;
import model.NoCoincidenteDataBaseAdapter;
import model.RutaDataBaseAdapter;
import model.UbicacionDataBaseAdapter;
import clases.Estado;
import clases.Geolocalizacion;
import clases.Medidor;
import clases.MedidorNoCoincidente;
import clases.Mensaje;
import clases.Ruta;
import clases.Ubicacion;
import clasesB.CommonInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apaolantonio.tomaestado.R;

public class ActivityTomarEstado extends Activity implements LocationListener{


    private EstadoDataBaseAdapter dbHelper;
    private NoCoincidenteDataBaseAdapter dbHelperNC;
    private TextView textviewMedidor;
    private TextView textviewRuta;
    private TextView textviewDireccion;
    private TextView txvid_tarifa;
    private TextView txvid_ubicacion;
    private TextView txvid_mensaje;
    EditText editTextEstado;
    private Menu menu;

    private TextView direccionDialog;
    private TextView medidorDialog;

    Button btnOrdenativo;
    private Button btnTomarEstado;
    private Medidor medidor;
    private Ruta ruta;
    private MedidorNoCoincidente nocoincidente;
    int cargado;
    int actualizar;
    int position;
    int cantIntentos=1;

    private Uri imagen;
    private ImageView ivIntentoCamara;
    private Geolocalizacion geo;

    private Estado est=new Estado();

    private GeolocalizacionDataBaseAdapter dbG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomarestado);


        Intent i = getIntent();
        Bundle r = i.getExtras();
        medidor = (Medidor) r.getSerializable(CommonInfo.KEY_BUNDLE_MEDIDOR);
        ruta = (Ruta) r.getSerializable(CommonInfo.KEY_BUNDLE_RUTA);
        position = (Integer) r.getSerializable(CommonInfo.KEY_BUNDLE_POSITION);
        position++;
        textviewMedidor = (TextView) findViewById(R.id.id_medidor);
        textviewRuta = (TextView) findViewById(R.id.id_ruta);
        textviewDireccion = (TextView) findViewById(R.id.id_direccion);
        txvid_tarifa = (TextView) findViewById(R.id.txvid_tarifa);
        txvid_ubicacion = (TextView) findViewById(R.id.txvid_ubicacion);
        txvid_mensaje = (TextView) findViewById(R.id.txvid_mensaje);

        textviewMedidor.setText(medidor.getMedidor());
        textviewRuta.setText(ruta.getRuta());
        textviewDireccion.setText(medidor.getDireccion());
        txvid_tarifa.setText(medidor.getTarifa());
        String ubi = obtenerUbicacion(medidor);
        String msj = obtenerMensaje(medidor);
        txvid_mensaje.setText(msj);
        txvid_mensaje.setTextColor(Color.rgb(224, 173, 0));
        txvid_ubicacion.setText(ubi);

        cargado=0;
        actualizar=0;
        geo = new Geolocalizacion(getApplicationContext());

        //btnOrdenativo.setEnabled(false);


        editTextEstado = (EditText) findViewById(R.id.editTextTomarEstado);
        editTextEstado.addTextChangedListener(new EstadoWatcher(editTextEstado,true));
        editTextEstado.setText("");

        System.out.print("holaaa");

        if(medidor.getValidacion()!=0)
        {

            cargado=1;
            btnTomarEstado= (Button) findViewById(R.id.botonTomarEstado);
            //btnTomarEstado.setText("Editar");
            btnTomarEstado.setEnabled(false);
            btnTomarEstado.setBackgroundResource(R.drawable.estilobotondisabled);


            editTextEstado.setTextColor(Color.rgb(15, 145, 63));
            editTextEstado.setEnabled(false);


            switch (medidor.getValidacion())
            {

                case 6: editTextEstado.setText("IDT");
                    editTextEstado.setTextColor(Color.rgb(255, 0, 0));
                    //editTextEstado.setTextSize(20);
                    break;

                default:
                    dbHelper=EstadoDataBaseAdapter.getInstance(getApplicationContext());
                    dbHelper.abrir();
                    est=dbHelper.buscar(medidor.get_id());
                    dbHelper.cerrar();
                    editTextEstado.setText(""+est.getEstado());
                    break;



            }

            TextView txvMedidorTomado = (TextView) findViewById(R.id.txvMedidorTomado);
            txvMedidorTomado.setText("MEDIDOR CARGADO!");
            txvMedidorTomado.setTextColor(Color.rgb(15, 145, 63));

            nocoincidente = new MedidorNoCoincidente();
            dbHelperNC=NoCoincidenteDataBaseAdapter.getInstance(getApplicationContext());
            dbHelperNC.abrir();
            nocoincidente=dbHelperNC.buscar(medidor.getDireccion());
            dbHelperNC.cerrar();

            if(nocoincidente!=null)
            {
                TextView txvMedReal=(TextView) findViewById(R.id.txvMedReal);
                TextView txv_MedReal=(TextView) findViewById(R.id.txv_MedReal);
                txvMedReal.setText("MEDIDOR REAL: ");
                txv_MedReal.setText(""+nocoincidente.getMedidorActual());
                txvMedReal.setTextColor(Color.rgb(15, 145, 63));
                txv_MedReal.setTextColor(Color.rgb(15, 145, 63));
            }

        }



        //	registerForContextMenu(editTextEstado);

    }



    public void TomarEstado(View view)
    {
        //editTextEstado = (EditText) findViewById(R.id.editTextTomarEstado);
        btnTomarEstado= (Button) findViewById(R.id.botonTomarEstado);
        btnTomarEstado.setEnabled(true);
        btnTomarEstado.setBackgroundResource(R.drawable.estiloboton);

        //Toast.makeText(getApplicationContext(), btnTomarEstado.getText().toString(), Toast.LENGTH_SHORT).show();

		/*if(btnTomarEstado.getText().toString().compareTo("Editar")==0)
		{


			editTextEstado.setEnabled(true);
			editTextEstado.addTextChangedListener(new EstadoWatcher(editTextEstado,true));
			editTextEstado.setText("");
			editTextEstado.setTextColor(Color.rgb(0, 0, 0));


			btnTomarEstado= (Button) findViewById(R.id.botonTomarEstado);
			btnTomarEstado.setText("Tomar Estado");

			//menu.getitem(0) corresponde a OPCIONES
			menu.getItem(0).setEnabled(true);


			actualizar = 1;
			//Toast.makeText(getApplicationContext(), toString(est.get_id()), Toast.LENGTH_SHORT).show();
		}

		else
		{*/
        if (validarCampoNulo() == false)
        {
            dialogoError("Error","Debes ingresar el estado.");
        }

        else if (validarUnoPorMedidor() == false && actualizar==0)
        {
            dialogoError("Error","Ya se ha cargado el estado para ese medidor.");
        }

        else
        {

            String estadoActual=editTextEstado.getText().toString();

            validarConsumo(estadoActual);
            if(medidor.getValidacion()!=1 && medidor.getValidacion()!=6 && cantIntentos<3)
            {
                medidor.setIntentos(medidor.getIntentos()+1);
                dialogoError("Error", "Ingrese la lectura nuevamente");
                cantIntentos++;
                Vibrator v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
                // Vibrar durante 3 segundos
                v.vibrate(1000);
            }

            else{

                est.setEstado(estadoActual);
                est.setId_Medidor(medidor.get_id());
                est.setConexiondirecta(false);
                est.setOrdenativo1(0);
                est.setOrdenativo2(0);
                est.setOrdenativo3(0);
                est.setOrdenativo4(0);
                est.setOrdenativo5(0);
                est.setOrdenativo6(0);
                est.setSecuencia(ruta.getMedidorestomados()+1);
                Toast.makeText(getApplicationContext(), ""+ruta.getMedidorestomados()+1, Toast.LENGTH_SHORT).show();
                String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
                est.setHora(timeStamp);


                dbHelper = EstadoDataBaseAdapter.getInstance(getApplicationContext());
                dbHelper.abrir();

                //
                Toast.makeText(getApplicationContext(), ""+est.getId_Medidor(), Toast.LENGTH_LONG).show();
				/*geo.set_id(est.getId_Medidor());
				geo.setLatitud(geo.getUltimaPosicionConocida().getLatitude());
				geo.setLongitud(geo.getUltimaPosicionConocida().getLongitude());*/


                if(actualizar==0)
                {
                    dbHelper.agregar(est);
                    ruta.setMedidorestomados(ruta.getMedidorestomados()+1);
                    RutaDataBaseAdapter dbHelperR = new RutaDataBaseAdapter(getApplicationContext());
                    dbHelperR.abrir();
                    dbHelperR.modificar(ruta);
                    ruta=dbHelperR.buscar(ruta.get_id());
                    dbHelperR.cerrar();

					/*if(geo.habilitado()==true)
					{
						Toast.makeText(getApplicationContext(), "gps habilitado", Toast.LENGTH_LONG).show();
						//activarGeolocalizacion();
						//agregarCoordenadas(geo);

					}*/


                }
                if(actualizar==1)
                {	//est.setEstado(estadoActual);
                    dbHelper.modificar(est);
					/*if(geo.habilitado()==true)
					{
						activarGeolocalizacion();
						modificarCoordenadas(geo);

					}*/
                }

                //	estado=dbHelper.buscar(medidor.get_id());
                dbHelper.cerrar();


                MedidorDataBaseAdapter dbHelperM = new MedidorDataBaseAdapter(getApplicationContext());
                dbHelperM.abrir();
                dbHelperM.modificar(medidor);
                dbHelperM.cerrar();

                editTextEstado.setTextColor(Color.rgb(15, 145, 63));
                editTextEstado.setEnabled(false);
                btnTomarEstado= (Button) findViewById(R.id.botonTomarEstado);
                btnTomarEstado.setEnabled(false);
                btnTomarEstado.setBackgroundResource(R.drawable.estilobotondisabled);
                menu.getItem(0).setEnabled(false);
                menu.getItem(1).setEnabled(true);
                menu.getItem(2).setEnabled(true);

                //btnOrdenativo.setEnabled(false);

                menu.getItem(0).setEnabled(false);

                cargado=1;

            }
        }
        //}


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_tomarestado, menu);
        //Con esto puedo utilizar el menu en cualquier parte de la activity
        this.menu=menu;


        btnTomarEstado= (Button) findViewById(R.id.botonTomarEstado);
        if(medidor.getValidacion()!=0)
        {
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(true);
            menu.getItem(2).setEnabled(true);
        }
        else
        {
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(false);
            menu.getItem(2).setEnabled(false);

        }


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcionImposibleTomar:
                imposibleTomar();
                return true;
            case R.id.opcionNoCoincidente:
                agregarNoCoincidente();
                return true;
            case R.id.itemModificarEstado:
                modificarEstado();
                return true;
            case R.id.itemEliminarEstado:
                eliminarEstado();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    public void agregarNoCoincidente()
    {

        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        final View noCoinc = li.inflate(R.layout.dialog_nocoincidente, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(noCoinc);

        direccionDialog = (TextView) noCoinc.findViewById(R.id.txvdireccionDialog);
        medidorDialog = (TextView) noCoinc.findViewById(R.id.txvmedidorDialog);


        alert.setTitle("No Coincidente");
        direccionDialog.setText("Direccion: " + medidor.getDireccion());
        direccionDialog.setTextColor(Color.BLUE);
        medidorDialog.setText("Medidor: " + medidor.getMedidor());
        medidorDialog.setTextColor(Color.BLUE);

        alert.setCancelable(false)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id) {


                                EditText medidorReal = (EditText) noCoinc.findViewById(R.id.etxMedidorRealDialog);
                                EditText estadoDialog = (EditText) noCoinc.findViewById(R.id.etxEstadoDialog);

                                nocoincidente = new MedidorNoCoincidente();
                                nocoincidente.setRuta(ruta.getRuta());
                                nocoincidente.setMedidorAnterior(medidor.getMedidor());
                                nocoincidente.setMedidorActual(medidorReal.getText().toString());
                                nocoincidente.setEstado(Integer.parseInt(estadoDialog.getText().toString()));
                                nocoincidente.setDireccion(medidor.getDireccion());
                                nocoincidente.setFolio(medidor.getFolio());
                                nocoincidente.setDs(medidor.getDigseg());

                                est.setEstado(""+nocoincidente.getEstado());
                                est.setId_Medidor(medidor.get_id());
                                est.setConexiondirecta(false);
                                est.setOrdenativo1(0);
                                est.setOrdenativo2(0);
                                est.setOrdenativo3(0);
                                est.setOrdenativo4(0);
                                est.setOrdenativo5(0);
                                est.setOrdenativo6(0);
                                est.setSecuencia(0);
                                String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
                                est.setHora(timeStamp);

                                //Si ya existe un estado para ese medidor, lo modifico.
                                dbHelper= EstadoDataBaseAdapter.getInstance(getApplicationContext());

                                dbHelperNC=NoCoincidenteDataBaseAdapter.getInstance(getApplicationContext());

                                if(actualizar==0)
                                {
                                    dbHelper.abrir();
                                    dbHelper.agregar(est);
                                    dbHelper.cerrar();

                                    ruta.setMedidorestomados(ruta.getMedidorestomados()+1);
                                    RutaDataBaseAdapter dbHelperR = new RutaDataBaseAdapter(getApplicationContext());
                                    dbHelperR.abrir();
                                    dbHelperR.modificar(ruta);
                                    ruta=dbHelperR.buscar(ruta.get_id());
                                    dbHelperR.cerrar();

                                    dbHelperNC.abrir();
                                    dbHelperNC.agregar(nocoincidente);
                                    nocoincidente=dbHelperNC.buscar(nocoincidente.getDireccion());
                                    dbHelperNC.cerrar();

                                }

                                if(actualizar==1)
                                {   dbHelper.abrir();
                                    dbHelper.modificar(est);
                                    dbHelper.cerrar();

                                    dbHelperNC.abrir();
                                    dbHelperNC.modificar(nocoincidente);
                                    dbHelperNC.cerrar();
                                }


                                medidor.setValidacion(1);
                                MedidorDataBaseAdapter dbHelperM = new MedidorDataBaseAdapter(getApplicationContext());
                                dbHelperM.abrir();
                                dbHelperM.modificar(medidor);
                                dbHelperM.cerrar();



                                editTextEstado.setTextColor(Color.rgb(15, 145, 63));
                                editTextEstado.setEnabled(false);
                                editTextEstado.setText(""+nocoincidente.getEstado());
                                btnTomarEstado= (Button) findViewById(R.id.botonTomarEstado);
                                btnTomarEstado.setEnabled(false);
                                btnTomarEstado.setBackgroundResource(R.drawable.estilobotondisabled);

                                //menu.getitem(0) corresponde a OPCIONES
                                menu.getItem(0).setEnabled(false);
                                menu.getItem(1).setEnabled(true);
                                menu.getItem(2).setEnabled(true);

                                TextView txvMedReal=(TextView) findViewById(R.id.txvMedReal);
                                TextView txv_MedReal=(TextView) findViewById(R.id.txv_MedReal);
                                txvMedReal.setText("MEDIDOR REAL: ");
                                txv_MedReal.setText(""+nocoincidente.getMedidorActual());
                                txvMedReal.setTextColor(Color.rgb(15, 145, 63));
                                txv_MedReal.setTextColor(Color.rgb(15, 145, 63));


                                Toast.makeText(getApplicationContext(), "ID: "+nocoincidente.get_id()+". Ruta: "+nocoincidente.getRuta()+". Medidor anterior: "+nocoincidente.getMedidorAnterior()+". Medidor actual: "+nocoincidente.getMedidorActual()+". Estado: "+nocoincidente.getEstado()+". Direccion: "+nocoincidente.getDireccion(), Toast.LENGTH_LONG).show();

                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();}
                        }	);


        AlertDialog alertDialog = alert.create();
        alertDialog.show();


    }

    public void botonProximo(View view)
    {



        MedidorDataBaseAdapter dbHelperM = new MedidorDataBaseAdapter(getApplicationContext());
        Medidor medidor = new Medidor();
        dbHelperM.abrir();
        Cursor cursorMedidor = dbHelperM.obtenerPorRuta(ruta.getRuta());

        if(position==cursorMedidor.getCount())
        {
            dialogoError("Fin de ruta","No existen m�s medidores para esta ruta");
        }
        else{
            cursorMedidor.moveToPosition(position);

            int rowId = cursorMedidor.getInt(cursorMedidor.getColumnIndex(AppDataBaseHelper.CAMPO_ID_MEDIDOR));

            medidor=dbHelperM.buscar(rowId);
            dbHelperM.cerrar();


            Intent intento= new Intent(getApplicationContext(),ActivityTomarEstado.class);
            Bundle recurso = new Bundle();
            recurso.putSerializable(CommonInfo.KEY_BUNDLE_MEDIDOR, medidor);
            recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, ruta);
            recurso.putSerializable(CommonInfo.KEY_BUNDLE_POSITION, position);


            intento.putExtras(recurso);

            startActivity(intento);
            finish();
        }



    }



    private boolean validarCampoNulo()
    {
        return editTextEstado.getError() == null;
    }

    public boolean validarUnoPorMedidor()
    {
        Estado est = new Estado();
        dbHelper= EstadoDataBaseAdapter.getInstance(getApplicationContext());
        dbHelper.abrir();
        est=dbHelper.buscar(medidor.get_id());
        dbHelper.cerrar();
        //Toast.makeText(getApplicationContext(), "Id Medidor: "+estado.getId_Medidor()+". Id Estado:"+estado.get_id(), Toast.LENGTH_LONG).show();
        if(est==null)
        {
            return true;
        }
        else
        {
            return false;
        }


    }
    public void dialogoError(String y,String x)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(y);
        alert.setMessage(x);
        alert.setPositiveButton("Aceptar", null);

        alert.show();

    }

    public String obtenerUbicacion(Medidor medidor)
    {

        UbicacionDataBaseAdapter dbHelper;
        Ubicacion ubi=new Ubicacion();
        dbHelper=UbicacionDataBaseAdapter.getInstance(getApplicationContext());
        dbHelper.abrir();
        ubi=dbHelper.buscar(medidor.getCodubicacion());
        dbHelper.cerrar();


        return ubi.getUbicacion();
    }

    public String obtenerMensaje(Medidor medidor)
    {

        MensajeDataBaseAdapter dbHelper;
        Mensaje msj=new Mensaje();
        dbHelper=MensajeDataBaseAdapter.getInstance(getApplicationContext());
        dbHelper.abrir();
        msj=dbHelper.buscar(medidor.getCodmensaje());
        dbHelper.cerrar();


        return msj.getMensaje();
    }



    public void imposibleTomar()
    {


        est.setEstado("");
        est.setId_Medidor(medidor.get_id());
        est.setConexiondirecta(false);
        est.setOrdenativo1(0);
        est.setOrdenativo2(0);
        est.setOrdenativo3(0);
        est.setOrdenativo4(0);
        est.setOrdenativo5(0);
        est.setOrdenativo6(0);
        est.setSecuencia(ruta.getMedidorestomados()+1);
        Toast.makeText(getApplicationContext(), ""+ruta.getMedidorestomados()+1, Toast.LENGTH_SHORT).show();

        String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
        est.setHora(timeStamp);

        dbHelper= EstadoDataBaseAdapter.getInstance(getApplicationContext());
        dbHelper.abrir();
        dbHelper.agregar(est);
        dbHelper.cerrar();

        if(actualizar==0){
            ruta.setMedidorestomados(ruta.getMedidorestomados()+1);
            RutaDataBaseAdapter dbHelperR = new RutaDataBaseAdapter(getApplicationContext());
            dbHelperR.abrir();
            dbHelperR.modificar(ruta);
            dbHelperR.cerrar();}

        medidor.setValidacion(6);
        MedidorDataBaseAdapter dbHelperM = new MedidorDataBaseAdapter(getApplicationContext());
        dbHelperM.abrir();
        dbHelperM.modificar(medidor);
        dbHelperM.cerrar();

        cargado=1;

        //Toast.makeText(getApplicationContext(), "Estado imposible de tomar", Toast.LENGTH_SHORT).show();
        editTextEstado.setTextColor(Color.rgb(15, 145, 63));
        editTextEstado.setEnabled(false);
        editTextEstado.setText("IDT");
        editTextEstado.setTextColor(Color.rgb(255, 0, 0));
        //editTextEstado.setTextSize(20);
        btnTomarEstado= (Button) findViewById(R.id.botonTomarEstado);
        btnTomarEstado.setEnabled(false);
        btnTomarEstado.setBackgroundResource(R.drawable.estilobotondisabled);

        menu.getItem(0).setEnabled(false);
        menu.getItem(1).setEnabled(true);
        menu.getItem(2).setEnabled(true);





    }

    @Override
    public void onBackPressed() {

		/*new AlertDialog.Builder(this)
	        .setTitle("Really Exit?")
	        .setMessage("Are you sure you want to exit?")
	        .setNegativeButton(android.R.string.no, null)
	        .setPositiveButton(android.R.string.yes, new OnClickListener() {

	            public void onClick(DialogInterface arg0, int arg1) {
	            	ActivityTomarEstado.super.onBackPressed();
	            }
	        }).create().show();*/



        Intent intento = new Intent(getApplicationContext(),ActivityMedidores.class);
        Bundle recurso = new Bundle();

        recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, ruta);

        intento.putExtras(recurso);
        startActivity(intento);
        finish();
    }



    public void ordenativo()
    {

        if(cargado==0)
        {
            dialogoError("Error","Primero debes guardar el estado");

        }
        else
        {
            Intent intento = new Intent(this,ActivityCheckOrdenativo.class);
            Bundle recurso = new Bundle();
            recurso.putSerializable(CommonInfo.KEY_BUNDLE_ESTADO, est);
            recurso.putSerializable(CommonInfo.KEY_BUNDLE_POSITION, position-1);

            intento.putExtras(recurso);

            startActivityForResult(intento,3);

        }


    }

    public void mensaje()
    {
        Intent intento= new Intent(getApplicationContext(),ActivityCheckMensaje.class);
        Bundle recurso = new Bundle();
        recurso.putSerializable(CommonInfo.KEY_BUNDLE_MEDIDOR, medidor);
        recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, ruta);
        recurso.putSerializable(CommonInfo.KEY_BUNDLE_POSITION, position-1);
        recurso.putString(CommonInfo.KEY_BUNDLE_MENSAJE,obtenerMensaje(medidor));


        intento.putExtras(recurso);

        startActivityForResult(intento,2);


    }

    public void ubicacion()
    {

        Intent intento= new Intent(getApplicationContext(),ActivityCheckUbicacion.class);
        Bundle recurso = new Bundle();
        recurso.putSerializable(CommonInfo.KEY_BUNDLE_MEDIDOR, medidor);
        recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, ruta);
        recurso.putSerializable(CommonInfo.KEY_BUNDLE_POSITION, position-1);


        intento.putExtras(recurso);

        startActivityForResult(intento,1);




    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }
    //Men�es Contextuales
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1: {
                Toast.makeText(getApplicationContext(), "Opcion 1",
                        Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.item2: {
                Toast.makeText(getApplicationContext(), "Opcion 2",
                        Toast.LENGTH_SHORT).show();
                break;
            }}
        return super.onContextItemSelected(item);
    }

    public void opciones(View view)
    {
        final String[] items = {"Ordenativos", "Mensajes", "Ubicaciones","Foto", "Cómo llegar?"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cargar");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch(items[item])
                {
                    case "Ordenativos": ordenativo(); break;
                    case "Mensajes": mensaje(); break;
                    case "Ubicaciones": ubicacion(); break;
                    case "Foto": foto(); break;
                    case "Cómo llegar?": Comollegar(); break;

                }

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    /*	C�digo validacion + descripci�n
     *
     * 	0: No Tomado
        1: Tomado OK
        2: Cons.Bajo
        3: Cons.Alto
        4: Cons.Nulo
        5: Cons.Negativo
        6: Imposible de Tomar

     */
    public void validarConsumo(String estado)
    {
        int est = Integer.parseInt(estado);
        int estAnt=Integer.parseInt(medidor.getEstant());

        //No alterar el orden d los if
        //////CONSUMO NULO/////////
        if(est==estAnt)
        {
            medidor.setValidacion(4);

        }

        //////CONSUMO NEGATIVO/////////
        else if(est<estAnt)
        {
            medidor.setValidacion(5);

        }

        ////////CONSUMO BAJO/////////
        else if((est-estAnt)<consumoBajo())
        {
            medidor.setValidacion(2);

        }

        //////CONSUMO ALTO/////////
        else if((est-estAnt)>consumoAlto())
        {
            medidor.setValidacion(3);

        }

        //////CONSUMO TOMADO OK/////////
        else
        {
            medidor.setValidacion(1);

        }
    }


    public int consumoBajo()
    {
        return 500;
    }

    public int consumoAlto()
    {
        return 1500;
    }

    private void foto()
    {
        ivIntentoCamara = (ImageView) findViewById(R.id.ivIntentoCamara);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        imagen = obtenerUriDesdeArchivo();

        i.putExtra(MediaStore.EXTRA_OUTPUT, imagen);

        startActivityForResult(i, 100);

    }

    private void Comollegar()
    {

        //Intent comollegar= new Intent(getApplicationContext(),ActivityComollegar.class);


        /*Bundle recurso = new Bundle();
        recurso.putSerializable(CommonInfo.KEY_BUNDLE_MEDIDOR, medidor);
        recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, ruta);
        recurso.putSerializable(CommonInfo.KEY_BUNDLE_POSITION, position-1);


        intento.putExtras(recurso);*/

       // startActivity(comollegar);


    }

    private Uri obtenerUriDesdeArchivo()
    {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"captor");

        if ( mediaStorageDir.exists() == false )
        {
            mediaStorageDir.mkdirs();
        }

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "captor.jpg");

        return Uri.fromFile(mediaFile);
    }

    @Override
    protected void onResume()
    {
        if ( imagen != null )
        {
            ivIntentoCamara.setImageURI(imagen);
        }

        super.onResume();
    }

    public void agregarCoordenadas(Geolocalizacion geo)
    {


        GeolocalizacionDataBaseAdapter dbG = GeolocalizacionDataBaseAdapter.getInstance(getApplicationContext());
        dbG.abrir();
        dbG.agregar(geo);
        dbG.cerrar();


    }

    public void modificarCoordenadas(Geolocalizacion geo)
    {

        GeolocalizacionDataBaseAdapter dbG = GeolocalizacionDataBaseAdapter.getInstance(getApplicationContext());
        dbG.abrir();
        dbG.modificar(geo);
        dbG.cerrar();


    }

    private void activarGeolocalizacion()
    {
        geo.setUpProveedor("gps", this);
        onLocationChanged(geo.getUltimaPosicionConocida());
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if ( location != null )
        {

            geo.setLatitud(location.getLatitude());
            geo.setLongitud(location.getLongitude());
            //Toast.makeText(getApplicationContext(), "Latitud: "+location.getLatitude(), Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "Longitud: "+location.getLongitude(), Toast.LENGTH_LONG).show();

        }
    }



    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void modificarEstado()
    {
        editTextEstado.setEnabled(true);
        editTextEstado.addTextChangedListener(new EstadoWatcher(editTextEstado,true));
        editTextEstado.setText("");
        editTextEstado.setTextColor(Color.rgb(0, 0, 0));


        btnTomarEstado= (Button) findViewById(R.id.botonTomarEstado);
        btnTomarEstado.setEnabled(true);
        btnTomarEstado.setBackgroundResource(R.drawable.estiloboton);

        //menu.getitem(0) corresponde a OPCIONES
        menu.getItem(0).setEnabled(true);
        menu.getItem(1).setEnabled(false);
        menu.getItem(2).setEnabled(false);
        actualizar = 1;
    }

    public void eliminarEstado()
    {

        //eliminar geolocalizacion est.idmedidor

        dbG=GeolocalizacionDataBaseAdapter.getInstance(getApplicationContext());
        dbG.abrir();
        dbG.eliminar(geo);
        dbG.cerrar();

        dbHelperNC = NoCoincidenteDataBaseAdapter.getInstance(getApplicationContext());
        dbHelperNC.abrir();
        MedidorNoCoincidente med = new MedidorNoCoincidente();
        dbHelperNC.buscar(medidor.getMedidor());
        dbHelperNC.eliminar(med);
        dbHelperNC.cerrar();

        medidor.setValidacion(0);
        MedidorDataBaseAdapter dbHelperM = new MedidorDataBaseAdapter(getApplicationContext());
        dbHelperM.abrir();
        dbHelperM.modificar(medidor);
        dbHelperM.cerrar();

        ruta.setMedidorestomados(ruta.getMedidorestomados()-1);
        RutaDataBaseAdapter dbHelperR = new RutaDataBaseAdapter(getApplicationContext());
        dbHelperR.abrir();
        dbHelperR.modificar(ruta);
        dbHelperR.cerrar();

        dbHelper = EstadoDataBaseAdapter.getInstance(getApplicationContext());
        dbHelper.abrir();
        Estado estado = new Estado();
        estado=dbHelper.buscar(medidor.get_id());
        dbHelper.eliminar(estado);
        dbHelper.cerrar();

        editTextEstado.setEnabled(true);
        editTextEstado.addTextChangedListener(new EstadoWatcher(editTextEstado,true));
        editTextEstado.setText("");
        editTextEstado.setTextColor(Color.rgb(0, 0, 0));


        btnTomarEstado= (Button) findViewById(R.id.botonTomarEstado);
        btnTomarEstado.setText("Tomar Estado");
        btnTomarEstado.setBackgroundResource(R.drawable.estiloboton);
        menu.getItem(0).setEnabled(true);
        menu.getItem(1).setEnabled(false);
        menu.getItem(2).setEnabled(false);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result=data.getStringExtra("result");
                txvid_ubicacion = (TextView) findViewById(R.id.txvid_ubicacion);
                txvid_ubicacion.setText(result);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        if(requestCode == 2)
        {
            Toast.makeText(getApplicationContext(), "mensaje", Toast.LENGTH_SHORT).show();
            String result=data.getStringExtra("result");
            txvid_mensaje = (TextView) findViewById(R.id.txvid_mensaje);
            txvid_mensaje.setText(result);

        }


    }


}