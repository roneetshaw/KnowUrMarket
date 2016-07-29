package com.example.kym;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

    private Context mcontext;
    //String[] texts = {"History of the Market","Stocks","Role of SEBI","Technical Indicator", "Chart Patterns", "Candlestick Patterns", "Options Strategy","Market FAQs"};
    int[] pic={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8};
    public ImageAdapter(Context context) {
        this.mcontext = context;
    }

    public int getCount() {
        return 8;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	TextView tv=new TextView(mcontext);
    	ImageView imageView = new ImageView(mcontext);
        imageView.setImageResource(pic[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(130,130));
        
        return imageView;
        
    	/*tv.setText(texts[position]);
    	tv.setTextSize(15);
    	tv.setLayoutParams(new GridView.LayoutParams(130,130));
    	return tv;*/
        }
}
