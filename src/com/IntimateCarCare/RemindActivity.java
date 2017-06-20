package com.IntimateCarCare;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Bll.CBLL;
import com.Entity.Remind;
import com.Entity.UserEntity;
import com.Fragment.FirstRemindFragment;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.ToastUtil;

public class RemindActivity extends FragmentActivity {

	private LinearLayout lin_back;
	private RelativeLayout rel_first, rel_second, rel_third;
	private TextView tv_history, tv_first, tv_second, tv_third, tv_firstnum,
			tv_secondnum, tv_thirdnum;
	private View vi_first, vi_second, vi_third;
	private int a = 0, b = 0, c = 0;// 用于消息计数

	// fragment
	private FirstRemindFragment fg1, fg2, fg3;
	private FragmentManager fManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_remind);
		fManager = getSupportFragmentManager();

		initView();
		RequestData();
		setListen();

	}

	private void initView() {
		// TODO Auto-generated method stub

		lin_back = (LinearLayout) findViewById(R.id.lin_back);
		tv_history = (TextView) findViewById(R.id.tv_history);
		tv_first = (TextView) findViewById(R.id.tv_first);
		tv_second = (TextView) findViewById(R.id.tv_second);
		tv_third = (TextView) findViewById(R.id.tv_third);
		vi_first = findViewById(R.id.vi_first);
		vi_second = findViewById(R.id.vi_second);
		vi_third = findViewById(R.id.vi_third);
		rel_first = (RelativeLayout) findViewById(R.id.rel_first);
		rel_second = (RelativeLayout) findViewById(R.id.rel_second);
		rel_third = (RelativeLayout) findViewById(R.id.rel_third);
		tv_firstnum = (TextView) findViewById(R.id.tv_firstnum);
		tv_secondnum = (TextView) findViewById(R.id.tv_secondnum);
		tv_thirdnum = (TextView) findViewById(R.id.tv_thirdnum);

		setChoiceItem(R.id.rel_first);
	}

	@SuppressWarnings("unchecked")
	private void RequestData() {
		// TODO Auto-generated method stub
		HashMap<String, String> idtakjson = new UserEntity()
				.getIdTaken(RemindActivity.this);
		new HttpTask(remindnumCallback, MyURL.REMINDNUM,RemindActivity.this).execute(idtakjson);
	}

	private HttpCallback remindnumCallback = new HttpCallback() {
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2remindnum(json);
			if (entity.isFlag()) {
				@SuppressWarnings("unchecked")
				List<Remind> list = (List<Remind>) entity.getData();
				for (int i = 0; i < list.size(); i++) {
					Remind remind = list.get(i);
					switch (remind.getLevel()) {
					// case 1:
					// tv_firstnum.setVisibility(View.VISIBLE);
					// tv_firstnum.setText(remind.getNum()+"");
					// break;
					case 2:
						tv_secondnum.setVisibility(View.VISIBLE);
						b = remind.getNum();
						tv_secondnum.setText(b + "");
						break;

					case 3:
						tv_thirdnum.setVisibility(View.VISIBLE);
						c = remind.getNum();
						tv_thirdnum.setText(c + "");
						break;

					default:
						break;
					}

				}

			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(RemindActivity.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity.restartApplication(RemindActivity.this);
				}
			}
		}
	};

	private void setListen() {
		// TODO Auto-generated method stub
		lin_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				int i = b + c;
				intent.putExtra("remindnum", i + "条未读");
				setResult(1, intent);
				finish();
			}
		});

		rel_first.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setChoiceItem(v.getId());
			}
		});

		rel_second.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setChoiceItem(v.getId());
			}
		});

		rel_third.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setChoiceItem(v.getId());
			}
		});

	}

	// 定义一个选中一个item后的处理
	@SuppressLint("ResourceAsColor")
	public void setChoiceItem(int resourseId) {
		// 隐藏所有的Fragment
		FragmentTransaction transaction = fManager.beginTransaction();
		hideFragments(transaction);

		switch (resourseId) {
		case R.id.rel_first:
			tv_first.setTextColor(Color.RED);
			tv_second.setTextColor(getResources().getColor(
					R.color.textgraylight));
			tv_third.setTextColor(getResources()
					.getColor(R.color.textgraylight));
			vi_first.setBackgroundColor(Color.RED);
			vi_second.setBackgroundColor(getResources().getColor(
					R.color.textgraylight));
			vi_third.setBackgroundColor(getResources().getColor(
					R.color.textgraylight));
			tv_firstnum.setVisibility(View.GONE);
			if (fg1 == null) {
				fg1 = new FirstRemindFragment();
				// 发送等级1，2，3用于获取数据
				Bundle bundle = new Bundle();
				bundle.putInt("level", 1);
				fg1.setArguments(bundle);
				transaction.add(R.id.content, fg1);

			} else {
				// 如果Fragment不为空，则直接将他显示出来
				transaction.show(fg1);
			}
			break;

		case R.id.rel_second:
			tv_first.setTextColor(getResources()
					.getColor(R.color.textgraylight));
			tv_second.setTextColor(Color.YELLOW);
			tv_third.setTextColor(getResources()
					.getColor(R.color.textgraylight));
			vi_first.setBackgroundColor(getResources().getColor(
					R.color.textgraylight));
			vi_second.setBackgroundColor(Color.YELLOW);
			vi_third.setBackgroundColor(getResources().getColor(
					R.color.textgraylight));
			tv_secondnum.setVisibility(View.GONE);
			b = 0;
			if (fg2 == null) {
				fg2 = new FirstRemindFragment();
				// 发送等级1，2，3用于获取数据
				Bundle bundle = new Bundle();
				bundle.putInt("level", 2);
				fg2.setArguments(bundle);
				transaction.add(R.id.content, fg2);

			} else {

				transaction.show(fg2);
			}
			break;
		case R.id.rel_third:
			tv_first.setTextColor(getResources()
					.getColor(R.color.textgraylight));
			tv_second.setTextColor(getResources().getColor(
					R.color.textgraylight));
			tv_third.setTextColor(Color.GREEN);
			vi_first.setBackgroundColor(getResources().getColor(
					R.color.textgraylight));
			vi_second.setBackgroundColor(getResources().getColor(
					R.color.textgraylight));
			vi_third.setBackgroundColor(Color.GREEN);
			tv_thirdnum.setVisibility(View.GONE);
			c = 0;
			if (fg3 == null) {
				fg3 = new FirstRemindFragment();
				// 发送等级1，2，3用于获取数据
				Bundle bundle = new Bundle();
				bundle.putInt("level", 3);
				fg3.setArguments(bundle);
				transaction.add(R.id.content, fg3);
			} else {
				transaction.show(fg3);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}

	// 隐藏所有的Fragment，避免Fragment混乱
	private void hideFragments(FragmentTransaction transaction) {
		if (fg1 != null) {
			transaction.hide(fg1);
		}
		if (fg2 != null) {
			transaction.hide(fg2);
		}
		if (fg3 != null) {
			transaction.hide(fg3);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		int i = b + c;
		intent.putExtra("remindnum", i + "条未读");
		setResult(1, intent);
		super.onBackPressed();
	}

}
