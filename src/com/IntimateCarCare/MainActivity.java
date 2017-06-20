package com.IntimateCarCare;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Bll.CBLL;
import com.Entity.CarSet;
import com.Entity.Goods;
import com.Entity.GoodsSet;
import com.Entity.HomeEntity;
import com.Entity.UserEntity;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.CircleImageView;
import com.tool.CustomDialog;
import com.tool.Dialogaddbox;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.SPUtils;
import com.tool.ToastUtil;

/**
 * @author ZL
 * 
 */

public class MainActivity extends Activity {

	private LinearLayout lin_weather, lin_mallhorizontalscrollview_header,
			lin_remotecheck, lin_my, lin0, lin_GPS, lin_carcheck, lin_consult;
	private RelativeLayout rel_remind;
	private TextView temperature, carwash, trafficlimit, tv_changecar,
			tv_score, tv_remindnum;
	private ImageView carimg, img_weather;
	private CircleImageView circleicon;
	private Button mall;
	private PopupWindow popupview;
	private CarSet carset;
	private ArrayAdapter<String> adaptertwo;
	private List<String> list;
	// 屏幕长宽
	private int width, height;
	// 水平滚动参数
	private HorizontalScrollView mallhorizontalscrollview;
	private final Handler mHandler = new Handler();

	/* 头像文件 */
	private static final String IMAGE_FILE_NAME = "temp_car_image.jpg";
	/* 请求识别码 */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_REQUEST = 0xa1;
	private static final int CODE_RESULT_REQUEST = 0xa2;
	// 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
	private static int output_X = 960;
	private static int output_Y = 480;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		width = dm.widthPixels;
		height = dm.heightPixels;

		AutoLogin();
		Initview();
		SetListenr();

	}

	private void Initview() {
		// TODO Auto-generated method stub
		lin_weather = (LinearLayout) findViewById(R.id.lin_weather);
		lin_mallhorizontalscrollview_header = (LinearLayout) findViewById(R.id.lin_mallhorizontalscrollview_header);
		temperature = (TextView) findViewById(R.id.temperature);
		carwash = (TextView) findViewById(R.id.carwash);
		trafficlimit = (TextView) findViewById(R.id.trafficlimit);
		carimg = (ImageView) findViewById(R.id.carimg);
		mall = (Button) findViewById(R.id.mall);
		mallhorizontalscrollview = (HorizontalScrollView) findViewById(R.id.mallhorizontalscrollview);
		circleicon = (CircleImageView) findViewById(R.id.circleicon);
		lin_remotecheck = (LinearLayout) findViewById(R.id.lin_remotecheck);
		lin_my = (LinearLayout) findViewById(R.id.lin_my);
		tv_changecar = (TextView) findViewById(R.id.tv_changecar);
		lin0 = (LinearLayout) findViewById(R.id.lin0);
		lin_GPS = (LinearLayout) findViewById(R.id.lin_GPS);
		tv_score = (TextView) findViewById(R.id.tv_score);
		tv_remindnum = (TextView) findViewById(R.id.tv_remindnum);
		lin_carcheck = (LinearLayout) findViewById(R.id.lin_carcheck);
		img_weather = (ImageView) findViewById(R.id.img_weather);
		lin_consult = (LinearLayout) findViewById(R.id.lin_consult);
		rel_remind = (RelativeLayout) findViewById(R.id.rel_remind);

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) circleicon
				.getLayoutParams();
		params.height = width / 4;
		params.width = params.height;
		circleicon.setLayoutParams(params);

	}

	private void SetListenr() {
		// TODO Auto-generated method stub

		mall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, MyShopping.class);
				startActivity(intent);
			}
		});
		mallhorizontalscrollview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				if (event.getAction() == MotionEvent.ACTION_UP) {
					mHandler.removeCallbacks(ScrollRunnable);
					mHandler.postDelayed(ScrollRunnable, 1000);
				}
				return false;
			}
		});
		lin_weather.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						MyweatherActivity.class);
				startActivity(intent);
			}
		});
		lin_remotecheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastUtil.show(MainActivity.this, "该功能待开发");
			}
		});

		lin_my.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, MyActivity.class);
				startActivityForResult(intent, 2);
			}
		});

		tv_changecar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getPopupWindow();
				// 这里是位置显示方式,
				popupview.showAsDropDown(lin0);
			}
		});

		lin_GPS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ((Boolean) SPUtils
						.get(MainActivity.this, "isBoxbind", false)) {
					Intent intent = new Intent(MainActivity.this,
							GPSActivity.class);
					startActivity(intent);
				} else {
					dialogredmain(MainActivity.this);
				}

			}
		});

		lin_carcheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ((Boolean) SPUtils
						.get(MainActivity.this, "isBoxbind", false)) {
					Intent intent = new Intent(MainActivity.this,
							CarReportActivity.class);
					startActivity(intent);
				} else {
					dialogredmain(MainActivity.this);
				}
			}
		});

		lin_consult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						ConsultActivity.class);
				startActivity(intent);
			}
		});

		rel_remind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						RemindActivity.class);
				startActivityForResult(intent, 1);
			}
		});

		carimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String[] arrayContestLevel = new String[] { "相机拍摄",
						"手机相册" };

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						MainActivity.this).setTitle("选择头像").setItems(
						arrayContestLevel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:
									choseHeadImageFromCameraCapture();
									break;
								case 1:
									choseHeadImageFromGallery();
								default:
									break;
								}
								dialog.cancel();
							}
						});
				alertDialog.create().show();

			}
		});

	}

	// 启动手机相机拍摄照片作为头像
	private void choseHeadImageFromCameraCapture() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// 判断存储卡是否可用，存储照片文件
		if (hasSdcard()) {
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(new File(Environment
							.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
		}

		startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
	}

	private void choseHeadImageFromGallery() {
		Intent intentFromGallery = new Intent();
		// 设置文件类型
		intentFromGallery.setType("image/*");
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
	}

	/**
	 * 检查设备是否存在SDCard的工具方法
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// 有存储的SDCard
			return true;
		} else {
			return false;
		}
	}

	private void myupload(final String path) {

		final RequestParams params = new RequestParams(MyURL.UPLOADCARPIC);
		int id = (Integer) SPUtils.get(MainActivity.this, "userId", -1);
		String tokens = (String) SPUtils.get(MainActivity.this, "userTokens",
				"");
		int carid = (Integer) SPUtils.get(MainActivity.this, "carId", -1);
		params.addBodyParameter("user_id", id + "");
		params.addBodyParameter("tokens", tokens);
		params.addBodyParameter("car_id", carid + "");
		params.addBodyParameter("pic", new File(path));
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject json = new JSONObject(result);
					if (json.getBoolean("flag")) {
						Bitmap bit = BitmapFactory.decodeFile(path);
						carimg.setImageBitmap(bit);

					} else {
						ToastUtil.show(MainActivity.this, "上传失败");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onCancelled(CancelledException cex) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
			}
		});

	}

	private Runnable ScrollRunnable = new Runnable() {
		@Override
		public void run() {
			int off = lin_mallhorizontalscrollview_header.getMeasuredWidth();
			// 模拟滑动
			if (off > 0) {
				mallhorizontalscrollview.scrollBy(1, 0);
				if (mallhorizontalscrollview.getScrollX() == off) {
					Thread.currentThread().interrupt();
				} else {
					mHandler.postDelayed(this, 10);
				}
			} else {
				mHandler.postDelayed(this, 1000);
			}
		}

	};

	@SuppressWarnings("unchecked")
	private void AutoLogin() {
		// TODO Auto-generated method stub
		if ((Integer) SPUtils.get(MainActivity.this, "userId", -1) == -1) {
			// 需要手动登录
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
		} else {
			Boolean tag;
			tag = (Boolean) SPUtils.get(MainActivity.this, "logintag", false);
			System.out.println(tag);
			SPUtils.remove(MainActivity.this, "logintag");

			if (tag) {// 成功登陆,获取数据
				RequestMainData();

			} else {// 需要自动登录
				String userAccount = (String) SPUtils.get(MainActivity.this,
						"userAccount", "");
				String userPassword = (String) SPUtils.get(MainActivity.this,
						"userPassword", "");
				HashMap<String, String> loginjson = new UserEntity()
						.toLoginJson(userAccount, userPassword);
				new HttpTask(loginCallback, MyURL.LOGIN, MainActivity.this)
						.execute(loginjson);

			}
		}

	}

	// 自动登录callback
	private HttpCallback loginCallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub

			CBLL cBllUser = CBLL.getInstance();
			JSONEntity entity = cBllUser.json2login(json);
			if (entity.isFlag()) {
				UserEntity UserEntity = (UserEntity) entity.getData();
				// 自动登录成功保存新令牌
				SPUtils.put(MainActivity.this, "userTokens",
						UserEntity.getTokens());
				// 获取首页数据
				RequestMainData();

			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(MainActivity.this, "服务器故障");
				} else if (entity.getMessage() == MyURL.MSG_PWD_ERROR) {
					ToastUtil.show(MainActivity.this, "用户名或密码错误，请重新输入");
					// 清空账号密码等缓存，重新登录
					SPUtils.clear(MainActivity.this);
					Intent intent = new Intent(MainActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				}
			}

		}
	};

	private void limit(String carnumber) {
		Calendar FF = Calendar.getInstance(); // 获取本地的时间
		int day = FF.get(Calendar.DAY_OF_MONTH);
		for (int i = carnumber.length(); i > 0; i--) {
			String ss = carnumber.substring(i - 1, i);
			char c = ss.charAt(0);
			if (Character.isDigit(c)) {
				if (day % 2 != 0) { // 单号
					if (c % 2 != 0) { // 车牌号尾号是单号
						trafficlimit.setText("今日畅行");
						SPUtils.put(MainActivity.this, "TAG", trafficlimit
								.getText().toString());// 将是否限行存进本地
					} else {
						trafficlimit.setText("今日限号");
						SPUtils.put(MainActivity.this, "TAG", trafficlimit
								.getText().toString());// 将是否限行存进本地
					}
				} else { // 日期是双号
					if (c % 2 != 0) { // 车牌是单号

						trafficlimit.setText("今日限号");
						SPUtils.put(MainActivity.this, "TAG", trafficlimit
								.getText().toString());// 将是否限行存进本地
					} else {

						trafficlimit.setText("今日畅行");
						SPUtils.put(MainActivity.this, "TAG", trafficlimit
								.getText().toString());// 将是否限行存进本地
					}
				}
				break;
			} else {

			}
		}
	}

	private HttpCallback homeCallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cBllUser = CBLL.getInstance();
			JSONEntity entity = cBllUser.json2home(json);
			if (entity.isFlag()) {
				HomeEntity homeentity = (HomeEntity) entity.getData();
				temperature.setText(homeentity.getWeaather().getTemperture());
				carwash.setText(homeentity.getWeaather().getWashcar());
				tv_changecar.setText(homeentity.getDefaultcarnum());
				tv_score.setText(homeentity.getSafescore() + "");
				tv_remindnum.setText(homeentity.getRemindnum() + "条未读");
				ImageOptions options;
				if (homeentity.getCarimg().indexOf("carpic") == -1) {
					options = new ImageOptions.Builder().setImageScaleType(
							ScaleType.FIT_CENTER).build();
				} else {
					options = new ImageOptions.Builder().setImageScaleType(
							ScaleType.FIT_XY).build();
				}

				x.image().bind(carimg, homeentity.getCarimg(), options);

				limit(homeentity.getDefaultcarnum()); // 限行判断

				// img_weather.setImageResource(resId)
				if (homeentity.getWeaather().getWeathercondition().equals("晴")) {
					img_weather.setImageResource(R.drawable.qing);
				} else if (homeentity.getWeaather().getWeathercondition()
						.equals("阴")) {
					img_weather.setImageResource(R.drawable.qing);
				} else if (homeentity.getWeaather().getWeathercondition()
						.equals("大雨")) {
					img_weather.setImageResource(R.drawable.dayu);
				} else if (homeentity.getWeaather().getWeathercondition()
						.equals("小雨")) {
					img_weather.setImageResource(R.drawable.xiaoyu);
				} else if (homeentity.getWeaather().getWeathercondition()
						.equals("小雪")) {
					img_weather.setImageResource(R.drawable.xiaoxue);
				} else if (homeentity.getWeaather().getWeathercondition()
						.equals("大雪")) {
					img_weather.setImageResource(R.drawable.daxue);
				} else if (homeentity.getWeaather().getWeathercondition()
						.equals("多云")) {
					img_weather.setImageResource(R.drawable.duoyun);
				} else {
					img_weather.setImageResource(R.drawable.yin);
				}

				if (!homeentity.isIsboxbind()) {
					ToastUtil.show(MainActivity.this, "请激活盒子");
				}
				if (!homeentity.isIsboxonline()) {
					ToastUtil.show(MainActivity.this, "盒子离线");
				}

				SPUtils.put(MainActivity.this, "carnumber",
						homeentity.getDefaultcarnum());// 将默认车牌存入本地
				SPUtils.put(MainActivity.this, "isBoxbind",
						homeentity.isIsboxbind());
				SPUtils.put(MainActivity.this, "carId", homeentity.getCarid());
				SPUtils.put(MainActivity.this, "isBoxonline",
						homeentity.isIsboxonline());

			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(MainActivity.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					restartApplication(MainActivity.this);
				}
			}
		}
	};

	private HttpCallback homegoodsCallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cBllUser = CBLL.getInstance();
			JSONEntity entity = cBllUser.json2homegoods(json);
			if (entity.isFlag()) {
				GoodsSet goodslist = (GoodsSet) entity.getData();
				for (int i = 0; i < goodslist.getSize(); i++) {
					Goods goods = goodslist.getItem(i);
					View goodsitem = LayoutInflater.from(MainActivity.this)
							.inflate(R.layout.item_mallhorizontalscrollview,
									null);
					ImageView goodsimg = (ImageView) goodsitem
							.findViewById(R.id.goodsimg);
					TextView goodsprice = (TextView) goodsitem
							.findViewById(R.id.goodsprice);

					LinearLayout.LayoutParams imgparams = (LayoutParams) goodsimg
							.getLayoutParams();
					imgparams.height = height / 100 * 6;
					imgparams.width = imgparams.height;
					goodsimg.setLayoutParams(imgparams);

					goodsimg.setBackgroundResource(R.drawable.icon_launcher);
					x.image().bind(goodsimg, goods.getPictures().getUrl());

					LinearLayout.LayoutParams priceparams = (LayoutParams) goodsprice
							.getLayoutParams();
					priceparams.height = height / 100 * 3;
					priceparams.width = height / 100 * 12;
					goodsprice.setLayoutParams(priceparams);
					goodsprice.setText("商品价格:" + goods.getMoney() + "元");

					final int goodsid = goods.getGoods_id();
					final int merchantid = goods.getMerchant().getMerchant_id();

					goodsitem.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(MainActivity.this,
									Shopping_Detail.class);
							intent.putExtra("merchantid", merchantid);
							intent.putExtra("goodsid", goodsid);
							startActivity(intent);
						}
					});
					lin_mallhorizontalscrollview_header.addView(goodsitem);
				}
				mHandler.post(ScrollRunnable);

			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(MainActivity.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					restartApplication(MainActivity.this);
				}
			}

		}
	};

	private HttpCallback carlistCallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cBllUser = CBLL.getInstance();
			JSONEntity entity = cBllUser.json2carlist(json);
			if (entity.isFlag()) {
				carset = (CarSet) entity.getData();
				// SPUtils.put(MainActivity.this, "carList", carset);//车辆list不存
			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(MainActivity.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					restartApplication(MainActivity.this);
				}
			}
		}
	};

	private HttpCallback exitCallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			finish();
		}
	};

	// 重启app
	public static void restartApplication(final Context context) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage("您的账号被迫下线。如非本人操作，建议修改密码。")
				.setPositiveButton("重新登录",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								arg0.dismiss();
								final Intent intent = context
										.getPackageManager()
										.getLaunchIntentForPackage(
												context.getPackageName());
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(intent);

							}
						})
				.setNegativeButton("退出", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						SPUtils.remove(context, "userAccount");
						SPUtils.remove(context, "userPassword");
						SPUtils.remove(context, "userId");
						arg0.dismiss();
						final Intent intent = context.getPackageManager()
								.getLaunchIntentForPackage(
										context.getPackageName());
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						context.startActivity(intent);

					}
				}).create().show();
	}

	// 获取首页数据
	@SuppressWarnings("unchecked")
	private void RequestMainData() {
		HashMap<String, String> idtakjson = new UserEntity()
				.getIdTaken(MainActivity.this);
		// 首页数据
		new HttpTask(homeCallback, MyURL.HOME, MainActivity.this)
				.execute(idtakjson);
		// 商城推荐列表
		new HttpTask(homegoodsCallback, MyURL.HOMEGOODS, MainActivity.this)
				.execute(idtakjson);
		// 车辆列表
		new HttpTask(carlistCallback, MyURL.CARLIST, MainActivity.this)
				.execute(idtakjson);

	}

	// 初始化popupwindow
	protected void initPopuptWindow() {
		View view = getLayoutInflater().inflate(R.layout.popupview_gray, null,
				false);
		ListView listview = (ListView) view.findViewById(R.id.listview);
		list = new ArrayList<String>();
		for (int i = 0; i < carset.getSize(); i++) {
			list.add(carset.getItem(i).getCar_num());
		}

		// 添加一条数据用于添加车辆监听
		list.add("添加车辆");
		final String[] mStrings = (String[]) list.toArray(new String[0]);
		adaptertwo = new ArrayAdapter<String>(this,
				R.layout.listview_simpleitem, mStrings);
		listview.setAdapter(adaptertwo);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// -----------------------切换车辆---------------
				if (position == carset.getSize()) {
					Intent intent = new Intent(MainActivity.this,
							AddLicenseplateActivity.class);
					intent.putExtra("build", 1);
					startActivityForResult(intent, 3);
				} else {
					String carid = carset.getItem(position).getCar_id() + "";
					HashMap<String, String> idtakjson = new UserEntity()
							.getIdTaken(MainActivity.this);
					idtakjson.put("car_id", carid);
					new HttpTask(homeCallback, MyURL.CHANGECAR,
							MainActivity.this).execute(idtakjson);

				}
				// 用于消失弹窗
				getPopupWindow();
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
					tv_changecar.setVisibility(View.VISIBLE);
					popupview.dismiss();
					popupview = null;

				}
				return false;
			}
		});

	}

	/***
	 * 获取PopupWindow实例
	 */
	private void getPopupWindow() {
		if (null != popupview) {
			tv_changecar.setVisibility(View.VISIBLE);
			popupview.dismiss();
			popupview = null;
			return;
		} else {
			initPopuptWindow();
			tv_changecar.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_CANCELED) {
			return;
		}
		switch (requestCode) {
		case 1:
			if (resultCode == 1) {
				String result_value = data.getStringExtra("remindnum");
				tv_remindnum.setText(result_value);
			}
			break;
		case 2:
			if (resultCode == 1) {
				RequestMainData();
			}
			break;
		case 3:
			if (resultCode == RESULT_OK) {
				// 将返回来的值进行处理

				RequestMainData();
				dialogredmain(MainActivity.this);
			}
			break;
		case CODE_GALLERY_REQUEST:

			cropRawPhoto(data.getData());
			break;

		case CODE_CAMERA_REQUEST:
			if (hasSdcard()) {
				File tempFile = new File(
						Environment.getExternalStorageDirectory(),
						IMAGE_FILE_NAME);
				cropRawPhoto(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
						.show();
			}
			break;

		case CODE_RESULT_REQUEST:
			if (data != null) {
				myupload(new File(Environment.getExternalStorageDirectory(),
						IMAGE_FILE_NAME).getPath());

			}
			break;
		default:
			break;
		}
	}

	/**
	 * 裁剪原始的图片
	 */
	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// 设置裁剪
		intent.putExtra("crop", "true");

		// aspectX, aspectY:宽高的比例
		intent.putExtra("aspectX", 2);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : 裁剪图片宽高
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		// intent.putExtra("return-data", true);
		File bitmapFile = new File(Environment.getExternalStorageDirectory(),
				IMAGE_FILE_NAME);
		Uri uritempFile = Uri.fromFile(bitmapFile);
		// Uri uritempFile = Uri.parse("file://" + "/" +
		// Environment.getExternalStorageDirectory().getPath() + "/"
		// +IMAGE_FILE_NAME);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
		// intent.putExtra("outputFormat",
		// Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public static void dialog(Context context) {
		// TODO Auto-generated method stub

		final Dialogaddbox dialog = new Dialogaddbox(context);
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
				// HashMap<String, String>changebox=new
				// Car().changebox(IntelligentBoxManage.this,Integer.parseInt(zhanghao),Integer.parseInt(mima));
				// new HttpTask(changboxcallbac, MyURL.).execute(changebox);
			}
		});
		dialog.show();

	}

	// 监听手机的物理按键单击事件，实现按返回退出提示
	// (non-Javadoc)
	// @see android.support.v4.app.FragmentActivity#onKeyDown(int,
	// android.view.KeyEvent)

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 判断用户是否单击的是返回键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			CustomDialog.Builder builder = new CustomDialog.Builder(
					MainActivity.this);
			builder.setMessage("确定要退出吗？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@SuppressWarnings("unchecked")
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							HashMap<String, String> idtakjson = new UserEntity()
							.getIdTaken(MainActivity.this);
					new HttpTask(exitCallback, MyURL.EXIT, MainActivity.this)
							.execute(idtakjson);
							
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

			builder.create().show();
		}

		return false;
	}

	public static void dialogredmain(final Context context) {
		// TODO Auto-generated method stub
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage("您还未绑定盒子，是否绑定新的盒子")
				// 设置显示的内容
				.setPositiveButton("绑定新盒子",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								// HashMap<String,String>changejialing=new
								// UserEntity().changenicheng(SPUtils.get(MyinfoActivity.this,
								// "userId",
								// -1).toString(),SPUtils.get(MyinfoActivity.this,
								// "userTokens", "").toString(),str,5);
								// new
								// HttpTask(sexcallback,MyURL.CHANGEINFOR).execute(changejialing);
								dialog.dismiss();
								// 弹出绑定界面
								dialog(context);

							}

						})
				.setNegativeButton("不了谢谢",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						}).create().show();

	}
}
