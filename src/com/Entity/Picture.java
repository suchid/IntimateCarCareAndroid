package com.Entity;

public class Picture {

	private int picture_id;		//图片id
	private String url="";			//图片地址
	private int location;		//图片位置
	private String format;		//图片格式
	private String size;		//图片大小
	private int type;			//类型
	
	private String cre_time;	//创建时间

	public int getPicture_id() {
		return picture_id;
	}

	public void setPicture_id(int picture_id) {
		this.picture_id = picture_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCre_time() {
		return cre_time;
	}

	public void setCre_time(String cre_time) {
		this.cre_time = cre_time;
	}
	
	
	
	
	
}
