package com.example.kym;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class TradeActivity extends ActionBarActivity
{
	String stockName,em;
	GetData database;
	String data,url;
	TextView t1_1,t1_2,t1_3,t1_4,t1_5,t1_6,tStockName,tOP;
	Button btnR,btnD;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		ActionBar ab=getSupportActionBar();
		ab.setTitle("");
		ab.setIcon(R.drawable.actionbarlogo);
		em=MainActivity.email;
		stockName = getIntent().getExtras().getString("value");
		database=new GetData(this);
		t1_1=(TextView)findViewById(R.id.txt1_1);t1_2=(TextView)findViewById(R.id.txt1_2);
		t1_3=(TextView)findViewById(R.id.txt1_3);t1_4=(TextView)findViewById(R.id.txt1_4);
		t1_5=(TextView)findViewById(R.id.txt1_5);t1_6=(TextView)findViewById(R.id.txt1_6);
		tStockName=(TextView)findViewById(R.id.txtStockName);tOP=(TextView)findViewById(R.id.txtOP);
		btnR=(Button)findViewById(R.id.btnR);
		btnD=(Button)findViewById(R.id.btnD);
		tStockName.setText(stockName);
		Cursor desp=getDetails();
		desp.moveToFirst();
		while(!desp.isAfterLast())
		{
			String id=desp.getString(0);
			String stk=desp.getString(1);
			if(id.contains(em) && stk.equalsIgnoreCase(stockName))
			{
				String nse=desp.getString(3);
				String q=desp.getString(2);
				tOP.setText("Executed Price :");
				t1_1.setText(stockName);t1_2.setText(nse);
				t1_3.setText(nse);t1_4.setText("executed");
				t1_5.setText(q);t1_6.setText("Buy");
			
			}
			desp.moveToNext();
		}
		btnR.setVisibility(View.INVISIBLE);btnD.setVisibility(View.INVISIBLE);
		ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
		if(cd.isConnectingToInternet()==true)
		{
			
		}
		else
		{
			
			Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
		}
		}	
	public Cursor getDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_PORTFOLIO, new String[] {"o_id","o_stock","o_quantity","p_buyprice"},null, null, null, null, null);
		//database.close();
		return cursor;
	}
}
