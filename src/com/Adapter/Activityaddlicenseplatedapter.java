package com.Adapter;

import java.util.ArrayList;
import java.util.List;
import com.Entity.ArcticSet;
import com.Entity.CarSet;
import com.IntimateCarCare.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Activityaddlicenseplatedapter extends BaseAdapter{

	private Context context;	
	private CarSet list;
	public Activityaddlicenseplatedapter(Context c,CarSet l){
		super();
		context=c;
		list=l;
	}
	
	class ViewHolder{
		TextView carcar;
		
	
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.getSize();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.getItem(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewholder;
		
		if(arg1 == null){
			arg1 = LayoutInflater.from(context).inflate(R.layout.item_addlicenseplateadapter, null);
			viewholder = new ViewHolder();
			
			viewholder.carcar = (TextView) arg1.findViewById(R.id.carcar);
			
			arg1.setTag(viewholder);
		}
		else{
			viewholder = (ViewHolder) arg1.getTag();
		}
		viewholder.carcar.setText(list.getItem(arg0).getArctic_name());
	
		return arg1;
		
	}

}
