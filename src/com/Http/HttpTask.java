package com.Http;

import java.util.HashMap;

import org.json.JSONObject;

import com.IntimateCarCare.LoginActivity;
import com.IntimateCarCare.R;
import com.tool.LoadingDialog;


import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

public class HttpTask extends AsyncTask<HashMap<String, String>, Integer, JSONObject> {

	private HttpCallback callback = null;
	private LoadingDialog dialog;
	
	public void setHttpCallback(HttpCallback callback) {
		this.callback = callback;
	}
	
	private String servlet = null;
	
	public void setServlet(String servlet)
	{
		this.servlet = servlet;
	}
	
	public HttpTask() {}
	
	public HttpTask(HttpCallback callback, String servlet,Context context) {
		this.callback = callback;
		this.servlet = servlet;
		
		dialog = new LoadingDialog(context, R.layout.view_tips_loading);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	
	@Override
	protected JSONObject doInBackground(HashMap<String, String>... arg0) {
		// TODO Auto-generated method stub
	
		String result = new HttpConnect().connectPost(servlet, arg0[0]);
		
		JSONObject json = null;
		try {
			if(result != null)
				json = new JSONObject(result);
			    System.out.println("json-------" + json);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return json;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		if(result != null)
		{
			if(callback != null)
				dialog.dismiss();
				callback.getResult(result);
		}
	}
	
	

}
