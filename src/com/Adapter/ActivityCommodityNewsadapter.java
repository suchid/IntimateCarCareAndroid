package com.Adapter;

import com.Adapter.ActivityMyShoppingadapter.ViewHolder;
import com.Entity.GoodsSet;
import com.IntimateCarCare.CommodityNewsActivity;
import com.IntimateCarCare.R;
import com.tool.SPUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityCommodityNewsadapter extends BaseAdapter{
    
	private Context context;
	private GoodsSet list;
	
	public ActivityCommodityNewsadapter(Context c,GoodsSet l){
		super();
		this.context=c;
		this.list=l;
	}
	
   class ViewHolder{
	
		ImageView image_shangpin;
		TextView shangpin_name;
		TextView price_shangpin;
		TextView shangjia_name;
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

		// TODO Auto-generated method stub
   ViewHolder viewholder;
		
	if(convertView==null){
			
	convertView = LayoutInflater.from(context).inflate(R.layout.item_commoditynewsadapter, null);
	viewholder = new ViewHolder();
	
	viewholder.shangpin_name=(TextView)convertView.findViewById(R.id.tv_shangpinname);
	viewholder.image_shangpin=(ImageView)convertView.findViewById(R.id.shangpin_image);
	viewholder.price_shangpin=(TextView)convertView.findViewById(R.id.tv_price);
	viewholder.shangjia_name=(TextView)convertView.findViewById(R.id.tv_shangjianame);
	
	
	convertView.setTag(viewholder);
		}
	
	else{
		viewholder = (ViewHolder) convertView.getTag();
	}
	
	viewholder.shangpin_name.setText(list.getItem(position).getName());//商品的价格
	viewholder.price_shangpin.setText(list.getItem(position).getMoney()+"");
	viewholder.shangjia_name.setText(list.getItem(position).getMerchant().getName());

	return convertView;
		
	
	}

}
