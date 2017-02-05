package com.example.pakingsystem;

import com.example.pakingsystem.DB.CommDB;
import com.example.pakingsystem.DB.UserDB;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private Button register;
	private Button forget_password;
	private Button change_password;
	private Button login;
	private CommDB comDBHelper;
	private UserDB userDB;
	private EditText name;
	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		comDBHelper = new CommDB(this);
        comDBHelper.open();
        userDB = new UserDB(this);
        userDB.open();
       SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);    
        
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);    
        Editor editor = sharedPreferences.edit();    
            
        if (isFirstRun){    
            Log.e("debug", "第一次运行");    
            editor.putBoolean("isFirstRun", false);    
            editor.commit();    
            Intent intent = new Intent();  
            intent.setClass(MainActivity.this,AddinitdataActivity.class);  
            startActivity(intent); 
            initview();
        } else { 
        	 Log.e("debug", "不是第一次运行"); 
        	 initview();
        }    
        initview();
	}

	private void initview() {
		register = (Button) findViewById(R.id.bt_register);
		forget_password = (Button) findViewById(R.id.bt_forget_password);
		change_password = (Button) findViewById(R.id.bt_change_password);
		login = (Button) findViewById(R.id.bt_login);
		
		name = (EditText) findViewById(R.id.login_name);
		password = (EditText) findViewById(R.id.login_password);
		
		register.setOnClickListener(this);
		forget_password.setOnClickListener(this);
		change_password.setOnClickListener(this);
		login.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_register:
			Intent intent =new Intent(this, RegisterAcitvity.class);
			startActivity(intent);
			break;
		case R.id.bt_forget_password:
			Intent intent1 =new Intent(this, Forget_PasswordActivity.class);
			startActivity(intent1);
			break;
		case R.id.bt_change_password:
			Intent intent2 =new Intent(this, Change_PasswordActivity.class);
			startActivity(intent2);
			break;
		case R.id.bt_login:
			if(name.getText().toString().isEmpty()){
				Toast.makeText(this, "请输入账号", Toast.LENGTH_LONG).show();
			}else if (password.getText().toString().isEmpty()) {
				Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
			}else if(userDB.queryUserByName(name.getText().toString()).getUsername()==null){
				Toast.makeText(this, "用户不存在", Toast.LENGTH_LONG).show();
			}else if (!userDB.queryUserByName(name.getText().toString()).getPassword().equals(password.getText().toString())) {
				Toast.makeText(this, "密码不正确", Toast.LENGTH_LONG).show();
			}else {
				if(userDB.queryUserByName(name.getText().toString()).getType().equals("0")){
					Toast.makeText(this, "登陆成功", Toast.LENGTH_LONG).show();
					Intent intent3 = new Intent(this, Admin_mainAcitivity.class);
					startActivity(intent3);
				}else {
					Toast.makeText(this, "登陆成功", Toast.LENGTH_LONG).show();
					Intent intent4 = new Intent(this, User_MainActivity.class);
					startActivity(intent4);
				}
			}
			break;
		default:
			break;
		}
		
	}
	
}
