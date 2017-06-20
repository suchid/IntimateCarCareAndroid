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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.Adapter.ActivityMyShoppingadapter;
import com.Bll.CBLL;
import com.Entity.Merchant;
import com.Entity.MerchantSet;
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

public class MyShopping extends Activity {
	private PullToRefreshListView refreshlistview;
	private ActivityMyShoppingadapter adapter;
	private MerchantSet list;
	private HashMap<String, String> map;
	private ImageView img_shangcheng_search, img_shangcheng_back, img_baoyang,
			img_weixiu, img_xiche, img_meirong, img_luntai, img_peijian,
			img_fuwu, img_youhui;
	private TextView tv_peijian, tv_luntai, tv_baoyang, tv_xiche, tv_meirong,
			tv_weixiu, tv_tehui, tv_fuwu;

	// 自定义翻页有关数据
	private int PAGE = 1;
	private int visibleLastIndex; // 用来可显示的最后一条数据的索引值
	private boolean moredata = true;// 用来判断页数时候要用到的
	private boolean firstdata = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myshopping);

		RequestMainData();
		// getDate();
		initView();
		setListen();

	}

	@SuppressWarnings("unchecked")
	private void RequestMainData() {
		// TODO Auto-generated method stub
		map = new Merchant().getmerchants(
				SPUtils.get(MyShopping.this, "userId", -1).toString(), SPUtils
						.get(MyShopping.this, "userTokens", "").toString(), 1,
				10);
		// 获得商家有关数据
		new HttpTask(merchantCallback, MyURL.MERCHANT,MyShopping.this).execute(map);
	}

	// 获取更多分页数据
	@SuppressWarnings("unchecked")
	private void Requestmore(int i) {
		map.put("page", i + "");
		new HttpTask(requestmoreCallback, MyURL.MERCHANT,MyShopping.this).execute(map);

	}

	private HttpCallback merchantCallback = new HttpCallback() {
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			refreshlistview.onRefreshComplete();
			CBLL cBllmerchant = CBLL.getInstance();
			JSONEntity entity = cBllmerchant.json2merchant(json);
			if (entity.isFlag()) {
				list = (MerchantSet) entity.getData();// 将数据都放进set方法中
				adapter = new ActivityMyShoppingadapter(MyShopping.this, list);
				refreshlistview.setAdapter(adapter);
				if (refreshlistview.getMode() == Mode.PULL_FROM_START) {
					refreshlistview.setMode(Mode.BOTH);// 可上拉下拉
				}

			}
		}
	};
	private HttpCallback requestmoreCallback = new HttpCallback() {
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cBllmerchant = CBLL.getInstance();
			JSONEntity entity = cBllmerchant.json2merchant(json);
			if (entity.isFlag()) {
				MerchantSet merchantlist = (MerchantSet) entity.getData();// 将数据都放进set方法中
				if (PAGE > (Integer) entity.getData1()) {// 判断是否是最后一页
					refreshlistview.onRefreshComplete();
					ToastUtil.show(MyShopping.this, "没有更多");
					refreshlistview.setMode(Mode.PULL_FROM_START);// 只能下拉刷新

				} else {// 还有更多数据
					List<Merchant> li = list.getMerchanList();
					li.addAll(merchantlist.getMerchanList());
					list = new MerchantSet(li);
					adapter.notifyDataSetChanged();
					refreshlistview.onRefreshComplete();
				}
			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(MyShopping.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity.restartApplication(MyShopping.this);
				}
			}
		}
	};

	/*
	 * private void getDate() { // TODO Auto-generated method stub
	 * List<Merchant>l=new ArrayList<Merchant>(); for(int i=0;i<10;i++){
	 * l.add(new Merchant("万里汽修","环城北路北大门","88769876")); } list=new
	 * MerchantSet(l); }
	 */
	private void setListen() {
		// TODO Auto-generated method stub
		refreshlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent=new Intent(MyShopping.this,Shangjia_Detail.class);
				//System.out.println("VVVVVVVVVVVVVV"+list.getItem(arg2).getMerchant_id());
				intent.putExtra("tag", "false");//如果是false 则是在商家列表跳过去的
				intent.putExtra("merchant_id", list.getItem(arg2-1).getMerchant_id());
				System.out.println("VVVVVVVVVVVVVV"+list.getItem(arg2-1).getMerchant_id());
	
				startActivity(intent);
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
		img_shangcheng_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		img_baoyang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyShopping.this,
						ShoppingGoItem.class);
				intent.putExtra("logo", "baoyang");
				startActivity(intent);
			}
		});
		img_xiche.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyShopping.this,
						ShoppingGoItem.class);
				intent.putExtra("logo", "xiche");
				startActivity(intent);
			}
		});
		img_meirong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyShopping.this,
						ShoppingGoItem.class);
				intent.putExtra("logo", "meirong");
				startActivity(intent);
			}
		});
		img_luntai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyShopping.this,
						CommodityNewsActivity.class);
				startActivity(intent);
			}
		});
		img_weixiu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyShopping.this,
						ShoppingGoItem.class);
				intent.putExtra("logo", "weixiu");
				startActivity(intent);
			}
		});
		img_peijian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyShopping.this,
						CommodityNewsActivity.class);
				startActivity(intent);
			}
		});
		img_youhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyShopping.this,
						ShoppingGoItem.class);
				intent.putExtra("logo", "tehui");
				startActivity(intent);
			}
		});
		img_fuwu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyShopping.this,
						ShoppingGoItem.class);
				intent.putExtra("logo", "fuwu");
				startActivity(intent);
			}
		});

		img_shangcheng_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyShopping.this,
						SearchMyShopping.class);
				startActivity(intent);

			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		img_baoyang = (ImageView) findViewById(R.id.img_baoyang);
		img_weixiu = (ImageView) findViewById(R.id.img_weixiu);
		img_xiche = (ImageView) findViewById(R.id.img_xiche);
		img_meirong = (ImageView) findViewById(R.id.img_meirong);
		img_luntai = (ImageView) findViewById(R.id.img_luntai);
		img_peijian = (ImageView) findViewById(R.id.img_peijian);
		img_fuwu = (ImageView) findViewById(R.id.img_fuwu);
		img_youhui = (ImageView) findViewById(R.id.img_youhui);
		img_shangcheng_search = (ImageView) findViewById(R.id.img_shangcheng_search);

		img_shangcheng_back = (ImageView) findViewById(R.id.img_shangcheng_back);
		refreshlistview = (PullToRefreshListView) findViewById(R.id.refreshlistview);
		refreshlistview.setMode(Mode.BOTH);
		tv_peijian = (TextView) findViewById(R.id.tv_peijian);
		tv_luntai = (TextView) findViewById(R.id.tv_luntai);
		tv_xiche = (TextView) findViewById(R.id.tv_xiche);
		tv_meirong = (TextView) findViewById(R.id.tv_meirong);
		tv_weixiu = (TextView) findViewById(R.id.tv_weixiu);
		tv_tehui = (TextView) findViewById(R.id.tv_youhui);
		tv_fuwu = (TextView) findViewById(R.id.tv_fuwu);
		tv_baoyang = (TextView) findViewById(R.id.tv_baoyang);

	}
}
