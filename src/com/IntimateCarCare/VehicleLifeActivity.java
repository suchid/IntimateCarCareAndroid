package com.IntimateCarCare;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class VehicleLifeActivity extends Activity{
	
   private RelativeLayout rel_baoxianriqi,rel_baoyangdata,rel_weizhangdata,rel_nianjiandata;
   private LinearLayout lin_back;
   protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_veiclelife);
	
	initview();
	setlisten();
}

private void initview() {
	// TODO Auto-generated method stub
	rel_nianjiandata=(RelativeLayout) findViewById(R.id.rel_nianjiandata);
	rel_baoxianriqi=(RelativeLayout)findViewById(R.id.rel_baoxianriqi);
	lin_back=(LinearLayout)findViewById(R.id.lin_back);
	rel_baoyangdata=(RelativeLayout)findViewById(R.id.rel_baoyangdata);
	rel_weizhangdata=(RelativeLayout)findViewById(R.id.rel_weizhangdata);
}

private void setlisten() {
	// TODO Auto-generated method stub
	rel_nianjiandata.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		Intent intent=new Intent(VehicleLifeActivity.this,YearInspection.class);
		startActivity(intent);
		}
	});
	rel_weizhangdata.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent =new Intent(VehicleLifeActivity.this,PeccancyActivity.class);
			startActivity(intent);
		}
	});
	rel_baoyangdata.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent =new Intent(VehicleLifeActivity.this,MaintenanceDate.class);
			startActivity(intent);
		}
	});
	lin_back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		finish();	
		}
	});
	rel_baoxianriqi.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent =new Intent(VehicleLifeActivity.this,InsuranceDate.class);
			startActivity(intent);
		}
	});
}
}
