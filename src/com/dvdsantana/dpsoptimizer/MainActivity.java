package com.dvdsantana.dpsoptimizer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private EditText ef_power;
	private EditText ef_critChance;
	private EditText ef_critDamage;
	private EditText ef_statPointTradeoff;
	private TextView tv_results;
	private TextView tv_recommendation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.ef_power = (EditText)findViewById(R.id.power);
		this.ef_critChance = (EditText)findViewById(R.id.critChance);
		this.ef_critDamage = (EditText)findViewById(R.id.critDamage);
		this.ef_statPointTradeoff = (EditText)findViewById(R.id.statPointTradeoff);
		this.tv_results = (TextView)findViewById(R.id.results);
		this.tv_recommendation = (TextView)findViewById(R.id.recommendation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void cleanForm(View view) {
		this.ef_power.setText("");
		this.ef_critChance.setText("");
	    this.ef_critDamage.setText("");
	    this.tv_results.setText("");
	    this.tv_recommendation.setText("");
	    this.ef_statPointTradeoff.setText("");
	}
	
	public void calculate(View view) {
		String value;
		boolean isOK = true;
		float power = 0;
		float critChance = 0;
		float critDamage = 0;
		float statPointTradeoff = 0;
		String required_msg = getResources().getString(R.string.required_field_error);
		
		value = this.ef_power.getText().toString();
		if (value.length() > 0) {
			power = Float.valueOf(value);
		} else {
			this.ef_power.setError(required_msg);
			isOK = false;
		}
		value = this.ef_critChance.getText().toString();
		if (value.length() > 0) {
			critChance = Float.valueOf(value);
		} else {
			this.ef_critChance.setError(required_msg);
			isOK = false;
		}
		value = this.ef_critDamage.getText().toString();
		if (value.length() > 0) {
			critDamage = Float.valueOf(value);
		} else {
			this.ef_critDamage.setError(required_msg);
			isOK = false;
		}
		value = this.ef_statPointTradeoff.getText().toString();
		if (value.length() > 0) {
			statPointTradeoff = Float.valueOf(value);
		} else {
			this.ef_statPointTradeoff.setError(required_msg);
			isOK = false;
		}
		
		if (isOK) {
			critDamage += 1.5;
			float f1 = dmgPerPower(critChance, critDamage);
		    float f2 = dmgPerPrecision(power, critDamage);
		    float f3 = statPointTradeoffPerCritDamage(power, critChance, statPointTradeoff);
		    float d = dmg(power, critChance, critDamage);
		    
		    StringBuilder log = new StringBuilder();
		    log.append(getResources().getString(R.string.damage_stats));
		    log.append("\n\n"+getResources().getString(R.string.damage_per_power)+f1);
		    log.append("\n"+getResources().getString(R.string.damage_per_precision)+ f2);
		    log.append("\n"+getResources().getString(R.string.damage_per_crit_chance) + (f2*21));
		    log.append("\n"+getResources().getString(R.string.statPointTradeoff_per_crit_damage) + f3);
		    log.append("\n"+getResources().getString(R.string.damage) + d);
		    
		    this.tv_results.setText(log);
		    
		    log.delete(0, log.length());
		    
		    log.append(getResources().getString(R.string.recommendation));

		    if (f3 > f1 && f3 > f2) {
		    	//Add crit damage at expense of power and/or precision
		    	log.append("\n\n"+getResources().getString(R.string.crit_power_prec));
		        if (f1 > f2) {
		        	//Prefer sacrificing precision before power
		        	log.append("\n"+getResources().getString(R.string.sacrificing_prec));
		        } else {
		        	//Prefer sacrificing power before precision
		        	log.append("\n"+getResources().getString(R.string.sacrificing_power));
		        }
		    } else if (f3 > f1) {
		    	//Add crit damage at expense of power
		    	log.append("\n"+getResources().getString(R.string.crit_power));
		    } else if (f3 > f2) {
		        //Add crit damage at expense of precision
		        log.append("\n"+getResources().getString(R.string.crit_prec));
		    } else {
		        //Do not add more crit damage
		        log.append("\n"+getResources().getString(R.string.no_crit));
		        if (f1 > f2) {
		            //Add more power before adding more precision
		            log.append("\n"+getResources().getString(R.string.more_prec));
		        } else {
		            //Add more precision before adding more power
		            log.append("\n"+getResources().getString(R.string.more_power));
		        }
		    }
		    log.append("\n"+getResources().getString(R.string.note) + statPointTradeoff);
		    log.append("\n"+getResources().getString(R.string.source));
		    
		    this.tv_recommendation.setText(log);

		    //log('power-to-critdamage delta', f1 - f3);
		    //log('precision-to-critdamage delta', f2 - f3);*/
		    
		}
	}
	
	protected float dmgPerPower(float cc, float cd) {
	    return 1 + cc * (cd - 1);
	}

	protected float dmgPerPrecision(float p, float cd) {
	    return (p * (cd - 1)) / 2100;
	}

	protected float statPointTradeoffPerCritDamage(float p, float cc, float spt) {
	    return p * cc / (spt * 100);
	}

	protected float dmg(float p, float cc, float cd) {
	    return p * (1 + cc * (cd - 1));
	}
}
