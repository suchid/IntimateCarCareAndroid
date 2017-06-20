package com.IntimateCarCare;

import java.util.ArrayList;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.Adapter.ActivityCommodityNewsadapter;
import com.Bll.CBLL;
import com.Entity.Car;
import com.Entity.Goods;
import com.Entity.GoodsSet;
import com.Entity.Merchant;
import com.Entity.UserEntity;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.SPUtils;
import com.tool.ToastUtil;

public class CommodityNewsActivity extends Activity {

	private PullToRefreshListView refreshlistview;
	private ImageView img_shangpin_detail_bac;
	private GoodsSet list;
	private ActivityCommodityNewsadapter adapter;
	private HashMap<String, String> map;
	private LinearLayout lin_bac;
	// 自定义翻页有关数据
	private int PAGE = 1;
	private int visibleLastIndex; // 用来可显示的最后一条数据的索引值
	private boolean moredata = true;// 用来判断页数时候要用到的
	private boolean firstdata = true;

	private TextView carname_shopitem, car_km_shopitem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_commoditynews);

		RequestMainData();
		initview();
		setListen();
	}

	private void setListen() {
		// TODO Auto-generated method stub
		lin_bac.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		refreshlistview
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						RequestMainData();
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

		refreshlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CommodityNewsActivity.this,
						Shopping_Detail.class);

				intent.putExtra("merchantid", list.getItem(arg2 - 1)
						.getMerchant().getMerchant_id());
				intent.putExtra("goodsid", list.getItem(arg2 - 1).getGoods_id());
				startActivity(intent);
			}
		});
	}

	private void initview() {
		// TODO Auto-generated method stub
		car_km_shopitem = (TextView) findViewById(R.id.car_km_shopitem);
		carname_shopitem = (TextView) findViewById(R.id.carname_shopitem);
		lin_bac = (LinearLayout) findViewById(R.id.lin_bac);
		img_shangpin_detail_bac = (ImageView) findViewById(R.id.img_shangpin_detail_bac);
		refreshlistview = (PullToRefreshListView) findViewById(R.id.refreshlistview);
		refreshlistview.setMode(Mode.BOTH);
	}

	@SuppressWarnings("unchecked")
	private void RequestMainData() {
		// TODO Auto-generated method stub
		map = new Merchant().getmerchants(
				SPUtils.get(CommodityNewsActivity.this, "userId", -1)
						.toString(),
				SPUtils.get(CommodityNewsActivity.this, "userTokens", "")
						.toString(), 1, 10);
		// 获得商品有关数据
		new HttpTask(goodsCallback, MyURL.GOODS, CommodityNewsActivity.this)
				.execute(map);

		HashMap<String, String> idtakjson = new UserEntity()
				.getIdTaken(CommodityNewsActivity.this);
		// 获取汽车配置界面请求
		new HttpTask(carconfigurationcallback, MyURL.MY,
				CommodityNewsActivity.this).execute(idtakjson);
	}

	// 获取更多分页数据
	@SuppressWarnings("unchecked")
	private void Requestmore(int i) {
		map.put("page", i + "");
		new HttpTask(requestmoreCallback, MyURL.GOODS,
				CommodityNewsActivity.this).execute(map);

	}

	private HttpCallback goodsCallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cBllmerchant = CBLL.getInstance();
			JSONEntity entity = cBllmerchant.json2goods(json);
			if (entity.isFlag()) {
				GoodsSet goodslist = (GoodsSet) entity.getData();// 将数据都放进set方法中
				List<Goods> l = new ArrayList<Goods>(); // 一个实体类的list
				for (int i = 0; i < goodslist.getSize(); i++) { // 循环set方法
					Goods goodsentity = goodslist.getItem(i);// 获取每一项实体类中全部的值

					l.add(goodsentity);
					list = new GoodsSet(l);
					adapter = new ActivityCommodityNewsadapter(
							CommodityNewsActivity.this, list);
					refreshlistview.setAdapter(adapter);
					if (refreshlistview.getMode() == Mode.PULL_FROM_START) {
						refreshlistview.setMode(Mode.BOTH);// 可上拉下拉
					}
				}

			}

		}
	};
	HttpCallback carconfigurationcallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			refreshlistview.onRefreshComplete();
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2caisetting(json);
			if (entity.isFlag()) {
				Car car = (Car) entity.getData();
				carname_shopitem.setText(car.getArctic_name()
						+ car.getBrand_name());
				car_km_shopitem.setText(car.getMileage());

			}
		}
	};
	private HttpCallback requestmoreCallback = new HttpCallback() {
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cBllmerchant = CBLL.getInstance();
			JSONEntity entity = cBllmerchant.json2goods(json);
			if (entity.isFlag()) {
				GoodsSet goodslist = (GoodsSet) entity.getData();
				if (PAGE > (Integer) entity.getData1()) {// 判断是否是最后一页
					refreshlistview.onRefreshComplete();
					ToastUtil.show(CommodityNewsActivity.this, "没有更多");
					refreshlistview.setMode(Mode.PULL_FROM_START);// 只能下拉刷新

				} else {// 还有更多数据
					List<Goods> li = list.getGoodsList();
					li.addAll(goodslist.getGoodsList());
					list = new GoodsSet(li);
					adapter.notifyDataSetChanged();
					refreshlistview.onRefreshComplete();
				}
			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(CommodityNewsActivity.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity.restartApplication(CommodityNewsActivity.this);
				}
			}
		}

	};

	// 重启app
	private void restartApplication() {
		final Intent intent = getPackageManager().getLaunchIntentForPackage(
				getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	}

}
