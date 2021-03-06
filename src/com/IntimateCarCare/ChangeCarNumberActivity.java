package com.IntimateCarCare;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Adapter.ActivityAddLicenseplateGridAdapter;
import com.Bll.CBLL;
import com.Entity.Car;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.ToastUtil;

public class ChangeCarNumberActivity extends Activity{
	
	private TextView belongto;
	private EditText et_car;
	private GridView grid2; 
	private ImageView img_commit,img_carnumber_bac;
	private RelativeLayout girdviewlistent2;
	private ActivityAddLicenseplateGridAdapter gridAdapter2;
	private List<String> list2;
	private String str,carnumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changecarnumber);
		
		String[] item = { "京", "沪", "浙", "苏", "粤", "鲁", "晋", "冀", "豫", "川",
				"渝", "辽", "吉", "黑", "皖", "鄂", "湘", "赣", "闽", "陕", "甘", "宁",
				"蒙", "津", "贵", "云", "桂", "琼", "青", "新", "藏", "港", "奥", "台",
				"民航" };
		list2 = Arrays.asList(item);
		
		initview();
		setListen();
		gridAdapter2 = new ActivityAddLicenseplateGridAdapter(
				ChangeCarNumberActivity.this, list2);
		grid2.setAdapter(gridAdapter2);
	}
	

	private void setListen() {
		// TODO Auto-generated method stub
		grid2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// 添加列表项被单击的监听器
		grid2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 显示被单击的图片的图片
				// imageView.setImageResource(imageIds[position]);
				TextView textView = (TextView) view.findViewById(R.id.btn); // 你cell.xml中要获取的textview
				str = (String) textView.getText();
				belongto.setText(str);
				girdviewlistent2.setVisibility(View.GONE);
			}
		});
		
		belongto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				girdviewlistent2.setVisibility(View.VISIBLE);
			}
		});
		img_commit.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
					 					 
				  Intent intent = new Intent();  
				  intent.putExtra("carnumber",et_car.getText().toString());
				  intent.putExtra("str",str);
	              setResult(2,intent);  	                
	             
	              carnumber=str+et_car.getText().toString();
	              HashMap<String, String>changecarnum=new Car().changecarnumber(ChangeCarNumberActivity.this,carnumber);
	             new HttpTask(changecarnumcallback,MyURL.CHANGENUM,ChangeCarNumberActivity.this).execute(changecarnum);
				
			}
		});
		img_carnumber_bac.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
	}
	
 HttpCallback	changecarnumcallback=new HttpCallback() {
	
	@Override
	public void getResult(JSONObject json) {
		// TODO Auto-generated method stub
		CBLL cbll=CBLL.getInstance();
		JSONEntity entity=cbll.json2changpassword(json);
		if(entity.isFlag()){
			ToastUtil.show(ChangeCarNumberActivity.this, "修改车牌号成功");
			 finish(); 
		}
		 else {
	 			if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
	 				ToastUtil.show(ChangeCarNumberActivity.this, "获取失败");
	 			} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
	 				// 重启app
	 				MainActivity.restartApplication(ChangeCarNumberActivity.this);
	 			}
	 		}
		}
	
};
	private void initview() {
		// TODO Auto-generated method stub
		img_carnumber_bac=(ImageView)findViewById(R.id.img_carnumber_bac);
		img_commit=(ImageView)findViewById(R.id.img_commit);
		et_car=(EditText)findViewById(R.id.et_car);
		girdviewlistent2=(RelativeLayout )findViewById(R.id.girdviewlistent2);
		belongto=(TextView)findViewById(R.id.belongto);
		grid2=(GridView)findViewById(R.id.gridview2);
	}
	
}
