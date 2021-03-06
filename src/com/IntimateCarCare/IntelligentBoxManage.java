package com.IntimateCarCare;

import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.Bll.CBLL;
import com.Entity.Car;
import com.Entity.UserEntity;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.CustomDialog;
import com.tool.Dialogchangebox;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.ToastUtil;

public class IntelligentBoxManage extends Activity {

	private Dialogchangebox dialog;
	private ImageView img_box_back;
	private TextView changebox, zhuxiaobox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_intelligentboxmanage);
		initview();
		setListen();
	}

	private void setListen() {
		// TODO Auto-generated method stub
		img_box_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		changebox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomDialog.Builder builder = new CustomDialog.Builder(
						IntelligentBoxManage.this);
				builder.setMessage("更换盒子成功后，新golo盒子会保留旧golo盒子的各种设置，请确认是否更换")
						// 设置显示的内容
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										// TODO Auto-generated method stub

										// HashMap<String,String>changejialing=new
										// UserEntity().changenicheng(SPUtils.get(MyinfoActivity.this,
										// "userId",
										// -1).toString(),SPUtils.get(MyinfoActivity.this,
										// "userTokens", "").toString(),str,5);
										// new
										// HttpTask(sexcallback,MyURL.CHANGEINFOR).execute(changejialing);
										dialog();

									}

								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								}).create().show();
			}
		});

		zhuxiaobox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				CustomDialog.Builder builder = new CustomDialog.Builder(
						IntelligentBoxManage.this);
				builder.setMessage("注销盒子后，golo盒子原来的历史数据和设置将不再保留，请确认是否注销")
						// 设置显示的内容
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@SuppressWarnings("unchecked")
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
										HashMap<String, String> changejialing = new UserEntity()
												.getIdTaken(IntelligentBoxManage.this);
										new HttpTask(surecallback,
												MyURL.CHANGEINFOR,IntelligentBoxManage.this)
												.execute(changejialing);

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								}).create().show();

			}
		});
	}

	private void dialog() {
		// TODO Auto-generated method stub

		dialog = new Dialogchangebox(IntelligentBoxManage.this);
		final EditText editText1 = (EditText) dialog.getEditText1();// 方法在CustomDialog中实现
		final EditText editText2 = (EditText) dialog.getEditText2();// 方法在CustomDialog中实现
		final String zhanghao = editText1.getText().toString();
		final String mima = editText2.getText().toString();

		dialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.setOnNegativeListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String, String> changebox = new Car().changebox(
						IntelligentBoxManage.this, Integer.parseInt(zhanghao),
						Integer.parseInt(mima));
				// new HttpTask(changboxcallbac, MyURL.).execute(changebox);
			}
		});
		dialog.show();

	}

	HttpCallback changboxcallbac = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub

			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2changeifon(json);
			if (entity.isFlag()) {
				ToastUtil.show(IntelligentBoxManage.this, "盒子更换成功");
			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(IntelligentBoxManage.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity.restartApplication(IntelligentBoxManage.this);
				}
			}

		}
	};
	HttpCallback surecallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2changeifon(json);
			if (entity.isFlag()) {
				ToastUtil.show(IntelligentBoxManage.this, "注销成功");
			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(IntelligentBoxManage.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity.restartApplication(IntelligentBoxManage.this);
				}
			}

		}
	};

	private void initview() {
		// TODO Auto-generated method stub
		zhuxiaobox = (TextView) findViewById(R.id.zhuxiaobox);
		img_box_back = (ImageView) findViewById(R.id.img_box_back);
		changebox = (TextView) findViewById(R.id.changebox);
	}
}
