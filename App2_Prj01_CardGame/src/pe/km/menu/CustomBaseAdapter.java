package pe.km.menu;

import java.util.ArrayList;

import pe.km.game.R;
import pe.km.game.UserInfo;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {
	private LayoutInflater inflator = null;
	private ArrayList<UserInfo> userInfo = null;
	private ViewHolder viewHolder = null;
	private Context context = null;

	public CustomBaseAdapter(Context context, ArrayList<UserInfo> userArrayList) {
		this.context = context;
		this.userInfo = userArrayList;
		this.inflator = LayoutInflater.from(context);

		for (int i = 0; i < this.userInfo.size(); i++) {
			Log.d("userInfo - >", this.userInfo.get(i).getDbId() + " // "
					+ this.userInfo.get(i).getName() + " // "
					+ this.userInfo.get(i).getScore());
		}
	}

	public int getCount() {
		return userInfo.size();
	}

	public UserInfo getItem(int position) {
		return userInfo.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			viewHolder = new ViewHolder();
			v = inflator.inflate(R.layout.list_item, null);
			viewHolder.tv_name = (TextView) v.findViewById(R.id.item_name);
			viewHolder.tv_score = (TextView) v.findViewById(R.id.item_score);
			viewHolder.tv_clearTime = (TextView) v
					.findViewById(R.id.item_clearTime);
			
			viewHolder.tv_name.setTextColor(Color.WHITE);
			viewHolder.tv_score.setTextColor(Color.WHITE);
			viewHolder.tv_clearTime.setTextColor(Color.WHITE);

			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}
		
		viewHolder.tv_name.setText(getItem(position).getName());
		
		viewHolder.tv_score.setTag(position);
		viewHolder.tv_score.setText(Integer.toString(getItem(position).getScore()));
		
		viewHolder.tv_clearTime.setTag(position);
		viewHolder.tv_clearTime.setText(Integer.toString(getItem(position).getClearTime()));

		return v;
	}

	private class ViewHolder {
		public TextView tv_name = null;
		public TextView tv_score = null;
		public TextView tv_clearTime = null;
	}

}
