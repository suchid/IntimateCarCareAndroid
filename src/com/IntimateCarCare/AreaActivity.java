package com.IntimateCarCare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

public class AreaActivity extends Activity{

	private LinearLayout lin_back;
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_area);
		
        
        initView();
        setListen();
		
		
	}





	private void initView() {
		// TODO Auto-generated method stub
		lin_back=(LinearLayout) findViewById(R.id.lin_back);
		
		
		
		
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
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
