package com.vunke.education.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vunke.education.R;
import com.vunke.education.model.RankingGreadViewObject;

import java.util.List;

public class Myadapter extends BaseAdapter{
	Context context;
	List list;
	public Myadapter(Context context,List list){
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		RankingGreadViewObject object = (RankingGreadViewObject) list.get(arg0);
		ViewHodle viewholde = null;
		if (arg1 == null) {
			viewholde = new ViewHodle();
			LayoutInflater from = LayoutInflater.from(context);
			arg1 = from.inflate(R.layout.greadview_item, null);
			viewholde.imageView = (ImageView) arg1.findViewById(R.id.rankings_item);
			viewholde.textView = (TextView) arg1.findViewById(R.id.rankings_item_text);
		}
		return arg1;
	}
	
	class ViewHodle {
		public ImageView imageView;
		public TextView textView;
	}
	
}
