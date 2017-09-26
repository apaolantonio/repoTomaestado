package com.example.apaolantonio.tomaestado.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.apaolantonio.tomaestado.R;

import watcher.LoginWatcher;

/**
 * Created by apaolantonio on 25/9/2017.
 */

public class ActivityLogin extends AppCompatActivity {

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
                //exportDB();
                //agregarNoCoincidente();
                return true;
            case R.id.itemEliminar:
                //eliminarTodo();
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

}