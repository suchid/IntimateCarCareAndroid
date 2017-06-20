package com.IntimateCarCare;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Adapter.ActivityMyShoppingadapter;
import com.Bll.CBLL;
import com.Entity.Car;
import com.Entity.Merchant;
import com.Entity.MerchantSet;
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

public class ShoppingGoItem extends Activity {

	private PullToRefreshListView refreshlistview;
	private ActivityMyShoppingadapter adapter;
	private MerchantSet list;
	private RelativeLayout rel_distant, rel_price;
	private TextView tv_distents, tv_price,tv_logo,carname_shopitem,car_km_shopitem;
	private PopupWindow popupview, popupview2;
	private ImageView img_shangcheng_back;
	private HashMap<String, String> map;
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
		setContentView(R.layout.activity_shoppinggoitem);

		RequestMainData();
		initView();
		setListen();
        getdata();
	}

	private void getdata() {
		// TODO Auto-generated method stub
		Intent intent=getIntent();
		if(intent.getStringExtra("logo").equals("baoyang")){
			tv_logo.setText("保养");
		}else if(intent.getStringExtra("logo").equals("xiche")){
			tv_logo.setText("洗车");
		}else if(intent.getStringExtra("logo").equals("meirong")){
			tv_logo.setText("美容");
		}else if(intent.getStringExtra("logo").equals("tehui")){
			tv_logo.setText("特惠");
		}else if(intent.getStringExtra("logo").equals("fuwu")){
			tv_logo.setText("服务");
		}else if(intent.getStringExtra("logo").equals("weixiu")){
			tv_logo.setText("维修");
		}
		
	}

	@SuppressWarnings("unchecked")
	private void RequestMainData() {
		// TODO Auto-generated method stub
		map = new Merchant().getmerchants(
				SPUtils.get(ShoppingGoItem.this, "userId", -1).toString(),
				SPUtils.get(ShoppingGoItem.this, "userTokens", "").toString(),
				1, 10);
		// 获得商家有关数据
		new HttpTask(allmerchantCallback, MyURL.MERCHANT,ShoppingGoItem.this).execute(map);
		
		   HashMap<String, String> idtakjson = new UserEntity()
			.getIdTaken(ShoppingGoItem.this);
			//获取汽车配置界面请求
			new HttpTask(carconfigurationcallback, MyURL.MY,ShoppingGoItem.this).execute(idtakjson);
	}

	// 获取更多分页数据
	@SuppressWarnings("unchecked")
	private void Requestmore(int i) {
		map.put("page", i + "");
		new HttpTask(requestmoreCallback, MyURL.MERCHANT,ShoppingGoItem.this).execute(map);

	}

	private HttpCallback allmerchantCallback = new HttpCallback() {
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			refreshlistview.onRefreshComplete();
			CBLL cBllmerchant = CBLL.getInstance();
			JSONEntity entity = cBllmerchant.json2merchant(json);
			if (entity.isFlag()) {
				list = (MerchantSet) entity.getData();// 将数据都放进set方法中
				adapter = new ActivityMyShoppingadapter(ShoppingGoItem.this,
						list);
				refreshlistview.setAdapter(adapter);
				if (refreshlistview.getMode() == Mode.PULL_FROM_START) {
					refreshlistview.setMode(Mode.BOTH);// 可上拉下拉
				}

			}

		}
	};
	HttpCallback carconfigurationcallback=new HttpCallback() {
		
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll =CBLL.getInstance(); 
			JSONEntity entity=cbll.json2caisetting(json);
			if(entity.isFlag()){
			Car car=(Car) entity.getData();
			carname_shopitem.setText(car.getArctic_name()+car.getBrand_name());
			car_km_shopitem.setText(car.getMileage());
			
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
				list = (MerchantSet) entity.getData();// 将数据都放进set方法中
				if (PAGE > (Integer) entity.getData1()) {// 判断是否是最后一页
					refreshlistview.onRefreshComplete();
					ToastUtil.show(ShoppingGoItem.this, "没有更多");
					refreshlistview.setMode(Mode.PULL_FROM_START);// 只能下拉刷新

				} else {// 还有更多数据
					List<Merchant> li = list.getMerchanList();
					li.addAll(list.getMerchanList());
					list = new MerchantSet(li);
					adapter.notifyDataSetChanged();
					refreshlistview.onRefreshComplete();
				}
			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(ShoppingGoItem.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity.restartApplication(ShoppingGoItem.this);
				}
			}
		}
	};

	private void initView() {
		// TODO Auto-generated method stub
		car_km_shopitem=(TextView) findViewById(R.id.car_km_shopitem);
		carname_shopitem=(TextView) findViewById(R.id.carname_shopitem);
		tv_logo=(TextView) findViewById(R.id.tv_logo);
		img_shangcheng_back = (ImageView) findViewById(R.id.img_shangcheng_back);
		refreshlistview = (PullToRefreshListView) findViewById(R.id.refreshlistview);
		refreshlistview.setMode(Mode.BOTH);
		rel_distant = (RelativeLayout) findViewById(R.id.rel_distant);
		rel_price = (RelativeLayout) findViewById(R.id.rel_price);
		tv_distents = (TextView) findViewById(R.id.tv_distents);
		tv_price = (TextView) findViewById(R.id.tv_price);
	}

	private void setListen() {
		// TODO Auto-generated method stub
		refreshlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent=new Intent(ShoppingGoItem.this,Shangjia_Detail.class);
				intent.putExtra("tag", "know");
				intent.putExtra("merchantid", list.getItem(arg2-1).getMerchant_id());

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
		rel_distant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				getPopupWindow();
				// 这里是位置显示方式,
				popupview2.showAsDropDown(rel_distant);
			}
		});

		rel_price.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getPopupWindow2();
				// 这里是位置显示方式,
				popupview.showAsDropDown(rel_price);
			}

		});
	}

	private void getPopupWindow() {
		if (null != popupview2) {

			popupview2.dismiss();
			popupview2 = null;
			return;
		} else {

			initPopuptWindow();

		}
	}

	private void getPopupWindow2() {
		if (null != popupview) {

			popupview.dismiss();
			popupview = null;
			return;
		} else {
			initPopuptWindow_price();

		}
	}

	// 初始化popupwindow
	protected void initPopuptWindow() {

		View view = getLayoutInflater().inflate(R.layout.popupview_shop, null,
				false);
		ListView listview = (ListView) view.findViewById(R.id.listview);
		final String[] mStrings = { "<500M", "<1000M", "<2000M", "<3000M",
				"<5000M" };
		listview.setAdapter(new ArrayAdapter<String>(this,
				R.layout.listview_shopitem, mStrings));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				tv_distents.setText(mStrings[position]);
				// 用于消失弹窗
				getPopupWindow();
			}
		});

		popupview2 = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		// popupview.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupgray));
		popupview2.setAnimationStyle(android.R.style.Animation_InputMethod);
		// 点击外面，窗口消失
		popupview2.setOutsideTouchable(true);
		popupview2.setFocusable(true);
		popupview2.setTouchable(true);
		// 防止软键盘被弹出菜单遮住
		popupview2
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popupview2 != null && popupview2.isShowing()) {
					// tv_changecar.setVisibility(View.VISIBLE);
					popupview2.dismiss();
					popupview2 = null;

				}
				return false;
			}
		});

	}

	protected void initPopuptWindow_price() {
		View view = getLayoutInflater().inflate(R.layout.popupview_shop, null,
				false);
		ListView listview = (ListView) view.findViewById(R.id.listview);
		final String[] mStringprice = { "<100元", "<200元", "<500元", "<1000元",
				"<3000元" };
		listview.setAdapter(new ArrayAdapter<String>(this,
				R.layout.listview_shopitem, mStringprice));

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				tv_price.setText(mStringprice[position]);
				// 用于消失弹窗
				getPopupWindow2();
			}
		});

		popupview = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		// popupview.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupgray));
		popupview.setAnimationStyle(android.R.style.Animation_InputMethod);
		// 点击外面，窗口消失
		popupview.setOutsideTouchable(true);
		popupview.setFocusable(true);
		popupview.setTouchable(true);
		// 防止软键盘被弹出菜单遮住
		popupview
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (popupview != null && popupview.isShowing()) {
					// tv_changecar.setVisibility(View.VISIBLE);
					popupview.dismiss();
					popupview = null;

				}
				return false;
			}
		});

	}
}