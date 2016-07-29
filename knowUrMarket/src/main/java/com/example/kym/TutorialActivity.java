package com.example.kym;

import java.io.IOException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class TutorialActivity extends ActionBarActivity
{
	Intent i=null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);
		GridView gv=(GridView)findViewById(R.id.GV);
		Bitmap x=BitmapFactory.decodeResource(null, R.drawable.blue);
		
		getSupportActionBar().setTitle("Market Tutorial:");
		getSupportActionBar().setSubtitle("Choose one:");
		 
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5999")));
		gv.setAdapter(new ImageAdapter(getApplicationContext()));
		 gv.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		            //Toast.makeText(TutorialActivity.this, "" + position, Toast.LENGTH_SHORT).show();
		            try{
		            switch(position)
		            {
		            case 3:
		            	i=new Intent(getApplicationContext(), Technical.class);
		            	startActivity(i);
		            	break;
		            
		            case 5:
		            	i=new Intent(getApplicationContext(), Candle.class);
		            	startActivity(i);
		            	break;
		            case 4:
		            	i=new Intent(getApplicationContext(), Chart.class);
		            	startActivity(i);
		            	break;
		            case 6:
		            	i=new Intent(getApplicationContext(), Option.class);
		            	startActivity(i);
		            	break;
		            case 0:
		            	i=new Intent(getApplicationContext(), History.class);
		            	startActivity(i);
		            	break;
		            case 1:
		            	i=new Intent(getApplicationContext(), Stock.class);
		            	startActivity(i);
		            	break;
		            case 2:
		            	i=new Intent(getApplicationContext(), Sebi.class);
		            	startActivity(i);
		            	break;
		            case 7:
		            	i=new Intent(getApplicationContext(), FAQs.class);
		            	startActivity(i);
		            	break;
		            }
		            }catch(Exception e){
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
			startActivity(i);
			
		}
		return super.onOptionsItemSelected(item);
	}

}
