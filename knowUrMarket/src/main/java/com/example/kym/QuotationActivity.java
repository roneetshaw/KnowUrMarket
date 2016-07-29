package com.example.kym;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Filter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.os.Build;

public class QuotationActivity extends ActionBarActivity implements OnItemClickListener, android.view.View.OnClickListener, TextWatcher
{
	public static SlidingMenu mMenu;
	private ListView menuList;
	private CustomAdapter mAdapter;
	private List<String> entries;
	GetData database;
	String x;String price,data,id,prev,st,perc,ch,ch_fix,u;
	AutoCompleteTextView txtQuotationStock;
	TextView tq1,tq2,tq3,tq4,tq5,tq6,txtChange,txtBchange,txtUpdated;
	ImageView imgChange;
	private ProgressDialog pDialog;
	private static final String[] STOCKS = new String[] {"ACC Ltd.", "Ambuja Cements Ltd.", "Asian Paints Ltd.", "Axis Bank Ltd.", "Bajaj Auto Ltd.","Bank of Baroda", "Bharat Heavy Electricals Ltd.", 
		"Bharat Petroleum Corporation Ltd.", "Bharti Airtel Ltd.", "Cairn India Ltd.",
		"Cipla Ltd.","Coal India Ltd.","DLF Ltd.","Dr. Reddy's Laboratories Ltd.","GAIL (India) Ltd.","Grasim Industries Ltd.","HCL Technologies Ltd.","HDFC Bank Ltd.",
		"Hero MotoCorp Ltd.","Hindalco Industries Ltd.","Hindustan Unilever Ltd.","Housing Development Finance Corporation Ltd.",
		"ITC Ltd.","ICICI Bank Ltd.","IDFC Ltd.","IndusInd Bank Ltd.","Infosys Ltd.","Jindal Steel & Power Ltd.",
		"Kotak Mahindra Bank Ltd.","Larsen & Toubro Ltd.","Lupin Ltd.",
		"True Ltd.","Maruti Suzuki India Ltd.","NMDC Ltd.",
		"NTPC Ltd.","Oil & Natural Gas Corporation Ltd.","Power Grid Corporation of India Ltd.",
		"Punjab National Bank","Reliance Industries Ltd.","Sesa Sterlite Ltd.",
		"State Bank of India","Sun Pharmaceutical Industries Ltd.","Tata Consultancy Services Ltd.",
		"Tata Motors Ltd.","Tata Power Co. Ltd.","Tata Steel Ltd.","Tech Mahindra Ltd.",
		"UltraTech Cement Ltd.","United Spirits Ltd.","Wipro Ltd."};
	HashMap<String, String> NseName=new HashMap<String,String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quotation);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		ActionBar ab=getSupportActionBar();
		ab.setTitle("");
		ab.setIcon(R.drawable.actionbarlogo);
		View v = this.getLayoutInflater().inflate(R.layout.menu_list_1, null, false);
		menuList = (ListView) v.findViewById(R.id.listUser);
		//create and attach menu
		mMenu = new SlidingMenu(this);
		mMenu.setMode(SlidingMenu.LEFT);
		mMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mMenu.setShadowWidthRes(R.dimen.shadow_width);
		mMenu.setShadowDrawable(R.drawable.shadow);
		mMenu.setBehindWidthRes(R.dimen.slide_width);
		mMenu.setFadeDegree(0.35f);
		mMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		//set menu layout
		mMenu.setMenu(v);
		database=new GetData(this);
		entries = new ArrayList<String>();
		entries.add("Home");
		entries.add("Balance");
		entries.add("Buy Shares");
		entries.add("OrderBook");
		entries.add("TradeBook");
		entries.add("Portfolio");
		entries.add("Quotation");
		entries.add("Nifty 50");
		entries.add("Currency");
		entries.add("Market Watch");
		entries.add("Reports");
		entries.add("Hot Picks");
		entries.add("About Us");
		
	//create and set menu Adapter
	mAdapter = new CustomAdapter(entries, R.layout.menu_row, this);
	menuList.setAdapter(mAdapter);
	menuList.setOnItemClickListener(this);
	NseName.put(STOCKS[0], "ACC");NseName.put(STOCKS[1], "AMBUJACEM");NseName.put(STOCKS[2], "ASIANPAINT");NseName.put(STOCKS[3], "AXISBANK");NseName.put(STOCKS[4], "BAJAJ-AUTO");
	NseName.put(STOCKS[5], "BANKBARODA");NseName.put(STOCKS[6], "BHEL");NseName.put(STOCKS[7], "BPCL");NseName.put(STOCKS[8], "BHARTIARTL");NseName.put(STOCKS[9], "CAIRN");
	NseName.put(STOCKS[10], "CIPLA");NseName.put(STOCKS[11], "COALINDIA");NseName.put(STOCKS[12], "DLF");NseName.put(STOCKS[13], "DRREDDY");NseName.put(STOCKS[14], "GAIL");
	NseName.put(STOCKS[15], "GRASIM");NseName.put(STOCKS[16], "HCLTECH");NseName.put(STOCKS[17], "HDFCBANK");NseName.put(STOCKS[18], "HEROMOTOCO");NseName.put(STOCKS[19], "HINDALCO");
	NseName.put(STOCKS[20], "HINDUNILVR");NseName.put(STOCKS[21], "HDFC");NseName.put(STOCKS[22], "ITC");NseName.put(STOCKS[23], "ICICIBANK");NseName.put(STOCKS[24], "IDFC");
	NseName.put(STOCKS[25], "INDUSINDBK");NseName.put(STOCKS[26], "INFY");NseName.put(STOCKS[27], "JINDALSTEL");NseName.put(STOCKS[28], "KOTAKBANK");NseName.put(STOCKS[29], "LT");
	NseName.put(STOCKS[30], "LUPIN");NseName.put(STOCKS[31], "POWERGRID");NseName.put(STOCKS[32], "MARUTI");NseName.put(STOCKS[33], "NMDC");NseName.put(STOCKS[34], "NTPC");
	NseName.put(STOCKS[35], "ONGC");NseName.put(STOCKS[36], "POWERGRID");NseName.put(STOCKS[37], "PNB");NseName.put(STOCKS[38], "RELIANCE");NseName.put(STOCKS[39], "SSLT");
	NseName.put(STOCKS[40], "SBIN");NseName.put(STOCKS[41], "SUNPHARMA");NseName.put(STOCKS[42], "TCS");NseName.put(STOCKS[43], "TATAMOTORS");NseName.put(STOCKS[44], "TATAPOWER");
	NseName.put(STOCKS[45], "TATASTEEL");NseName.put(STOCKS[46], "TECHM");NseName.put(STOCKS[47], "ULTRACEMCO");NseName.put(STOCKS[48], "MCDOWELL-N");NseName.put(STOCKS[49], "WIPRO");
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.menu_row1,R.id.txtAutoComp, STOCKS);
	txtQuotationStock=(AutoCompleteTextView)findViewById(R.id.autoCompleteQuotation);
	txtQuotationStock.addTextChangedListener(this);
	txtQuotationStock.setAdapter(adapter);
	tq1=(TextView)findViewById(R.id.textViewQ1);tq2=(TextView)findViewById(R.id.textViewQ2);
	tq3=(TextView)findViewById(R.id.textViewQ3);tq4=(TextView)findViewById(R.id.textViewQ4);
	tq5=(TextView)findViewById(R.id.textViewQ5);tq6=(TextView)findViewById(R.id.textViewQ6);
	txtChange=(TextView)findViewById(R.id.textViewQChange);txtBchange=(TextView)findViewById(R.id.textViewQBigPrice);
	imgChange=(ImageView)findViewById(R.id.imageViewChange);txtUpdated=(TextView)findViewById(R.id.textViewUpdateAt);
	ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
	if(cd.isConnectingToInternet()==true)
	{
		
	}
	else
	{
		
		Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
	}
	}	
	public void doNew(View v)
	{
		Intent i=new Intent(this,QuotationActivity.class);
		startActivity(i);
	}
	public void doRefresh(View v)
	{
		new GetDetails().execute();
		tq1.setText(st);tq2.setText(price);
		tq3.setText(prev);tq4.setText(id);
		tq6.setText(perc);txtChange.setText(ch);
		txtBchange.setText(price);
	
		if(convert(ch_fix) ==true)
		{
			imgChange.setImageResource(R.drawable.arrowgreen);
		}
		else
			imgChange.setImageResource(R.drawable.arrowred);
		txtUpdated.setText("Updated at :"+u);
	}
	public void doLoad(View v)
	{
		/*Intent i=new Intent(this,QuotationActivity.class);
		startActivity(i);*/
		new GetDetails().execute();
		tq1.setText(st);tq2.setText(price);
		tq3.setText(prev);tq4.setText(id);
		tq6.setText(perc);txtChange.setText(ch);
		txtBchange.setText(price);
	
		if(convert(ch_fix) ==true)
		{
			imgChange.setImageResource(R.drawable.arrowgreen);
		}
		else
			imgChange.setImageResource(R.drawable.arrowred);
		txtUpdated.setText("Updated at :"+u);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Intent i;
		if(position==0)
		{
			i= new Intent(this, GameActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==1)
		{
			i= new Intent(this, BalanceActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==2)
		{
			i= new Intent(this, BuySellActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==3)
		{
			i= new Intent(this, OrderBookActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==4)
		{
			i= new Intent(this, TradeBookActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==5)
		{
			i= new Intent(this, PortfolioActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==6)
		{
			i= new Intent(this, QuotationActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==7)
		{
			i= new Intent(this, NiftyActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==8)
		{
			i= new Intent(this, CurrenyActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==9)
		{
			i= new Intent(this, MarketWatchActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==10)
		{
			i= new Intent(this, ReportActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==11)
		{
			i= new Intent(this, HotPicksActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else if(position==12)
		{
			i= new Intent(this, AboutUsActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		
		//handle ActionBar title click
		case android.R.id.home: 
			mMenu.toggle(true);
			break;
		}
		return false;
	}
	@Override
	public void onClick(View v) 
	{
		
		
	}
	@Override
	public void afterTextChanged(Editable arg0)
	{
		x=NseName.get(txtQuotationStock.getText().toString());
		Log.i("kym", x+" :value");
		if(x==null)
		{}
		else
		{
			
			/*new GetPrice().execute();
			new GetPrice().execute();
			new GetPrice().execute();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
			if(cd.isConnectingToInternet()==false)
			{
				Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
			}
			else
			{
			/*String url="http://finance.google.co.uk/finance/info?client=ig&q=NSE:"+x;
            ServiceHandler sh = new ServiceHandler();
            data = sh.makeServiceCall(url, ServiceHandler.GET);
    		JSONObject jsonObj;
    		try 
    		{
    			jsonObj = new JSONObject(data);
    			price=jsonObj.getString("l_cur");
    			id=jsonObj.getString("id");
    			st=jsonObj.getString("t");
    			prev=jsonObj.getString("pcls_fix");
    			perc=jsonObj.getString("cp");
    			ch=jsonObj.getString("c");
    			ch_fix=jsonObj.getString("c_fix");
    			u=jsonObj.getString("ltt");
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} */
		}
		}
	}
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) 
	{
		
		
	}
	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) 
	{
		
		
	}
	public boolean convert(String x1)
	{
		Log.i("kym", x1+" :value in convert");
		char ch=' ';
		try{
		ch=x1.charAt(0);}catch(Exception e){}
		if(ch=='-')
			return false;
		else if(ch=='+')
			return true;
		else
			return true;
	}
	private class GetDetails extends AsyncTask<Void, Void, Void> 
	{
		 
	        @Override
	        protected void onPreExecute() 
	        {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(QuotationActivity.this);
	            pDialog.setMessage("Please wait...");
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0)
	        {
	        	String url="http://finance.google.co.uk/finance/info?client=ig&q=NSE:"+x;
	            ServiceHandler sh = new ServiceHandler();
	            data = sh.makeServiceCall(url, ServiceHandler.GET);
	    		JSONObject jsonObj;
	    		try 
	    		{
	    			jsonObj = new JSONObject(data);
	    			price=jsonObj.getString("l_cur");
	    			id=jsonObj.getString("id");
	    			st=jsonObj.getString("t");
	    			prev=jsonObj.getString("pcls_fix");
	    			perc=jsonObj.getString("cp");
	    			ch=jsonObj.getString("c");
	    			ch_fix=jsonObj.getString("c_fix");
	    			u=jsonObj.getString("ltt");
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
	            synchronized (this)
	            {
	                this.notifyAll(); // this call will work
	            }
	            
	        }
	 
	}

}
