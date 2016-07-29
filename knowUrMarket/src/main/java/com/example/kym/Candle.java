package com.example.kym;

import java.io.IOException;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;

public class Candle extends ListActivity{

		String list[]={"Candlestick Basics","Bearish Engulfing","Bullish Engulfing","Doji","Hammer","Windows"};
		String classes[]={"Basics","Bear","Bull","Doji","Hamm","Windows"};
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			//setContentView(R.layout.technical);
			setListAdapter(new ArrayAdapter<String>(Candle.this,
					android.R.layout.simple_list_item_1, list));
		}
		
		protected void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			String cheese = classes[position];
			try {
				Class ourClass = Class.forName("com.example.kym." + cheese);
				Intent ourIntent = new Intent(getApplicationContext(), ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}


