package com.example.kym;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class FrontActivity extends ActionBarActivity 
{
	TextView tReg;
	TextView tLog;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		ActionBar ab=getSupportActionBar();
		ab.setTitle("");
		ab.setIcon(R.drawable.actionbarlogo);
		tLog=(TextView)findViewById(R.id.txtLogin);
		tReg=(TextView)findViewById(R.id.txtReg);
		tLog.setMovementMethod(LinkMovementMethod.getInstance());
		tReg.setMovementMethod(LinkMovementMethod.getInstance());
	}
	public void login(View v)
	{
		Intent i=new Intent(FrontActivity.this, LoginActivity1.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}
	public void reg(View v)
	{
		Intent i=new Intent(FrontActivity.this, RegActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}
	public void tutorial(View v)
	{
		Intent i=new Intent(FrontActivity.this, TutorialActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}

}
