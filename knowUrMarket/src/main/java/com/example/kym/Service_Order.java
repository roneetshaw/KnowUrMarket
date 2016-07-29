package com.example.kym;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;





import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class Service_Order extends Service
{
	private static final String TAG = Service_Order.class.getSimpleName();
	private Timer timer;
	int i=0;
	GetData database;SQLiteDatabase db;
	String url,price,data;
	String em="";
	int pal;
	//String k="hello";
	private TimerTask updateTask = new TimerTask()
	{
	    @Override
	    public void run() 
	    {
	    	ConnectionDetectore cd=new ConnectionDetectore(getApplicationContext());
	    	if(cd.isConnectingToInternet()==true)
	    	{
	    	Cursor desp=getDetails();
	    	Log.i(TAG, em+"inside run");
	    	if(desp.moveToFirst())
	    	{
			while(!desp.isAfterLast())
			{
					String id=desp.getString(0);
					String stockName=desp.getString(1);
					String UID=findEmail(id,stockName);
					Log.i(TAG, stockName+": order from"+id);
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
					double o_price=Double.parseDouble(desp.getString(4));
					double curr_p=convert(price);
					if(o_price >= curr_p)
					{
						ping(stockName,desp.getString(3));
						//insert the stock in portfolio
						ContentValues cv_p=new ContentValues();
						pal=checkP(id);
						if(pal>=0)
						{
							Cursor jk=getPortDetails();
							jk.moveToPosition(pal);
							double po=Double.parseDouble(jk.getString(4));
							int qo=Integer.parseInt(jk.getString(2));
							int x1=Integer.parseInt(desp.getString(3));
							double e1=qo*po;//stored price
							double e2=x1*curr_p;//new price
							int k_p=x1+qo;
							double e=(e1+e2)/k_p;
							cv_p.put(GetData.O_QUATITY, k_p+"");
							cv_p.put(GetData.P_BUYPRICE,e+"");
						}
						else
						{
							cv_p.put(GetData.O_ID,id);
							cv_p.put(GetData.O_STOCK, stockName);
							cv_p.put(GetData.O_QUATITY, desp.getString(3));
							cv_p.put(GetData.P_BUYPRICE,curr_p+"");
							cv_p.put(GetData.P_PROFIT, "0");
						}
						db=database.getWritableDatabase();
						//update if exist
						if(pal>=0)
						{
							db.update(GetData.TABLE_PORTFOLIO, cv_p, "o_id='"+id+"'", null);
						}
						else
						{
							Log.i("Service_order",UID+"Order executed");
							db.insert(GetData.TABLE_PORTFOLIO, null, cv_p);
						}
						ContentValues valB=new ContentValues();
						double a=Integer.parseInt(desp.getString(3))*(o_price-curr_p);
						if(a>=100)
						{
							Cursor fv=getBalDetails(UID);
							fv.moveToFirst();
							String d=fv.getString(0);
							double y=Double.parseDouble(d)+a;
							valB.put(GetData.B_LEFT, y+"");
							db=database.getWritableDatabase();
							db.update(GetData.TABLE_BALANCE, valB, "u_email='"+UID+"'", null);
						}
						//delete the stock from orderbook
						db.delete(GetData.TABLE_ORDERBOOK, "o_id = '"+(id)+"'",null);
					}
				desp.moveToNext();
			}
	    	Log.i(TAG, "Timer task doing work");
	    	}
	    	else
	    		Log.i(TAG, "Order Book empty");
	    	}
	    	else
	    	{
	    		
	    	}
	    }
	    	
    	
	};
	@Override
	public void onCreate() 
	{
		super.onCreate();
		Log.i(TAG, "Service creating");
	    timer = new Timer("TweetCollectorTimer");
	    timer.schedule(updateTask, 1000L, 60 * 1000L);
	    database=new GetData(this);
	    Log.i(TAG, em+"onCreate()");
	    
	}
	public String findEmail(String idFind,String sName)
	{
		int pos;
		pos=idFind.indexOf(sName, 0);
        String uid=idFind.substring(0, pos);
        return uid;
	}
	public void ping(String x,String y)
	{
		Intent resultIntent = new Intent(this, LoginActivity1.class);
		PendingIntent resultPendingIntent =
		PendingIntent.getActivity
		(
		    this,
		    0,
		    resultIntent,
		    PendingIntent.FLAG_UPDATE_CURRENT
		);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
		        getApplicationContext()).setSmallIcon(R.drawable.arrowst)
		        .setContentTitle("Order Executed")
		        .setContentText(x+" "+y+" Shares bought")
		        .setContentIntent(resultPendingIntent).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(0, mBuilder.build());
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
	public int checkP(String id2)
	{
		Cursor f=getPortDetails();
		f.moveToFirst();
		while(!f.isAfterLast())
		{
			if(f.getString(0).equals(id2))
				return f.getPosition();
			f.moveToNext();
		}
		return -999;
	}
	public Cursor getDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_ORDERBOOK, new String[] {"o_id","o_stock","o_status","o_quantity","o_price","m_price","o_action"},null, null, null, null, null);
		//database.close();
		return cursor;
	}
	public Cursor getBalDetails(String UID)
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_BALANCE, new String[] {"b_left"},"u_email = '"+UID+"'", null, null, null, null);
		//database.close();
		return cursor;
	}
	public Cursor getPortDetails()
	{
		Cursor cursor = database.getReadableDatabase().query(GetData.TABLE_PORTFOLIO, new String[] {"o_id","o_stock","o_quantity","p_profit","p_buyprice"},null, null, null, null, null);
		//database.close();
		return cursor;
	}
	public void play(String x) 
	{
		em=x;
		Log.i(TAG, "Roneet clicked");
	}
	public int result()
	{
		if(i==3)
			return 3;
		else
			return 0;
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	@Override
	public void onDestroy()
	{
		
	    super.onDestroy();
	    Log.i(TAG, "Service destroying");
	     
	    timer.cancel();
	    timer = null;
	}
static class ServiceStub extends IService_MusicPlayer.Stub
{
	WeakReference mService;       
	ServiceStub(Service_Order service) 
	{
		mService = new WeakReference(service);
	}

	@Override
	public void play(String x)
	{
		((Service_Order) mService.get()).play(x);
	}

	@Override
	public int result() throws RemoteException {
		// TODO Auto-generated method stub
		return ((Service_Order)mService.get()).result();
	}


}

private final IBinder mBinder = new ServiceStub(this);
}