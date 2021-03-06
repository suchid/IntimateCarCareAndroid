package com.Adapter;

import com.Entity.CarReportBriefSet;
import com.IntimateCarCare.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ActivityHistoryCarReportListAdapter extends BaseAdapter{

	private Context context;	
	private CarReportBriefSet list;
	
	public ActivityHistoryCarReportListAdapter(Context c,CarReportBriefSet l){
		super();
		this.context=c;
		this.list=l;
		}
	
	// 定义内部类作为占位符
		class ViewHolder {
			TextView tv_time;
			TextView tv_faultnum;
			TextView tv_score;
			
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
					R.layout.item_historycarreportbrief, null);
			viewholder = new ViewHolder();
			// 获得视图中的对象控件
			viewholder.tv_time= (TextView) convertView
					.findViewById(R.id.tv_time);
			viewholder.tv_faultnum = (TextView) convertView
					.findViewById(R.id.tv_faultnum);
			viewholder.tv_score = (TextView) convertView
					.findViewById(R.id.tv_score);

			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		viewholder.tv_time.setText(list.getItem(position).getReport_time()+"车况检查报告");
		viewholder.tv_faultnum.setText("出现故障次数:"+list.getItem(position).getReport_faultnum());
		viewholder.tv_score.setText(list.getItem(position).getReport_score()+"");
		return convertView;
	}

}
