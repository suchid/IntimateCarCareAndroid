package com.IntimateCarCare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.Adapter.ActivitymaintenancedateAdapter;
import com.Bll.CBLL;
import com.Entity.MaintainRecord;
import com.Entity.MaintainRecordSet;
import com.Entity.Merchant;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.SPUtils;

public class MaintenanceDate extends Activity{
 
	private LinearLayout lin_back;
	private ImageView img_commit;
	private ActivitymaintenancedateAdapter adapter;
	private String creattime,tv_priject,remark_c;
	private int  cycle,money;
	private MaintainRecordSet list;
	private ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_maintenancedate);
		findview();
		setListen();
		RequestData();
	}
	
    @SuppressWarnings("unchecked")
	private void RequestData() {
		// TODO Auto-generated method stub
    	 HashMap<String, String> getmaintenance= new Merchant().getmerchant
  				(SPUtils.get(MaintenanceDate.this, "userId", -1).toString(),SPUtils.get(MaintenanceDate.this, "userTokens", "").toString());
  		new HttpTask(mymaintenance, MyURL.MAINTENANCE,MaintenanceDate.this).execute(getmaintenance);
	}

    HttpCallback mymaintenance=new HttpCallback() {
		
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
		CBLL cbll=CBLL.getInstance();
	    JSONEntity entity=cbll.json2Maintenancedate(json);
	    if(entity.isFlag()){
	    	list=(MaintainRecordSet) entity.getData();  	
	    	adapter = new ActivitymaintenancedateAdapter(MaintenanceDate.this, list);    	    
	    	listview.setAdapter(adapter);
	    }
		}
	};
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			switch (requestCode) {
			case 1:
				if(resultCode==RESULT_OK){
					creattime=data.getStringExtra("shijian");
					cycle=data.getIntExtra("licheng", -1);
					tv_priject=data.getStringExtra("xiangmu");
					money=data.getIntExtra("feiyong", -1);
					remark_c=data.getStringExtra("beizhu");
					
					if(list==null){
						List<MaintainRecord>l=new ArrayList<MaintainRecord>();
						l.add(new MaintainRecord(creattime,cycle,tv_priject,money,remark_c));
						list=new MaintainRecordSet(l);	
						adapter = new ActivitymaintenancedateAdapter(MaintenanceDate.this, list);
						listview.setAdapter(adapter);
					}else{
						         //获取之前的List数据
						List<MaintainRecord>ll=list.getMaintainRecordList();
						ll.add(new MaintainRecord(creattime,cycle,tv_priject,money,remark_c));
						list=new MaintainRecordSet(ll);	
						adapter = new ActivitymaintenancedateAdapter(MaintenanceDate.this, list);
						adapter.notifyDataSetChanged();
					
					}
						
				}
				break;
			case 2:
				if(resultCode==2){				
					RequestData();
				}
				break;
	
			default:
				break;
			}
		}
	private void findview() {
		// TODO Auto-generated method stub
		lin_back=(LinearLayout)findViewById(R.id.lin_back);
		img_commit=(ImageView)findViewById(R.id.img_commit);
		listview=(ListView)findViewById(R.id.lis_maintenance);
	}
	private void setListen() {
		// TODO Auto-generated method stub
		lin_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		img_commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(MaintenanceDate.this,Addmaintenancedate.class);	
				startActivityForResult(intent, 1);
			
				
			}
		});
	}

}
