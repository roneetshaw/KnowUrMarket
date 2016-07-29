package com.example.kym;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class LoginActivity1 extends ActionBarActivity 
{
	EditText edLEmail;EditText edLPass;
	GetData database;
	String mail,p;
	TextView tfp;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_activity1);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		ActionBar ab=getSupportActionBar();
		ab.setTitle("");
		ab.setIcon(R.drawable.actionbarlogo);
		edLEmail=(EditText)findViewById(R.id.edLoginE);
		edLPass=(EditText)findViewById(R.id.edLoginPass);
		database=new GetData(LoginActivity1.this);
		tfp=(TextView)findViewById(R.id.textViewfp);
		tfp.setMovementMethod(LinkMovementMethod.getInstance());
	}
	public void onClickForgot(View v)
	{
		Intent i=new Intent(this,ForgotPasswordActivity.class);
		startActivity(i);
	}
	public void login(View v)
	{
		mail=edLEmail.getText().toString();
		p=edLPass.getText().toString();
		String n,n1;
		Cursor d=getDetails();
		int l=d.getCount();
		if(l==1)
		{
			d.moveToFirst();
			n=d.getString(1);
			n1=d.getString(0);
			Intent i=new Intent(LoginActivity1.this, MainActivity.class);
			i.putExtra("Uname", n);
			i.putExtra("email", n1);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else
			Toast.makeText(this, "Incorret UserName or Password", Toast.LENGTH_SHORT).show();
		d.close();
	}
	public Cursor getDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_INFO, new String[] {"u_email","u_name","password","phone"},"password like "+"'"+p+"' AND u_email like '"+mail+"'", null, null, null, null);
		//database.close();
		return cursor;
	}
}
