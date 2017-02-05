package com.example.pakingsystem;

import java.util.ArrayList;
import java.util.List;

import com.example.pakingsystem.DB.PakingDB;
import com.example.pakingsystem.DB.PlacesDB;
import com.example.pakingsystem.MODEL.Paking;
import com.example.pakingsystem.MODEL.Places;
import com.example.pakingsystem.adapter.ListCarAdapter;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Pakiing_infoActivity extends Activity implements OnClickListener {
	private ImageButton back;
	private TextView title;
	private ImageButton other_info;
	private ListView list;
	private List<Paking> pakingList = new ArrayList<Paking>();
	private PakingDB pakingDB;
	private PlacesDB placesDB;
	private ListCarAdapter listcarAdapter;
	private TextView use;
	private TextView no_use;
	
	
	private String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.admim_car_info);
		Intent intent = getIntent();
		name = intent.getStringExtra("placesname");
		pakingDB = new PakingDB(this);
		pakingDB.open();
		placesDB = new PlacesDB(this);
		placesDB.open();
		
		pakingList = PakingDB.fetchsome(name);
		initview();
		
	}

	private void initview() {
		back = (ImageButton) findViewById(R.id.ib_back);
		title = (TextView) findViewById(R.id.tv_title);
		other_info = (ImageButton) findViewById(R.id.ib_other);
		list = (ListView) findViewById(R.id.list_car);
		
		use = (TextView) findViewById(R.id.tv_car_use);
		no_use = (TextView) findViewById(R.id.tv_car_no_use);
		
		use.setText("已用车位："+ pakingList.size());
		int total = Integer.parseInt(placesDB.queryUserByName(name).getCount());
		String no = Integer.toString(total-pakingList.size());
		
		no_use.setText("剩余车位：" + no );
		listcarAdapter = new ListCarAdapter(this, pakingList);
		list.setAdapter(listcarAdapter);
		
		other_info.setVisibility(View.VISIBLE);
		
		title.setText(name);
		
		back.setOnClickListener(this);
		other_info.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_back:
			finish();
			break;
		case R.id.ib_other:
			showpopwindow(v);
			break;
		default:
			break;
		}
		
	}

	private void showpopwindow(View v) {
		View contentView = LayoutInflater.from(this).inflate(R.layout.other_info, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  
          Button setup = (Button) contentView.findViewById(R.id.bt_pop_setup);
          setup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				Intent intent = new Intent(getApplicationContext(),SetUpActivity.class);
				intent.putExtra("name", name);
				startActivity(intent);
				
			}
		});
          Button count = (Button) contentView.findViewById(R.id.bt_pop_count);
          count.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				Intent intent1 = new Intent(getApplicationContext(), DataStatisAcitivity.class);
				startActivity(intent1);
				
			}
		});
          Button revenue = (Button) contentView.findViewById(R.id.bt_pop_revenue);
          revenue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				
				
			}
		});
          popupWindow.setFocusable(true);
          popupWindow.setBackgroundDrawable(new ColorDrawable(0x0000FF));
          // 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
          final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
          wlBackground.alpha = 0.5f;      // 0.0 完全不透明,1.0完全透明
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
