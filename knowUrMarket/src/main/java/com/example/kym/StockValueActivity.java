package com.example.kym;

import org.json.JSONException;
import org.json.JSONObject;



import android.R.integer;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class StockValueActivity extends ActionBarActivity 
{
	String stockName,em;
	TextView t1_1,t1_2,t1_3,t1_4,tPortName;
	GetData database;
	String data,url;
	String price="";int c=0;
	SQLiteDatabase db;
	double h2=0;
	private ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_value);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		ActionBar ab=getSupportActionBar();
		ab.setTitle("");
		ab.setIcon(R.drawable.actionbarlogo);
		t1_1=(TextView)findViewById(R.id.textV1_1);t1_2=(TextView)findViewById(R.id.textV1_2);
		t1_3=(TextView)findViewById(R.id.textV1_3);t1_4=(TextView)findViewById(R.id.textV1_4);
		tPortName=(TextView)findViewById(R.id.txtUT);
		stockName = getIntent().getExtras().getString("value");
		database=new GetData(StockValueActivity.this);
		db=database.getWritableDatabase();
		em=MainActivity.email;
		tPortName.setText(stockName);
		ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
		if(cd.isConnectingToInternet()==true)
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
		double stock_p=convert(price);
		Cursor desp=getDetails();
		desp.moveToFirst();
		while(!desp.isAfterLast())
		{
			String id=desp.getString(0);
			String stk=desp.getString(1);
			if(id.contains(em) && stk.equalsIgnoreCase(stockName))
			{
				String nse=desp.getString(4);
				String pro=desp.getString(3);
				String q=desp.getString(2);
				double i_p=Double.parseDouble(nse);
				Log.i("stockValue",i_p+": Buy price");
				Log.i("stockValue",stock_p+": current price");
				int q1=Integer.parseInt(q);
				double profit=q1*(stock_p-i_p);
				profit=Math.round(profit*100);
				profit=profit/100.0;
				Log.i("key",profit+": current profit");
				h2=q1*stock_p;
				if(profit!=Double.parseDouble(pro))
				{
					//update portfolio database
					ContentValues valp=new ContentValues();
					valp.put(GetData.P_PROFIT, profit+"");
					db.update(GetData.TABLE_PORTFOLIO, valp, "o_id='"+(em+stockName)+"'", null);
				}
				t1_1.setText(stockName);t1_2.setText(q);
				t1_3.setText(nse);
				if(profit > 0)
				{
					t1_4.setTextColor(Color.GREEN);
					t1_4.setText(profit+"");
				}
				else if(profit < 0)
				{
					t1_4.setTextColor(Color.RED);
					t1_4.setText(profit+"");
				}
				else
					t1_4.setText(profit+"");
			
			}
			desp.moveToNext();
		}
		desp.close();
		}
		else
		{
			
			Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
		}
	}	
	private class GetPrice extends AsyncTask<Void, Void, Void> 
	{
		private final ProgressDialog dialog = new ProgressDialog(StockValueActivity.this);
	        @Override
	        protected void onPreExecute() 
	        {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(StockValueActivity.this);
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
	public double convert(String x)
	{
		String news="";
		double pr;
		x=x.substring(3, x.length());
		int n=x.length();
		for(int i=0;i<n;i++)
		{ 
			if((x.charAt(i)>='0' && x.charAt(i)<='9')) 
			{ 
				news=news+x.charAt(i); 
			} 
			else if(x.charAt(i)=='.')
			{ 
				news=news+".";
			} 
			else 
				continue;
		}
		pr=Double.parseDouble(news);
		return pr;
	}
	public void doRefresh(View v)
	{
		ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
		if(cd.isConnectingToInternet()==true)
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
		double stock_p=convert(price);
		Cursor desp=getDetails();
		desp.moveToFirst();
		while(!desp.isAfterLast())
		{
			String id=desp.getString(0);
			String stk=desp.getString(1);
			if(id.contains(em) && stk.equalsIgnoreCase(stockName))
			{
				String nse=desp.getString(4);
				String pro=desp.getString(3);
				String q=desp.getString(2);
				double i_p=Double.parseDouble(nse);
				Log.i("stockValue",i_p+": Buy price");
				Log.i("stockValue",stock_p+": current price");
				int q1=Integer.parseInt(q);
				
				double profit=q1*(stock_p-i_p);
				profit=Math.round(profit*100);
				profit=profit/100.0;
				Log.i("key",profit+": current profit");
				h2=q1*stock_p;
				if(profit!=Double.parseDouble(pro))
				{
					//update database(portfolio)
					ContentValues valp=new ContentValues();
					valp.put(GetData.P_PROFIT, profit+"");
					db.update(GetData.TABLE_PORTFOLIO, valp, "o_id='"+(em+stockName)+"'", null);
				}
				t1_1.setText(stockName);t1_2.setText(q);
				t1_3.setText(nse);
				if(profit > 0)
				{
					t1_4.setTextColor(Color.GREEN);
					t1_4.setText(profit+"");
				}
				else if(profit < 0)
				{
					t1_4.setTextColor(Color.RED);
					t1_4.setText(profit+"");
				}
				else
					t1_4.setText(profit+"");
			
			}
			desp.moveToNext();
		}
		desp.close();
		}
		else
		{
			
			Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
		}
	}
	public void doClickSell(View v)
	{
		ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
		if(cd.isConnectingToInternet()==true)
		{
		
		AlertDialog.Builder sell=new AlertDialog.Builder(this);
		sell.setTitle("Are You Sure ?").setMessage("Press Yes to Continue");
		sell.setPositiveButton("YES",new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				double p2=Double.parseDouble(t1_4.getText().toString());
				Cursor desp=getBalDetails();
				desp.moveToFirst();
				String h=desp.getString(3);//total profit
				double net_p=p2+Double.parseDouble(h);
				double left=Double.parseDouble(desp.getString(2))+h2;
				//update balance database
				ContentValues valB=new ContentValues();
				valB.put(GetData.B_PROFIT,net_p+"");
				valB.put(GetData.B_LEFT, left+"");
				db=database.getWritableDatabase();
				db.update(GetData.TABLE_BALANCE, valB, "u_email='"+em+"'", null);
				//delete from portfolio 
				db.delete(GetData.TABLE_PORTFOLIO, "o_id = '"+(em+stockName)+"'",null);
				AlertDialog.Builder sell1=new AlertDialog.Builder(StockValueActivity.this);
				sell1.setTitle("Shares Sold").setMessage("Press Ok to Continue");
				sell1.setPositiveButton("OK",new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						Intent i=new Intent(StockValueActivity.this,PortfolioActivity.class);
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
				desp.close();
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
		else
		{
			stopService(new Intent(this,Service_Order.class));
			Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
		}
	}
	public Cursor getDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_PORTFOLIO, new String[] {"o_id","o_stock","o_quantity","p_profit","p_buyprice"},null, null, null, null, null);
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
