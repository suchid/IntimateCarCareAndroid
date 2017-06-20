package com.IntimateCarCare;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.xutils.x;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Bll.CBLL;
import com.Entity.Merchant;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.JSONEntity;
import com.tool.MyURL;

public class Shangjia_Detail extends Activity{
  
	private ImageView img_shangcheng_back,shangjia_image;
	private RelativeLayout rel_baidumap;
	private TextView tv_shangjianame_detail,shangjia_address_detail,call_shangjia_detail,
	description,textView1;
	private Merchant merchantentity;                                                    
	private String longtude,latitude;
	
	private HashMap<String, String>merchantdetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shangjiadetail);
		
		RequestMainData();
		initview();
		setListent();
	
	
	}
  @SuppressWarnings("unused")
private void getdata(){
		Intent intent=getIntent();
		 if(intent.getStringExtra("tag").equals("true")){
			merchantdetail.put("merchant_id", intent.getIntExtra("merchantid", -1)+"");
			
		 }else if(intent.getStringExtra("tag").equals("false")){
			merchantdetail.put("merchant_id",intent.getIntExtra("merchant_id", -1)+"");
			
		 }
		 else if(intent.getStringExtra("tag").equals("know")){
			merchantdetail.put("merchant_id",intent.getIntExtra("merchantid", -1)+"");
		 }
  }
	@SuppressWarnings("unchecked")
	private void RequestMainData() {
		// TODO Auto-generated method stub	
		merchantdetail=new Merchant().getMerchantDetail(Shangjia_Detail.this);
		getdata();
		new HttpTask(merchantdetailcallback,MyURL.MERCHANTDETAIL,Shangjia_Detail.this).execute(merchantdetail);
	}
	
	  HttpCallback merchantdetailcallback=new HttpCallback() {
		
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL CBllmerchantdetail =CBLL.getInstance();
			JSONEntity entity =CBllmerchantdetail.json2merchantdetail(json);
			if(entity.isFlag()){	
				merchantentity =(Merchant)entity.getData();
				description.setText(merchantentity.getBusiness_description());
				tv_shangjianame_detail.setText(merchantentity.getName());
				shangjia_address_detail.setText(merchantentity.getAddress());
				call_shangjia_detail.setText(merchantentity.getTel());
				longtude=merchantentity.getLongtude();
				latitude=merchantentity.getLatitude();
				
			 if(!merchantentity.getPictures().getUrl().equals("")){
				x.image().bind(shangjia_image,merchantentity.getPictures().getUrl());
			}
				else{
					shangjia_image.setImageResource(R.drawable.shangpin_image);
				}
		  }
		}
	};
	private void setListent() {
		// TODO Auto-generated method stub
		
		img_shangcheng_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 finish();	
			}
		});
		
		textView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phone_number = merchantentity.getTel();
				if (phone_number != null && !phone_number.equals("")) {
					// 调用系统的拨号服务实现电话拨打功能
					// 封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri
							.parse("tel:" + phone_number));
					startActivity(intent);
				}
			}
		});
		rel_baidumap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(isAvilible(Shangjia_Detail.this,"com.baidu.BaiduMap")){//传入指定应用包名  
		                Intent intent = null;  
		                try {  
		                	 String name=tv_shangjianame_detail.getText().toString();
						     String jingdu=longtude;
						     String weidu=latitude;
		                     String date="intent://map/marker?location="+jingdu+","+weidu+"&title="+name+"&content="+name+"&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
				             intent = Intent.getIntent(date); 
				             startActivity(intent); //启动调用  
		             
		                } catch (URISyntaxException e) {  
		                    Log.e("intent", e.getMessage());  
		                }  
		            }else{//未安装  
		                //market为路径，id为包名  
		                //显示手机上所有的market商店  
		                Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");  
		                Intent intent = new Intent(Intent.ACTION_VIEW, uri);   
		                startActivity(intent);   
		            }
			}

		});
	}
	
	private void initview() {
		// TODO Auto-generated method stub
		textView1=(TextView) findViewById(R.id.textView1);
		shangjia_image=(ImageView)findViewById(R.id.shangjia_image);
		tv_shangjianame_detail=(TextView)findViewById(R.id.tv_shangjianame_detail);
		rel_baidumap=(RelativeLayout)findViewById(R.id.rel_baidumap);
		img_shangcheng_back=(ImageView)findViewById(R.id.img_shangcheng_back);
		call_shangjia_detail=(TextView)findViewById(R.id.call_shangjia_detail);
		description=(TextView)findViewById(R.id.description);
		shangjia_address_detail=(TextView)findViewById(R.id.shangjia_address_detail);
	}
	
	  /** 
     * 检查手机上是否安装了指定的软件 
     * @param context 
     * @param packageName：应用包名 
     * @return 
     */  
	   private boolean isAvilible(Context context, String packageName){   
	        //获取packagemanager   
	        final PackageManager packageManager = context.getPackageManager();  
	      //获取所有已安装程序的包信息   
	        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);  
	      //用于存储所有已安装程序的包名   
	        List<String> packageNames = new ArrayList<String>();  
	        //从pinfo中将包名字逐一取出，压入pName list中   
	        if(packageInfos != null){   
	            for(int i = 0; i < packageInfos.size(); i++){   
	                String packName = packageInfos.get(i).packageName;   
	                packageNames.add(packName);   
	            }   
	        }   
	      //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE   
	        return packageNames.contains(packageName);  
	  } 
}
