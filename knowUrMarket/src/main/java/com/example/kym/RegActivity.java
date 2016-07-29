package com.example.kym;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.os.Build;

public class RegActivity extends ActionBarActivity 
{
	EditText edName;EditText edEmail;EditText phone;EditText pass;EditText ConPas;
	GetData database;SQLiteDatabase db;
	String nm;String mail;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		ActionBar ab=getSupportActionBar();
		ab.setTitle("");
		ab.setIcon(R.drawable.actionbarlogo);
		edName=(EditText)findViewById(R.id.edName);edEmail=(EditText)findViewById(R.id.edEmail);
		pass=(EditText)findViewById(R.id.edPass);ConPas=(EditText)findViewById(R.id.edCpass);
		phone=(EditText)findViewById(R.id.edMobile);
		database=new GetData(RegActivity.this);
	}
	public void doReg(View v)
	{
		String err=" ";int c=0;
		ContentValues valInfo=new ContentValues();
		db=database.getWritableDatabase();
		String ph,p,cp;
		mail=edEmail.getText().toString();nm=edName.getText().toString();
		ph=phone.getText().toString();p=pass.getText().toString();
		cp=ConPas.getText().toString();
		if(nm.length()==0)
		{
			c++;
			err=err+c+" . Name missing.\n ";
		}
		else
		{
			valInfo.put(GetData.U_NAME, nm);
		}
		if(mail.length()==0)
		{
			c++;
			err=err+c+" . Email missing.\n ";
		}
		else
		{
			valInfo.put(GetData.U_EMAIL, mail);
		}
		if(p.length()==0)
		{
			c++;
			err=err+c+" . Password Empty.\n ";
		}
		else
		{
			
		}
		if(cp.length()==0)
		{
			c++;
			err=err+c+" . Password did not match.\n ";
		}
		else
		{
			
		}
		if(cp.compareTo(p)!=0)
		{
			c++;
			err=err+c+" . Password did not match.\n ";
		}
		else
		{
			valInfo.put(GetData.PASSWORD, p);
		}
		if(ph.length()==0)
		{
			c++;
			err=err+c+" . mobile no. missing.\n ";
		}
		else
		{
			valInfo.put(GetData.PHONE, ph);
		}
		AlertDialog.Builder s=new AlertDialog.Builder(this);
		s.setTitle("Errors").setMessage("Fix the Following");
		final TextView value=new TextView(this);
		s.setView(value);
		if (Build.VERSION.SDK_INT >= 11)
		{
			value.setTextColor(Color.BLACK);
		}
		else
			value.setTextColor(Color.WHITE);
		value.setText(err);
		s.setPositiveButton("OK",new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				return;
			}
		});
		s.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				return;
			}
		});
		if(c>0)
			s.show();
		else
		{
			db.insert(GetData.TABLE_INFO, null, valInfo);
			AlertDialog.Builder s1=new AlertDialog.Builder(this);
			s1.setTitle("Congratulation").setMessage("Registration Successfull");
			final TextView value1=new TextView(this);
			s1.setView(value);
			if (Build.VERSION.SDK_INT >= 11)
			{
				value.setTextColor(Color.BLACK);
			}
			else
				value.setTextColor(Color.WHITE);
			value.setText("Press OK to Continue");
			s1.setPositiveButton("OK",new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					Intent i=new Intent(RegActivity.this, MainActivity.class);
					i.putExtra("Uname", nm);
					i.putExtra("email", mail);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				}
			});
			s1.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					return;
				}
			});
			s1.show();
		}
		database.close();
		db.close();
	}
	
}
