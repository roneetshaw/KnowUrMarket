package com.example.kym;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

public class GameSplashActivity extends Activity 
{
	String url;
	IService_MusicPlayer mService = null;
	boolean disconnected = true;
	ConnectionDetectore cd;
	public static String email;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_game_splash);
		cd=new ConnectionDetectore(getApplicationContext());
		email=MainActivity.email;
	}
	public void onClickPlay(View v)
	{
		if(cd.isConnectingToInternet()==true)
		{
		Intent i3 = new Intent(Service_Order.class.getName());
	    startService(i3);
	    //bindService(i2, osc, 0);
		try {
			mService.play(email);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			Intent i=new Intent(this, GameActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
		else
		{
			Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
	}
	public void onClickInst(View v)
	{
		url="file:///android_asset/instruction.html";
		AlertDialog.Builder s=new AlertDialog.Builder(this);
		s.setTitle("Instruction");
		WebView wv = new WebView(this);
		wv.loadUrl(url);
		//wv.setHorizontalScrollBarEnabled(false);
		s.setView(wv);
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
	public void onClickAbout(View v)
	{
//		Intent i=new Intent(this,AboutUsActivity.class);
//		startActivity(i);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_splash, menu);
		return true;
	}
	private ServiceConnection osc = new ServiceConnection() 
	 {

		    @Override
		    public void onServiceConnected(ComponentName name, IBinder service) {
		mService = IService_MusicPlayer.Stub.asInterface(service);
		disconnected = false;
		    }

		    @Override
		    public void onServiceDisconnected(ComponentName name) {
		disconnected = true;
		    }      
	};
	public void jack(View v)
	{
		int x1=0;
		try {
			mService.play("");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			x1=mService.result();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(x1==3)
			Toast.makeText(this, "Roneet is super rich", Toast.LENGTH_SHORT).show();
		else
		{}
	}
	 @Override
	 public void onResume(){
	     super.onResume();
	     Log.i("MainActivity","OnResume");
	     Intent i2 = new Intent(Service_Order.class.getName());
	     startService(i2);
	     bindService(i2, osc, 0);
	 }
	     
	 @Override
	 public void onPause(){
	     super.onPause();
	     if (!disconnected){
	     unbindService(osc);
	     }
	 }
}
