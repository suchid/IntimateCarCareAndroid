package com.IntimateCarCare;

import java.util.HashMap;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.Adapter.Activityaddlicenseplatedapter;
import com.Bll.CBLL;
import com.Entity.Car;
import com.Entity.CarSet;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.ToastUtil;

public class ChangeCarConfiguration extends Activity {
	private Spinner Spinneraa;
	private ListView list2;
	
	private Activityaddlicenseplatedapter caradapter;
	private RelativeLayout rel_chepinpai,car_type,rel_vin;
	private TextView tv_chepinpai,tv_cartype,tv_shoudong,tv_zidong,tv_vin;
	boolean visibility_Flag = false;
	private CarSet listdate;
	private ImageView img_3,img_4,img_allcommit,img_back2;
	private int brand_id;
	private String vinnum,carbrands,cararctic,str;
	 private int tag=1;//默认手动
	 private EditText edt_pailiang,edt_year,edt_youxiang,edt_licheng;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changecarconfiguration);
		//getData();
		initview();
		setListen();
		
		String[] mItems = getResources().getStringArray(R.array.spingarr);
		ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mItems);
		Spinneraa.setAdapter(Adapter);
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		switch (requestCode) {
		case 2:
			if (resultCode == RESULT_OK) {
				carbrands=data.getStringExtra("carname");
				tv_chepinpai.setText(carbrands);		
				brand_id =data.getIntExtra("brandid", -1);
			}
			break;
		case 3:	
			if (resultCode == RESULT_OK) {
				vinnum=data.getStringExtra("vinnum").toString();
				tv_vin.setText(vinnum);
			}
		default:
		}
	}

	/*private void getData() {
		// TODO Auto-generated method stub
		List<Car> l = new ArrayList<Car>();
		l.add(new Car("宝马MAX6",2));
		l.add(new Car("宝马MAX7",2));
		l.add(new Car("宝马MIX2",2));
		l.add(new Car("宝马MIX3",2));
		listdate = new CarSet(l);
	}*/
	
	private void setListen() {
		// TODO Auto-generated method stub
		
		list2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				cararctic=listdate.getItem(arg2).getArctic_name();
				tv_cartype.setText(cararctic);
				list2.setVisibility(View.GONE);
				visibility_Flag = true;
				img_4.setVisibility(View.VISIBLE);
				img_3.setVisibility(View.GONE);
			}
		});
		car_type.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				if (!tv_chepinpai.getText().toString().equals("")) {
					
					if (visibility_Flag) {
						HashMap<String, String>selectcar=new Car().selectarctic_name(ChangeCarConfiguration.this,brand_id);
						new HttpTask(allbrangscallback,MyURL.ALLARCTIC,ChangeCarConfiguration.this).execute(selectcar);
						
						list2.setAdapter(caradapter);
						list2.setVisibility(View.VISIBLE);
						visibility_Flag = false;
						img_4.setVisibility(View.GONE);
						img_3.setVisibility(View.VISIBLE);
					} else {
						list2.setVisibility(View.GONE);
						visibility_Flag = true;
						img_4.setVisibility(View.VISIBLE);
						img_3.setVisibility(View.GONE);
					}
				
				} else {
					ToastUtil.show(ChangeCarConfiguration.this, "请选择车的品牌");
				}
				
			}
		});
		img_back2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		rel_chepinpai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ChangeCarConfiguration.this,
						AllCarClassify.class);
				startActivityForResult(intent, 2);
			}
		});
		tv_zidong.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_shoudong.setBackgroundColor(android.graphics.Color.GRAY);
				tv_zidong.setBackgroundColor(android.graphics.Color.YELLOW);
				tag=2;//自动
			}
		});
		tv_shoudong.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_shoudong.setBackgroundColor(android.graphics.Color.YELLOW);
				tv_zidong.setBackgroundColor(android.graphics.Color.GRAY);
				tag=1;//手动
			}
		});
		img_allcommit.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(tag==1){
			 	str="自动挡" ; 
				  }
				  else{
			     str="手动挡" ; 
					  
				  }
              HashMap<String, String>changecarconfig=new Car().changecarconfigurat(
              ChangeCarConfiguration.this,carbrands,cararctic,vinnum,str,
              edt_pailiang.getText().toString(),edt_year.getText().toString(),
              edt_youxiang.getText().toString(),edt_licheng.getText().toString()
              );
			  new HttpTask(changcarconfigcallbavk,MyURL.CHANGECARBASE,ChangeCarConfiguration.this).execute(changecarconfig);
				 
				 /* intent.putExtra("carbrands",carbrands); 
				  intent.putExtra("cararctic",cararctic); 
				  intent.putExtra("vinnum",vinnum);
				  intent.putExtra("pailiang",edt_pailiang.getText().toString());
				  if(tag==1){
					 intent.putExtra("tag","自动挡");  
				  }
				  else{
					  intent.putExtra("tag","手动挡"); 
				  }
				  intent.putExtra("nianluan",edt_year.getText().toString());
				  intent.putExtra("youxiang",edt_youxiang.getText().toString());
				  intent.putExtra(" edt_licheng", edt_licheng.getText().toString()); 
	              setResult(4, intent);  */ 
			
			}
		});
		rel_vin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent=new Intent(ChangeCarConfiguration.this,VINnumberActivity.class);
			startActivityForResult(intent, 3);
			}
		});
	}
	HttpCallback allbrangscallback=new HttpCallback() {
		
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll=CBLL.getInstance();
			JSONEntity entity=cbll.json2getallbrands(json);
			if(entity.isFlag()){
				listdate=(CarSet) entity.getData();
				caradapter=new Activityaddlicenseplatedapter(ChangeCarConfiguration.this,listdate);
				list2.setAdapter(caradapter);
			}
			}
	};
	HttpCallback changcarconfigcallbavk=new HttpCallback() {
		
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub

			CBLL cbll=CBLL.getInstance();
	         JSONEntity entity= cbll.json2addinsurance(json);
	         if(entity.isFlag()){
	           ToastUtil.show(ChangeCarConfiguration.this, "修改成功");
	           Intent intent = new Intent(ChangeCarConfiguration.this,MyCarSetting.class);  
			   startActivity(intent);
		    }
	         else {
	 			if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
	 				ToastUtil.show(ChangeCarConfiguration.this, "修改失败");
	 			} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
	 				// 重启app
	 				MainActivity.restartApplication(ChangeCarConfiguration.this);
	 			}
	 		}
		
		}
	};
	private void initview() {
		// TODO Auto-generated method stub
		edt_year=(EditText) findViewById(R.id.edt_year);
		edt_pailiang=(EditText) findViewById(R.id.edt_pailiang);
		tv_vin=(TextView) findViewById(R.id.tv_vin);
		rel_vin=(RelativeLayout)findViewById(R.id.rel_vin);
		tv_zidong=(TextView)findViewById(R.id.tv_zidong);
		tv_shoudong=(TextView)findViewById(R.id.tv_shoudong);
		list2 = (ListView) findViewById(R.id.list_carxing);
		img_3=(ImageView)findViewById(R.id.img_3);
	    img_4=(ImageView)findViewById(R.id.img_4);
		car_type=(RelativeLayout)findViewById(R.id.car_type);
		rel_chepinpai=(RelativeLayout)findViewById(R.id.rel_chepinpai);
		tv_chepinpai=(TextView)findViewById(R.id.tv_chepinpai);
		tv_cartype=(TextView)findViewById(R.id.tv_cartype);
		Spinneraa = (Spinner) findViewById(R.id.Spinneraa);
		img_allcommit=(ImageView)findViewById(R.id.img_allcommit);
		img_back2=(ImageView)findViewById(R.id.img_back2);
		edt_youxiang=(EditText) findViewById(R.id.edt_youxiang);
		edt_licheng=(EditText) findViewById(R.id.edt_licheng);
	}
}
