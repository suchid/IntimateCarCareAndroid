package com.IntimateCarCare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.Adapter.Activitypeccancyadapter;
import com.Bll.CBLL;
import com.Entity.Merchant;
import com.Entity.WeiZhang;
import com.Entity.WeiZhangSet;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.SPUtils;

public class PeccancyActivity extends Activity{

	private Activitypeccancyadapter adapter;
	private WeiZhangSet list;
	private ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_peccancyactivity);
	    FindView();
	    SetListen();
	    //Data();
	    RequestDate();
	}
	@SuppressWarnings("unchecked")
	private void RequestDate() {
		// TODO Auto-generated method stub
		HashMap<String, String>lookpeccancy=new Merchant().getmerchant
				(SPUtils.get(PeccancyActivity.this, "userId", -1).toString(),SPUtils.get(PeccancyActivity.this, "userTokens", "").toString());
		new HttpTask(allpeccancycallback,MyURL.ALLPECCANCY,PeccancyActivity.this).execute(lookpeccancy) ;
	}
	
  HttpCallback	allpeccancycallback =new HttpCallback() {
	
	@Override
	public void getResult(JSONObject json) {
		// TODO Auto-generated method stub
	CBLL cbll=CBLL.getInstance();
	JSONEntity entity=cbll.json2Peccancy(json);
	if(entity.isFlag()){
		WeiZhangSet weizhanglist =(WeiZhangSet) entity.getData();
		List<WeiZhang>l=new ArrayList<WeiZhang>();
		for(int i=0;i<weizhanglist.getSize();i++){
			WeiZhang weilist=weizhanglist.getItem(i);
			l.add(weilist);
			list=new WeiZhangSet(l);
			adapter = new Activitypeccancyadapter(PeccancyActivity.this, list);
			listview.setAdapter(adapter);
		}
		
		
	}
	}
};
/*	private void Data() {
		// TODO Auto-generated method stub
		List<WeiZhang>l=new ArrayList<WeiZhang>();
		for(int i=0;i<10;i++){
			l.add(new WeiZhang("[浙江宁波]新昌县新昌市", "500", 5, "2016-11-27 18:40","驾驶机动车违反道路交通信号灯通行", "324523"));
			list=new WeiZhangSet(l);
		}	
		adapter=new Activitypeccancyadapter(PeccancyActivity.this,list);
		listview.setAdapter(adapter);
	}*/
	private void SetListen() {
		// TODO Auto-generated method stub
		
	}
	private void FindView() {
		// TODO Auto-generated method stub
		listview=(ListView)findViewById(R.id.listview);
	}
}
