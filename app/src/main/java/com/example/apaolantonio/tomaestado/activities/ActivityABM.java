package com.example.apaolantonio.tomaestado.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.apaolantonio.tomaestado.R;

import clases.Empleado;
import clasesB.CommonInfo;
import model.EmpleadoDataBaseAdapter;
import watcher.LoginWatcher;

public class ActivityABM extends Activity {

	EmpleadoDataBaseAdapter dbHelper;
	EditText etxEmpleadoNombre ;
	EditText etxEmpleadoLegajo;
	EditText etxPass;
	CheckBox checkEmpleadoAdmin;
	Empleado emp=new Empleado();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abm);
		
		etxEmpleadoNombre = (EditText) findViewById(R.id.etxEmpleadoNombre);
		etxEmpleadoLegajo = (EditText) findViewById(R.id.etxEmpleadoLegajo);
		etxPass = (EditText) findViewById(R.id.etxPass);
		checkEmpleadoAdmin= (CheckBox) findViewById(R.id.checkEmpleadoAdmin);
		
		if(CommonInfo.MODIFICAR_EMPLEADO==1)
		{
			
			Button btnAgregarEmpleado = (Button) findViewById(R.id.btnAgregarEmpleado);
			btnAgregarEmpleado.setText("Modificar");
			Intent i = getIntent();
			Bundle r = i.getExtras();
			emp = (Empleado) r.getSerializable(CommonInfo.KEY_BUNDLE_EMPLEADO);
			etxEmpleadoNombre.setText(emp.getNombre());
			etxEmpleadoLegajo.setText(""+emp.getLegajo());
			etxPass.setText(emp.getPass());
			checkEmpleadoAdmin.setChecked(emp.isAdmin());
			
			
			
			//Toast.makeText(getApplicationContext(), ""+emp.isAdmin(), Toast.LENGTH_SHORT).show();
			
		}
		else
		{			
			setUpComponents();
			dbHelper= EmpleadoDataBaseAdapter.getInstance(this);
		}
		//dbHelper=EmpleadoDataBaseAdapter.getInstance(this);
		//setUpComponents();
	}
	
	private void setUpComponents() {
				
		etxEmpleadoNombre.addTextChangedListener(new LoginWatcher(etxEmpleadoNombre, "El usuario",5,20));
		etxEmpleadoLegajo.addTextChangedListener(new LoginWatcher(etxEmpleadoLegajo,"El usuario"," 5 d�gitos",5));
		etxPass.addTextChangedListener(new LoginWatcher(etxPass,"La contrase�a"," 8 caracteres",8));
		etxEmpleadoNombre.setText("");
		etxEmpleadoLegajo.setText("");
		etxPass.setText("");
	}
	
	public void btnAgregarEmpleado(View view)
	{
		//Toast.makeText(getApplicationContext(), "aceptar", Toast.LENGTH_SHORT).show();
		if(validarCampoNulo()==false)
		{
			dialogoMensaje("Error","Verifique que los campos est�n completos correctamente.");
		}
		else
		{
			
			emp.setNombre(etxEmpleadoNombre.getText().toString());
			emp.setLegajo(Integer.parseInt(etxEmpleadoLegajo.getText().toString()));
			emp.setPass(etxPass.getText().toString());
			emp.setAdmin(checkEmpleadoAdmin.isChecked());
			
					
			dbHelper.abrir();
			
			/*if(CommonInfo.MODIFICAR_EMPLEADO==1)
				{
				dbHelper.modificar(emp);
					Toast.makeText(getApplicationContext(), ""+emp.getNombre(), Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(), ""+emp.getLegajo(), Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(), ""+emp.getPass(), Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(), ""+emp.isAdmin(), Toast.LENGTH_SHORT).show();
					
					AlertDialog.Builder alert = new AlertDialog.Builder(this);
					
					alert.setTitle("�xito");
					alert.setMessage("El usuario "+ etxEmpleadoNombre.getText().toString().toUpperCase() +" fue modificado correctamente");
					alert.setPositiveButton("Aceptar", new OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent j = new Intent(getApplicationContext(),ActivityBM.class);
							startActivity(j);
							finish();
							
						}});
					
					alert.show();
				}

			else
			{ 
				Empleado emp= new Empleado();
				emp.setNombre(etxEmpleadoNombre.getText().toString());
				emp.setLegajo(Integer.parseInt(etxEmpleadoLegajo.getText().toString()));
				emp.setPass(etxPass.getText().toString());
				emp.setAdmin(checkEmpleadoAdmin.isChecked());
				dbHelper.agregar(emp);
				dialogoMensaje("�xito","El usuario "+ etxEmpleadoNombre.getText().toString().toUpperCase() +" fue agregado correctamente");
				setUpComponents();
				}*/
			dbHelper.agregar(emp);
			dbHelper.cerrar();
			dialogoMensaje("�xito","El usuario "+ etxEmpleadoNombre.getText().toString().toUpperCase() +" fue agregado correctamente");
			setUpComponents();
			Toast.makeText(getApplicationContext(), ""+emp.isAdmin(), Toast.LENGTH_SHORT).show();
		}
	}
	
	
		private boolean validarCampoNulo()
		{
			return (etxEmpleadoNombre.getError() == null && 
					etxPass.getError() == null &&
					etxEmpleadoLegajo.getError()==null);
		}
	
		public void dialogoMensaje(String y, String x)
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			
			alert.setTitle(y);
			alert.setMessage(x);
			alert.setPositiveButton("Aceptar", null);
			
			alert.show();
			 
		}

}
