package com.example.symptombasedmedicinefinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class LevelAdapter2 extends ArrayAdapter<String> {
	private final Activity context;
	
	private final String[] parkingname;
	
	
	public LevelAdapter2(Activity context, String[] parkingname) {
		super(context, R.layout.itemlist, parkingname);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.parkingname=parkingname;
	
		
	}
	
		
		// TODO Auto-generated constructor stub
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.itemlist, null,true);
		
		TextView txtparkingname = (TextView) rowView.findViewById(R.id.parkingname);
		
		txtparkingname.setText(parkingname[position]);
	
		
		return rowView;
		
	};
}
