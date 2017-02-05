package com.example.pakingsystem;

import java.sql.SQLException;

import com.example.pakingsystem.DB.CommDB;
import com.example.pakingsystem.DB.UserDB;
import com.example.pakingsystem.MODEL.User;
import android.annotation.SuppressLint;
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
import android.widget.Toast;


public class RegisterAcitvity extends Activity implements OnClickListener{
	private ImageButton back;
	private Button register;
	private EditText name;
	private EditText password;
	private EditText type;
	private EditText question;
	private EditText answer;
	private EditText t_password;
	private UserDB userDB ;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		userDB = new UserDB(this);
		userDB.open();
		initview();
	}

	private void initview() {
		back = (ImageButton) findViewById(R.id.ib_back);
		register = (Button) findViewById(R.id.bt_register);
		
		name = (EditText) findViewById(R.id.et_rg_name);
		password = (EditText) findViewById(R.id.et_rg_password);
		t_password = (EditText) findViewById(R.id.et_rg_tpassword);
		question = (EditText) findViewById(R.id.et_rg_question);
		answer = (EditText) findViewById(R.id.et_rg_answer);
		type= (EditText) findViewById(R.id.et_rg_type);
		
		back.setOnClickListener(this);
		register.setOnClickListener(this);
		
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//返回上一级
		case R.id.ib_back:
			finish();
			break;
			//注册按钮，首先判断是否已经注册了
		case R.id.bt_register:
			
			if (name.getText().toString().isEmpty()) {
				Toast.makeText(this, "账号不能为空", Toast.LENGTH_LONG).show();
			}else if (password.getText().toString().isEmpty()) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
			}else if(!password.getText().toString().equals(t_password.getText().toString())){
				Toast.makeText(this, "密码不一致", Toast.LENGTH_LONG).show();
			}else{
				if(userDB.queryUserByName(name.getText().toString()).getUsername()!= null){
					Toast.makeText(this, "此账号已被注册", Toast.LENGTH_LONG).show();
				}else {
					register();
				}
				
			}
			
			break;
		default:
			break;
		}
		
	}
	/**
	 * 注册
	 */

	private void register() {
		
		//插入数据
		userDB.createUser(name.getText().toString(), password.getText().toString(), question.getText().toString(), answer.getText().toString(), type.getText().toString());
		Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
		//成功跳转登录界面，并关闭界面
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		finish();
	}

}
