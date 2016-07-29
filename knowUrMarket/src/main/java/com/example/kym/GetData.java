package com.example.kym;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

public class GetData extends SQLiteOpenHelper
{
	JSONArray categories = null;
	static final String TAG="StatusData";
	public static final String DB_NAME="helper.db";
	public static final int DB_VERSION=1;
	public static final String TABLE_INFO="REGISTRATION";
	public static final String TABLE_ORDERBOOK="ORDERBOOK";
	public static final String TABLE_PORTFOLIO="PORTFOLIO";
	public static final String TABLE_BALANCE="BALANCE";
	public static final String U_NAME="u_name";
	public static final String U_EMAIL="u_email";
	public static final String PASSWORD="password";
	public static final String PHONE="phone";
	
	public static final String O_ID="o_id";
	public static final String O_STOCK="o_stock";
	public static final String O_STATUS="o_status";
	public static final String O_QUATITY="o_quantity";
	public static final String O_PRICE="o_price";
	public static final String M_PRICE="m_price";
	public static final String O_ACTION="o_action";
	
	public static final String P_PROFIT="p_profit";
	public static final String P_BUYPRICE="p_buyprice";
	
	public static final String B_BAL="b_bal";
	public static final String B_LEFT="b_left";
	public static final String B_PROFIT="b_profit";
	
	
	
	
	Context context;
	SQLiteDatabase db;
	public GetData(Context context) 
	{
		super(context, DB_NAME, null, DB_VERSION);
		
	}
	@Override
	public void onCreate(SQLiteDatabase db) //called once
	{
		String sql=String.format("CREATE TABLE %s"+"(%s text primary key,%s text,%s text,%s text)", TABLE_INFO,U_EMAIL,U_NAME,PASSWORD,PHONE);
		String sql1=String.format("CREATE TABLE %s"+"(%s text primary key,%s text,%s text,%s text,%s text,%s text,%s text)",TABLE_ORDERBOOK,O_ID,O_STOCK,O_STATUS,O_QUATITY,O_PRICE,M_PRICE,O_ACTION);
		String sql2=String.format("CREATE TABLE %s"+"(%s text primary key,%s text,%s text,%s text,%s text)", TABLE_PORTFOLIO,O_ID,O_STOCK,O_QUATITY,P_PROFIT,P_BUYPRICE);
		String sql3=String.format("CREATE TABLE %s"+"(%s text primary key,%s text,%s text,%s text)", TABLE_BALANCE,U_EMAIL,B_BAL,B_LEFT,B_PROFIT);
		db.execSQL(sql);
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
			
	}
}
