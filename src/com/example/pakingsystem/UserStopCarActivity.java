package com.example.pakingsystem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.pakingsystem.DB.PakingDB;
import com.example.pakingsystem.DB.PlacesDB;
import com.example.pakingsystem.MODEL.Paking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserStopCarActivity extends Activity implements OnClickListener{
	private TextView title;
	private ImageButton back;
	private TextView total;
	private TextView nouse;
	
	private String name;
	private String count;
	private PakingDB pakingDB;
	private Button stop_car;
	private Button out_car;
	private PlacesDB placesDB;
	private EditText licese_num;
	private EditText places_num;
	private List<Paking> pakingList = new ArrayList<Paking>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_stop_car);
		Intent intent= getIntent();
		name = intent.getStringExtra("name");
		count = intent.getStringExtra("count");
		pakingDB = new PakingDB(this);
		pakingDB.open();
		placesDB = new PlacesDB(this);
		placesDB.open();
		pakingList = PakingDB.fetchsome(name);
		initview();
	}

	private void initview() {
		title = (TextView) findViewById(R.id.tv_title);
		back = (ImageButton) findViewById(R.id.ib_back);
		total = (TextView) findViewById(R.id.tv_user_stop_car_total);
		stop_car = (Button) findViewById(R.id.bt_user_stop_car);
		out_car = (Button) findViewById(R.id.bt_out_car);
		licese_num = (EditText) findViewById(R.id.et_user_stop_car_lnum);
		places_num = (EditText) findViewById(R.id.et_user_stop_car_num);
		nouse = (TextView) findViewById(R.id.tv_user_stop_car_nouse);
		
		total.setText("总车位："+count);
		int total = Integer.parseInt(placesDB.queryUserByName(name).getCount());
		String no = Integer.toString(total-pakingList.size());
		
		nouse.setText("剩余车位：" + no );
		title.setText(name);
		back.setOnClickListener(this);
		
		stop_car.setOnClickListener(this);
		out_car.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_back:
			finish();
			break;
		case R.id.bt_user_stop_car:
			stopcar();
			break;
		case R.id.bt_out_car:
			outcar();
			break;
		default:
			break;
		}
		
	}

	private void outcar() {
		SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");       
		String    date    =    sDateFormat.format(new    java.util.Date());
		if(licese_num.getText().toString().isEmpty()){
			Toast.makeText(getApplicationContext(), "请输入车牌号", Toast.LENGTH_SHORT).show();

		}else if ( places_num.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), "请输入车位号", Toast.LENGTH_SHORT).show();

		}else if (pakingDB.queryPakingByLName(licese_num.getText().toString())==null) {
			Toast.makeText(getApplicationContext(), "当前没有这辆车", Toast.LENGTH_LONG).show();
		}else if (!pakingDB.queryPakingByLName(licese_num.getText().toString()).getPlaces_number().equals(places_num.getText().toString())) {
			Toast.makeText(getApplicationContext(), "车位号不对", Toast.LENGTH_LONG).show();
		}else{
			 if (pakingDB.queryPakingByLName(licese_num.getText().toString()).getOut_time().equals("null")) {
				 pakingDB.updateCar(licese_num.getText().toString(), date);
				 Toast.makeText(getApplicationContext(), "出库成功", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getApplicationContext(), "车不在停车场中", Toast.LENGTH_LONG).show();
				
			}

		}
	}

	private void stopcar() {
		
		SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");       
		String    date    =    sDateFormat.format(new    java.util.Date());
		if(licese_num.getText().toString().isEmpty()){
			Toast.makeText(getApplicationContext(), "请输入车牌号", Toast.LENGTH_SHORT).show();

		}else if ( places_num.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), "请输入车位号", Toast.LENGTH_SHORT).show();

		}else if (pakingDB.queryPakingByLName(licese_num.getText().toString()).getLicese_number() == null) {
			PakingDB.createUser(name, licese_num.getText().toString(), date, null, places_num.getText().toString(), "2","0");			
			Toast.makeText(getApplicationContext(), "停车成功", Toast.LENGTH_SHORT).show();
		}else if(pakingDB.queryPakingByLName(licese_num.getText().toString()).getPlaces_name() !=null||(pakingDB.queryPakingByName(licese_num.getText().toString()).getOut_time()!=null&&pakingDB.queryPakingByName(licese_num.getText().toString()).getPlaces_name().equals(places_num.getText().toString()))){
			Toast.makeText(getApplicationContext(), "车位已被停了，下次请早！", Toast.LENGTH_SHORT).show();

		}else{
			Toast.makeText(getApplicationContext(), "车还没开走，请把车开走，再来停吧！", Toast.LENGTH_LONG).show();
		}
		
	}
	

}
