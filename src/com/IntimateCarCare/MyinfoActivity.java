package com.IntimateCarCare;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Bll.CBLL;
import com.Entity.UserEntity;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.CircleImageView;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.SPUtils;
import com.tool.ToastUtil;

public class MyinfoActivity extends Activity {

	private RelativeLayout rel_my_words, rel_phone, rel_driving_years,
			rel_driving_licence, rel_area, rel_yearsold;
	private TextView tv_licence_time, tv_driving_years, tv_phone, tv_my_words,
			tv_nickname, tv_sex, tv_yearsold, tv_account, tv_area;
	private CircleImageView img_userhead;
	private ImageView img_test;
	private LinearLayout lin_nickname, lin_sex, lin_back;

	/* 头像文件 */
	private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
	/* 请求识别码 */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_REQUEST = 0xa1;
	private static final int CODE_RESULT_REQUEST = 0xa2;
	// 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
	private static int output_X = 480;
	private static int output_Y = 480;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myinfo);

		initView();
		RequestData();
		setListen();

	}

	@SuppressWarnings("unchecked")
	private void RequestData() {
		// TODO Auto-generated method stub
		HashMap<String, String> idtakjson = new UserEntity()
				.getIdTaken(MyinfoActivity.this);
		new HttpTask(myinfoCallback, MyURL.MYINFO,MyinfoActivity.this).execute(idtakjson);

	}

	private HttpCallback myinfoCallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2myinfo(json);

			if (entity.isFlag()) {
				UserEntity user = (UserEntity) entity.getData();
				tv_licence_time.setText(user.getDrive_time());
				tv_driving_years.setText(user.getDrive_age());

				tv_phone.setText(user.getContact());
				tv_my_words.setText(user.getSignature());
				tv_nickname.setText(user.getNick());
				tv_sex.setText(user.getSex());

				tv_yearsold.setText(user.getAge());
				tv_account.setText(user.getUser_account());
				tv_area.setText(user.getArea());
				x.image().bind(img_test, user.getHead_pic(),
						new Callback.CommonCallback<Drawable>() {
							@Override
							public void onSuccess(Drawable result) {
								// TODO Auto-generated method stub
								img_userhead.setImageDrawable(result);
							}

							@Override
							public void onError(Throwable ex,
									boolean isOnCallback) {
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
			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(MyinfoActivity.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity.restartApplication(MyinfoActivity.this);
				}
			}

		}
	};

	private void initView() {
		// TODO Auto-generated method stub
		rel_my_words = (RelativeLayout) findViewById(R.id.rel_my_words);
		rel_phone = (RelativeLayout) findViewById(R.id.rel_phone);
		rel_driving_years = (RelativeLayout) findViewById(R.id.rel_driving_years);
		rel_driving_licence = (RelativeLayout) findViewById(R.id.rel_driving_licence);
		rel_area = (RelativeLayout) findViewById(R.id.rel_area);
		lin_back = (LinearLayout) findViewById(R.id.lin_back);
		tv_licence_time = (TextView) findViewById(R.id.tv_licence_time);
		tv_driving_years = (TextView) findViewById(R.id.tv_driving_years);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_my_words = (TextView) findViewById(R.id.tv_my_words);
		img_userhead = (CircleImageView) findViewById(R.id.img_userhead);
		lin_nickname = (LinearLayout) findViewById(R.id.lin_nickname);
		tv_nickname = (TextView) findViewById(R.id.tv_nickname);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		lin_sex = (LinearLayout) findViewById(R.id.lin_sex);
		rel_yearsold = (RelativeLayout) findViewById(R.id.rel_yearsold);
		tv_yearsold = (TextView) findViewById(R.id.tv_yearsold);
		tv_account = (TextView) findViewById(R.id.tv_account);
		img_test = (ImageView) findViewById(R.id.img_test);
		tv_area = (TextView) findViewById(R.id.tv_area);

	}

	HttpCallback namecallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2changeifon(json);
			if (entity.isFlag()) {
				ToastUtil.show(MyinfoActivity.this, "修改昵称成功");
			}
		}
	};
	HttpCallback sexcallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub

			// TODO Auto-generated method stub
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2changeifon(json);
			if (entity.isFlag()) {
				ToastUtil.show(MyinfoActivity.this, "修改成功");
			}

		}
	};

	HttpCallback wordcallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2changeifon(json);
			if (entity.isFlag()) {
				ToastUtil.show(MyinfoActivity.this, "修改签名成功");
			}
		}
	};

	private void setListen() {
		lin_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rel_driving_licence.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar cal = Calendar.getInstance();
				final DatePickerDialog mDialog = new DatePickerDialog(
						MyinfoActivity.this, null, cal.get(Calendar.YEAR), cal
								.get(Calendar.MONTH), cal
								.get(Calendar.DAY_OF_MONTH));

				// 手动设置按钮
				mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成",
						new DialogInterface.OnClickListener() {
							@SuppressWarnings("unchecked")
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
								DatePicker datePicker = mDialog.getDatePicker();
								cal.set(datePicker.getYear(),
										datePicker.getMonth(),
										datePicker.getDayOfMonth());
								tv_licence_time.setText(DateFormat.format(
										"yyy-MM-dd", cal));
								System.out.println("LLLLLLLL"
										+ DateFormat.format("yyy-MM-dd", cal)
										+ "");
								HashMap<String, String> changejialing = new UserEntity()
										.changenicheng(
												SPUtils.get(
														MyinfoActivity.this,
														"userId", -1)
														.toString(),
												SPUtils.get(
														MyinfoActivity.this,
														"userTokens", "")
														.toString(),
												DateFormat.format("yyy-MM-dd",
														cal) + "", 7);
								new HttpTask(sexcallback, MyURL.CHANGEINFOR,MyinfoActivity.this)
										.execute(changejialing);
							}
						});
				// 取消按钮，如果不需要直接不设置即可
				mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.out.println("BUTTON_NEGATIVE~~");
							}
						});
				mDialog.show();
			}
		});

		rel_driving_years.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText et = new EditText(MyinfoActivity.this);
				et.setBackgroundColor(Color.TRANSPARENT);
				et.setInputType(InputType.TYPE_CLASS_NUMBER);
				et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
						3) });
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MyinfoActivity.this);
				builder.setTitle("请输入您的驾龄")
						.setView(et)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										String str = et.getText().toString();
										if (str.isEmpty()) {
											ToastUtil.show(MyinfoActivity.this,
													"驾龄不能为空");
										} else {
											tv_driving_years.setText(str);
											HashMap<String, String> changejialing = new UserEntity()
													.changenicheng(
															SPUtils.get(
																	MyinfoActivity.this,
																	"userId",
																	-1)
																	.toString(),
															SPUtils.get(
																	MyinfoActivity.this,
																	"userTokens",
																	"")
																	.toString(),
															str, 5);
											new HttpTask(sexcallback,
													MyURL.CHANGEINFOR,MyinfoActivity.this)
													.execute(changejialing);
										}
									}
								}).setNegativeButton("取消", null).show();
			}
		});

		rel_yearsold.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText et = new EditText(MyinfoActivity.this);
				et.setBackgroundColor(Color.TRANSPARENT);
				et.setInputType(InputType.TYPE_CLASS_NUMBER);
				et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
						3) });
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MyinfoActivity.this);
				builder.setTitle("请输入您的年龄")
						.setView(et)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@SuppressWarnings("unchecked")
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										String str = et.getText().toString();
										if (str.isEmpty()) {
											ToastUtil.show(MyinfoActivity.this,
													"年龄不能为空");
										} else {
											tv_yearsold.setText(str);
											HashMap<String, String> changeoldyear = new UserEntity()
													.changenicheng(
															SPUtils.get(
																	MyinfoActivity.this,
																	"userId",
																	-1)
																	.toString(),
															SPUtils.get(
																	MyinfoActivity.this,
																	"userTokens",
																	"")
																	.toString(),
															str, 6);
											new HttpTask(sexcallback,
													MyURL.CHANGEINFOR,MyinfoActivity.this)
													.execute(changeoldyear);
										}
									}
								}).setNegativeButton("取消", null).show();
			}
		});

		rel_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText et = new EditText(MyinfoActivity.this);
				et.setBackgroundColor(Color.TRANSPARENT);
				et.setInputType(InputType.TYPE_CLASS_PHONE);
				et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
						20) });
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MyinfoActivity.this);
				builder.setTitle("请输入您的联系方式")
						.setView(et)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										String str = et.getText().toString();
										if (str.isEmpty()) {
											ToastUtil.show(MyinfoActivity.this,
													"联系方式不能为空");
										} else {
											tv_phone.setText(str);
											HashMap<String, String> changephone = new UserEntity()
													.changenicheng(
															SPUtils.get(
																	MyinfoActivity.this,
																	"userId",
																	-1)
																	.toString(),
															SPUtils.get(
																	MyinfoActivity.this,
																	"userTokens",
																	"")
																	.toString(),
															str, 4);
											new HttpTask(sexcallback,
													MyURL.CHANGEINFOR,MyinfoActivity.this)
													.execute(changephone);
										}

									}
								}).setNegativeButton("取消", null).show();
			}
		});

		rel_my_words.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText et = new EditText(MyinfoActivity.this);
				et.setBackgroundColor(Color.TRANSPARENT);
				et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
						120) });
				et.setHint("最多输入120个字");
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MyinfoActivity.this);
				builder.setTitle("请输入您的签名")
						.setView(et)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										String str = et.getText().toString();
										if (str.isEmpty()) {
											tv_my_words.setText("懒人一般都是不写签名的");
										} else {
											tv_my_words.setText(str);
											HashMap<String, String> changemyword = new UserEntity()
													.changenicheng(
															SPUtils.get(
																	MyinfoActivity.this,
																	"userId",
																	-1)
																	.toString(),
															SPUtils.get(
																	MyinfoActivity.this,
																	"userTokens",
																	"")
																	.toString(),
															str, 3);
											new HttpTask(wordcallback,
													MyURL.CHANGEINFOR,MyinfoActivity.this)
													.execute(changemyword);

										}

									}
								}).setNegativeButton("取消", null).show();
			}
		});

		img_userhead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String[] arrayContestLevel = new String[] { "相机拍摄",
						"手机相册" };

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						MyinfoActivity.this).setTitle("选择头像").setItems(
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

		lin_nickname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText et = new EditText(MyinfoActivity.this);
				et.setBackgroundColor(Color.TRANSPARENT);
				et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
						20) });
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MyinfoActivity.this);
				builder.setTitle("请输入您的新昵称")
						.setView(et)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@SuppressWarnings("unchecked")
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										String str = et.getText().toString();
										if (str.isEmpty()) {
											tv_nickname.setText("昵称不能为空");
										} else {

											HashMap<String, String> changenicheng = new UserEntity()
													.changenicheng(
															SPUtils.get(
																	MyinfoActivity.this,
																	"userId",
																	-1)
																	.toString(),
															SPUtils.get(
																	MyinfoActivity.this,
																	"userTokens",
																	"")
																	.toString(),
															str, 1);
											new HttpTask(namecallback,
													MyURL.CHANGEINFOR,MyinfoActivity.this)
													.execute(changenicheng);
											tv_nickname.setText(str);
										}

									}
								}).setNegativeButton("取消", null).show();
			}
		});

		lin_sex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MyinfoActivity.this)
						.setTitle("请选择性别")
						.setItems(new String[] { "男", "女" },
								new DialogInterface.OnClickListener() {

									@SuppressWarnings("unchecked")
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if (which == 0) {
											tv_sex.setText("男");
											HashMap<String, String> changesex = new UserEntity()
													.changenicheng(
															SPUtils.get(
																	MyinfoActivity.this,
																	"userId",
																	-1)
																	.toString(),
															SPUtils.get(
																	MyinfoActivity.this,
																	"userTokens",
																	"")
																	.toString(),
															"男", 2);
											new HttpTask(sexcallback,
													MyURL.CHANGEINFOR,MyinfoActivity.this)
													.execute(changesex);
										} else if (which == 1) {
											HashMap<String, String> changesex = new UserEntity()
													.changenicheng(
															SPUtils.get(
																	MyinfoActivity.this,
																	"userId",
																	-1)
																	.toString(),
															SPUtils.get(
																	MyinfoActivity.this,
																	"userTokens",
																	"")
																	.toString(),
															"女", 2);
											new HttpTask(sexcallback,
													MyURL.CHANGEINFOR,MyinfoActivity.this)
													.execute(changesex);
											tv_sex.setText("女");
										}
									}
								}).setNegativeButton("取消", null).show();
			}
		});

		rel_area.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyinfoActivity.this,
						AreaActivity.class);
				startActivity(intent);
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

		final RequestParams params = new RequestParams(MyURL.UPLOADUSERPIC);
		int id = (Integer) SPUtils.get(MyinfoActivity.this, "userId", -1);
		String tokens = (String) SPUtils.get(MyinfoActivity.this, "userTokens",
				"");
		params.addBodyParameter("user_id", id + "");
		params.addBodyParameter("tokens", tokens);
		params.addBodyParameter("pic", new File(path));
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject json = new JSONObject(result);
					if (json.getBoolean("flag")) {
						Bitmap bit = BitmapFactory.decodeFile(path);
						img_userhead.setImageBitmap(bit);

					} else {
						ToastUtil.show(MyinfoActivity.this, "上传失败");
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

	/**
	 * 裁剪原始的图片
	 */
	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// 设置裁剪
		intent.putExtra("crop", "true");

		// aspectX, aspectY:宽高的比例
		intent.putExtra("aspectX", 1);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_CANCELED) {
			ToastUtil.show(MyinfoActivity.this, "取消");
			return;
		}
		switch (requestCode) {
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

}
