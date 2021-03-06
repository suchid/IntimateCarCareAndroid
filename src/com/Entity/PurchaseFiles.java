package com.Entity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.tool.SPUtils;

/**
 * @author:LinHaiZhen
 * @date:2016年11月20日 下午9:20:38
 * @Description:购买档案
 */
public class PurchaseFiles {

	private int money;				//购买金额（分）
	private String date;			//购买日期
	private String work_unit;       //购买单位
	private String unit_tel;        //购买单位联系电话

	private int car_id;

  public PurchaseFiles(){}
  public HashMap<String, String>changepurchase(Context context,int Money,String Date,String work_unit,String Unit_tel){
	HashMap<String, String>map=new HashMap<String, String>();
	map.put("money",Money+"");
	map.put("date", Date);
	map.put("work_unit", work_unit);
	map.put("unit_tel", Unit_tel);
	map.put("user_id",SPUtils.get(context, "userId", -1).toString());
 	map.put("tokens",SPUtils.get(context, "userTokens", "").toString());
   return map;
	 
 }	
 public PurchaseFiles(JSONObject json){
	 try {
		 
		 if(json.has("money")){
		this.money=json.getInt("money");
		 }else{
			 this.money=-1;
		 }
		 if(json.has("date")){
			 this.date=json.getString("date");
		 }else{
			 this.date="";
		 }
		if(json.has("work_unit")){
			this.work_unit=json.getString("work_unit");
		}else{
			this.work_unit="";
		}
		if(json.has("unit_tel")){
			this.unit_tel=json.getString("unit_tel");
		}else{
			this.unit_tel="";
		}
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWork_unit() {
		return work_unit;
	}

	public void setWork_unit(String work_unit) {
		this.work_unit = work_unit;
	}

	public String getUnit_tel() {
		return unit_tel;
	}

	public void setUnit_tel(String unit_tel) {
		this.unit_tel = unit_tel;
	}

	public int getCar_id() {
		return car_id;
	}

	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}
	
	
	
	
}
