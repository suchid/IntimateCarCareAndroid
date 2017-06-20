package com.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.Adapter.ActivityDataFlowsAdapter.ViewHolder;
import com.Entity.DataFlowSet;
import com.Entity.RedRecordSet;
import com.IntimateCarCare.R;

public class ActivityRedRecordAdapter extends BaseAdapter{
	private Context context;	
	private RedRecordSet list;
	
	public ActivityRedRecordAdapter(Context c,RedRecordSet l){
		super();
		this.context=c;
		this.list=l;
		}
	
	// 定义内部类作为占位符
		class ViewHolder {
			TextView tv_desc;
			TextView tv_time;
			TextView tv_money;
			
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
		ViewHolder viewholder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_redrecord, null);
			viewholder = new ViewHolder();
			// 获得视图中的对象控件
			viewholder.tv_desc= (TextView) convertView
					.findViewById(R.id.tv_desc);
			viewholder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			viewholder.tv_money = (TextView) convertView
					.findViewById(R.id.tv_money);

			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		viewholder.tv_desc.setText(list.getItem(position).getDesc());
		viewholder.tv_time.setText(list.getItem(position).getCre_time());
		if(list.getItem(position).getType()==0){
			viewholder.tv_money.setText("-"+list.getItem(position).getNum());
			viewholder.tv_money.setTextColor(Color.RED);
		}else{
			viewholder.tv_money.setText("+"+list.getItem(position).getNum());
			viewholder.tv_money.setTextColor(Color.GREEN);
		}
		
		return convertView;
	}
}
