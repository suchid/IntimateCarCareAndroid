package com.IntimateCarCare;

import java.util.HashMap;

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
import android.widget.TextView;

import com.Adapter.ActivityDataFlowsAdapter;
import com.Adapter.ActivityFaultListAdapter;
import com.Bll.CBLL;
import com.Entity.CarReport;
import com.Entity.DataFlowSet;
import com.Entity.FaultSet;
import com.Entity.UserEntity;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.ToastUtil;

public class HistoryCarReportDetail extends Activity{

	private TextView tv_carcheckscore, tv_faultcodenum,
	tv_dataflownum,tv_conclusion,tv_time;
	private LinearLayout lin_back;
	private ListView listview, listview1;
	private FaultSet faultlist;
	private DataFlowSet dataflowlist;
	private ActivityFaultListAdapter faultlistadapter;
	private ActivityDataFlowsAdapter dataflowadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_historycarreportdetail);

		initView();
		RequestData();
		setListen();

	}

	@SuppressWarnings("unchecked")
	private void RequestData() {
		// TODO Auto-generated method stub
		Intent intent=getIntent();
		int reportid=intent.getIntExtra("reportid", -1);
		HashMap<String, String> idtakjson = new UserEntity()
				.getIdTaken(HistoryCarReportDetail.this);
		idtakjson.put("dreport_id", reportid+"");
		
		new HttpTask(historycarreportCallback, MyURL.HISTORYCARREPORTDETAIL,HistoryCarReportDetail.this).execute(idtakjson);
	}

	private HttpCallback historycarreportCallback = new HttpCallback() {

		@Override
		public void getResult(JSONObject json) {
			// 历史详情和最新车况检查 数据项一样   所以用一样的数据解析
			CBLL cbll = CBLL.getInstance();
			JSONEntity entity = cbll.json2carreport(json);
			if (entity.isFlag()) {
				CarReport carreport=(CarReport) entity.getData();
				tv_carcheckscore.setText(carreport.getDetect_score()+"");
				tv_faultcodenum.setText(carreport.getFaults().size()+"");
				tv_dataflownum.setText(carreport.getDataflows().size()+"");
				tv_conclusion.setText(carreport.getDetect_conclusion());
				tv_time.setText(carreport.getDetect_time()+"的车况检查报告");
				faultlist=new FaultSet(carreport.getFaults());
				dataflowlist=new DataFlowSet(carreport.getDataflows());
				faultlistadapter=new ActivityFaultListAdapter(HistoryCarReportDetail.this, faultlist);
				dataflowadapter=new ActivityDataFlowsAdapter(HistoryCarReportDetail.this, dataflowlist);
				listview.setAdapter(faultlistadapter);
				listview1.setAdapter(dataflowadapter);
				
				
			} else {
				if (entity.getMessage() == MyURL.MSG_OTHERS_ERROR) {
					ToastUtil.show(HistoryCarReportDetail.this, "获取失败");
				} else if (entity.getMessage() == MyURL.MSG_TOKENS_ERROR) {
					// 重启app
					MainActivity.restartApplication(HistoryCarReportDetail.this);
				}
			}

		}
	};

	private void initView() {
		// TODO Auto-generated method stub
		tv_carcheckscore = (TextView) findViewById(R.id.tv_carcheckscore);
		tv_faultcodenum = (TextView) findViewById(R.id.tv_faultcodenum);
		tv_dataflownum = (TextView) findViewById(R.id.tv_dataflownum);
		lin_back = (LinearLayout) findViewById(R.id.lin_back);
		listview = (ListView) findViewById(R.id.listview);
		listview1 = (ListView) findViewById(R.id.listview1);
		tv_conclusion=(TextView) findViewById(R.id.tv_conclusion);
		tv_time=(TextView) findViewById(R.id.tv_time);

	}

	private void setListen() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
					Intent intent=new Intent(HistoryCarReportDetail.this,FaultDetail.class);
					intent.putExtra("faultid", faultlist.getItem(position).getFault_id());;
					startActivity(intent);
			}
		});
		
		listview1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HistoryCarReportDetail.this,DataFlowDetail.class);
				intent.putExtra("dataflowid", dataflowlist.getItem(position).getDataflow_id());
				startActivity(intent);
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
