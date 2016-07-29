package com.example.kym;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.text.method.LinkMovementMethod;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
{
	public static String data;TextView txtUserName;TextView txtlogout;
	public static String email;
	GetData database;SQLiteDatabase db;
	ConnectionDetectore cd;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		ActionBar ab=getSupportActionBar();
		ab.setTitle("");
		ab.setIcon(R.drawable.actionbarlogo);
		txtUserName=(TextView)findViewById(R.id.txtUserName);
		txtlogout=(TextView)findViewById(R.id.txtLogout);
		txtlogout.setMovementMethod(LinkMovementMethod.getInstance());
		txtlogout.setTextColor(Color.BLUE);
		data = getIntent().getExtras().getString("Uname");
		email = getIntent().getExtras().getString("email");
		cd=new ConnectionDetectore(getApplicationContext());
		txtUserName.setText("Welcome :"+data);
		ContentValues valBal=new ContentValues();
		database=new GetData(this);
		db=database.getWritableDatabase();
		valBal.put(GetData.U_EMAIL, email);
		valBal.put(GetData.B_BAL, "300000");
		valBal.put(GetData.B_LEFT, "300000");
		valBal.put(GetData.B_PROFIT, "0");
		db.insert(GetData.TABLE_BALANCE, null, valBal);
	}
	public void logout(View v)
	{
		Intent i=new Intent(MainActivity.this, FrontActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		finish();
	}
	public void game(View v)
	{
		
			Intent i=new Intent(this, GameSplashActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}
	public void tutorial(View v)
	{
		Intent i=new Intent(MainActivity.this, TutorialActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}
	
	public void onBackPressed() 
	{
		
	}
}
