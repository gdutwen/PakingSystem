package com.example.pakingsystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class Forget_PasswordActivity extends Activity implements OnClickListener{
	private TextView title;
	private ImageButton back;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forget_password);
		initview();
		title.setText("忘记密码");
	}


	private void initview() {
		title = (TextView) findViewById(R.id.tv_title);
		back = (ImageButton) findViewById(R.id.ib_back);
		
		back.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_back:
			finish();
			break;

		default:
			break;
		}
		
	}

}
