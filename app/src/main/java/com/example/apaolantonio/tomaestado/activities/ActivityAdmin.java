package com.example.apaolantonio.tomaestado.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apaolantonio.tomaestado.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import clases.Captor;
import clases.Estado;
import clases.Medidor;
import clases.Mensaje;
import clases.Ordenativo;
import clases.Ruta;
import clases.Ubicacion;
import clasesB.BackUpManager;
import clasesB.CommonInfo;
import model.AppDataBaseHelper;
import model.CaptorDataBaseAdapter;
import model.ConexionDirectaDataBaseAdapter;
import model.EstadoDataBaseAdapter;
import model.MedidorDataBaseAdapter;
import model.MensajeDataBaseAdapter;
import model.NoCoincidenteDataBaseAdapter;
import model.NoIncorporadoDataBaseAdapter;
import model.OrdenativoDataBaseAdapter;
import model.RutaDataBaseAdapter;
import model.UbicacionDataBaseAdapter;

/**
 * Created by apaolantonio on 26/9/2017.
 */

public class ActivityAdmin extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        init();

    }

    public void init() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Toast.makeText(getApplicationContext(), ""+width+" "+height, Toast.LENGTH_LONG).show();

        ImageView imgD = (ImageView) findViewById(R.id.imgDatos);
        ImageView imgO = (ImageView) findViewById(R.id.imgOperario);
        ImageView imgB = (ImageView) findViewById(R.id.imgBackup);
        ImageView imgA = (ImageView) findViewById(R.id.imgAdministracion);


        imgD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ImageView imgD = (ImageView) findViewById(R.id.imgDatos);
                //imgD.setBackgroundColor(Color.parseColor("#9975E7"));

                popupDatos();
                //Toast.makeText(getApplicationContext(), "datos", Toast.LENGTH_SHORT).show();
            }
        });

        imgO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ImageView imgD = (ImageView) findViewById(R.id.imgDatos);
                //imgD.setBackgroundColor(Color.parseColor("#9975E7"));
                irModoOperario();
                Toast.makeText(getApplicationContext(), "operario", Toast.LENGTH_SHORT).show();
            }
        });

        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ImageView imgD = (ImageView) findViewById(R.id.imgDatos);
                //imgD.setBackgroundColor(Color.parseColor("#9975E7"));
                popupBackup();
                Toast.makeText(getApplicationContext(), "backup", Toast.LENGTH_SHORT).show();
            }
        });

        imgA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ImageView imgD = (ImageView) findViewById(R.id.imgDatos);
                //imgD.setBackgroundColor(Color.parseColor("#9975E7"));
                popupABM();
                //Toast.makeText(getApplicationContext(), "abm usuarios", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void popupBackup() {
        String title = "BackUp";
        final String[] opciones = {"Realizar Backup", "Restaurar Backup"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch (opciones[item]) {
                    case "Realizar Backup":
                        realizarBackUp();
                        break;
                    case "Restaurar Backup":
                        restaurarBackUp();
                        break;
                }

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void realizarBackUp() {
        BackUpManager back = new BackUpManager(getApplicationContext());

        back.crearBackUp();


			/*MensajeDataBaseAdapter dbHelperM;
			dbHelperM = MensajeDataBaseAdapter.getInstance(getApplicationContext());

			dbHelperM.abrir();
			dbHelperM.eliminarTodos();
			dbHelperM.cerrar();*/


    }

    public void restaurarBackUp() {
        BackUpManager back = new BackUpManager(getApplicationContext());

        if (back.restaurarBackUp() == true) {
            Toast.makeText(getApplicationContext(), "Copia exitosa", Toast.LENGTH_SHORT).show();
        }


			/*MensajeDataBaseAdapter dbHelperM;
			dbHelperM = MensajeDataBaseAdapter.getInstance(getApplicationContext());

			dbHelperM.abrir();
			dbHelperM.eliminarTodos();
			dbHelperM.cerrar();*/


    }

    public void popupDatos() {
        String title = "Datos";
        final String[] opciones = {"Cargar rutas", "Descargar rutas"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        Toast.makeText(getApplicationContext(), "popup datos", Toast.LENGTH_LONG).show();
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch (opciones[item]) {
                    case "Cargar rutas":
			    		/*	MyAsyncTask task = new MyAsyncTask();
			    			task.execute();*/
                        //Toast.makeText(getApplicationContext(), "antes de cargaBD", Toast.LENGTH_SHORT).show();
                        cargarBD();

                        break;
                    case "Descargar rutas":
                        descargar();
                        break;

                    default:
                        break;
                }

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void cargarBD() {
        Toast.makeText(getApplicationContext(), "Entro a cargarBD", Toast.LENGTH_SHORT).show();
        //Find the directory for the SD Card using the API
        //*Don't* hardcode "/sdcard"
        File sdcard = Environment.getExternalStorageDirectory();

        //Get the text file
        File file = new File(sdcard, "captor/carga.txt");

        //Read text from file
        //StringBuilder text = new StringBuilder();

        try {
            //Creamos un objeto para leer el archivo
            BufferedReader reader = new BufferedReader(new FileReader(file));
            //Leemos una linea del archivo
            String linea = new String();


            while ((linea = reader.readLine()) != null) {
                switch (linea.substring(0, 2)) {
                    case "00":
                        cargarCaptor(linea);
                        break;
                    case "01":
                        cargarRuta(linea);
                        break;
                    case "02":
                        cargarMedidor(linea);
                        break;
                    case "03":
                        cargarMensaje(linea);
                        break;
                    case "04":
                        cargarUbicacion(linea);
                        break;
                    case "05":
                        cargarOrdenativo(linea);
                        break;
                    default:
                        break;
                }
            }
            //Toast.makeText(getApplicationContext(), "Cantidad de mensajes: " + rutas + "Cantidad de medidores: "+medidores , Toast.LENGTH_SHORT).show();
            reader.close();


        } catch (IOException ex) {
            //Manejamos los errores posibles
            ex.printStackTrace();
        }

    }

    public void cargarRuta(String linea) {
        Ruta ruta = new Ruta();
        RutaDataBaseAdapter dbHelperRuta;
        dbHelperRuta = RutaDataBaseAdapter.getInstance(getApplicationContext());

        ruta.setRuta(linea.substring(2, 6));
        ruta.setPlan(linea.substring(6, 8));
        ruta.setrutaInt(linea.substring(8, 12));
        ruta.setParam(Integer.parseInt(linea.substring(12, 15)));
        ruta.setCantclientes(Integer.parseInt(linea.substring(15, 17)));
        ruta.setTotalmedidores(Integer.parseInt(linea.substring(17, 21)));
        ruta.setMedidorestomados(Integer.parseInt(linea.substring(21, 25)));
        ruta.setReginicmedidores(Integer.parseInt(linea.substring(25, 29)));
        ruta.setUltsec_fol_ds_sentidote(Integer.parseInt(linea.substring(29, 40)));

        dbHelperRuta.abrir();
        dbHelperRuta.agregar(ruta);
        dbHelperRuta.cerrar();


    }


    public void cargarMedidor(String linea) {
        Medidor medidor = new Medidor();
        MedidorDataBaseAdapter dbHelperMedidor;
        dbHelperMedidor = MedidorDataBaseAdapter.getInstance(getApplicationContext());

        medidor.setRuta(linea.substring(2, 6));
        medidor.setFolio(linea.substring(6, 11));
        medidor.setDigseg(linea.substring(11, 13));
        medidor.setMedidor(linea.substring(13, 26));
        medidor.setDireccion(linea.substring(26, 54));
        medidor.setFacmul(Integer.parseInt(linea.substring(55, 58)));
        medidor.setTarifa(linea.substring(58, 62));
        medidor.setEstant(linea.substring(62, 68));
        medidor.setPromedio(linea.substring(68, 74));
        medidor.setCodubicacion(linea.substring(74, 78));
        medidor.setCodmensaje(linea.substring(78, 80));
        medidor.setValidacion(0);
        medidor.setIntentos(1);

        dbHelperMedidor.abrir();
        dbHelperMedidor.agregar(medidor);
        dbHelperMedidor.cerrar();


    }

    public void cargarUbicacion(String linea) {

        Ubicacion ubicacion = new Ubicacion();
        UbicacionDataBaseAdapter dbHelperU;
        dbHelperU = UbicacionDataBaseAdapter.getInstance(getApplicationContext());

        ubicacion.setcodubi(Integer.parseInt(linea.substring(2, 4)));
        ubicacion.setUbicacion(linea.substring(4));


        dbHelperU.abrir();
        dbHelperU.agregar(ubicacion);
        dbHelperU.cerrar();

    }

    public void cargarOrdenativo(String linea) {

        Ordenativo ordenativo = new Ordenativo();
        OrdenativoDataBaseAdapter dbHelperO;
        dbHelperO = OrdenativoDataBaseAdapter.getInstance(getApplicationContext());

        ordenativo.setord(Integer.parseInt(linea.substring(2, 5)));
        ordenativo.setOrdenativo(linea.substring(5));


        dbHelperO.abrir();
        dbHelperO.agregar(ordenativo);
        dbHelperO.cerrar();

    }

    public void cargarMensaje(String linea) {
        Mensaje mensaje = new Mensaje();
        MensajeDataBaseAdapter dbHelperM;
        dbHelperM = MensajeDataBaseAdapter.getInstance(getApplicationContext());

        mensaje.setmsj(Integer.parseInt(linea.substring(2, 4)));
        mensaje.setMensaje(linea.substring(4));


        dbHelperM.abrir();
        dbHelperM.agregar(mensaje);
        dbHelperM.cerrar();

    }

    public void cargarCaptor(String linea) {
//Tuve que modificar un poquito el archivo excel para que tome bien los valores.
//Le agregu� un 0 (cero) al IdCarga, sino quedaba defasado.
//Le cambi� la password a 'xxxxxxxx'

        Captor captor = new Captor();
        CaptorDataBaseAdapter dbHelper;
        dbHelper = CaptorDataBaseAdapter.getInstance(getApplicationContext());

        captor.setIdCarga(linea.substring(2, 14));
        captor.setNroCaptor(Integer.parseInt(linea.substring(14, 18)));
        captor.setLegajo(Integer.parseInt(linea.substring(18, 23)));
        captor.setNombre(linea.substring(23, 39));
        captor.setPass(linea.substring(39, 47));


        dbHelper.abrir();
        dbHelper.agregar(captor);
        dbHelper.cerrar();

		/*	Toast.makeText(getApplicationContext(),""+ captor.getNroCaptor(), Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(),""+ captor.getLegajo(), Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(),""+ captor.getNombre(), Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(),""+ captor.getPass(), Toast.LENGTH_SHORT).show();*/
    }

    public void irModoOperario() {
        CommonInfo.ADMINISTRADOR = 0;
        Intent intento = new Intent(this, ActivityRutas.class);
        startActivity(intento);

    }

    public void descargar() {
        try {
            //Creamos un objeto para escribir el archivo
            //OutputStreamWriter writer = new	OutputStreamWriter(openFileOutput("descarga.txt", getApplicationContext().MODE_PRIVATE));
            //Creamos contenido

            File sdcard = Environment.getExternalStorageDirectory();

            //Get the text file
            File file = new File(sdcard, "captor/descargacaptor.txt");


            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));

			    /*fout.write("Texto de prueba.");
			    fout.close();*/


            //////////DESCARGA DEL CAPTOR
            Captor captor = new Captor();
            CaptorDataBaseAdapter dbHelper;
            dbHelper = CaptorDataBaseAdapter.getInstance(getApplicationContext());
            dbHelper.abrir();
            captor = dbHelper.buscar();
            dbHelper.cerrar();

            writer.write("50" + agregarceros(captor.getIdCarga(), 12) + captor.getLegajo() + "000" + "\n"); //El 000 equivale al idObservacion que supuestamente NO se utiliza


            //////////DESCARGA DE LAS RUTAS
            RutaDataBaseAdapter dbHelperRuta;
            dbHelperRuta = RutaDataBaseAdapter.getInstance(getApplicationContext());
            Cursor cursor;
            dbHelperRuta.abrir();
            cursor = dbHelperRuta.obtenerTodos();
            dbHelperRuta.cerrar();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);

                String idRuta = cursor.getString(cursor.getColumnIndex(AppDataBaseHelper.CAMPO_RUTA));
                String plan = cursor.getString(cursor.getColumnIndex(AppDataBaseHelper.CAMPO_PLAN));
                String rutaint = cursor.getString(cursor.getColumnIndex(AppDataBaseHelper.CAMPO_RUTAINT));
                writer.write("51" + idRuta + plan + rutaint + "000" + "\n"); //El 000 equivale al idObservacion que supuestamente NO se utiliza
            }


            /////////////DESCARGA DE LOS MEDIDORES
            Cursor cursorM;
            MedidorDataBaseAdapter dbHelperM;
            dbHelperM = MedidorDataBaseAdapter.getInstance(getApplicationContext());
            dbHelperM.abrir();
            cursorM = dbHelperM.obtenerTodos();
            dbHelperM.cerrar();

            for (int i = 0; i < cursorM.getCount(); i++) {
                cursorM.moveToPosition(i);

                String idRuta = cursorM.getString(cursorM.getColumnIndex(AppDataBaseHelper.CAMPO_RUTA_MEDIDOR));
                String folio = cursorM.getString(cursorM.getColumnIndex(AppDataBaseHelper.CAMPO_FOLIO));
                String ds = cursorM.getString(cursorM.getColumnIndex(AppDataBaseHelper.CAMPO_DIGSEG));
                String ubi = cursorM.getString(cursorM.getColumnIndex(AppDataBaseHelper.CAMPO_CODUBICACION));
                String msj = cursorM.getString(cursorM.getColumnIndex(AppDataBaseHelper.CAMPO_CODMENSAJE));
                int intentos = cursorM.getInt(cursorM.getColumnIndex(AppDataBaseHelper.CAMPO_INTENTOS));
                int validacion = cursorM.getInt(cursorM.getColumnIndex(AppDataBaseHelper.CAMPO_VALIDACION));
                String promedio = cursorM.getString(cursorM.getColumnIndex(AppDataBaseHelper.CAMPO_PROMEDIO));


                EstadoDataBaseAdapter dbHelperE;
                dbHelperE = EstadoDataBaseAdapter.getInstance(getApplicationContext());
                Estado est = new Estado();
                dbHelperE.abrir();
                est = dbHelperE.buscar(cursorM.getInt(cursorM.getColumnIndex(AppDataBaseHelper.CAMPO_ID_MEDIDOR)));
                dbHelperE.cerrar();

                if (est == null) {
						/*Toast.makeText(getApplicationContext(), "52"+idRuta+folio+ds+ubi+msj+intentos+validacion+"000000"+promedio+"0"+
								"000"+"000"+"000"+"000"+"000"+"000"+"000"+"\n", Toast.LENGTH_LONG).show();*/
                    writer.write("52" + idRuta + folio + ds + ubi + msj + intentos + validacion + "000000" + promedio + "0000" +
                            "000" + "000" + "000" + "000" + "000" + "000" + "000" + "000" + "\n");

                } else {
                    String hora = est.getHora().substring(0, 2);
                    String min = est.getHora().substring(2, 4);
                    writer.write("52" + idRuta + folio + ds + ubi + msj + intentos + validacion + agregarceros(est.getEstado(), 6) + promedio + hora + min + agregarceros("" + est.getSecuencia(), 3) +
                            agregarceros("" + est.getOrdenativo1(), 3) + agregarceros("" + est.getOrdenativo2(), 3) + agregarceros("" + est.getOrdenativo3(), 3) +
                            agregarceros("" + est.getOrdenativo4(), 3) + agregarceros("" + est.getOrdenativo5(), 3) + agregarceros("" + est.getOrdenativo6(), 3) + "000" + "\n");
                }

            }


            /////////////DESCARGA DE LOS NO INCORPORADOS: CODIGO 53
            Cursor cursorNI;
            NoIncorporadoDataBaseAdapter dbHelperNI;
            dbHelperNI = NoIncorporadoDataBaseAdapter.getInstance(getApplicationContext());
            dbHelperNI.abrir();
            cursorNI = dbHelperNI.obtenerTodos();
            dbHelperNI.cerrar();

            if (cursorNI.getCount() > 0) {
                for (int i = 0; i < cursorNI.getCount(); i++) {
                    cursorNI.moveToPosition(i);

                    String idRuta = cursorNI.getString(cursorNI.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_RUTA));
                    int medidor = cursorNI.getInt(cursorNI.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_MEDIDOR));
                    int estado = cursorNI.getInt(cursorNI.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_ESTADO));
                    String dire = cursorNI.getString(cursorNI.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_DIRECCION));
                    String folio = cursorNI.getString(cursorNI.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_FOLIO));
                    String ds = cursorNI.getString(cursorNI.getColumnIndex(AppDataBaseHelper.CAMPO_NOINCORPORADO_DS));


                    writer.write("53" + idRuta + medidor + estado + dire + folio + ds + "000" + "\n"); //ACA S� SE UTILIZA EL idOBS


                }
            }


            /////////////DESCARGA DE LAS CONEXIONES DIRECTAS: CODIGO 54
            Cursor cursorCD;
            ConexionDirectaDataBaseAdapter dbHelperCD;
            dbHelperCD = ConexionDirectaDataBaseAdapter.getInstance(getApplicationContext());
            dbHelperCD.abrir();
            cursorCD = dbHelperCD.obtenerTodos();
            dbHelperCD.cerrar();

            if (cursorCD.getCount() > 0) {
                for (int i = 0; i < cursorCD.getCount(); i++) {
                    cursorCD.moveToPosition(i);

                    String idRuta = cursorCD.getString(cursorCD.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_RUTA));
                    String dire = cursorCD.getString(cursorCD.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_DIRECCION));
                    int tipo = cursorCD.getInt(cursorCD.getColumnIndex(AppDataBaseHelper.CAMPO_CONEXION_TIPO));


                    writer.write("54" + idRuta + dire + tipo + "\n"); //ACA S� SE UTILIZA EL idOBS
                    //Toast.makeText(getApplicationContext(), "54"+idRuta+dire+tipo, Toast.LENGTH_LONG).show();


                }
            }


            /////////////DESCARGA DE LOS NO COINCIDENTES: CODIGO 55
            Cursor cursorNC;
            NoCoincidenteDataBaseAdapter dbHelperNC;
            dbHelperNC = NoCoincidenteDataBaseAdapter.getInstance(getApplicationContext());
            dbHelperNC.abrir();
            cursorNC = dbHelperNC.obtenerTodos();
            dbHelperNC.cerrar();

            if (cursorNC.getCount() > 0) {
                for (int i = 0; i < cursorNC.getCount(); i++) {
                    cursorNC.moveToPosition(i);

                    String idRuta = cursorNC.getString(cursorNC.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_RUTA));
                    String folio = cursorNC.getString(cursorNC.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_FOLIO));
                    String ds = cursorNC.getString(cursorNC.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_DS));
                    int med = cursorNC.getInt(cursorNC.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_MEDIDORACTUAL));
                    int estado = cursorNC.getInt(cursorNC.getColumnIndex(AppDataBaseHelper.CAMPO_NOCOINCIDENTE_ESTADO));
                    writer.write("55" + idRuta + folio + ds + med + estado + "\n"); //ACA S� SE UTILIZA EL idOBS
                    //Toast.makeText(getApplicationContext(), "55"+idRuta+folio+ds+med+estado, Toast.LENGTH_LONG).show();


                }
            }
            //Cerramos el archivo
            writer.close();

        } catch (IOException ex) {
            //Manejamos los errores posibles
            ex.printStackTrace();
        }


    }

    public String agregarceros(String cadena, int i) {

        String retorno = "";
        int j;
        for (j = 0; j < (i - cadena.length()); j++) {
            retorno = retorno + "0";
        }


        retorno = retorno + cadena;
        Toast.makeText(getApplicationContext(), retorno, Toast.LENGTH_LONG).show();
        return retorno;


    }

    public void popupABM() {
        String title = "ABM";
        final String[] opciones = {"Nuevo usuario", "Ver lista"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch (opciones[item]) {
                    case "Nuevo usuario":
                        agregarEmpleado();
                        break;
                    case "Ver lista":
                        verLista();
                        break;
                }

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void agregarEmpleado() {
        Intent intento = new Intent(this, ActivityABM.class);
        CommonInfo.MODIFICAR_EMPLEADO = 0;
        startActivity(intento);

    }

    public void verLista() {
        Intent intento = new Intent(this, ActivityBM.class);
        startActivity(intento);

    }
}