package com.example.kym;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Bollinger extends ActionBarActivity implements OnClickListener{

	Button play,breaking,vol;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences getData=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String s=getData.getString("lang", "E");
		if(s.contentEquals("E"))
			setContentView(R.layout.bollinger);
		else if(s.contentEquals("H"))
		{
			setContentView(R.layout.bollingerh);
		}
		else if(s.contentEquals("B"))
		{
			setContentView(R.layout.bollingerb);
		}
		ImageView iv=(ImageView)findViewById(R.id.iv2);
		iv.setImageResource(R.drawable.bb);
		play=(Button)findViewById(R.id.playing);
		breaking=(Button)findViewById(R.id.breakout);
		vol=(Button)findViewById(R.id.volatility);
		play.setOnClickListener(this);
		breaking.setOnClickListener(this);
		vol.setOnClickListener(this);
		getSupportActionBar().setTitle("Bollinger Bands");
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent bol = null;
		switch(v.getId())
		{
		case R.id.playing:
			bol=new Intent(getApplicationContext(), Playing.class);
			break;
		case R.id.breakout:
			 bol=new Intent(getApplicationContext(), Breakout.class);
			break;
		case R.id.volatility:
			 bol=new Intent(getApplicationContext(), Volatile.class);
			break;
		}
		startActivity(bol);
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
			Intent j=new Intent(getApplicationContext(), Bollinger.class);
			startActivity(j);
		}
	}
}
