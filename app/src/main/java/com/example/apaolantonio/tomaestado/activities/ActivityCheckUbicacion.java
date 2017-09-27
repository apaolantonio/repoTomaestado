package com.example.apaolantonio.tomaestado.activities;

import android.support.v7.app.ActionBarActivity;
import model.AppDataBaseHelper;
import model.MedidorDataBaseAdapter;
import model.UbicacionDataBaseAdapter;
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
import android.view.View;
import android.widget.ListView;

import com.example.apaolantonio.tomaestado.R;


public class ActivityCheckUbicacion extends Activity {

    private UbicacionDataBaseAdapter dbHelper;
    private MyCursorAdapter adapter;
    private ListView lstUbi;
    private Cursor cursor;
    public int contadorMensaje=0;
    private Medidor medidor;
    private Ruta ruta;
    private int position;
    String cod;
    String msj;



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utiliza el mismo layout que ActivityCheckMensaje
        setContentView(R.layout.activity_check_mensaje);

        Intent i = getIntent();
        Bundle r = i.getExtras();
        medidor = (Medidor) r.getSerializable(CommonInfo.KEY_BUNDLE_MEDIDOR);
        ruta = (Ruta) r.getSerializable(CommonInfo.KEY_BUNDLE_RUTA);
        position = (Integer) r.getSerializable(CommonInfo.KEY_BUNDLE_POSITION);

        dbHelper = UbicacionDataBaseAdapter.getInstance(this);
        dbHelper.abrir();




        String[] from = {AppDataBaseHelper.CAMPO_UBICACION_MEDIDOR,
                AppDataBaseHelper.CAMPO_COD_UBICACION
        };

        int[] to = {
                R.id.radioUbi,
                R.id.txvRadioUbicacionID
        };


        cursor = dbHelper.obtenerTodos();
        dbHelper.cerrar();
        adapter = new MyCursorAdapter(this, R.layout.list_radiobtn_ubi, cursor, from, to, 0);

        lstUbi = (ListView) findViewById(R.id.lstMensaje);
        lstUbi.setAdapter(adapter);

        lstUbi.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
            {

                msj = cursor.getString(cursor.getColumnIndex(AppDataBaseHelper.CAMPO_UBICACION_MEDIDOR));
                cod = cursor.getString(cursor.getColumnIndex(AppDataBaseHelper.CAMPO_COD_UBICACION));
                //Toast.makeText(getApplicationContext(), msj , Toast.LENGTH_SHORT).show();
                dialogo("Aviso","El medidor será cambiado a "+msj);

            }
        });

    }



    public String toString(int x)
    {
        String ret = x+"";
        return  ret;

    }


    /*public void btnAceptarOrdenativo(View view)
    {

        int i=0; int cantOrden=0;
        for(i=0;i<cursorMensaje.getCount();i++)
        {
            if(itemChecked.get(i)==true)
            {
                cantOrden++;
                String orden=listaOrdenativo.get(i);
                Toast.makeText(getApplicationContext(), orden, Toast.LENGTH_SHORT).show();
            }

            switch (cantOrden)
            {
                case 1: estado.setOrdenativo1(listaOrdenativoID.get(i));break;
                case 2: estado.setOrdenativo2(listaOrdenativoID.get(i));break;
                case 3: estado.setOrdenativo3(listaOrdenativoID.get(i));break;
                case 4: estado.setOrdenativo4(listaOrdenativoID.get(i));break;
                case 5: estado.setOrdenativo5(listaOrdenativoID.get(i));break;
                case 6: estado.setOrdenativo6(listaOrdenativoID.get(i));break;


            }


        }


        dbHelperE=EstadoDataBaseAdapter.getInstance(getApplicationContext());
        dbHelperE.abrir();
        dbHelperE.modificar(estado);
        estado=dbHelperE.buscar(estado.getId_Medidor());
        dbHelperE.cerrar();



        Toast.makeText(getApplicationContext(), ""+estado.getEstado() + " "+estado.getId_Medidor()+" "+estado.getOrdenativo1()+" "+estado.getOrdenativo2()+" "+estado.getOrdenativo3()+" "+estado.getOrdenativo4()+" "+estado.getOrdenativo5()+" "+estado.getOrdenativo6(), Toast.LENGTH_LONG).show();
        finish();
    }*/
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


		  /*  for(i=0;i<c.getCount();i++)
		    {
		    	c.moveToPosition(i);
		    	itemChecked.add(false);
		    	listaMensaje.add(c.getString(c.getColumnIndex(AppDataBaseHelper.CAMPO_MENSAJE)));
		    	listaMensajeID.add(c.getInt(c.getColumnIndex(AppDataBaseHelper.CAMPO_MENSAJECOD)));

		    }*/
        }

	/*	@Override
		public View getView(final int pos, View view, ViewGroup parent) {
		    if (view == null) {
		        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        view = inflater.inflate(R.layout.list_radiobtn_msj, null);
		    }


		    final RadioButton rBtn = (RadioButton) view.findViewById(R.id.radioMsj); // your
		    final TextView txv = (TextView) view.findViewById(R.id.txvRadioMensajeID);

		    rBtn.setText(listaMensaje.get(pos));
		    txv.setText(""+listaMensajeID.get(pos));

		    rBtn.setOnClickListener(new OnClickListener() {

		        public void onClick(View v) {

		        	RadioButton rBtn = (RadioButton) v.findViewById(R.id.radioMsj);

		        	if(itemChecked.get(pos)) rBtn
		        }
		    });
		    rBtn.setChecked(itemChecked.get(pos)); // this will Check or Uncheck the


		    return view;
		}
	}*/
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

                medidor.setCodubicacion(cod);
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
        finish();

    }

}


