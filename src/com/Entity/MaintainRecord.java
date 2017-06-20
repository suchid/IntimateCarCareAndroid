package com.Entity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.IntimateCarCare.ChangeMaintenance;
import com.tool.SPUtils;

/**
 * @author:LinHaiZhen
 * @date:2016年11月21日 下午8:44:35
 * @Description:保养记录
 */
public class MaintainRecord {

	private int mrecord_id;			//保养记录id 
	private int cycle;				//保养里程
	//private String gap;				//保养间隔
	private String item_desc;		//保养项目描述（用逗号隔开的保养项目字符串）(中文逗号)
	private int money;			  //保养费用
	private String time;			//保养时间
	private String remark_c;		//备注文本
	
	private String cre_time;		//创建日期
	private int car_id;				//车子id
	
	
	public MaintainRecord() {
		super();
	}
	
	public MaintainRecord(JSONObject json,int i){
		
		if(i==1){
		try {
			this.mrecord_id=json.getInt("mrecord_id");
			this.cycle=json.getInt("cycle");
			this.item_desc=json.getString("item_desc");
			this.money=json.getInt("money");
			this.remark_c=json.getString("remark_c");
			this.time=json.getString("time");
			this.cre_time=json.getString("cre_time");
							
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		}
	}
	public MaintainRecord(String creattime, int cycle2, String tv_priject,
			int money2, String remark_c2) {
		// TODO Auto-generated constructor stub
		this.cre_time=creattime;
		this.cycle=cycle2;
		this.item_desc=tv_priject;	
		this.money=money2;
		this.remark_c=remark_c2;
		
	}
	public HashMap<String, String>addmaintainrecord(Context context, String CreatTime,int FeiYong,String Licheng,String XiangMu,String BeiZhu){
		HashMap<String, String>map=new HashMap<String, String>();
		map.put("time", CreatTime);
		map.put("money", FeiYong+"");
		map.put("cycle", Licheng);
		map.put("item_desc", XiangMu);
		map.put("remark_c", BeiZhu);
		map.put("mrecord_id", SPUtils.get(context, "mrecord", -1).toString());
    	map.put("user_id", SPUtils.get(context, "userId", -1).toString());
		map.put("tokens",SPUtils.get(context, "userTokens", "").toString());
		return map;
		
	}
	public int getMrecord_id() {
		return mrecord_id;
	}
	public void setMrecord_id(int mrecord_id) {
		this.mrecord_id = mrecord_id;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
//	public String getGap() {
//		return gap;
//	}
//	public void setGap(String gap) {
//		this.gap = gap;
//	}
	public String getItem_desc() {
		return item_desc;
	}
	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRemark_c() {
		return remark_c;
	}
	public void setRemark_c(String remark_c) {
		this.remark_c = remark_c;
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
