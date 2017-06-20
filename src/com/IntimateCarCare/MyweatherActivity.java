package com.IntimateCarCare;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.Adapter.ActivityMyWeatherAdapter;
import com.Bll.CBLL;
import com.Entity.Setweather;
import com.Entity.UserEntity;
import com.Entity.weather;
import com.Http.HttpCallback;
import com.Http.HttpTask;
import com.tool.CalendarUtil;
import com.tool.JSONEntity;
import com.tool.MyURL;
import com.tool.SPUtils;
import com.tool.ToastUtil;

public class MyweatherActivity extends Activity {

	private ListView listview;
	private TextView solar_calendar,plate_number,prohibited_place;//系统时间
	private TextView lunar_calendar;//农历时间
	private ActivityMyWeatherAdapter adapter;
	private ImageView back_weather;
	private Setweather list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weather);
		
		RequestMainData();
		initView();
	    setListener();
	    timenow();
	    
	plate_number.setText(""+SPUtils.get(MyweatherActivity.this, "carnumber", ""));
	if(SPUtils.get(MyweatherActivity.this, "TAG", "").equals("今日畅行")){
		prohibited_place.setText("畅行  江夏桥、灵桥");
	}
	else{
		prohibited_place.setText("禁行  江夏桥、灵桥");
	}
	//getData();//初始化数据
	//adapter = new ActivityMyWeatherAdapter(MyweatherActivity.this, list);
	//listview.setAdapter(adapter);
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		back_weather=(ImageView)findViewById(R.id.back_weather);
		listview=	(ListView)findViewById(R.id.weatherlist);
		solar_calendar=(TextView)findViewById(R.id.solar_calendar);
		lunar_calendar=(TextView)findViewById(R.id.lunar_calendar);
		plate_number=(TextView)findViewById(R.id.plate_number);
		prohibited_place=(TextView)findViewById(R.id.prohibited_place);
		//weather_back=(LinearLayout)findViewById(R.id.weather_back);
		
	}
	
	private void setListener() {
		// TODO Auto-generated method stub
		back_weather.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
	
	}
/*	private void getData() {
		// TODO Auto-generated method stub		
			List<weather>l=new ArrayList<weather>();
			l.add(new weather("10.3","周一",R.drawable.cloudy,"晴","27℃-32℃","适合洗车"));
			l.add(new weather("10.4","周二",R.drawable.cloudy,"晴","27℃-32℃","适合洗车"));
			l.add(new weather("10.5","周三",R.drawable.cloudy,"晴","27℃-32℃","适合洗车"));
			l.add(new weather("10.6","周四",R.drawable.cloudy,"晴","27℃-32℃","适合洗车"));
			l.add(new weather("10.7","周五",R.drawable.cloudy,"晴","27℃-32℃","适合洗车"));
			l.add(new weather("10.8","周六",R.drawable.cloudy,"晴","27℃-32℃","适合洗车"));
			l.add(new weather("10.9","周日",R.drawable.cloudy,"晴","27℃-32℃","适合洗车"));
		    list=new Setweather(l);
	}*/
	private void timenow(){
		Time t=new Time(); 
		t.setToNow(); // 取得系统时间。  
		int year = t.year;  
		int month = t.month+1;  
		int date = t.monthDay; 
		solar_calendar.setText(year+"."+month+"."+date);
		
		String day = new CalendarUtil(Calendar.getInstance()).getDay();
		lunar_calendar.setText("农历  "+day);
	}
	
	

	
	
	@SuppressWarnings("unchecked")
	private void RequestMainData(){
		
	HashMap<String, String> allweather=new UserEntity().getIdTaken(MyweatherActivity.this);
		//获得天气活动有关数据
	new HttpTask(weatherCallback, MyURL.WEATHER,MyweatherActivity.this).execute(allweather);
		
	}
	
	private HttpCallback weatherCallback=new HttpCallback() {
		
		@Override
		public void getResult(JSONObject json) {
			// TODO Auto-generated method stub
			CBLL cBllWeather = CBLL.getInstance();
			JSONEntity entity = cBllWeather.json2weather(json);
			if(entity.isFlag()){
				
				Setweather weatherlist = (Setweather) entity.getData();
				List<weather>l=new ArrayList<weather>();	
				for(int i=0; i<weatherlist.getSize(); i++){	
					weather weatherentity = weatherlist.getItem(i);
					
					l.add(weatherentity);
							
				list=new Setweather(l);
				adapter = new ActivityMyWeatherAdapter(MyweatherActivity.this, list);
				listview.setAdapter(adapter);	
				
				
			}
			}
			else{
			ToastUtil.show(MyweatherActivity.this, "请检查网络");
			}
		}
	};
}
