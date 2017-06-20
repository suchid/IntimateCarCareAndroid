package com.Entity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.tool.SPUtils;

import android.content.Context;

public class Car {
	
	private int car_id;           	//车子id
	private String car_num;		  	//车牌
	private String arctic_name;   	//车型名称
	private String brand_name;    	//品牌名称
	private int brand_id;           //品牌id
	private String car_pic;       	//车子图片
	private String car_color;		//车辆颜色
	private String vin_num;       	//vin码（车架号）
	private String gearbox;       	//变速箱
	private String displacement;  	//排量
	private String copy;          	//年款
	private String fuel_tank;     	//油箱容量
	private String mileage;       	//当前里程
	private String engine_num;    	//发动机号
	private String insurance_date;	//保险到期日期
	private String annual_date;     //年检到期日期
	private int red_num;			//红包数目
	private String  box_sn;            //序列号
	
	private int money ;//车辆购买金额

	private String brand_logo;        //车辆logo图片
	private String box_password;      //盒子密码


	private boolean bound_box;		//是否绑定盒子
	private String bound_stime;		//绑定开始日期
	private  String sortLetters;  //汽车的首字母

	public Car(){}

	public Car(String string,int i){
		if(i==1){
		this.car_num=string;
		}	
		else if(i==2){
			this.arctic_name=string;
		}
	}
    
	public Car(String brand_logo,String string){
		this.car_num=string;
		this.brand_logo=brand_logo;
	}
	public Car(JSONObject jsonObject,int i) {
		// TODO Auto-generated constructor stub
		try {
			if(i==0){//车辆列表数据
				this.car_id=jsonObject.getInt("car_id");
				this.car_num=jsonObject.getString("car_num");
				this.car_pic=jsonObject.getString("car_pic");
				this.bound_box=jsonObject.getBoolean("bound_box");
			}else if(i==1){//我的页面数据
				this.brand_name=jsonObject.getString("brand_name");
				this.arctic_name=jsonObject.getString("arctic_name");
				this.mileage=jsonObject.getString("mileage");
				if(this.mileage.equals("null")){
					this.mileage="0";
				}
				this.brand_logo=jsonObject.getString("brand_logo");
				this.car_num=jsonObject.getString("car_num");
				this.red_num=jsonObject.getInt("red_num");
				this.car_id=jsonObject.getInt("car_id");
			}
			else if(i==2){
				this.car_id=jsonObject.getInt("car_id");
				this.car_num=jsonObject.getString("car_num");
				this.brand_name=jsonObject.getString("brand_name");
				this.arctic_name=jsonObject.getString("arctic_name");
				JSONObject j=jsonObject.getJSONObject("box");
				if(j.has("sn_num")){
					this.box_sn=j.getString("sn_num");
				}
				this.brand_logo=jsonObject.getString("brand_logo");
			}
			else if(i==3){
				this.brand_id=jsonObject.getInt("brand_id");
				this.brand_name=jsonObject.getString("brand_name");
			}
			else if(i==4){
				this.arctic_name=jsonObject.getString("arctic_name");
			}
			else if(i==5){
				this.brand_name=jsonObject.getString("brand_name");
				this.arctic_name=jsonObject.getString("arctic_name");
				this.mileage=jsonObject.getString("mileage");
				this.brand_logo=jsonObject.getString("brand_logo");
				this.car_num=jsonObject.getString("car_num");
				this.red_num=jsonObject.getInt("red_num");
				this.car_id=jsonObject.getInt("car_id");
				this.vin_num=jsonObject.getString("vin_num");
				this.gearbox=jsonObject.getString("gearbox");
				this.displacement=jsonObject.getString("displacement");
				this.copy=jsonObject.getString("copy");
				this.money=jsonObject.getInt("money");
			
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, String>selectarctic_name(Context context,int Brand_Id){
		HashMap<String, String>map=new HashMap<String, String>();
		map.put("user_id", SPUtils.get(context, "userId", -1).toString());
		map.put("tokens",SPUtils.get(context, "userTokens", "").toString());
		map.put("brand_id", Brand_Id+"");
		return map;
	}
	
	public HashMap<String, String>changebox(Context context,int xuliehao,int mima){
		HashMap<String, String>map=new HashMap<String, String>();
		map.put("user_id", SPUtils.get(context, "userId", -1).toString());
		map.put("tokens",SPUtils.get(context, "userTokens", "").toString());
		map.put("box_sn", xuliehao+"");
		map.put("box_password", mima+"");
		return map;
	}
	
	public HashMap<String, String>changecarconfigurat(Context context,String Brangs,String CheXing,
		String Vin,String BianSu,String PaiLiang,String NianKuan,String RongLiang
		,String LiCheng){
		HashMap<String, String>map=new HashMap<String, String>();
		map.put("user_id", SPUtils.get(context, "userId", -1).toString());
		map.put("tokens",SPUtils.get(context, "userTokens", "").toString());
		map.put("brand_name", Brangs);
		map.put("arctic_name", CheXing);
		map.put("vin_num", Vin);
		map.put("gearbox", BianSu);
		map.put("displacement", PaiLiang);
		map.put("copy", NianKuan);
		map.put("fuel_tank", RongLiang);
		map.put("mileage", LiCheng);
		return map;
	}
	public HashMap<String, String>changecarnumber(Context context,String CarNumber){
		HashMap<String, String>map=new HashMap<String, String>();
		map.put("user_id", SPUtils.get(context, "userId", -1).toString());
		map.put("tokens",SPUtils.get(context, "userTokens", "").toString());
		map.put("car_num", CarNumber);
		return map;
	}
	
	public HashMap<String, String>addnewcar(Context context,String carbum,
		String carbrands,String carchexing,String carbianshuxiang,String carpailiang){
		HashMap<String, String>map=new HashMap<String, String>();
		map.put("user_id", SPUtils.get(context, "userId", -1).toString());
		map.put("tokens",SPUtils.get(context, "userTokens", "").toString());
		map.put("car_num", carbum);
		map.put("brand_name", carbrands);
		map.put("arctic_name", carchexing);
		map.put("gearbox", carbianshuxiang);
		map.put("displacement", carpailiang);
		return map;
	}
	
	public String getBrand_logo() {
		return brand_logo;
	}

	public void setBrand_logo(String brand_logo) {
		this.brand_logo = brand_logo;
	}

	public int getCar_id() {
		return car_id;
	}


	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}


	public String getCar_num() {
		return car_num;
	}


	public void setCar_num(String car_num) {
		this.car_num = car_num;
	}
	public String getBox_password() {
		return box_password;
	}
  
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void setBox_password(String box_password) {
		this.box_password = box_password;
	}

	public String getArctic_name() {
		return arctic_name;
	}


	public void setArctic_name(String arctic_name) {
		this.arctic_name = arctic_name;
	}
	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}


	public String getBrand_name() {
		return brand_name;
	}


	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}


	public String getCar_pic() {
		return car_pic;
	}


	public void setCar_pic(String car_pic) {
		this.car_pic = car_pic;
	}


	public String getCar_color() {
		return car_color;
	}


	public void setCar_color(String car_color) {
		this.car_color = car_color;
	}


	public String getVin_num() {
		return vin_num;
	}


	public void setVin_num(String vin_num) {
		this.vin_num = vin_num;
	}


	public String getGearbox() {
		return gearbox;
	}


	public void setGearbox(String gearbox) {
		this.gearbox = gearbox;
	}


	public String getDisplacement() {
		return displacement;
	}


	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}


	public String getCopy() {
		return copy;
	}


	public void setCopy(String copy) {
		this.copy = copy;
	}


	public String getFuel_tank() {
		return fuel_tank;
	}


	public void setFuel_tank(String fuel_tank) {
		this.fuel_tank = fuel_tank;
	}


	public String getMileage() {
		return mileage;
	}


	public void setMileage(String mileage) {
		this.mileage = mileage;
	}


	public String getEngine_num() {
		return engine_num;
	}


	public void setEngine_num(String engine_num) {
		this.engine_num = engine_num;
	}


	public String getInsurance_date() {
		return insurance_date;
	}


	public void setInsurance_date(String insurance_date) {
		this.insurance_date = insurance_date;
	}


	public String getAnnual_date() {
		return annual_date;
	}


	public void setAnnual_date(String annual_date) {
		this.annual_date = annual_date;
	}


	public int getRed_num() {
		return red_num;
	}


	public void setRed_num(int red_num) {
		this.red_num = red_num;
	}

	public boolean isBound_box() {
		return bound_box;
	}


	public void setBound_box(boolean bound_box) {
		this.bound_box = bound_box;
	}


	public String getBound_stime() {
		return bound_stime;
	}


	public void setBound_stime(String bound_stime) {
		this.bound_stime = bound_stime;

	}

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;

	}

	public String getBox_sn() {
		return box_sn;
	}

	public void setBox_sn(String box_sn) {
		this.box_sn = box_sn;

	};
	
	
	
	
}
