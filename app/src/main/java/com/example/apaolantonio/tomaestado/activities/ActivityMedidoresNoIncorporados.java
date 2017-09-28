package com.example.apaolantonio.tomaestado.activities;

/**
 * Created by apaolantonio on 28/9/2017.
 */
import model.AppDataBaseHelper;
import model.NoIncorporadoDataBaseAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.apaolantonio.tomaestado.R;

public class ActivityMedidoresNoIncorporados extends Activity {

    NoIncorporadoDataBaseAdapter dbNI;
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

        String[] from = {AppDataBaseHelper.CAMPO_NOINCORPORADO_MEDIDOR,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_DIRECCION,
                AppDataBaseHelper.CAMPO_NOINCORPORADO_ESTADO
        };


        int[] to = {R.id.txvNroMedidorNI,
                R.id.txvDireccionNI,
                R.id.txvEstadoNI
        };
        //Toast.makeText(getApplicationContext(), nombreruta, Toast.LENGTH_LONG).show();


        dbNI=NoIncorporadoDataBaseAdapter.getInstance(getApplicationContext());
        dbNI.abrir();
        Cursor cursorNI=dbNI.obtenerPorRuta(nombreruta);
        dbNI.cerrar();


        adapter = new MyCursorAdapter(getApplicationContext(), R.layout.list_item_medidorni, cursorNI, from, to, 0);
        lstMedidor= (ListView) findViewById(R.id.lstMedidorNI);
        lstMedidor.setAdapter(adapter);


    }

    private class MyCursorAdapter extends SimpleCursorAdapter{

        @SuppressLint("NewApi")
        public MyCursorAdapter(Context context, int layout, Cursor c,
                               String[] from, int[] to, int flags) {

            super(context, layout, c, from, to, flags);
        }


    }

}
