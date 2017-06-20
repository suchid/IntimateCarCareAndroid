package com.IntimateCarCare;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.Adapter.ActivityHistoryCarReportListAdapter;
import com.Bll.CBLL;
import com.Entity.CarReportBrief;
import com.Entity.CarReportBriefSet;
import com.Entity.UserEntity;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.ToastUtil;

public class HistoryCarReportListActivity extends Activity {

	private LinearLayout lin_back;
	// private ListView listview;
	private PullToRefreshListView refreshlistview;
	private CarReportBriefSet list;
	private ActivityHistoryCarReportListAdapter adapter;
	private HashMap<String, String> map;

	private int PAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_historycarreportlist);

		initView();
		RequestData();
		setListen();

	}

	private void initView() {
		// TODO Auto-generated method stub
		lin_back = (LinearLayout) findViewById(R.id.lin_back);
		refreshlistview = (PullToRefreshListView) findViewById(R.id.refreshlistview);
		refreshlistview.setMode(Mode.BOTH);// 可上拉下拉

	}

	@SuppressWarnings("unchecked")
	private void RequestData() {
		// TODO Auto-generated method stub
		map = new UserEntity().getIdTaken(HistoryCarReportListActivity.this);
		map.put("page", "1");
		map.put("num", "10");
		new HttpTask(historycarreportlistCallback, MyURL.HISTORYCARREPORTLIST,HistoryCarReportListActivity.this)
				.execute(map);

	}

	// 获取更多分页数据
	@SuppressWarnings("unchecked")
	private void Requestmore(int i) {
		map.put("page", i + "");
		new HttpTask(requestmoreCallback, MyURL.HISTORYCARREPORTLIST,HistoryCarReportListActivity.this)
				.execute(map);
	}

	private HttpCallback requestmoreCallback = new HttpCallback() {
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2historycarreportlist(json);
			if (entity.isFlag()) {
				CarReportBriefSet l = (CarReportBriefSet) entity.getData();
				if (PAGE > (Integer) entity.getData1()) {// 判断是否是最后一页
					refreshlistview.onRefreshComplete();
					ToastUtil.show(HistoryCarReportListActivity.this, "没有更多");
					refreshlistview.setMode(Mode.PULL_FROM_START);// 只能下拉刷新
				} else {// 还有更多数据
					List<CarReportBrief> li = list.getBriefList();
					li.addAll(l.getBriefList());
					list = new CarReportBriefSet(li);
					adapter.notifyDataSetChanged();
					refreshlistview.onRefreshComplete();
				}
			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(HistoryCarReportListActivity.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity
							.restartApplication(HistoryCarReportListActivity.this);
				}
			}
		}
	};

	private HttpCallback historycarreportlistCallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2historycarreportlist(json);
			if (entity.isFlag()) {
				list = (CarReportBriefSet) entity.getData();
				adapter = new ActivityHistoryCarReportListAdapter(
						HistoryCarReportListActivity.this, list);
				refreshlistview.setAdapter(adapter);
				refreshlistview.onRefreshComplete();
				if (refreshlistview.getMode() == Mode.PULL_FROM_START) {
					refreshlistview.setMode(Mode.BOTH);// 可上拉下拉
				}

			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(HistoryCarReportListActivity.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity
							.restartApplication(HistoryCarReportListActivity.this);
				}
			}
		}
	};

	private void setListen() {
		// TODO Auto-generated method stub
		refreshlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
					Intent intent = new Intent(
							HistoryCarReportListActivity.this,
							HistoryCarReportDetail.class);
					intent.putExtra("reportid", list.getItem(position - 1)
							.getReport_id());
					startActivity(intent);
				}

			}

		});

		refreshlistview
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						RequestData();
						PAGE = 1;
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						PAGE++;
						Requestmore(PAGE);
					}

				});

		lin_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

}
