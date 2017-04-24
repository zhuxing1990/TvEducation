package com.vunke.education.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vunke.education.R;
import com.vunke.education.model.RanKingsBean;
import com.vunke.education.util.PicassoUtil;

import java.util.List;

public class RankingsAdapter extends DefaultAdapter<RanKingsBean.AllBean>{
	private Context context;
	private List<RanKingsBean.AllBean> list;
	public RankingsAdapter(List<RanKingsBean.AllBean> list,Context context) {
		super(list);
		this.context = context;
		this.list = list;
	}

	@Override
	public BaseHolder getHolder() {
		return new RanKingsHoledr();
	}

	public class RanKingsHoledr extends BaseHolder<RanKingsBean.AllBean>{
		private ImageView imageview;
		private TextView textView;
		@Override
		protected View initView() {
			View view = View.inflate(context,R.layout.greadview_item,null);
			imageview = (ImageView) view.findViewById(R.id.rankings_item);
			textView = (TextView) view.findViewById(R.id.rankings_item_text);
			return view;
		}

		@Override
		protected void refreshView(RanKingsBean.AllBean data, int position, ViewGroup parent) {
			PicassoUtil.getInstantiation().onBigNetImage(context,data.getPic1(),imageview);
			textView.setText(data.getTitle());
		}
	}
}
