package com.example.kym;

import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
	
	List<String> menuEntries;
	int layoutResourceId;
	Context mContext;
	
	//constructor
	public CustomAdapter(List<String> entries, int resID, Context con)
	{
		this.menuEntries = entries;
		this.layoutResourceId = resID;
		this.mContext = con;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return menuEntries.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return menuEntries.get(position);
	}

	@Override
	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		if(v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(layoutResourceId, parent, false);
		}
		
		TextView title = (TextView) v.findViewById(R.id.txtMenuRow);
		//ImageView img=(ImageView) v.findViewById(R.id.imgEvents);
		title.setText(menuEntries.get(position));
		
		/*if((menuEntries.get(position)).equalsIgnoreCase("cyber crusade"))
		{
			img.setImageResource(R.drawable.gaming);
		}
		if((menuEntries.get(position)).equalsIgnoreCase("robotics"))
		{
			img.setImageResource(R.drawable.robotics);
		}
		if((menuEntries.get(position)).equalsIgnoreCase("compute-aid"))
		{
			img.setImageResource(R.drawable.computing);
		}
		if((menuEntries.get(position)).equalsIgnoreCase("newron"))
		{
			img.setImageResource(R.drawable.electronics);
		}
		if((menuEntries.get(position)).equalsIgnoreCase("just like that"))
		{
			img.setImageResource(R.drawable.fun);
		}
		if((menuEntries.get(position)).equalsIgnoreCase("in_focus"))
		{
			img.setImageResource(R.drawable.photography);
		}
		if((menuEntries.get(position)).equalsIgnoreCase("food for fun"))
		{
			img.setImageResource(R.drawable.food);
		}
		if((menuEntries.get(position)).equalsIgnoreCase("money matters"))
		{
			img.setImageResource(R.drawable.money_matters);
		}
		if((menuEntries.get(position)).equalsIgnoreCase("create it"))
		{
			img.setImageResource(R.drawable.creative);
		}
		if((menuEntries.get(position)).equalsIgnoreCase("innovati"))
		{
			img.setImageResource(R.drawable.entrepreneural);
		}*/
		return v;
	}
}
