package com.IntimateCarCare;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.Bll.CBLL;
import com.Entity.TourReportDetail;
import com.Entity.UserEntity;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapDrawFrameCallback;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.tool.CircleImageView;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.ToastUtil;

public class TourReportDetailActivity extends Activity implements
		OnMapDrawFrameCallback {

	private CircleImageView img_back;
	private TextView tv_averageoil, tv_averagespeed, tv_drivetime,
			tv_oilconsumption, tv_totalmileage;

	// 地图参数
	private MapView mMapView = null;
	private BaiduMap mBaiduMap;

	// opengl画图相关
	private List<LatLng> latLngPolygon = new ArrayList<LatLng>();
	private float[] vertexs;
	private FloatBuffer vertexBuffer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tourreportdetail);

		intView();
		RequestData();
		setListen();

	}

	@SuppressWarnings("unchecked")
	private void RequestData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		int id = intent.getIntExtra("treport_id", -1);
		if (id != -1) {
			HashMap<String, String> idtakjson = new UserEntity()
					.getIdTaken(TourReportDetailActivity.this);
			idtakjson.put("tid", id + "");
			new HttpTask(tourreportdetailCallback, MyURL.TOURREPORTDETAIL,TourReportDetailActivity.this)
					.execute(idtakjson);
		} else {
			ToastUtil.show(TourReportDetailActivity.this, "数据错误");
		}

	}

	private HttpCallback tourreportdetailCallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2tourreportdetail(json);
			if (entity.isFlag()) {
				TourReportDetail detail = (TourReportDetail) entity.getData();
				tv_averageoil.setText(detail.getAve_fuel() + "L/100Km");
				tv_averagespeed.setText(detail.getAve_speed() + "Km/H");
				tv_drivetime.setText(detail.getTour_duration() + "H");
				tv_oilconsumption.setText(detail.getFuel_wear() + "L");
				tv_totalmileage.setText(detail.getTour_nil() + "Km");
				latLngPolygon = detail.getLat_lnglist();
				// 显示所有点
				showall(latLngPolygon);

			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(TourReportDetailActivity.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity
							.restartApplication(TourReportDetailActivity.this);
				}
			}

		}
	};

	private void intView() {
		// TODO Auto-generated method stub
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setOnMapDrawFrameCallback(this);

		img_back = (CircleImageView) findViewById(R.id.img_back);
		tv_averageoil = (TextView) findViewById(R.id.tv_averageoil);
		tv_averagespeed = (TextView) findViewById(R.id.tv_averagespeed);
		tv_drivetime = (TextView) findViewById(R.id.tv_drivetime);
		tv_oilconsumption = (TextView) findViewById(R.id.tv_oilconsumption);
		tv_totalmileage = (TextView) findViewById(R.id.tv_totalmileage);

	}

	private void setListen() {
		// TODO Auto-generated method stub
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				// LatLng latlng1 = new LatLng(39.97923, 116.357428);
				// LatLng latlng2 = new LatLng(39.94923, 116.397428);
				// LatLng latlng3 = new LatLng(39.96923, 116.437428);
				// latLngPolygon.add(latlng1);
				// latLngPolygon.add(latlng2);
				// latLngPolygon.add(latlng3);
				// showall(latLngPolygon);
			}
		});

	}

	@Override
	public void onMapDrawFrame(GL10 gl, MapStatus drawingMapStatus) {
		// TODO Auto-generated method stub
		if (mBaiduMap.getProjection() != null && (!latLngPolygon.isEmpty())) {
			calPolylinePoint(drawingMapStatus);
			drawPolyline(gl, Color.argb(255, 255, 0, 0), vertexBuffer, 20, 3,
					drawingMapStatus);
		}
	}

	private void calPolylinePoint(MapStatus mspStatus) {
		// TODO Auto-generated method stub
		PointF[] polyPoints = new PointF[latLngPolygon.size()];
		vertexs = new float[3 * latLngPolygon.size()];
		int i = 0;
		for (LatLng xy : latLngPolygon) {
			polyPoints[i] = mBaiduMap.getProjection().toOpenGLLocation(xy,
					mspStatus);
			vertexs[i * 3] = polyPoints[i].x;
			vertexs[i * 3 + 1] = polyPoints[i].y;
			vertexs[i * 3 + 2] = 0.0f;
			i++;
		}
		// for (int j = 0; j < vertexs.length; j++) {
		// System.out.println("vertexs[" + j + "]: " + vertexs[j]);
		// }
		vertexBuffer = makeFloatBuffer(vertexs);
	}

	private FloatBuffer makeFloatBuffer(float[] fs) {
		// TODO Auto-generated method stub
		ByteBuffer bb = ByteBuffer.allocateDirect(fs.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(fs);
		fb.position(0);
		return fb;
	}

	private void drawPolyline(GL10 gl, int color, FloatBuffer lineVertexBuffer,
			float lineWidth, int pointSize, MapStatus drawingMapStatus) {
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		float colorA = Color.alpha(color) / 255f;
		float colorR = Color.red(color) / 255f;
		float colorG = Color.green(color) / 255f;
		float colorB = Color.blue(color) / 255f;

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, lineVertexBuffer);
		gl.glColor4f(colorR, colorG, colorB, colorA);
		gl.glLineWidth(lineWidth);
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, pointSize);

		gl.glDisable(GL10.GL_BLEND);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	private void showall(List<LatLng> list) {
		// TODO Auto-generated method stub
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		for (LatLng item : list) {
			builder.include(item);
		}
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder
				.build()));
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		super.onDestroy();
	}

}
