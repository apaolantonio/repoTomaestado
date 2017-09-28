package com.example.apaolantonio.tomaestado.activities;

import android.support.v7.app.ActionBarActivity;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.AppDataBaseHelper;
import model.ConexionDirectaDataBaseAdapter;
import model.NoIncorporadoDataBaseAdapter;
import model.RutaDataBaseAdapter;


import adaptadores.ExpandableListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.example.apaolantonio.tomaestado.R;


public class ActivityEstadistica extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> listDataHeaderRutas;
    HashMap<String, List<String>> listDataChild;

    RutaDataBaseAdapter dbRuta;
    NoIncorporadoDataBaseAdapter dbNI;
    ConexionDirectaDataBaseAdapter dbCD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(myListItemClicked);

    }

    private OnChildClickListener myListItemClicked =  new OnChildClickListener() {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {


            //listDataHeader.get(groupPosition)---> Obtengo la cabecera del grupo
            //display it or do something with it
            //Toast.makeText(getApplicationContext(),childPosition+" "+ listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();

    	 /*childPosition
    	  * 0: Medidores no tomados
    	  * 1: Medidores nuevos
    	  * 2: Conexiones directas
    	  * 3: Progreso*/

            switch(childPosition)
            {
                case 1:
                    Intent intento = new Intent(v.getContext(),ActivityMedidoresNoIncorporados.class);
                    intento.putExtra("ruta", listDataHeaderRutas.get(groupPosition).toString());
                    startActivity(intento);
                    break;

                case 2:
                    Intent intent = new Intent(v.getContext(),ActivityMedidoresCD.class);
                    intent.putExtra("ruta", listDataHeaderRutas.get(groupPosition).toString());
                    startActivity(intent);
                    break;
                default: break;



            }


            return false;

        }




    };
    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataHeaderRutas = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        dbRuta=RutaDataBaseAdapter.getInstance(getApplicationContext());
        dbNI=NoIncorporadoDataBaseAdapter.getInstance(getApplicationContext());
        dbCD = ConexionDirectaDataBaseAdapter.getInstance(getApplicationContext());

        dbRuta.abrir();
        Cursor cursor = dbRuta.obtenerTodos();
        dbRuta.cerrar();
        int i=0;
        for(i=0;i<cursor.getCount();i++)
        {
            cursor.moveToPosition(i);
            String nombreRuta= cursor.getString(cursor.getColumnIndex(AppDataBaseHelper.CAMPO_RUTA));
            listDataHeader.add("Ruta "+nombreRuta);
            listDataHeaderRutas.add(nombreRuta);

            List<String> datos = new ArrayList<String>();
            double x=cursor.getInt(cursor.getColumnIndex(AppDataBaseHelper.CAMPO_TOTALMEDIDORES));
            double y=cursor.getInt(cursor.getColumnIndex(AppDataBaseHelper.CAMPO_MEDIDORESTOMADOS));
            double j=x-y;

            datos.add("Medidores NO tomados:  " + (int) j);

            dbNI.abrir();
            Cursor cursorNI=dbNI.obtenerPorRuta(nombreRuta);
            dbNI.cerrar();
            datos.add("Medidores nuevos: " + cursorNI.getCount());

            dbCD.abrir();
            Cursor cursorCD=dbCD.obtenerPorRuta(nombreRuta);
            dbCD.cerrar();
            datos.add("Conexiones directas: " + cursorCD.getCount());


            DecimalFormat RedondearADos = new DecimalFormat("####.##");
            double progreso = (y/x)*100.0;

            datos.add("Progreso: " + RedondearADos.format(progreso)+"%");

            listDataChild.put(listDataHeader.get(i), datos);
        }


    }
}