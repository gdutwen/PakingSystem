package com.example.pakingsystem;


import java.util.ArrayList;
import java.util.List;

import com.example.pakingsystem.DB.PakingDB;
import com.example.pakingsystem.DB.PlacesDB;
import com.example.pakingsystem.MODEL.Places;
import com.example.pakingsystem.adapter.PlacesAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Admin_mainAcitivity extends Activity implements OnClickListener{
	
	private ImageButton ib_back;
	private ImageButton ib_add;
	private TextView title;
	
	private Button add_plot;
	private Button cancel;

	private PlacesDB placesDB;
	private EditText name;
	private EditText location;
	private EditText count;

	private ListView list;
	private PlacesAdapter placesAdapter;
	
	private List<Places> placesList = new ArrayList<Places>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.admin_main);
	    placesDB = new PlacesDB(this);
	    placesDB.open();
	    placesList =PlacesDB.fetchAll();
		initview();
		
		
	}

	private void initview() {
		ib_back = (ImageButton) findViewById(R.id.ib_back);
		ib_add =(ImageButton) findViewById(R.id.ib_add);
		title = (TextView) findViewById(R.id.tv_title);
		list = (ListView) findViewById(R.id.list_places);
		
		ib_back.setVisibility(View.INVISIBLE);
		ib_add.setVisibility(View.VISIBLE);
		title.setText("停车场管理主界面");
		
		placesAdapter = new PlacesAdapter(this,placesList);
		list.setAdapter(placesAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), Pakiing_infoActivity.class);
				intent.putExtra("placesname", placesList.get(position).getName());
				startActivity(intent);
				
			}
		});
		
		ib_add.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {		
		case R.id.ib_add:
			showpopwindow(v);
		default:
			break;
		}
		
	}

	private void showpopwindow(View v) {
		 View contentView = LayoutInflater.from(this).inflate(R.layout.add_paking_plot, null);
	        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  

	         
	            	add_plot = (Button) contentView.findViewById(R.id.bt_add_plot);
	                cancel = (Button) contentView.findViewById(R.id.bt_cancel_plot);
	                
	                name = (EditText) contentView.findViewById(R.id.et_add_places_name);
	                location = (EditText) contentView.findViewById(R.id.et_add_places_location);
	                count = (EditText) contentView.findViewById(R.id.et_add_places_count);
	         
	                add_plot.setOnClickListener(new OnClickListener() {
				
					@Override
					public void onClick(View v) {
						if (name.getText().toString().isEmpty()) {
							Toast.makeText(getApplicationContext(), "请输入停车场名称", Toast.LENGTH_LONG).show();
						}else if (location.getText().toString().isEmpty()) {
							Toast.makeText(getApplicationContext(), "请输入停车场位置", Toast.LENGTH_LONG).show();
						}else if (count.getText().toString().isEmpty()) {
							Toast.makeText(getApplicationContext(), "请输入停车场车位数", Toast.LENGTH_LONG).show();
						}else {
							placesDB.createPlaces(name.getText().toString(), location.getText().toString(), count.getText().toString());
							Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
							popupWindow.dismiss(); 
						
						} 
					}
				});
	                cancel.setOnClickListener(new OnClickListener() {
	    				
						@Override
						public void onClick(View v) {
							
							popupWindow.dismiss();   
						}
					});
	                
	          popupWindow.setFocusable(true);
	          popupWindow.setBackgroundDrawable(new ColorDrawable(0x0000FF));
	          // 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
	          final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
	          wlBackground.alpha = 0.2f;      // 0.0 完全不透明,1.0完全透明
	          getWindow().setAttributes(wlBackground);
	          // 当PopupWindow消失时,恢复其为原来的颜色
	          popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
	              @Override
	              public void onDismiss() {
	                  wlBackground.alpha = 1.0f;
	                  getWindow().setAttributes(wlBackground);
	              }
	          });
	          
	     popupWindow.showAtLocation(v,Gravity.CENTER,0,0); 
		
	}

}
