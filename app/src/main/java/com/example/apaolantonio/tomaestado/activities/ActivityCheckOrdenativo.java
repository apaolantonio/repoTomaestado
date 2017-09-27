package com.example.apaolantonio.tomaestado.activities;

import java.util.ArrayList;
import model.AppDataBaseHelper;
import model.EstadoDataBaseAdapter;
import model.OrdenativoDataBaseAdapter;
import clases.Estado;
import clasesB.CommonInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.apaolantonio.tomaestado.R;


public class ActivityCheckOrdenativo extends Activity {

    private OrdenativoDataBaseAdapter dbHelper;
    private EstadoDataBaseAdapter dbHelperE;
    private MyCursorAdapter adapter;
    private ListView lstOrdenativo;
    private Estado estado;
    private Cursor cursorOrdenativos;
    public int contadorOrden=0;
    private int position;

    private ArrayList<String> listaOrdenativo = new ArrayList<String>();
    private ArrayList<Integer> listaOrdenativoID = new ArrayList<Integer>();
    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();

    @SuppressLint("NewApi")
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ordenativo);

        Intent i = getIntent();
        Bundle r = i.getExtras();
        position = (Integer) r.getSerializable(CommonInfo.KEY_BUNDLE_POSITION);
        estado = (Estado) r.getSerializable(CommonInfo.KEY_BUNDLE_ESTADO);
        //Toast.makeText(this, "El estado que llego es el: " + toString(estado.get_id()), Toast.LENGTH_LONG).show();

        dbHelper = OrdenativoDataBaseAdapter.getInstance(this);
        dbHelper.abrir();




        String[] from = {AppDataBaseHelper.CAMPO_ORDENATIVO,
                AppDataBaseHelper.CAMPO_ORD
        };

        int[] to = {
                R.id.chkOrdenativo,
                R.id.txvOrdenativoId
        };


        cursorOrdenativos = dbHelper.obtenerTodos();
        dbHelper.cerrar();
        adapter = new MyCursorAdapter(this, R.layout.list_checkbox_ordenativo, cursorOrdenativos, from, to, 0);

        lstOrdenativo = (ListView) findViewById(R.id.lstOrdenativo);
        lstOrdenativo.setAdapter(adapter);

		/*lstOrdenativo.setOnItemClickListener(new OnItemClickListener(){
			@Override
		    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

					CheckBox check = (CheckBox) view.findViewById(R.id.chkOrdenativo);

					if(contadorOrden>=6)
					{
						dialogoError("Error","La cantidad de ordenativos debe ser menor a 6");
						Toast.makeText(getApplicationContext(), "La cantidad de ordenativos no debe ser mayor 6", Toast.LENGTH_SHORT).show();
					}

					if(!check.isChecked() && contadorOrden<6)
					{

						check.setChecked(true);
						itemChecked.set(position, true);
						contadorOrden++;
						//Toast.makeText(getApplicationContext(), "Checkeada la pos " + position , Toast.LENGTH_SHORT).show();
					}
					else
					{

						check.setChecked(false);
						itemChecked.set(position, false);
						contadorOrden--;
						//Toast.makeText(getApplicationContext(), "Descheckeada la pos " + position , Toast.LENGTH_SHORT).show();
					}


			}

		});*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_check_ordenativo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnAceptarOrdenativo(View view)
    {

        int i=0; int cantOrden=0;
        for(i=0;i<cursorOrdenativos.getCount();i++)
        {
            if(itemChecked.get(i)==true)
            {
                cantOrden++;
                String orden=listaOrdenativo.get(i);
                Toast.makeText(getApplicationContext(), orden, Toast.LENGTH_SHORT).show();


                switch (cantOrden)
                {
                    case 1: estado.setOrdenativo1(listaOrdenativoID.get(i));
                        Toast.makeText(getApplicationContext(), ""+listaOrdenativoID.get(i), Toast.LENGTH_SHORT).show();break;
                    case 2: estado.setOrdenativo2(listaOrdenativoID.get(i));
                        Toast.makeText(getApplicationContext(), ""+listaOrdenativoID.get(i), Toast.LENGTH_SHORT).show();break;
                    case 3: estado.setOrdenativo3(listaOrdenativoID.get(i));Toast.makeText(getApplicationContext(), ""+listaOrdenativoID.get(i), Toast.LENGTH_SHORT).show();break;
                    case 4: estado.setOrdenativo4(listaOrdenativoID.get(i));Toast.makeText(getApplicationContext(), ""+listaOrdenativoID.get(i), Toast.LENGTH_SHORT).show();break;
                    case 5: estado.setOrdenativo5(listaOrdenativoID.get(i));Toast.makeText(getApplicationContext(), ""+listaOrdenativoID.get(i), Toast.LENGTH_SHORT).show();break;
                    case 6: estado.setOrdenativo6(listaOrdenativoID.get(i));Toast.makeText(getApplicationContext(), ""+listaOrdenativoID.get(i), Toast.LENGTH_SHORT).show();break;
                    default: break;


                }
            }
        }


        dbHelperE=EstadoDataBaseAdapter.getInstance(getApplicationContext());
        dbHelperE.abrir();
        dbHelperE.modificar(estado);
        estado=dbHelperE.buscar(estado.getId_Medidor());
        dbHelperE.cerrar();



        Toast.makeText(getApplicationContext(), ""+estado.getEstado() + " "+estado.getId_Medidor()+" "+estado.getOrdenativo1()+" "+estado.getOrdenativo2()+" "+estado.getOrdenativo3()+" "+estado.getOrdenativo4()+" "+estado.getOrdenativo5()+" "+estado.getOrdenativo6(), Toast.LENGTH_LONG).show();

        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }
    public void btnCancelarOrdenativo(View view)
    {
        finish();
    }

    public class MyCursorAdapter extends SimpleCursorAdapter {



        private Cursor c;
        private Context context;

        // itemChecked will store the position of the checked items.

        @SuppressLint("NewApi")
        public MyCursorAdapter(Context context, int layout, Cursor c,
                               String[] from, int[] to, int flags) {


            super(context, layout, c, from, to, flags);
            this.c=c;
            this.context=context;

            for (int i = 0; i < this.getCount(); i++) {
                itemChecked.add(i, false); // inicializa todos los items en falso
                c.moveToPosition(i);
                listaOrdenativoID.add(i, c.getInt(c.getColumnIndex(AppDataBaseHelper.CAMPO_ORD)));
                listaOrdenativo.add(i, c.getString(c.getColumnIndex(AppDataBaseHelper.CAMPO_ORDENATIVO)));
            }
        }


        /*Lo que hago en el m�todo getView es guardar en dos list distintas el
         * estado del checkbox (true o false) y en otra lista los string que
         * del cursor, esto lohago porque android lo que hace es reutilizar
         * las vistas, entonces si me desplazo hacia abajo se me destildaban
         * los checkbox que iba marcando. no se si es la opci�n m�s eficiente pero
         * anda por lo pronto*/
        @Override
        public View getView(final int pos, View view, ViewGroup parent) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_checkbox_ordenativo, null);
            }


            final CheckBox cBox = (CheckBox) view.findViewById(R.id.chkOrdenativo); // your
            final TextView txv = (TextView) view.findViewById(R.id.txvOrdenativoId);

            cBox.setText(listaOrdenativo.get(pos));
            txv.setText(""+listaOrdenativoID.get(pos));
            // CheckBox
            cBox.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {

                    CheckBox cb = (CheckBox) v.findViewById(R.id.chkOrdenativo);


                    if (cb.isChecked())
                    {
                        itemChecked.set(pos, true);
                        contadorOrden++;


                    } else if (!cb.isChecked()) {
                        itemChecked.set(pos, false);
                        contadorOrden--;


                    }

                }
            });
            cBox.setChecked(itemChecked.get(pos));

            if(contadorOrden>6) Toast.makeText(getApplicationContext(), "Te pasaste", Toast.LENGTH_SHORT).show();

            // CheckBox in ListView
            // according to their original
            // position and CheckBox never
            // loss his State when you
            // Scroll the List Items.
            return view;
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
}