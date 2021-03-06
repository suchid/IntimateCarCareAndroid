package com.IntimateCarCare;

import java.util.HashMap;
import org.json.JSONObject;
import com.Bll.CBLL;
import com.Entity.Car;
import com.Entity.UserEntity;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.JSONEntity;
import com.tool.MyURL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CarConfiguration extends Activity{
	
 private TextView changeshezhi,tv_carpinpai,tv_carchexing,tv_vin,
                  tv_bianshuxiang,tv_pailiang,tv_niankuan;
 private ImageView img_baccarshezhi;
 private EditText edt_jine,edt_licheng;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_carconfiguration);
		initview();
		setListen();
		RequestData();
	}
	@SuppressWarnings("unchecked")
	private void RequestData() {
		// TODO Auto-generated method stub
		HashMap<String, String> idtakjson = new UserEntity()
		.getIdTaken(CarConfiguration.this);
		//获取汽车配置界面请求
		new HttpTask(carconfigurationcallback, MyURL.MY,CarConfiguration.this).execute(idtakjson);
	}
	
	HttpCallback carconfigurationcallback=new HttpCallback() {
		
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
		CBLL cbll =CBLL.getInstance(); 
		JSONEntity entity=cbll.json2caisetting(json);
		if(entity.isFlag()){
		Car car=(Car) entity.getData();
		tv_carpinpai.setText(car.getBrand_name());
		tv_carchexing.setText(car.getArctic_name());
		tv_vin.setText(car.getVin_num());
		tv_bianshuxiang.setText(car.getGearbox());
		tv_pailiang.setText(car.getDisplacement());
		tv_niankuan.setText(car.getCopy());
		edt_licheng.setText(car.getMileage());
		edt_jine.setText(car.getMoney()+"");
		System.out.println("AAAAAAAAAAA"+car.getMoney()+"");
		
		}
		}
	};
	private void setListen() {
		// TODO Auto-generated method stub
		changeshezhi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(CarConfiguration.this,ChangeCarConfiguration.class);
				startActivity(intent);
				finish();
			}
		});
		img_baccarshezhi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	private void initview() {
		// TODO Auto-generated method stub
		edt_jine=(EditText) findViewById(R.id.edt_jine);
		tv_niankuan=(TextView) findViewById(R.id.tv_niankuan);
		tv_pailiang=(TextView) findViewById(R.id.tv_pailiang);
		tv_bianshuxiang=(TextView) findViewById(R.id.tv_bianshuxiang);
		tv_vin=(TextView) findViewById(R.id.tv_vin);
		tv_carchexing=(TextView) findViewById(R.id.tv_carchexing);
		tv_carpinpai=(TextView) findViewById(R.id.tv_carpinpai);
		img_baccarshezhi=(ImageView)findViewById(R.id.img_baccarshezhi);
		changeshezhi=(TextView)findViewById(R.id.changeshezhi);
		edt_licheng=(EditText) findViewById(R.id.edt_licheng);
	}
}
