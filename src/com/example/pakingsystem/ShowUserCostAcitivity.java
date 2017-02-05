package com.example.pakingsystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShowUserCostAcitivity extends Activity {
	private ImageButton back;
	private TextView title;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_cost);
		initview ();
		
	}


	private void initview() {
		back = (ImageButton) findViewById(R.id.ib_back);
		title = (TextView) findViewById(R.id.tv_title);
		
		title.setText("消费记录");
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
		
	}

}
