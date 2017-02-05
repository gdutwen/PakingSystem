package com.example.pakingsystem.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.pakingsystem.R;
import com.example.pakingsystem.DB.PlacesDB;
import com.example.pakingsystem.MODEL.Places;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlacesAdapter extends BaseAdapter {
	
	private ViewHolder viewholder;
	private List<Places> placeslist = new ArrayList<Places>();
	private LayoutInflater layoutInflater;
	private Context context;
	public PlacesAdapter(Context context,List<Places> placeslist) {
		this.context = context;
		this.placeslist = placeslist;
	}

	@Override
	public int getCount() {
		return placeslist.size();
	}

	@Override
	public Object getItem(int position) {
		return placeslist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewholder = new ViewHolder();
		if (convertView == null) {
			layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.list_places, null);
			viewholder.name = (TextView) convertView.findViewById(R.id.tv_places_name);
			viewholder.location = (TextView) convertView.findViewById(R.id.tv_places_location);
			viewholder.count = (TextView) convertView.findViewById(R.id.tv_places_count);
			convertView.setTag(viewholder);
		}else{
			viewholder = (ViewHolder) convertView.getTag();
		}
		viewholder.name.setText(placeslist.get(position).getName());
		viewholder.location.setText(placeslist.get(position).getLocation());
		viewholder.count.setText(placeslist.get(position).getCount());
		return convertView;
	}
	public class ViewHolder{
		private TextView name;
		private TextView location;
		private TextView count;
	}

}
