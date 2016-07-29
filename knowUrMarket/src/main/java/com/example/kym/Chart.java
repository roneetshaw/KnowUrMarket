package com.example.kym;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Chart  extends ListActivity{

	String list[]={"Head and Shoulders","Double Top","Double Bottom","Flags","Support & Resistance"};
	String classes[]={"HNS","DT","DB","Flags","SNR"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.technical);
		setListAdapter(new ArrayAdapter<String>(Chart.this,
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
