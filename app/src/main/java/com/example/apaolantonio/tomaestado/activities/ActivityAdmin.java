package com.example.apaolantonio.tomaestado.activities;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apaolantonio.tomaestado.R;

/**
 * Created by apaolantonio on 26/9/2017.
 */

public class ActivityAdmin extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        init();

    }

    public void init()
    {
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

                //popupDatos();
                Toast.makeText(getApplicationContext(), "datos", Toast.LENGTH_SHORT).show();
            }
        });

        imgO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ImageView imgD = (ImageView) findViewById(R.id.imgDatos);
                //imgD.setBackgroundColor(Color.parseColor("#9975E7"));
                //irModoOperario();
                Toast.makeText(getApplicationContext(), "operario", Toast.LENGTH_SHORT).show();
            }
        });

        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ImageView imgD = (ImageView) findViewById(R.id.imgDatos);
                //imgD.setBackgroundColor(Color.parseColor("#9975E7"));
               // popupBackup();
                Toast.makeText(getApplicationContext(), "backup", Toast.LENGTH_SHORT).show();
            }
        });

        imgA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ImageView imgD = (ImageView) findViewById(R.id.imgDatos);
                //imgD.setBackgroundColor(Color.parseColor("#9975E7"));
                //popupABM();
                Toast.makeText(getApplicationContext(), "abm usuarios", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
