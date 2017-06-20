package com.Entity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.IntimateCarCare.ChangeInsuranceInformation;
import com.tool.SPUtils;

/**
 * @author:LinHaiZhen
 * @date:2016年11月21日 下午8:47:37
 * @Description:保险记录
 */
public class InsuranceRecord {

	private int irecord_id;			//保险记录id
	private String insurance_num;	//交强险单号
	private String insurance_unit;	//投保单位
	private int  money;			//保险费用
	private String remark_c;		//备注文本
	private String end_date;		//到期日	
	private String cre_time;		//创建日期
	private int car_id;		//车子id
	
	public InsuranceRecord(){}
	public InsuranceRecord(String string2,String string3,String string4,int string5,String string6){
		
	
		this.insurance_num=string2;
		this.insurance_unit=string3;	
		this.end_date=string4;
		this.money=string5;
		this.remark_c=string6;
	}
	
	public InsuranceRecord(JSONObject jsonObject,int i) {
		// TODO Auto-generated constructor stub
		
			try {
				if(i==0){
				this.cre_time=jsonObject.getString("cre_time");
				this.insurance_num=jsonObject.getString("insurance_num");
				this.money=jsonObject.getInt("money");
				this.remark_c=jsonObject.getString("remark_c");
				
				this.end_date=jsonObject.getString("end_date");
				this.cre_time=jsonObject.getString("cre_time");
				this.car_id=jsonObject.getInt("car_id");
				this.irecord_id=jsonObject.getInt("irecord_id");
				this.insurance_unit=jsonObject.getString("insurance_unit");
				
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

    public HashMap<String, String>sendinsurance(Context context,String Insurance_Num,String Insurance_Unit,String End_Date,int Money,String remark_c){
    	HashMap<String, String>map=new HashMap<String, String>();
    	map.put("insurance_num", Insurance_Num);
    	map.put("insurance_unit", Insurance_Unit);
    	map.put("end_date", End_Date);
    	map.put("money", Money+"");
    	map.put("remark_c",remark_c);
    	map.put("user_id", SPUtils.get(context, "userId", -1).toString());
    	map.put("tokens",SPUtils.get(context, "userTokens", "").toString());
		return map;  	
    }
	
    public HashMap<String, String>changeinsurance(Context context,String Insurance_Num,String Insurance_Unit,String End_Date,int Money,String remark_c){
    	HashMap<String, String>map=new HashMap<String, String>();
    	map.put("insurance_num", Insurance_Num);
    	map.put("insurance_unit", Insurance_Unit);
    	map.put("end_date", End_Date);
    	map.put("money", Money+"");
    	map.put("remark_c",remark_c); 
    	map.put("irecord_id", SPUtils.get(context, "irecordid", -1).toString());
    	map.put("user_id", SPUtils.get(context, "userId", -1).toString());
		map.put("tokens",SPUtils.get(context, "userTokens", "").toString());
		return map;  	
    }  
	public int getIrecord_id() {
		return irecord_id;
	}
	public void setIrecord_id(int irecord_id) {
		this.irecord_id = irecord_id;
	}
	public String getInsurance_num() {
		return insurance_num;
	}
	public void setInsurance_num(String insurance_num) {
		this.insurance_num = insurance_num;
	}
	public String getInsurance_unit() {
		return insurance_unit;
	}
	public void setInsurance_unit(String insurance_unit) {
		this.insurance_unit = insurance_unit;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getRemark_c() {
		return remark_c;
	}
	public void setRemark_c(String remark_c) {
		this.remark_c = remark_c;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getCre_time() {
		return cre_time;
	}
	public void setCre_time(String cre_time) {
		this.cre_time = cre_time;
	}
	public int getCar_id() {
		return car_id;
	}
	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}
	
	
	
}
