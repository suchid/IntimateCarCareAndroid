package com.IntimateCarCare;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.image.ImageOptions;

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
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Bll.CBLL;
import com.Entity.Car;
import com.Entity.Goods;
import com.Entity.Merchant;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.JSONEntity;
import com.tool.MyURL;

public class Shopping_Detail extends Activity{
 private RelativeLayout rel_onlyshop;
 private String goodsid,merchantid;
 private Merchant merchantentity;
 private ImageView img_shangcheng_back,shangjia_image;
 private TextView tv_shangpinname,shoping_prive,shangjia_name,shangjia_tel,shangjia_address,
 tv_abstrb,tv_licheng,textView1,textView2,carname_shopitem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shoppingdetail);
	
	    
		initview();
		setListen();
	  
	
		RequestMainData();
	}
	
	@SuppressWarnings("unchecked")
	private void RequestMainData() {
		// TODO Auto-generated method stub
		   Intent intent=getIntent();	
		   merchantid=intent.getIntExtra("merchantid",-1)+"";
		   goodsid=intent.getIntExtra("goodsid",-1)+"";
		 HashMap<String, String> shoppingdetail=new Goods().getgoodsdetail(Shopping_Detail.this);
		 shoppingdetail.put("goods_id",goodsid);
		 shoppingdetail.put("merchant_id",merchantid);
	     //获得商品有关数据

	     new HttpTask(allgoodsCallback, MyURL.GOODSDETAIL,Shopping_Detail.this).execute(shoppingdetail);
	}
	
	
	HttpCallback allgoodsCallback=new HttpCallback() {
		
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub	
			CBLL CBllmerchantdetail =CBLL.getInstance();
			JSONEntity entity =CBllmerchantdetail.json2goodsdetail(json);
			
			if(entity.isFlag()){
				Goods goodsentity =(Goods) entity.getData();
			    merchantentity =(Merchant) entity.getData1();
			    Car mm= (Car) entity.getData2();
				String km=mm.getMileage();
				
			    carname_shopitem.setText(mm.getArctic_name()+  mm.getBrand_name());
				tv_shangpinname.setText(goodsentity.getName());
				shoping_prive.setText(goodsentity.getMoney()+"");
				shangjia_name.setText(merchantentity.getName());
				shangjia_tel.setText(merchantentity.getTel());
				if(merchantentity.getAddress().equals("")){
					shangjia_address.setText("地址不详");
				}else{
					shangjia_address.setText(merchantentity.getAddress());
				}
				
				tv_abstrb.setText(goodsentity.getDesc());
				if(km.equals("null")){
			    tv_licheng.setText("");
				}else{		
			    tv_licheng.setText(km);	
				}
				
				
				ImageOptions options;
				options=new ImageOptions.Builder()
				.setImageScaleType(ScaleType.CENTER_INSIDE)
				.build();
				 if(!goodsentity.getPictures().getUrl().isEmpty()){
						x.image().bind(shangjia_image, goodsentity.getPictures().getUrl(),options);
					}
						else{
							shangjia_image.setImageResource(R.drawable.shangpin_image);
						}
			}
		}
	};
	
	private void initview() {
		// TODO Auto-generated method stub
		carname_shopitem=(TextView) findViewById(R.id.carname_shopitem);
		textView2=(TextView) findViewById(R.id.textView2);
		textView1=(TextView) findViewById(R.id.textView1);
		rel_onlyshop=(RelativeLayout) findViewById(R.id.rel_onlyshop);
		tv_licheng=(TextView) findViewById(R.id.tv_licheng);
		img_shangcheng_back=(ImageView)findViewById(R.id.img_shangcheng_back);
		tv_shangpinname=(TextView)findViewById(R.id.tv_shangpinname);
		shoping_prive=(TextView)findViewById(R.id.shoping_prive);
		shangjia_name=(TextView)findViewById(R.id.shangjia_name);
		shangjia_tel=(TextView)findViewById(R.id.shangjia_tel);
		shangjia_address=(TextView)findViewById(R.id.shangjia_address);
		tv_abstrb=(TextView)findViewById(R.id.tv_abstrb);
		shangjia_image=(ImageView)findViewById(R.id.shangjia_image);
	}
	private void setListen() {
		// TODO Auto-generated method stub
		textView2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(isAvilible(Shopping_Detail.this,"com.baidu.BaiduMap")){//传入指定应用包名  
		                Intent intent = null;  
		                try {  
		                	 String name=merchantentity.getName();
						     String jingdu=merchantentity.getLongtude();
						     String weidu=merchantentity.getLatitude();
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
		textView1.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phone_number = merchantentity.getTel();
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri
						.parse("tel:" + phone_number));
				startActivity(intent);	
			}
		});
		rel_onlyshop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent=new Intent(Shopping_Detail.this,Shangjia_Detail.class);
			intent.putExtra("tag", "true");//如果是true 则是在商品详情跳过去的
			intent.putExtra("merchantid", merchantentity.getMerchant_id());
			System.out.println("KKKKKKKKKKKK"+merchantentity.getMerchant_id());
			startActivity(intent);
			}
		});
		img_shangcheng_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		
	}
	
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
