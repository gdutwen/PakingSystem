package com.example.pakingsystem;

import com.example.pakingsystem.DB.PakingDB;
import com.example.pakingsystem.MODEL.Paking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SetUpActivity extends Activity implements OnClickListener{
	private TextView title;
	private ImageButton back;
	private Button setup;
	private Button cancel;
	private TextView text_cost;
	private EditText update_cost;
	private String name;
	private PakingDB pakingDB;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.paking_count);
		Intent  intent = getIntent();
		name = intent.getStringExtra("name");
		Log.e("name", name);
		pakingDB = new PakingDB(this);
		pakingDB.open();
		initview();
	}

	private void initview() {
		title = (TextView) findViewById(R.id.tv_title);
		back = (ImageButton) findViewById(R.id.ib_back);
		setup =(Button) findViewById(R.id.bt_setup);
		cancel = (Button) findViewById(R.id.bt_cancel_setup);
		text_cost = (TextView) findViewById(R.id.tv_setup_price);
		update_cost = (EditText) findViewById(R.id.et_setup_price);
		
		back.setOnClickListener(this);
		setup.setOnClickListener(this);
		cancel.setOnClickListener(this);
		
		title.setText("设置");
		text_cost.setText("目前的收费标准是"+pakingDB.queryPakingByName(name).getPrice()+"元/小时");
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_back:
			finish();
			break;
		case R.id.bt_setup:
			setup();
			break;
		case R.id.bt_cancel_setup:
			cancel();
			break;
		default:
			break;
		}
		
	}

	private void setup() {
		if (update_cost.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), "请输入价格", Toast.LENGTH_SHORT).show();
		}else{
			pakingDB.updatePrice(name,update_cost.getText().toString());
			update_cost.setText("");
			text_cost.setText("目前的收费标准是"+pakingDB.queryPakingByName(name).getPrice()+"元/小时");
			Toast.makeText(getApplicationContext(), "目前的收费标准是"+pakingDB.queryPakingByName(name).getPrice()+"元/小时", Toast.LENGTH_SHORT).show();
			
		}
		
	}

	private void cancel() {
		update_cost.setText("");
		
	}
	

}
