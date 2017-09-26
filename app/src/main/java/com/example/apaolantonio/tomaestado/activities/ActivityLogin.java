package com.example.apaolantonio.tomaestado.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apaolantonio.tomaestado.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import model.CaptorDataBaseAdapter;
import model.ConexionDirectaDataBaseAdapter;
import model.EmpleadoDataBaseAdapter;
import model.EstadoDataBaseAdapter;
import model.GeolocalizacionDataBaseAdapter;
import model.MedidorDataBaseAdapter;
import model.MensajeDataBaseAdapter;
import model.NoCoincidenteDataBaseAdapter;
import model.NoIncorporadoDataBaseAdapter;
import model.OrdenativoDataBaseAdapter;
import model.RutaDataBaseAdapter;
import model.UbicacionDataBaseAdapter;
import watcher.LoginWatcher;

/**
 * Created by apaolantonio on 25/9/2017.
 */

public class ActivityLogin extends AppCompatActivity {

    private RutaDataBaseAdapter dbRuta;
    private MedidorDataBaseAdapter dbMedidor;
    private EstadoDataBaseAdapter dbEstado;
    private ConexionDirectaDataBaseAdapter dbCD;
    private OrdenativoDataBaseAdapter dbORD;
    private NoCoincidenteDataBaseAdapter dbNC;
    private NoIncorporadoDataBaseAdapter dbNI;
    private CaptorDataBaseAdapter dbC;
    private MensajeDataBaseAdapter dbM;
    private UbicacionDataBaseAdapter dbU;
    private EmpleadoDataBaseAdapter dbE;
    private GeolocalizacionDataBaseAdapter dbG;

    private EditText etxUser;
    private EditText etxPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpComponents();
    }

    private void setUpComponents() {

        etxUser = (EditText) findViewById(R.id.etxUser);
        etxPass = (EditText) findViewById(R.id.etxPass);
        etxUser.addTextChangedListener(new LoginWatcher(etxUser, "El usuario"," 5 dígitos",5));
        etxPass.addTextChangedListener(new LoginWatcher(etxPass,"La contraseña"," 8 caracteres",8));
        etxUser.setText("");
        etxPass.setText("");

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_login, menu);
        return true;
    }

  @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
           case R.id.itemGeolocalizacion:
                //geo();
                //imposibleTomar();
                return true;
            case R.id.itemExportar:
                exportDB();
                //agregarNoCoincidente();
                return true;
            case R.id.itemEliminar:
                eliminarTodo();
                //modificarEstado();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ir(View view)
    {

        Intent intento = new Intent(this,ActivityAdmin.class);
        startActivity(intento);

    }

    public void eliminarTodo()
    {
        dbRuta= RutaDataBaseAdapter.getInstance(getApplicationContext());
        dbMedidor= MedidorDataBaseAdapter.getInstance(getApplicationContext());
        dbEstado= EstadoDataBaseAdapter.getInstance(getApplicationContext());
        dbNC= NoCoincidenteDataBaseAdapter.getInstance(getApplicationContext());
        dbORD= OrdenativoDataBaseAdapter.getInstance(getApplicationContext());
        dbCD= ConexionDirectaDataBaseAdapter.getInstance(getApplicationContext());
        dbNI= NoIncorporadoDataBaseAdapter.getInstance(getApplicationContext());
        dbC= CaptorDataBaseAdapter.getInstance(getApplicationContext());
        dbM= MensajeDataBaseAdapter.getInstance(getApplicationContext());
        dbU= UbicacionDataBaseAdapter.getInstance(getApplicationContext());
        dbE= EmpleadoDataBaseAdapter.getInstance(getApplicationContext());
        dbG= GeolocalizacionDataBaseAdapter.getInstance(getApplicationContext());

        dbRuta.abrir();	dbRuta.eliminarTodos();dbRuta.cerrar();
        dbMedidor.abrir();dbMedidor.eliminarTodos();dbMedidor.cerrar();
        dbEstado.abrir();dbEstado.eliminarTodos();dbEstado.cerrar();
        dbNC.abrir();	dbNC.eliminarTodos();dbNC.cerrar();
        dbORD.abrir();dbORD.eliminarTodos();dbORD.cerrar();
        dbCD.abrir();dbCD.eliminarTodos(getApplicationContext());dbCD.cerrar();
        dbNI.abrir();dbNI.eliminarTodos();dbNI.cerrar();
        dbC.abrir();dbC.eliminarTodos();dbC.cerrar();
        dbM.abrir();dbM.eliminarTodos();dbM.cerrar();
        dbU.abrir();dbU.eliminarTodos();dbU.cerrar();
        dbE.abrir();dbE.eliminarTodos();dbE.cerrar();
        dbG.abrir();dbG.eliminarTodos();dbG.cerrar();
    }

    public void exportDB(){
        Toast.makeText(this, "ITEM EXPORT", Toast.LENGTH_LONG).show();
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.example.apaolantonio.tomaestado" +"/databases/"+"DB_medidores";
        String backupDBPath = "DB_medidores.sqlite";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}