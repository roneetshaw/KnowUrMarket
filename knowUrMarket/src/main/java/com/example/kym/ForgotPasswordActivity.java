package com.example.kym;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class ForgotPasswordActivity extends ActionBarActivity 
{
	EditText edEmail;
	GetData database;
	String p;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		ActionBar ab=getSupportActionBar();
		ab.setTitle("");
		ab.setIcon(R.drawable.actionbarlogo);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		database=new GetData(ForgotPasswordActivity.this);
		edEmail=(EditText)findViewById(R.id.editTextMobileNumber);
	}
	public void onClickConfirm(View v)
	{
		String pass="";
		p=edEmail.getText().toString();
		
		Cursor cd=getDetails();
		if(cd.moveToFirst())
		{
			pass=cd.getString(1);
			ContentValues values = new ContentValues();
			values.put("address", "KnowYourMarket");
			values.put("body", "Your KnowYourMarket Password is "+pass);
			getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
			cd.close();
			Intent intent = new Intent("android.intent.action.MAIN");
			intent.setComponent(new ComponentName("com.android.mms","com.android.mms.ui.ConversationList"));
			startActivity(intent);
		}
		else
			Toast.makeText(this, "Invalid Email-ID ", Toast.LENGTH_LONG).show();
	}
	public Cursor getDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_INFO, new String[] {"u_email","password"},"u_email like "+"'"+p+"'", null, null, null, null);
		//database.close();
		return cursor;
	}
}
