package com.example.apaolantonio.tomaestado.activities;

import android.support.v7.app.ActionBarActivity;
import model.AppDataBaseHelper;
import model.EstadoDataBaseAdapter;
import model.MedidorDataBaseAdapter;
import model.MensajeDataBaseAdapter;
import clases.Estado;
import clases.Medidor;
import clases.Ruta;
import clasesB.CommonInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.view.View;
import android.widget.ListView;

import com.example.apaolantonio.tomaestado.R;


public class ActivityCheckMensaje extends Activity {

    private MensajeDataBaseAdapter dbHelper;
    private EstadoDataBaseAdapter dbHelperE;
    private MyCursorAdapter adapter;
    private ListView lstMensaje;
    private Estado estado;
    private Cursor cursorMensaje;
    public int contadorMensaje=0;
    public String cod;

    private Medidor medidor;
    private Ruta ruta;
    private int position;
    String msj;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_mensaje);

        Intent i = getIntent();
        Bundle r = i.getExtras();
        medidor = (Medidor) r.getSerializable(CommonInfo.KEY_BUNDLE_MEDIDOR);
        ruta = (Ruta) r.getSerializable(CommonInfo.KEY_BUNDLE_RUTA);
        position = (Integer) r.getSerializable(CommonInfo.KEY_BUNDLE_POSITION);
        msj= (String) r.getString(CommonInfo.KEY_BUNDLE_MENSAJE);


        //Intent i = getIntent();
        //Bundle r = i.getExtras();

        //estado = (Estado) r.getSerializable(CommonInfo.KEY_BUNDLE_ESTADO);
        //Toast.makeText(this, "El estado que llego es el: " + toString(estado.get_id()), Toast.LENGTH_LONG).show();

        dbHelper = MensajeDataBaseAdapter.getInstance(this);
        dbHelper.abrir();




        String[] from = {AppDataBaseHelper.CAMPO_MENSAJE,
                AppDataBaseHelper.CAMPO_MENSAJECOD
        };

        int[] to = {
                R.id.txvRadioMensaje,
                R.id.txvRadioMensajeID
        };


        cursorMensaje = dbHelper.obtenerTodos();
        dbHelper.cerrar();
        adapter = new MyCursorAdapter(this, R.layout.list_radiobtn_msj, cursorMensaje, from, to, 0);

        lstMensaje = (ListView) findViewById(R.id.lstMensaje);
        lstMensaje.setAdapter(adapter);

        lstMensaje.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
            {

                msj = cursorMensaje.getString(cursorMensaje.getColumnIndex(AppDataBaseHelper.CAMPO_MENSAJE));
                cod = cursorMensaje.getString(cursorMensaje.getColumnIndex(AppDataBaseHelper.CAMPO_MENSAJECOD));
                Toast.makeText(getApplicationContext(), msj , Toast.LENGTH_SHORT).show();
                dialogo("Aviso","El medidor será cambiado a "+msj);

            }
        });
    }



    public String toString(int x)
    {
        String ret = x+"";
        return  ret;

    }



    public void btnCancelarMensaje(View view)
    {
        finish();
    }

    public class MyCursorAdapter extends SimpleCursorAdapter {



        private Cursor c;
        private Context context;

        // itemChecked will store the position of the checked items.

        @SuppressLint("NewApi")
        public MyCursorAdapter(Context context, int layout, Cursor c,
                               String[] from, int[] to, int flags)
        {
            super(context, layout, c, from, to, flags);
            this.c=c;
            this.context=context;


        }



    }
    public void dialogo(String y,String x)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(y);
        alert.setMessage(x);
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int id) {

                dialog.cancel();
            }
        }	);
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int id) {

                medidor.setCodmensaje(cod);
                MedidorDataBaseAdapter dbHelper = MedidorDataBaseAdapter.getInstance(getApplicationContext());
                dbHelper.abrir();
                dbHelper.modificar(medidor);
                dbHelper.cerrar();

                MedidorDataBaseAdapter dbHelperM = new MedidorDataBaseAdapter(getApplicationContext());
                Medidor medidor = new Medidor();
                dbHelperM.abrir();
                Cursor cursorMedidor = dbHelperM.obtenerPorRuta(ruta.getRuta());


                cursorMedidor.moveToPosition(position);

                int rowId = cursorMedidor.getInt(cursorMedidor.getColumnIndex(AppDataBaseHelper.CAMPO_ID_MEDIDOR));

                medidor=dbHelperM.buscar(rowId);
                dbHelperM.cerrar();


				/*Intent intento= new Intent(getApplicationContext(),ActivityTomarEstado.class);
				Bundle recurso = new Bundle();
		        recurso.putSerializable(CommonInfo.KEY_BUNDLE_MEDIDOR, medidor);
		        recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, ruta);
		        recurso.putSerializable(CommonInfo.KEY_BUNDLE_POSITION, position);


		        intento.putExtras(recurso);

		    	startActivity(intento);
		    	finish();*/

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",msj);
                setResult(RESULT_OK,returnIntent);
                finish();



            }});


        alert.show();

    }


    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",msj);
        setResult(RESULT_OK,returnIntent);
        finish();

    }



}
