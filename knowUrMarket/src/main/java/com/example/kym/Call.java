package com.example.kym;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Call extends ActionBarActivity{

	Button b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences getData=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String s=getData.getString("lang", "E");
		if(s.contentEquals("E"))
			setContentView(R.layout.call);
		else if(s.contentEquals("H"))
		{
			setContentView(R.layout.callh);
		}
		else if(s.contentEquals("B"))
		{
			setContentView(R.layout.callb);
		}
		getSupportActionBar().setTitle("Buying Call Options");
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5999")));
		b=(Button)findViewById(R.id.nextcall);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
				Intent i=new Intent(getApplicationContext(),Class2.class);
				startActivity(i);
				}catch(ActivityNotFoundException e){
					e.printStackTrace();
				}
			}
		});
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
			Intent j=new Intent(getApplicationContext(), Call.class);
			startActivity(j);
		}
	}
}
