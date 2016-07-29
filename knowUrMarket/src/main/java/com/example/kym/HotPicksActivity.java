package com.example.kym;

import java.util.ArrayList;
import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class HotPicksActivity extends ActionBarActivity implements OnItemClickListener, android.view.View.OnClickListener, Runnable
{
	ProgressBar pbHP;
	public static SlidingMenu mMenu;
	private ListView menuList;
	private CustomAdapter mAdapter;
	private List<String> entries;
	GetData database;
	WebView wbhotpick;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_picks);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		ActionBar ab=getSupportActionBar();
		ab.setTitle("");
		ab.setIcon(R.drawable.actionbarlogo);
		wbhotpick=(WebView)findViewById(R.id.webViewHotPicks);
		wbhotpick.getSettings().setJavaScriptEnabled(true);
		wbhotpick.loadUrl("file:///android_asset/hotpicks.html");
		wbhotpick.setBackgroundColor(0);
        wbhotpick.setBackgroundResource(R.drawable.finalbck);
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
	ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
	if(cd.isConnectingToInternet()==true)
	{
		
	}
	else
	{
		Toast.makeText(this, "No Internet Connection. Press Back to Continue", Toast.LENGTH_SHORT).show();
	}
	pbHP=(ProgressBar)findViewById(R.id.progressBarHP);
	new Thread(this).start();
	pbHP.setVisibility(View.VISIBLE);
	}	
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(3000);
			pbHP.setVisibility(View.INVISIBLE);
		}
		catch(Exception e){return;}
		
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
