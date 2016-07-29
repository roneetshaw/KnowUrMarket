package com.example.kym;


import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

public class LaunchActivity extends Activity implements Runnable
{
	WebView wbL;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);
        wbL=(WebView)findViewById(R.id.webViewGIF);
        wbL.setVerticalScrollBarEnabled(false);
        wbL.setHorizontalScrollBarEnabled(false);
        wbL.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
              return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
          });
        String URL="splash.gif";
        String html = "<html><body><img src=\"" + URL + "\" width=\"100%\" height=\"100%\"\"/></body></html>";
        wbL.loadDataWithBaseURL("file:///android_asset/",html, "text/html","utf-8", null);
        wbL.getSettings().setJavaScriptEnabled(true);
        wbL.setBackgroundColor(0);
        wbL.setBackgroundResource(R.drawable.nkk);
        if (Build.VERSION.SDK_INT >= 11) // Android v3.0+
        	 try {
        	  Method method = View.class.getMethod("setLayerType", int.class, Paint.class);
        	  method.invoke(wbL, 1, new Paint()); // 1 = LAYER_TYPE_SOFTWARE (API11)
        	 } catch (Exception e) {
        	}
        //wbL.loadUrl("file:///android_asset/splash.gif");
        new Thread(this).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launch, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			Intent i=new Intent(this,FrontActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void run() 
	{
		try
		{
			Thread.sleep(4500);
			Intent i=new Intent(LaunchActivity.this,FrontActivity.class);
			startActivity(i);
		} catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
