package com.example.apaolantonio.tomaestado.activities;

import clases.MedidorConexionDirecta;
import clases.Medidor;
import clases.MedidorNoIncorporado;
import clases.Ruta;
import clasesB.CommonInfo;
import model.ConexionDirectaDataBaseAdapter;
import model.MedidorDataBaseAdapter;
import model.AppDataBaseHelper;
import model.NoIncorporadoDataBaseAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apaolantonio.tomaestado.R;


public class ActivityMedidores extends AppCompatActivity{


    //private Class<?> activityCaller=null;
    private ListView lstMedidor;
    private ListView lstMedidorNoincorporado;

    private MyCursorAdapter adapter;


    private MedidorDataBaseAdapter dbHelper;
    private ConexionDirectaDataBaseAdapter dbHelperCD;
    private NoIncorporadoDataBaseAdapter dbHelperNI;

    private Ruta ruta;
    private Medidor medidorSeleccionado;
    private Medidor medidorP;
    private Cursor cursorMedidor;
    private TextView rutaDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidores);

        cargarActivity();

    }

	/*@Override
	public void onResume()
	{
		super.onResume();
		cargarActivity();
	}*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_medidores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcionConexionDirecta:
                agregarConexionDirecta();
                return true;
            case R.id.opcionNoIncorporado:
                agregarNoIncorporado();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void agregarNoIncorporado() {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        final View noInc = li.inflate(R.layout.dialog_noincorporado, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(noInc);
        rutaDialog = (TextView) noInc.findViewById(R.id.txvRutaDialogNI);

        alert.setTitle("No Incorporado");
        rutaDialog.setText("Ruta: " + ruta.getRuta());
        rutaDialog.setTextColor(Color.BLUE);

        alert.setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id) {

                                MedidorNoIncorporado noIncorporado = new MedidorNoIncorporado();
                                EditText direccionNI = (EditText) noInc.findViewById(R.id.etxDireccionDialogNI);
                                EditText etxMedidor = (EditText) noInc.findViewById(R.id.etxMedidorDialogNI);
                                EditText etxEstado = (EditText) noInc.findViewById(R.id.etxEstadoDialogNI);


                                noIncorporado.setRuta(ruta.getRuta());
                                noIncorporado.setDireccion(direccionNI.getText().toString());
                                noIncorporado.setMedidor(etxMedidor.getText().toString());
                                noIncorporado.setEstado(Integer.parseInt(etxEstado.getText().toString()));
                                if(medidorP==null)
                                {
                                    noIncorporado.setFolio("");
                                    noIncorporado.setDs("");
                                }
                                else
                                {
                                    noIncorporado.setFolio(""+medidorP.getFolio());
                                    noIncorporado.setDs(""+medidorP.getDigseg());
                                }


                                dbHelperNI=NoIncorporadoDataBaseAdapter.getInstance(getApplicationContext());
                                dbHelperNI.abrir();
                                dbHelperNI.agregar(noIncorporado);

                                noIncorporado=dbHelperNI.buscar(noIncorporado.getDireccion());

                                dbHelperNI.cerrar();
                                Toast.makeText(getApplicationContext(), "ID: "+noIncorporado.get_id()+". Ruta: "+noIncorporado.getRuta()+". Medidor: "+noIncorporado.getMedidor()+". Direccion: "+noIncorporado.getDireccion()+". Estado: "+noIncorporado.getEstado(), Toast.LENGTH_LONG).show();

                                Intent intento = new Intent(getApplicationContext(),ActivityMedidores.class);
                                Bundle recurso = new Bundle();

                                recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, ruta);

                                intento.putExtras(recurso);

                                startActivity(intento);
                                finish();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();}
                        }	);


        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    public String toString(int x)
    {
        String ret = x+"";
        return  ret;

    }

    public void agregarConexionDirecta()
    {

        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        final View conDirecta = li.inflate(R.layout.dialog_conexiondirecta, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(conDirecta);
        rutaDialog = (TextView) conDirecta.findViewById(R.id.txvrutaDialog);

        alert.setTitle("Conexi�n Directa");
        rutaDialog.setText("Ruta: " + ruta.getRuta());
        rutaDialog.setTextColor(Color.BLUE);
        alert.setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id) {

                                MedidorConexionDirecta cDirecta = new MedidorConexionDirecta();
                                EditText direccion = (EditText) conDirecta.findViewById(R.id.etxDireccion);
                                EditText responsable = (EditText) conDirecta.findViewById(R.id.etxResponsable);


                                cDirecta.setRuta(ruta.getRuta());
                                cDirecta.setDireccion(direccion.getText().toString());
                                cDirecta.setResponsable(responsable.getText().toString());
                                cDirecta.setTipo(0);

                                dbHelperCD=ConexionDirectaDataBaseAdapter.getInstance(getApplicationContext());
                                dbHelperCD.abrir();
                                dbHelperCD.agregar(cDirecta);

                                cDirecta=dbHelperCD.buscar(cDirecta.getDireccion());

                                dbHelperCD.cerrar();
                                Toast.makeText(getApplicationContext(), "ID: "+cDirecta.get_id()+". Ruta: "+cDirecta.getRuta()+". Responsable: "+cDirecta.getResponsable()+". Direccion: "+cDirecta.getDireccion()+". Tipo: "+cDirecta.getTipo() , Toast.LENGTH_LONG).show();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();}
                        }	);


        AlertDialog alertDialog = alert.create();
        alertDialog.show();


    }

    public void cargarActivity()
    {

        Intent i = getIntent();
        Bundle r = i.getExtras();
        ruta = (Ruta) r.getSerializable(CommonInfo.KEY_BUNDLE_RUTA);

        //dbHelper.eliminarTodos();

        //activityCaller = (Class<?>) i.getSerializableExtra(CommonInfo.KEY_ACTIVITY_CALLER);



        String[] from = {AppDataBaseHelper.CAMPO_MEDIDOR,
                AppDataBaseHelper.CAMPO_DIRECCION,
                AppDataBaseHelper.CAMPO_VALIDACION
        };


        int[] to = {R.id.txvNroMedidor,
                R.id.txvDireccion,
                R.id.medidorTomadoOK
        };




        dbHelper = MedidorDataBaseAdapter.getInstance(this);
        dbHelper.abrir();
        if(cursorMedidor==null)
        {
            cursorMedidor = dbHelper.obtenerPorRuta(ruta.getRuta());

            //Esto es para obtener el �ltimo folio y ds
            medidorP=new Medidor();
            cursorMedidor.moveToPosition(0);
            medidorP=dbHelper.buscar(cursorMedidor.getInt(cursorMedidor.getColumnIndex(AppDataBaseHelper.CAMPO_ID_MEDIDOR)));
        }



        adapter = new MyCursorAdapter(this, R.layout.list_item_medidor, cursorMedidor, from, to, 0);
        //	adapterNoIncorporado = new MyCursorAdapter(this, R.layout.list_item_medidor, cursorMedidorNI, fromNoIncorporado, toNoIncorporado, 0);




        //lstMedidorNoincorporado = (ListView) findViewById(R.id.lstMedidorNoincorporado);
        lstMedidor = (ListView) findViewById(R.id.lstMedidor);
        //lstMedidorNoincorporado.setAdapter(adapterNoIncorporado);
        lstMedidor.setAdapter(adapter);





        lstMedidor.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                final Cursor cursorMedidor = dbHelper.obtenerPorRuta(ruta.getRuta());
                cursorMedidor.moveToPosition(position);

                int rowId = cursorMedidor.getInt(cursorMedidor.getColumnIndex(AppDataBaseHelper.CAMPO_ID_MEDIDOR));
                medidorSeleccionado = new Medidor();
                medidorSeleccionado=dbHelper.buscar(rowId);
                dbHelper.cerrar();

                Intent intento = new Intent(view.getContext(),ActivityTomarEstado.class);

                Bundle recurso = new Bundle();

                recurso.putSerializable(CommonInfo.KEY_BUNDLE_MEDIDOR, medidorSeleccionado);
                recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, ruta);
                //Paso la posicion para hacer el secuenciamiento
                recurso.putSerializable(CommonInfo.KEY_BUNDLE_POSITION, position);

                intento.putExtras(recurso);

                startActivity(intento);
                finish();
                //Toast.makeText(getApplicationContext(), "Ha pulsado el item " + position, Toast.LENGTH_SHORT).show();
            }


        });

    }

    private class MyCursorAdapter extends SimpleCursorAdapter{

        @SuppressLint("NewApi")
        public MyCursorAdapter(Context context, int layout, Cursor c,
                               String[] from, int[] to, int flags) {

            super(context, layout, c, from, to, flags);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //obtiene la referencia a la fila
            View view = super.getView(position, convertView, parent);

            TextView txtv = (TextView) view.findViewById(R.id.medidorTomadoOK);
            if(Integer.parseInt(txtv.getText().toString()) != 0){
                txtv.setTextColor(Color.rgb(15, 145, 63));
                txtv.setText("REGISTRADO");
            }
            else
            {
                txtv.setTextColor(Color.rgb(255, 0, 0));
                txtv.setText("NO TOMADO");
            }

            return view;
        }
    }

    @Override
    public void onBackPressed() {


        Intent intento = new Intent(getApplicationContext(),ActivityRutas.class);
        //Bundle recurso = new Bundle();

        //recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, ruta);

        //intento.putExtras(recurso);
        //intento.putExtra(CommonInfo.KEY_ACTIVITY_CALLER, recurso);
        startActivity(intento);
        finish();
    }

}

