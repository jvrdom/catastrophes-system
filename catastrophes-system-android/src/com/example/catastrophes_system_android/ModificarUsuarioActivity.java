package com.example.catastrophes_system_android;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.widget.DatePicker;

public class ModificarUsuarioActivity extends Activity implements OnClickListener {
	
	private int Day = 1;
	private int Month = 0;
	private int Year = 1980;
	private ImageButton BtnCalendario;
	private EditText FechaNacimiento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modificar_usuario);
		
		FechaNacimiento = (EditText) findViewById(R.id.FechaNacimiento);	
		BtnCalendario = (ImageButton) findViewById(R.id.BtnCalendario);					
		BtnCalendario.setOnClickListener(this);		
		
		final TextView Nombre = (TextView) findViewById(R.id.Nombre);
		final TextView Apellido = (TextView) findViewById(R.id.Apellido);
		final TextView Email = (TextView) findViewById(R.id.Email);
		final TextView Telefono = (TextView) findViewById(R.id.Telefono);
		final TextView Usuario = (TextView) findViewById(R.id.Usuario);
		final TextView Password = (TextView) findViewById(R.id.Password);
		Button BtnCancelar = (Button) findViewById(R.id.Cancelar);
		Button BtnModificar = (Button) findViewById(R.id.Modificar);	
		
		// Tengo Que Llamar A La Operacion Que Me Da Los Datos Del Usuario
		// Seteo Los Datos Del Usuario
		Nombre.setText("Mi Nombre");
		Apellido.setText("Mi Apellido");
		Email.setText("Mi Email");
		Telefono.setText("Mi Telefono");
		FechaNacimiento.setText("01 / 01 / 1980");
		Usuario.setText("Mi Usuario");
		Password.setText("Mi Password");		
		
		BtnCancelar.setOnClickListener(new OnClickListener() {				
			@Override public void onClick(View v) {				
				Intent intent = new Intent(ModificarUsuarioActivity.this, MenuPrincipalActivity.class);
				startActivity(intent);
			}
		});
		
		BtnModificar.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {								
				// Obtengo Los Datos
				Toast mensaje;
				String ModNombre = Nombre.getText().toString();
				String ModApellido = Apellido.getText().toString();
				String ModEmail = Email.getText().toString();
				String ModTelefono = Telefono.getText().toString();
				String ModUsuario = Usuario.getText().toString();
				String ModPassword = Password.getText().toString();
				// Hago Las Validaciones 
				if(ModNombre.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Nombre",Toast.LENGTH_SHORT); }
				else if(ModApellido.isEmpty()){	mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Apellido",Toast.LENGTH_SHORT);	}
				else if(ModEmail.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Email",Toast.LENGTH_SHORT); }
				else if(ModTelefono.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Tu Telefono",Toast.LENGTH_SHORT); }
				else if(ModUsuario.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Un Nombre De Usuario",Toast.LENGTH_SHORT); }
				else if(ModPassword.isEmpty()){ mensaje = Toast.makeText(getApplicationContext(),"Ingresa Una Contraseña",Toast.LENGTH_SHORT); }
				else{ 
					// LLamo A La Operacion Que Modifica Los Datos Del Usuario
					mensaje = Toast.makeText(getApplicationContext(),"Datos Actualizados Correctamente",Toast.LENGTH_SHORT); 
					Intent intent = new Intent(ModificarUsuarioActivity.this, MenuPrincipalActivity.class);
					startActivity(intent);
				}
				mensaje.setGravity(Gravity.CENTER_VERTICAL,0,240); 	
				mensaje.show();				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro_usuario, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}	
	
	@Override public void onClick(View v) {
	  showDialog(0);
	}

	@Override
	@Deprecated  protected Dialog onCreateDialog(int id) {
	  return new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog_NoActionBar, datePickerListener, Year, Month, Day);
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
	    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
	       	String StrDay = Integer.toString(selectedDay);
	    	String StrMonth = Integer.toString(selectedMonth + 1);
	    	String StrYear = Integer.toString(selectedYear);	    	
	    	if(selectedDay<10) StrDay = "0"+StrDay;	    
	    	if(selectedMonth<9) StrMonth = "0"+StrMonth;		    	
	    	FechaNacimiento.setText(StrDay + " / " + StrMonth + " / " + StrYear);
	    }
	};		
}
