package com.example.pakingsystem.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.pakingsystem.R;
import com.example.pakingsystem.MODEL.Places;
import com.example.pakingsystem.adapter.PlacesAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserPakingAdapter extends BaseAdapter {
	private ViewHolder viewholder;
	private List<Places> placeslist = new ArrayList<Places>();
	private LayoutInflater layoutInflater;
	private Context context;
	public UserPakingAdapter(Context context,List<Places> placeslist) {
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
			convertView = layoutInflater.inflate(R.layout.list_user_paking, null);
			viewholder.name = (TextView) convertView.findViewById(R.id.tv_user_pakingname);
			convertView.setTag(viewholder);
		}else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		viewholder.name.setText(placeslist.get(position).getName());
		return convertView;
	}
	public class ViewHolder{
		private TextView name;
	}

}
