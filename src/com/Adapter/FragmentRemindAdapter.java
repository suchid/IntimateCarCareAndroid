package com.Adapter;

import com.Entity.RemindSet;
import com.IntimateCarCare.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentRemindAdapter extends BaseAdapter{

	private Context context;	
	private  RemindSet list;
	
	public FragmentRemindAdapter(Context c,RemindSet l){
		super();
		this.context=c;
		this.list=l;
		}
	
	// 定义内部类作为占位符
		class ViewHolder {
			TextView tv_time;
			TextView tv_content;
			ImageView img_new;
			
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
					R.layout.item_remind, null);
			viewholder = new ViewHolder();
			// 获得视图中的对象控件
			viewholder.tv_time =(TextView) convertView
					.findViewById(R.id.tv_time);
			viewholder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			viewholder.img_new=(ImageView) convertView
					.findViewById(R.id.img_new);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		viewholder.tv_time.setText(list.getItem(position).getCre_time());
		viewholder.tv_content.setText(list.getItem(position).getContent());
		if(list.getItem(position).getRead()==0){
			viewholder.img_new.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

}
