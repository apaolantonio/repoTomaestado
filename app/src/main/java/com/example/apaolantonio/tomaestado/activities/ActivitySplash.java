package com.example.apaolantonio.tomaestado.activities;

/**
 * Created by apaolantonio on 25/9/2017.
 */

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.apaolantonio.tomaestado.R;


public class ActivitySplash extends Activity{

    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Esto es lo que se llama un splash screen
        //muestra una pantalla de presentación al inicio
        //durante unos pocos segundos
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent intento = new Intent(getApplicationContext(),ActivityLogin.class);
                startActivity(intento);
                finish();
            }
        };


        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }



  /*  public void init(View view)
    {
        Intent intento = new Intent(this,ActivityLogin.class);
        startActivity(intento);

    }*/


   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


}
