package com.example.apaolantonio.tomaestado.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.apaolantonio.tomaestado.R;

import watcher.LoginWatcher;

/**
 * Created by apaolantonio on 25/9/2017.
 */

public class ActivityLogin extends Activity{

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

}
