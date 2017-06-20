package com.Adapter;

import java.util.List;

import com.IntimateCarCare.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityAddLicenseplateGridAdapter extends BaseAdapter {
	//private static final String TAG = "MyAdapter";
	private Context context;	
	private List<String> list;
	public ActivityAddLicenseplateGridAdapter(Context c,List<String> l){
		super();
		context=c;
		list=l;
		}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	//	Log.d(TAG, "position=" + position + ",convertView=" + convertView);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_activityaddlicenseplategirdadapter, null);// �������൱��ʱ��
			viewHolder = new ViewHolder();
			viewHolder.mTextName = (TextView) convertView
					.findViewById(R.id.btn);
			viewHolder.mTextName.setText(list.get(position).toString());
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		
		return convertView;
	}

	static class ViewHolder {
		TextView mTextName;
		
	}

}
