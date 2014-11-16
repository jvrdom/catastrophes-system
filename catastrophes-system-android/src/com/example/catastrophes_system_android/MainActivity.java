package com.example.catastrophes_system_android;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
	
		Button BtnMenuLogin = (Button) findViewById(R.id.MenuLogin);
		Button BtnMenuRegistro = (Button) findViewById(R.id.MenuRegistro);
		
		BtnMenuLogin.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		
		BtnMenuRegistro.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {				
				Intent intent = new Intent(MainActivity.this, RegistroUsuarioActivity.class);
				startActivity(intent);
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
