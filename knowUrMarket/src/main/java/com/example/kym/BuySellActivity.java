package com.example.kym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class BuySellActivity extends ActionBarActivity implements OnItemClickListener, android.view.View.OnClickListener, TextWatcher
{
	public static SlidingMenu mMenu;
	private ListView menuList;
	private CustomAdapter mAdapter;
	private List<String> entries;
	GetData database;SQLiteDatabase db;
	AutoCompleteTextView txtStock;TextView tNseName;TextView tNseCode;
	RadioButton rbLimit;RadioButton rbMarket;
	RadioGroup rgPrice;
	EditText quantity;
	int kl=0,pal=0;
	String em=MainActivity.email;
	String price="";
	TextView tBuy;
	String nse;
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
	int red[]=new int[50];
	HashMap<String, String> NseName=new HashMap<String,String>();
	HashMap<String, String> NseCode=new HashMap<String,String>();
	Button btnAction;EditText edPrice;
	int m=3;
	int y=0;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_sell);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		ActionBar ab=getSupportActionBar();
		ab.setTitle("");
		ab.setIcon(R.drawable.actionbarlogo);
		quantity=(EditText)findViewById(R.id.edQuantity);
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
	
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.menu_row1,R.id.txtAutoComp, STOCKS);
	txtStock=(AutoCompleteTextView)findViewById(R.id.autoCompleteStockNames);
	txtStock.addTextChangedListener(this);
	txtStock.setAdapter(adapter);
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
	NseCode.put(STOCKS[0], "INE012A01025");NseCode.put(STOCKS[1], "INE079A01024");NseCode.put(STOCKS[2], "INE021A01026");NseCode.put(STOCKS[3], "INE238A01026");NseCode.put(STOCKS[4], "INE917I01010");
	NseCode.put(STOCKS[5], "INE028A01013");NseCode.put(STOCKS[6], "INE257A01026");NseCode.put(STOCKS[7], "INE029A01011");NseCode.put(STOCKS[8], "INE397D01024");NseCode.put(STOCKS[9], "INE910H01017");
	NseCode.put(STOCKS[10], "INE059A01026");NseCode.put(STOCKS[11], "INE522F01014");NseCode.put(STOCKS[12], "INE271C01023");NseCode.put(STOCKS[13], "INE089A01023");NseCode.put(STOCKS[14], "INE129A01019");
	NseCode.put(STOCKS[15], "INE047A01013");NseCode.put(STOCKS[16], "INE860A01027");NseCode.put(STOCKS[17], "INE040A01026");NseCode.put(STOCKS[18], "INE158A01026");NseCode.put(STOCKS[19], "INE038A01020");
	NseCode.put(STOCKS[20], "INE030A01027");NseCode.put(STOCKS[21], "INE001A01036");NseCode.put(STOCKS[22], "INE154A01025");NseCode.put(STOCKS[23], "INE090A01013");NseCode.put(STOCKS[24], "INE043D01016");
	NseCode.put(STOCKS[25], "INE095A01012");NseCode.put(STOCKS[26], "INE009A01021");NseCode.put(STOCKS[27], "INE749A01030");NseCode.put(STOCKS[28], "INE237A01028");NseCode.put(STOCKS[29], "INE018A01030");
	NseCode.put(STOCKS[30], "INE326A01037");NseCode.put(STOCKS[31], "INE101A01026");NseCode.put(STOCKS[32], "INE585B01010");NseCode.put(STOCKS[33], "INE584A01023");NseCode.put(STOCKS[34], "INE733E01010");
	NseCode.put(STOCKS[35], "INE213A01029");NseCode.put(STOCKS[36], "INE752E01010");NseCode.put(STOCKS[37], "INE160A01014");NseCode.put(STOCKS[38], "INE002A01018");NseCode.put(STOCKS[39], "INE205A01025");
	NseCode.put(STOCKS[40], "INE062A01012");NseCode.put(STOCKS[41], "INE044A01036");NseCode.put(STOCKS[42], "INE467B01029");NseCode.put(STOCKS[43], "INE155A01022");NseCode.put(STOCKS[44], "INE245A01021");
	NseCode.put(STOCKS[45], "INE081A01012");NseCode.put(STOCKS[46], "INE669C01028");NseCode.put(STOCKS[47], "INE481G01011");NseCode.put(STOCKS[48], "INE854D01016");NseCode.put(STOCKS[49], "INE075A01022");
	tNseName=(TextView)findViewById(R.id.txtNseName);
	tNseCode=(TextView)findViewById(R.id.textViewNseCode);
	rgPrice=(RadioGroup)findViewById(R.id.radioGroupPrice);
	rbMarket=(RadioButton)findViewById(R.id.radioMarket);
	rbLimit=(RadioButton)findViewById(R.id.radioLimit);
	btnAction=(Button)findViewById(R.id.btnBuySell);
	edPrice=(EditText)findViewById(R.id.edPrice);
	edPrice.setEnabled(false);
	rgPrice.setOnCheckedChangeListener(new OnCheckedChangeListener() 
    {

     public void onCheckedChanged(RadioGroup group, int checkedId) 
     {
         if(rbMarket.isChecked())
         {
        	 edPrice.setEnabled(false);
        	 m=1;
         }
         else if(rbLimit.isChecked())
         {
        	 edPrice.setEnabled(true);
        	 m=0;
         }
      }
    });
	tBuy=(TextView)findViewById(R.id.textViewBuy);
	ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
	if(cd.isConnectingToInternet()==true)
	{
		
	}
	else
	{
		Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
	}
	}	
	
	@Override
	public void afterTextChanged(Editable arg0)
	{
		String x=NseName.get(txtStock.getText().toString());
		String x1=NseCode.get(txtStock.getText().toString());
		
		if(x==null)
		{
			tNseName.setText("");
			tNseCode.setText("");
		}
		else
		{
			tNseName.setText(x);
			tNseCode.setText(x1);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{	
	}
	public void onClickReset(View v)
	{
		Intent i=new Intent(this, BuySellActivity.class);
		startActivity(i);
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
	private class GetPrice extends AsyncTask<Void, Void, Void> 
	{
		 
	        @Override
	        protected void onPreExecute() 
	        {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(BuySellActivity.this);
	            pDialog.setMessage("Please wait...");
	            pDialog.setCancelable(false);
	            pDialog.show();
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0)
	        {
	        	String data,url;
	    		ServiceHandler sh = new ServiceHandler();
	        	url="http://finance.google.co.uk/finance/info?client=ig&q=NSE:"+nse;
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
	public void onClickBuy(View v)
	{
		ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
		if(cd.isConnectingToInternet()==false)
		{
			Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
		}
		else{
		y=0;
		//String data,url;
		//ServiceHandler sh = new ServiceHandler();
		JSONObject event_data;
		ContentValues valDetail=new ContentValues();
		ContentValues valPortfolio=new ContentValues();
		db=database.getWritableDatabase();
		String em=MainActivity.email;
		String q=quantity.getText().toString();
		nse=tNseName.getText().toString();
		String status="",action="",c_price="";
		
		/*url="http://finance.google.co.uk/finance/info?client=ig&q=NSE:"+nse;
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
		if(m==1)
		{
			status="executed";
		}
		else if(m==0)
		{
			status="pending";
			c_price=edPrice.getText().toString();
		}
		action="Buy";
		//Toast.makeText(this, em+" "+nse+" "+status+" "+q+" "+price+" "+c_price+" "+action, Toast.LENGTH_LONG).show();
		try{
		if(txtStock.getText().length()>0 && q.length()>0 && m!=3)
		{
			double v2;
			v2=convert(price);
			Log.i("buySell", v2+"inside update order "+price);
			int x1=Integer.parseInt(q);
			Log.i("buysellActivity", x1+"quantity"+q);
			if(m==0 && price.length() > 0)
			{
				double x = Double.parseDouble(c_price);
				String sea=em+nse;
				kl=checkO(sea);
				if(kl>=0)
				{
					Log.i("buySell", kl+"inside update order");
					Cursor jk=getOrderDetails();
					jk.moveToPosition(kl);
					double po=Double.parseDouble(jk.getString(3));
					int qo=Integer.parseInt(jk.getString(2));
					double e1=qo*po;
					double e2=x1*x;
					int k_p=x1+qo;
					double e=(e1+e2)/k_p;
					q=k_p+"";
					valDetail.put(GetData.O_QUATITY, q);
					valDetail.put(GetData.O_PRICE,e+"");
				}
				else
				{
					Log.i("buySell", kl+"inside insert order");
					valDetail.put(GetData.O_ID, em+nse);
					valDetail.put(GetData.O_STOCK, nse);
					valDetail.put(GetData.O_STATUS, status);
					valDetail.put(GetData.O_QUATITY, q);
					valDetail.put(GetData.M_PRICE, v2+"");
					valDetail.put(GetData.O_PRICE,c_price);
					valDetail.put(GetData.O_ACTION, action);
				}
			}
			else if(m==1)
			{
				double x =convert(price);
				String sea1=em+nse;
				pal=checkP(sea1);
				if(pal>=0)
				{
					Log.i("buySell", pal+"inside update Port");
					Cursor jk=getPortDetails();
					jk.moveToPosition(pal);
					double po=Double.parseDouble(jk.getString(4));
					int qo=Integer.parseInt(jk.getString(2));
					double e1=qo*po;//stored price
					double e2=x1*x;//current price
					int k_p=x1+qo;
					double e=(e1+e2)/k_p;
					q=k_p+"";
					valPortfolio.put(GetData.O_QUATITY, q);
					valPortfolio.put(GetData.P_BUYPRICE,e+"");
				}
				else
				{
					Log.i("buySell", pal+"inside insert Port");
					valPortfolio.put(GetData.O_ID, em+nse);
					valPortfolio.put(GetData.O_STOCK, nse);
					valPortfolio.put(GetData.O_QUATITY, q);
					valPortfolio.put(GetData.P_BUYPRICE, v2+"");
					valPortfolio.put(GetData.P_PROFIT, "0");
				}
			}
			else
			{
				y=1;
				Toast.makeText(this, "Price Value Required", Toast.LENGTH_LONG).show();
			}
		}
		else
		{
			y=1;
			AlertDialog.Builder s=new AlertDialog.Builder(this);
			s.setTitle("Errors").setMessage("Error in Values");
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
				s.show();
		}
		}catch(Exception e)
		{
			y=1;
			Toast.makeText(this, e.toString()+"Error in values", Toast.LENGTH_LONG).show();
		}
		if(y==0 && m==0)
		{
			double j=Integer.parseInt(q);
			double cost=j*Double.parseDouble(c_price);
			Cursor desp=getDetails();
			desp.moveToFirst();
			String h=desp.getString(2);
			desp.moveToNext();
			double bal_left=Double.parseDouble(h)-cost;
			if(bal_left>=0)
			{
				//update balance table
			ContentValues valB=new ContentValues();
			valB.put(GetData.B_LEFT,bal_left+"");
			db=database.getWritableDatabase();
			db.update(GetData.TABLE_BALANCE, valB, "u_email='"+em+"'", null);
			//update if already present in order book
			if(kl>=0)
			{
				db.update(GetData.TABLE_ORDERBOOK, valDetail, "o_id='"+(em+nse)+"'", null);
			}
			//insert in order book
			else
			{
				db.insert(GetData.TABLE_ORDERBOOK, null, valDetail);
			}
			AlertDialog.Builder s_od=new AlertDialog.Builder(this);
			s_od.setTitle("Successful").setMessage("Order Placed \n press Ok to visit OrderBook");
			s_od.setPositiveButton("OK",new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					Intent i=new Intent(BuySellActivity.this, OrderBookActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				}
			});
			s_od.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					return;
				}
			});
			s_od.show();
			}
			else
				Toast.makeText(this, "Not enough Balance", Toast.LENGTH_SHORT).show();
		}
		else if(y==0 && m==1)
		{
			double j=Integer.parseInt(q);
			double cost=j*convert(price);
			Cursor desp=getDetails();
			desp.moveToFirst();
			String h=desp.getString(2);
			desp.moveToNext();
			double bal_left=Double.parseDouble(h)-cost;
			if(bal_left>=0)
			{
				ContentValues valB=new ContentValues();
				valB.put(GetData.B_LEFT,bal_left+"");
				db=database.getWritableDatabase();
				//update balance table
				db.update(GetData.TABLE_BALANCE, valB, "u_email='"+em+"'", null);
				//update portfolio if exist
				if(pal>=0)
				{
					db.update(GetData.TABLE_PORTFOLIO, valPortfolio, "o_id='"+(em+nse)+"'", null);
				}
				else{
				//insert 
				db.insert(GetData.TABLE_PORTFOLIO, null, valPortfolio);
				}
				Intent resultIntent = new Intent(this, PortfolioActivity.class);
				PendingIntent resultPendingIntent =
			    PendingIntent.getActivity(
			    this,
			    0,
			    resultIntent,
			    PendingIntent.FLAG_UPDATE_CURRENT
			    );

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
			        getApplicationContext()).setSmallIcon(R.drawable.arrowst)
			        .setContentTitle("Order Executed")
			        .setContentText(nse+" "+q+" Shares bought")
			        .setContentIntent(resultPendingIntent).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
			
			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(0, mBuilder.build());
			AlertDialog.Builder s_p=new AlertDialog.Builder(this);
			s_p.setTitle("Successful").setMessage("Shares Bought \n press Ok to visit Portfolio");
			s_p.setPositiveButton("OK",new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					Intent i=new Intent(BuySellActivity.this,PortfolioActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				}
			});
			s_p.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					return;
				}
			});
			s_p.show();
			}
			else
				Toast.makeText(this, "Not enough Balance", Toast.LENGTH_SHORT).show();
		}
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
	
	public int checkP(String m1)
	{
		Cursor f=getPortDetails();
		f.moveToFirst();
		while(!f.isAfterLast())
		{
			if(f.getString(0).equals(m1))
				return f.getPosition();
			f.moveToNext();
		}
		return -999;
	}
	public int checkO(String m2)
	{
		Cursor f1=getOrderDetails();
		f1.moveToFirst();
		while(!f1.isAfterLast())
		{
			if(f1.getString(0).equals(m2))
				return f1.getPosition();
			f1.moveToNext();
		}
		return -999;
	}
	public Cursor getDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_BALANCE, new String[] {"u_email","b_bal","b_left","b_profit"},"u_email = '"+em+"'", null, null, null, null);
		//database.close();
		return cursor;
	}
	public Cursor getPortDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_PORTFOLIO, new String[] {"o_id","o_stock","o_quantity","p_profit","p_buyprice"},null, null, null, null, null);
		//database.close();
		return cursor;
	}
	public Cursor getOrderDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_ORDERBOOK, new String[] {"o_id","o_stock","o_quantity","o_price"},null, null, null, null, null);
		//database.close();
		return cursor;
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
