package com.example.kym;

import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class OrderActivity extends ActionBarActivity 
{
	String stockName,em;
	GetData database;
	String data,url;
	SQLiteDatabase db;
	private ProgressDialog pDialog;
	TextView t1_1,t1_2,t1_3,t1_4,t1_5,t1_6,tStockName;
	String price="";
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
		db=database.getWritableDatabase();
		t1_1=(TextView)findViewById(R.id.txt1_1);t1_2=(TextView)findViewById(R.id.txt1_2);
		t1_3=(TextView)findViewById(R.id.txt1_3);t1_4=(TextView)findViewById(R.id.txt1_4);
		t1_5=(TextView)findViewById(R.id.txt1_5);t1_6=(TextView)findViewById(R.id.txt1_6);
		tStockName=(TextView)findViewById(R.id.txtStockName);
		tStockName.setText(stockName);
		Cursor desp=getDetails();
		desp.moveToFirst();
		while(!desp.isAfterLast())
		{
			String id=desp.getString(0);
			String s=desp.getString(2);
			String stk=desp.getString(1);
			if(id.contains(em) && s.equals("pending") && stk.equalsIgnoreCase(stockName))
			{
				//String nse=desp.getString(1);
				t1_1.setText(stockName);t1_2.setText(desp.getString(4));
				t1_3.setText(desp.getString(5));t1_4.setText("pending");
				t1_5.setText(desp.getString(3));t1_6.setText(desp.getString(6));
			
			}
			desp.moveToNext();
		}
		ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
		if(cd.isConnectingToInternet()==true)
		{
			
		}
		else
		{
			Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
		}
		}	
	public void onClickDel(View v)
	{
		ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
		if(cd.isConnectingToInternet()==false)
		{
			Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
		}
		else
		{
		AlertDialog.Builder sell=new AlertDialog.Builder(this);
		sell.setTitle("Are You Sure ?").setMessage("Press Yes to Continue");
		sell.setPositiveButton("YES",new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				//update balance
				Cursor d=getBalDetails();
				if(d.moveToFirst())
				{
					double n=Double.parseDouble(d.getString(2));
					Cursor hj=getDetails();
					hj.moveToFirst();
					int l=Integer.parseInt(hj.getString(3));
					double p=Double.parseDouble(hj.getString(4));
					double pl=l*p;
					double o=pl+n;
					ContentValues valp=new ContentValues();
					valp.put(GetData.B_LEFT, o+"");
					db.update(GetData.TABLE_BALANCE, valp, "u_email ='"+em+"'", null);
					hj.close();
				}
				d.close();
				//delete from orderbook
				db.delete(GetData.TABLE_ORDERBOOK, "o_id = '"+(em+stockName)+"'",null);
				AlertDialog.Builder sell1=new AlertDialog.Builder(OrderActivity.this);
				sell1.setTitle("Order Removed").setMessage("Press Ok to Continue");
				sell1.setPositiveButton("OK",new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						Intent i=new Intent(OrderActivity.this,OrderBookActivity.class);
						startActivity(i);
					}
				});
				sell1.setNegativeButton("Cancel",new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						return;
					}
				});
				sell1.show();
			}
		});
		sell.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				return;
			}
		});
		sell.show();
		}
	}
	private class GetPrice extends AsyncTask<Void, Void, Void> 
	{
		
	        @Override
	        protected void onPreExecute() 
	        {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(OrderActivity.this);
	            pDialog.setMessage("Please wait...");
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0)
	        {
	        	ServiceHandler sh = new ServiceHandler();
	    		url="http://finance.google.co.uk/finance/info?client=ig&q=NSE:"+stockName;
	    		data = sh.makeServiceCall(url, ServiceHandler.GET);
	    		JSONObject jsonObj;
	    		try 
	    		{
	    			jsonObj = new JSONObject(data);
	    			price=jsonObj.getString("l_cur");
	    		} catch (JSONException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		return null;
	        }
	 
	        @Override
	        protected void onPostExecute(Void result)
	        {
	            super.onPostExecute(result);
	            if (pDialog.isShowing())
	                pDialog.dismiss();
	        }
	 
	}	
	public void doRefresh(View v)
	{
		ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
		if(cd.isConnectingToInternet()==false)
		{
			Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
		}
		else
		{
					
		/*ServiceHandler sh = new ServiceHandler();
		url="http://finance.google.co.uk/finance/info?client=ig&q=NSE:"+stockName;
		data = sh.makeServiceCall(url, ServiceHandler.GET);
		JSONObject jsonObj;
		
		try 
		{
			jsonObj = new JSONObject(data);
			price=jsonObj.getString("l_cur");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
			new GetPrice().execute();
			while(price.length()==0){}
		Cursor desp=getDetails();
		desp.moveToFirst();
		while(!desp.isAfterLast())
		{
			String id=desp.getString(0);
			String s=desp.getString(2);
			String stk=desp.getString(1);
			if(id.contains(em) && s.equals("pending") && stk.equalsIgnoreCase(stockName))
			{
				//String nse=desp.getString(1);
				t1_1.setText(stockName);t1_2.setText(desp.getString(4));
				t1_3.setText(price);t1_4.setText("pending");
				t1_5.setText(desp.getString(3));t1_6.setText(desp.getString(6));
			
			}
			desp.moveToNext();
		}
	}
	}
	public Cursor getDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_ORDERBOOK, new String[] {"o_id","o_stock","o_status","o_quantity","o_price","m_price","o_action"},null, null, null, null, null);
		//database.close();
		return cursor;
	}
	public Cursor getBalDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_BALANCE, new String[] {"u_email","b_bal","b_left","b_profit"},"u_email = '"+em+"'", null, null, null, null);
		//database.close();
		return cursor;
	}
}
