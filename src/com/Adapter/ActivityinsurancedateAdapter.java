package com.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import com.Adapter.Activityaddlicenseplatedapter.ViewHolder;
import com.Bll.CBLL;
import com.Entity.AnnualInspection;
import com.Entity.AnnualInspectionSet;
import com.Entity.InsuranceRecord;
import com.Entity.InsuranceRecordSet;
import com.Entity.Merchant;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.IntimateCarCare.ChangeInsuranceInformation;
import com.IntimateCarCare.InsuranceDate;
import com.IntimateCarCare.R;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.SPUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityinsurancedateAdapter extends BaseAdapter{ //保险的适配器
    
	private Context context;
	private InsuranceRecordSet list;
	private String danhao,danwei,feiyong,beizhu,shijian;
	public ActivityinsurancedateAdapter(Context c,InsuranceRecordSet  l){
		this.context=c;
		this.list=l;
		
	}
	
  class	ViewHolder {
	  TextView  insurance_unit;//投保单位
	  TextView  insurance_num;	//交强险单号
	  TextView  end_date;		//到期日
	  TextView  remark_c;		//备注文本
	  TextView cre_time;		//创建日期
	  TextView money;
	  ImageView xiugai;
  }

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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewholder ;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.item_activityinsurancedate, null);
			
			viewholder = new ViewHolder();
			viewholder.cre_time = (TextView) convertView.findViewById(R.id.tv_data);
			viewholder.insurance_num= (TextView) convertView.findViewById(R.id.tv_danhao);
			viewholder.insurance_unit = (TextView) convertView.findViewById(R.id.tv_danwei);
			viewholder.end_date = (TextView) convertView.findViewById(R.id.tv_daoqi);
			viewholder.money = (TextView) convertView.findViewById(R.id.tv_feiyong);
			viewholder.remark_c = (TextView) convertView.findViewById(R.id.tv_beizhu);
			viewholder.xiugai = (ImageView) convertView.findViewById(R.id.xiugai);
			convertView.setTag(viewholder);
		}
		else{
			viewholder = (ViewHolder) convertView.getTag();
		}
		
		viewholder.cre_time.setText(list.getItem(position).getCre_time());
		viewholder.insurance_num.setText(list.getItem(position).getInsurance_num());
		viewholder.insurance_unit.setText(list.getItem(position).getInsurance_unit());	
		viewholder.end_date.setText(list.getItem(position).getEnd_date());
		viewholder.money.setText(list.getItem(position).getMoney()+"");
		viewholder.remark_c.setText(list.getItem(position).getRemark_c());
		
		viewholder.xiugai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent=new Intent(context,ChangeInsuranceInformation.class);	
	        SPUtils.put(context, "irecordid", list.getItem(position).getIrecord_id());
			((Activity) context).startActivityForResult(intent, 2);
		  
			}
		});
		return convertView;
	}
	


}
