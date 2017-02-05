package com.example.pakingsystem;

import java.util.ArrayList;
import java.util.List;

import com.example.pakingsystem.DB.PlacesDB;
import com.example.pakingsystem.MODEL.Places;
import com.example.pakingsystem.adapter.UserPakingAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class UserPakingAcitvity extends Activity {
	
	private TextView title;
	private ImageButton back;
	private ListView list;
	private PlacesDB placesDB;
	private UserPakingAdapter userpakingAdapter;
	private List<Places> placesList = new ArrayList<Places>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_paking);
		placesDB = new PlacesDB(this);
	    placesDB.open();
	    placesList =PlacesDB.fetchAll();
		initview();
	}

	private void initview() {
		title = (TextView) findViewById(R.id.tv_title);
		back = (ImageButton) findViewById(R.id.ib_back);
		list = (ListView) findViewById(R.id.list_user_places);
		
		title.setText("停车场");
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		
		userpakingAdapter = new UserPakingAdapter(this, placesList);
		list.setAdapter(userpakingAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), UserStopCarActivity.class);
				intent.putExtra("name", placesList.get(position).getName());
				intent.putExtra("count", placesList.get(position).getCount());
				startActivity(intent);
				
			}
		});
	}

}
