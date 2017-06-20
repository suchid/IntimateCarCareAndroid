package com.Adapter;

import com.Entity.WeiZhangSet;
import com.IntimateCarCare.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Activitypeccancyadapter extends BaseAdapter{

	private Context context;
	private WeiZhangSet list;
	public Activitypeccancyadapter (Context c,WeiZhangSet l){
		this.context=c;
		this.list=l;
	}
	
	class ViewHolder{
		TextView address;
		TextView money;
		TextView score;
		TextView time;
		TextView item;
		TextView code;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.getSize();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	   ViewHolder viewholder=null;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.activity_peccancuadapter, null);
			viewholder=new ViewHolder();
			viewholder.address=(TextView) convertView.findViewById(R.id.tv_adaress);
			viewholder.money=(TextView) convertView.findViewById(R.id.money);//包括分数
			viewholder.item=(TextView) convertView.findViewById(R.id.tv_item);
			viewholder.code=(TextView) convertView.findViewById(R.id.code);
			viewholder.time=(TextView) convertView.findViewById(R.id.tv_date);
			
			convertView.setTag(viewholder);
		
		}else{
			viewholder = (ViewHolder) convertView.getTag();
		}

		viewholder.address.setText(list.getItem(position).getAddress());
  		viewholder.money.setText("扣"+list.getItem(position).getScore()+"分，"+"罚款"+list.getItem(position).getMoney()+"元");
  		viewholder.item.setText(list.getItem(position).getItem());
  		viewholder.code.setText(list.getItem(position).getCode());
  		viewholder.time.setText(list.getItem(position).getTime());
		return convertView;
	}

}
