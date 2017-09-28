package com.example.apaolantonio.tomaestado.activities;

/**
 * Created by apaolantonio on 28/9/2017.
 */

import model.AppDataBaseHelper;
import model.ConexionDirectaDataBaseAdapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.apaolantonio.tomaestado.R;

public class ActivityMedidoresCD extends Activity {

    ConexionDirectaDataBaseAdapter dbCD;
    String nombreruta;
    private MyCursorAdapter adapter;
    private ListView lstMedidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidoresni);

        nombreruta = (String) getIntent().getStringExtra("ruta").toString();
        TextView txvRuta = (TextView) findViewById(R.id.txvRutaNoincorporado);
        txvRuta.setText("Ruta "+nombreruta);
        //Toast.makeText(getApplicationContext(), nombreruta+": "+cursorNI.getCount(), Toast.LENGTH_LONG).show();
        cargarActivity();




    }

    public void cargarActivity()
    {

		 /*
		  * Voy a utilizar el mismo layout que us� en los No incorporados,
		  * modificando algunos valores de los textView.
		  * */


        String[] from = {AppDataBaseHelper.CAMPO_CONEXION_DIRECCION,
                AppDataBaseHelper.CAMPO_CONEXION_RESPONSABLE
        };


        int[] to = {R.id.txvNroMedidorNI,
                R.id.txvDireccionNI
        };
        //Toast.makeText(getApplicationContext(), nombreruta, Toast.LENGTH_LONG).show();


        dbCD=ConexionDirectaDataBaseAdapter.getInstance(getApplicationContext());
        dbCD.abrir();
        Cursor cursorCD=dbCD.obtenerPorRuta(nombreruta);
        dbCD.cerrar();


        adapter = new MyCursorAdapter(getApplicationContext(), R.layout.list_item_medidorni, cursorCD, from, to, 0);
        lstMedidor= (ListView) findViewById(R.id.lstMedidorNI);
        lstMedidor.setAdapter(adapter);


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

            TextView direccion = (TextView) view.findViewById(R.id.txvMedidorNI);
            TextView responsable = (TextView) view.findViewById(R.id.txv_DireccionNI);
            TextView est = (TextView) view.findViewById(R.id.txv_EstadoNI);

            direccion.setText("Dirección: ");
            responsable.setText("Responsable de conexión: ");
            est.setText("");
            return view;
        }

    }


}