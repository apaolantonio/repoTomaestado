package com.example.apaolantonio.tomaestado.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.apaolantonio.tomaestado.R;

import clases.Empleado;
import clasesB.CommonInfo;
import model.AppDataBaseHelper;
import model.EmpleadoDataBaseAdapter;

public class ActivityBM extends Activity
{

	private ListView listBM;
	private Empleado empleadoSeleccionado;
	private EmpleadoDataBaseAdapter dbHelper;
	private MyCursorAdapter adapter;
	private Cursor cursorEmpleados;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bm);
		
		empleadoSeleccionado=new Empleado();
		
		
		

		
		String[] from = {AppDataBaseHelper.CAMPO_EMPLEADO_NOMBRE,
				AppDataBaseHelper.CAMPO_EMPLEADO_LEGAJO,
				AppDataBaseHelper.CAMPO_EMPLEADO_ADMIN
		};
		
		int[] to = {R.id.txvUsuarioList,
				R.id.txvLegajoList,
				R.id.txvAdministradorList
		};
		dbHelper = EmpleadoDataBaseAdapter.getInstance(this);
		if(cursorEmpleados==null)
		{
			
		dbHelper.abrir();
		cursorEmpleados = dbHelper.obtenerTodos();
		dbHelper.cerrar();}
		
		adapter = new MyCursorAdapter(this, R.layout.list_item_abm, cursorEmpleados, from, to, 0);
		
		listBM = (ListView) findViewById(R.id.listBM);
		
		listBM.setAdapter(adapter);
		
		listBM.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				
				
				//Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();
				dbHelper.abrir();
		    	
		    	final Cursor cursorRutas = dbHelper.obtenerTodos();
		        cursorRutas.moveToPosition(position);
		       
		       int rowId = cursorRutas.getInt(cursorRutas.getColumnIndex(AppDataBaseHelper.CAMPO_ID_EMPLEADO));
		       
		       
		       empleadoSeleccionado=dbHelper.buscar(rowId);
		       dbHelper.cerrar();
		       //Toast.makeText(getApplicationContext(), ""+empleadoSeleccionado.getNombre(), Toast.LENGTH_SHORT).show();
		       popUp(position,empleadoSeleccionado);
				
			}});
		
	}
	
	public void popUp(int position, final Empleado emp)
	{
		final String[] opc = {"Modificar","Eliminar"};
		   	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(emp.getNombre().toUpperCase());
		builder.setItems(opc, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        
		    	switch(opc[item])
		    	{
		    		case "Modificar": modificarEmp(emp); break;
		    		case "Eliminar": eliminarEmp(emp); break;
		    	}
		    	
		        dialog.cancel();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
		
	}
	
	public void modificarEmp(Empleado emp)
	{
		CommonInfo.MODIFICAR_EMPLEADO=1;
		Intent intento = new Intent(getApplicationContext(),ActivityABM.class);
        Bundle recurso = new Bundle();
        
        recurso.putSerializable(CommonInfo.KEY_BUNDLE_EMPLEADO, emp);

        intento.putExtras(recurso);
        //intento.putExtra(CommonInfo.KEY_ACTIVITY_CALLER, recurso);
        startActivity(intento);
	}
	
	public void eliminarEmp(Empleado emp)
	{
		dbHelper.abrir();
		dbHelper.eliminarEmp(emp);
		dbHelper.cerrar();
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("�xito");
		alert.setMessage("El usuario "+ emp.getNombre().toUpperCase() +" fue eliminado");
		alert.setPositiveButton("Aceptar", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent j = new Intent(getApplicationContext(),ActivityBM.class);
				startActivity(j);
				finish();
				
			}});
		
		alert.show();
		
	}
	
	
	private class MyCursorAdapter extends SimpleCursorAdapter {
		
		  @SuppressLint("NewApi")
		public MyCursorAdapter(Context context, int layout, Cursor c,
                               String[] from, int[] to, int flags) {
			  
		   super(context, layout, c, from, to, flags);
		  }  
		 
		@Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		 
		 //obtiene la referencia a la fila
		   View view = super.getView(position, convertView, parent);
		  
		  
		
		   //R.id.txvAdministradorList
	        
	    	   TextView txvAdministradorList= (TextView) view.findViewById(R.id.txvAdministradorList);
	    	   if(Integer.parseInt(txvAdministradorList.getText().toString())==1)
	    	   {
	    	   
	    	   txvAdministradorList.setTextColor(Color.rgb(15, 145, 63));
	    	   txvAdministradorList.setText("Administrador");
	    	   }
	    	   else
	    	   {
		    	   txvAdministradorList.setTextColor(Color.rgb(255, 0, 0));
		    	   txvAdministradorList.setText("Operario");
		       }
	       return view;
		}
		
	}
	
	
}
