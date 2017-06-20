package com.IntimateCarCare;

import java.util.Calendar;
import java.util.HashMap;
import org.json.JSONObject;
import com.Bll.CBLL;
import com.Entity.AnnualInspection;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.ToastUtil;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddYearInspection extends Activity{

	private LinearLayout lin_back;
	private ImageView img_commitinfo;
	private TextView tv_nowdata,tv_nextdata;
	private EditText edt_feiyong,edt_beizhu;
	private String nowdata,nextdata,beizhu;
	private int feiyong;
	private RelativeLayout rel_nowdata,rel_nextdata;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addyearinspection);
		findview();
		setListen();
	}

	private void findview() {
		// TODO Auto-generated method stub
		lin_back=(LinearLayout) findViewById(R.id.lin_back);
		img_commitinfo=(ImageView) findViewById(R.id.img_commitinfo);
		tv_nowdata=(TextView) findViewById(R.id.tv_nowdata);
		tv_nextdata=(TextView) findViewById(R.id.tv_nextdata);
		edt_feiyong=(EditText) findViewById(R.id.edt_feiyong);
		edt_beizhu=(EditText) findViewById(R.id.edt_beizhu);
		rel_nowdata=(RelativeLayout) findViewById(R.id.rel_nowdata);
		rel_nextdata=(RelativeLayout) findViewById(R.id.rel_nextdata);
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
		img_commitinfo.setOnClickListener(new OnClickListener() {
			
		
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("ttttttttttttttt");
				if(tv_nowdata.getText().toString().isEmpty()&&
						tv_nextdata.getText().toString().isEmpty()&&
						edt_feiyong.getText().toString().isEmpty()&&
						edt_beizhu.getText().toString().isEmpty()
						){
					ToastUtil.show(AddYearInspection.this, "所输信息有误！");
				}
				else{
					nowdata=tv_nowdata.getText().toString();
					nextdata=tv_nextdata.getText().toString();
					feiyong=Integer.parseInt(edt_feiyong.getText().toString());
					beizhu=edt_beizhu.getText().toString();
					   HashMap<String, String>sendinsurance=new AnnualInspection().addannual(AddYearInspection.this,nowdata, nextdata, feiyong, beizhu);
				       new  HttpTask (seninsurancecallback,MyURL.ADDYEARINSPECTION,AddYearInspection.this).execute(sendinsurance);
				}	   
			}
		});
		rel_nowdata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar cal = Calendar.getInstance();
				final DatePickerDialog mDialog = new DatePickerDialog(
						AddYearInspection.this, null, cal.get(Calendar.YEAR), cal
								.get(Calendar.MONTH), cal
								.get(Calendar.DAY_OF_MONTH));

				// 手动设置按钮
				mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成",
						new DialogInterface.OnClickListener() {
							@SuppressWarnings("unchecked")
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
								DatePicker datePicker = mDialog.getDatePicker();
								cal.set(datePicker.getYear(),
										datePicker.getMonth(),
										datePicker.getDayOfMonth());
								tv_nowdata.setText(DateFormat.format(
										"yyy-MM-dd", cal));
								System.out.println("LLLLLLLL"+DateFormat.format(
										"yyy-MM-dd", cal)+"");
								
							}
						});
				// 取消按钮，如果不需要直接不设置即可
				mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.out.println("BUTTON_NEGATIVE~~");
							}
						});
				mDialog.show();
			}
		});
		rel_nextdata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar cal = Calendar.getInstance();
				final DatePickerDialog mDialog = new DatePickerDialog(
						AddYearInspection.this, null, cal.get(Calendar.YEAR), cal
								.get(Calendar.MONTH), cal
								.get(Calendar.DAY_OF_MONTH));

				// 手动设置按钮
				mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成",
						new DialogInterface.OnClickListener() {
							@SuppressWarnings("unchecked")
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
								DatePicker datePicker = mDialog.getDatePicker();
								cal.set(datePicker.getYear(),
										datePicker.getMonth(),
										datePicker.getDayOfMonth());
								tv_nextdata.setText(DateFormat.format(
										"yyy-MM-dd", cal));
								System.out.println("LLLLLLLL"+DateFormat.format(
										"yyy-MM-dd", cal)+"");
		
							}
						});
				// 取消按钮，如果不需要直接不设置即可
				mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.out.println("BUTTON_NEGATIVE~~");
							}
						});
				mDialog.show();
			}
		});
	}
	HttpCallback seninsurancecallback=new HttpCallback() {
		
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll =CBLL.getInstance();
			JSONEntity entity=cbll.json2addinsurance(json);
			if(entity.isFlag()){		
				Intent intent =new Intent();
				intent.putExtra("nowdata",nowdata); 
				intent.putExtra("nextdata",nextdata); 
				intent.putExtra("feiyong",feiyong+"");
				intent.putExtra("beizhu",beizhu);
				ToastUtil.show(AddYearInspection.this,"添加成功");
				setResult(RESULT_OK, intent);  
		        finish();
			}
			else{
				ToastUtil.show(AddYearInspection.this,"添加失败");
				}
		}
	};
}
