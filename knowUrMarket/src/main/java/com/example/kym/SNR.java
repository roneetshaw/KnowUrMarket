package com.example.kym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class SNR extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences getData=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String s=getData.getString("lang", "E");
		if(s.contentEquals("E"))
			setContentView(R.layout.snr);
		else if(s.contentEquals("H"))
		{
			setContentView(R.layout.snrh);
		}
		else if(s.contentEquals("B"))
		{
			setContentView(R.layout.snrb);
		}
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5999")));
		getSupportActionBar().setTitle("Support & Resistance");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tutorial, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.Language) {
			Intent i=new Intent(getApplicationContext(),Prefs.class);
			startActivityForResult(i, 0);
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		{
			Intent j=new Intent(getApplicationContext(), SNR.class);
			startActivity(j);
		}
	}
}
