package com.dvdsantana.dpsoptimizer;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText power;
	private EditText critChance;
	private EditText critDamage;
	private EditText results;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.power = (EditText)findViewById(R.id.power);
		this.critChance = (EditText)findViewById(R.id.critChance);
		this.critDamage = (EditText)findViewById(R.id.critDamage);
		this.results = (EditText)findViewById(R.id.results);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void cleanForm(View view) {
		this.power.setText("");
		this.critChance.setText("");
	    this.critDamage.setText("");
	    this.results.setText("");
	}
	
	public void calculate(View view) {
		String power = this.power.getText().toString();
		String critChance = this.critChance.getText().toString();
		String critDamage = this.critDamage.getText().toString();
		
		this.results.setText(power + critChance + critDamage);
	}

}
