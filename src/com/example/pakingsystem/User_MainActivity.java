package com.example.pakingsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class User_MainActivity extends Activity {
	private TextView title;
	private ImageButton back;
	private Button stopcar;
	private Button cost;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_main);
		initview();
	}

	private void initview() {
		title = (TextView) findViewById(R.id.tv_title);
		back = (ImageButton) findViewById(R.id.ib_back);
		stopcar = (Button) findViewById(R.id.bt_usertostopcar);
		cost = (Button) findViewById(R.id.bt_query_cost);
		
		back.setVisibility(View.INVISIBLE);
		title.setText("用户主界面");
		
		stopcar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), UserPakingAcitvity.class);
				startActivity(intent);
			}
		});
		cost.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1 =new Intent(getApplicationContext(), ShowUserCostAcitivity.class);
				startActivity(intent1);
				
			}
		});
	}

}
