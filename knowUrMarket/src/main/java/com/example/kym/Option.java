package com.example.kym;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Option extends ListActivity{

	String list[]={"Call Options","Put Options","Bull Call Spread","Bear Put Spread"};
	String classes[]={"Call","Put","BCS","BPS"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Option.this,
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
