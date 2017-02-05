package com.example.pakingsystem.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.pakingsystem.R;
import com.example.pakingsystem.MODEL.Paking;
import com.example.pakingsystem.MODEL.Places;
import com.example.pakingsystem.adapter.UserPakingAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListCarAdapter extends BaseAdapter {
	
	private ViewHolder viewholder;
	private List<Paking> pakinglist = new ArrayList<Paking>();
	private LayoutInflater layoutInflater;
	private Context context;
	
	public ListCarAdapter(Context context,List<Paking> pakinglist) {
		this.context = context;
		this.pakinglist = pakinglist;
	}
  
	@Override
	public int getCount() {
		return pakinglist.size();
	}

	@Override
	public Object getItem(int position) {
		return pakinglist.get(position);
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
			convertView = layoutInflater.inflate(R.layout.list_car, null);
			viewholder.licese_num = (TextView) convertView.findViewById(R.id.tv_car_lnum);
			viewholder.in_time = (TextView) convertView.findViewById(R.id.tv_car_in_time);
			viewholder.total_time = (TextView) convertView.findViewById(R.id.tv_car_time);
			viewholder.places_num = (TextView) convertView.findViewById(R.id.tv_car_places_num);
			viewholder.total = (TextView) convertView.findViewById(R.id.tv_car_cost);
			convertView.setTag(viewholder);
		}else{
			viewholder = (ViewHolder) convertView.getTag();
		}
		viewholder.licese_num.setText("车牌号："+pakinglist.get(position).getLicese_number());
		viewholder.in_time.setText("入场时间："+pakinglist.get(position).getIn_time());
		viewholder.places_num.setText("车位号："+pakinglist.get(position).getPlaces_number());
		viewholder.total_time.setText("出场时间："+pakinglist.get(position).getOut_time());
		viewholder.total.setText("当前消费："+pakinglist.get(position).getTotal());
		return convertView;
	}
	public class ViewHolder{
		private TextView licese_num;
		private TextView in_time;
		private TextView total_time;
		private TextView places_num;
		private TextView total;
	}

}
