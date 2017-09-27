package com.example.apaolantonio.tomaestado.activities;

import clases.Ruta;

import clasesB.CommonInfo;
import model.RutaDataBaseAdapter;
import model.AppDataBaseHelper;


import android.support.v7.app.AppCompatActivity;
import android.widget.SimpleCursorAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;

import com.example.apaolantonio.tomaestado.R;


public class ActivityRutas extends AppCompatActivity {

    private ListView lstRuta;
    private Ruta rutaSeleccionada;
    private RutaDataBaseAdapter dbHelper;
    private MyCursorAdapter adapter;
    private SearchView searchView;
    private Cursor cursorRutas;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta);

        //cargarRutas(this);

        if(CommonInfo.ADMINISTRADOR==0)cargarActivity();




    }

    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        //if(CommonInfo.ADMINISTRADOR==1){
        getMenuInflater().inflate(R.menu.menu_activity_rutas, menu);
		
		
		/* MenuItem searchItem = menu.findItem(R.id.action_search);
		 searchView = (SearchView) searchItem.getActionView();
		 searchView.setQueryHint("Search...");*/
        // searchView.setOnQueryTextListener(this);
        //}
        return true;

    }

	/*@Override
	public void onResume()
	{
		super.onResume();
		cargarActivity();
		
		
	}*/



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuRuta:
                Intent intento = new Intent(getApplicationContext(),ActivityEstadistica.class);
                startActivity(intento);
                return true;


            default: return super.onOptionsItemSelected(item);
        }
    }



    @SuppressLint("NewApi")
    public void cargarActivity()
    {
        rutaSeleccionada=new Ruta();





        String[] from = {AppDataBaseHelper.CAMPO_RUTA,
                AppDataBaseHelper.CAMPO_MEDIDORESTOMADOS,
                AppDataBaseHelper.CAMPO_TOTALMEDIDORES
        };

        int[] to = {R.id.txvRuta,
                R.id.txvCantidadTomados,
                R.id.txvMedidoresTotales
        };
        dbHelper = RutaDataBaseAdapter.getInstance(this);
        if(cursorRutas==null)
        {

            dbHelper.abrir();
            cursorRutas = dbHelper.obtenerTodos();
            dbHelper.cerrar();}

        adapter = new MyCursorAdapter(this, R.layout.list_item_ruta, cursorRutas, from, to, 0);

        lstRuta = (ListView) findViewById(R.id.lstRuta);

        lstRuta.setAdapter(adapter);

        lstRuta.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                dbHelper.abrir();

                final Cursor cursorRutas = dbHelper.obtenerTodos();
                cursorRutas.moveToPosition(position);

                int rowId = cursorRutas.getInt(cursorRutas.getColumnIndex(AppDataBaseHelper.CAMPO_ID));
                rutaSeleccionada=dbHelper.buscar(rowId);
                dbHelper.cerrar();

                Intent intento = new Intent(view.getContext(),ActivityMedidores.class);
                Bundle recurso = new Bundle();

                recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, rutaSeleccionada);

                intento.putExtras(recurso);
                intento.putExtra(CommonInfo.KEY_ACTIVITY_CALLER, recurso);

                startActivity(intento);
                finish();
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
            final int pos=position;
            //obtiene la referencia a la fila
            View view = super.getView(position, convertView, parent);

            TextView txvCT = (TextView) view.findViewById(R.id.txvCantidadTomados);
            TextView txvMT = (TextView) view.findViewById(R.id.txvMedidoresTotales);


            if(txvCT.getText().toString().compareTo(txvMT.getText().toString()) == 0){
                TextView rutaTerminada= (TextView) view.findViewById(R.id.rutaTerminada);
                rutaTerminada.setTextColor(Color.rgb(15, 145, 63));
                rutaTerminada.setText("RUTA TERMINADA");
            }

            // Button imgBoton = (Button) view.findViewById(R.id.imgButtonRuta);
		   
		 /*  ImageButton btn = ((ImageButton) view.findViewById(R.id.imgButtonRuta));
	    	
	    	btn.setOnClickListener(new View.OnClickListener() {
	    		@Override public void onClick(View v) {
	    			cursorRutas.moveToPosition(pos);
	 		       
	 		        int rowId = cursorRutas.getInt(cursorRutas.getColumnIndex(AppDataBaseHelper.CAMPO_ID));
	 		        dbHelper.abrir();
	 		        rutaSeleccionada=dbHelper.buscar(rowId);
	 		        dbHelper.cerrar();
	 		        
	 		        Intent intento = new Intent(getApplicationContext(),ActivityEstadistica.class);
			        Bundle recurso = new Bundle();
			        
			        recurso.putSerializable(CommonInfo.KEY_BUNDLE_RUTA, rutaSeleccionada);

			        intento.putExtras(recurso);
			        
			       
			        startActivity(intento);
	    			
	    		}
				
			});*/

            return view;
        }



    }
	
	
	
	/*@Override
	public void onBackPressed() {	
	}*/

}