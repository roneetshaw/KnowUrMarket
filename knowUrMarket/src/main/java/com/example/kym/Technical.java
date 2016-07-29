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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Technical extends ListActivity{

	String list[]={"Accumulation Distribution","Accumulative Swing Index","Bollinger Bands","Pivot Points","Volume Index","Rate of Change","Relative Strength Index"};
	String classes[]={"Accumulation","AccuSwing","Bollinger","Pivot","Vix","ROC","RSI"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		setListAdapter(new ArrayAdapter<String>(Technical.this,
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
