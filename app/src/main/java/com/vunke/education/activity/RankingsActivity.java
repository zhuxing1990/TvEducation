package com.vunke.education.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.vunke.education.R;
import com.vunke.education.adapter.Myadapter;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.model.RankingGreadViewObject;
import com.vunke.education.view.TvFocusGridView2;

import java.util.ArrayList;
import java.util.List;


public class RankingsActivity extends BaseActivity {
	TvFocusGridView2 greadView =  null;
	private View currentFocus = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rankings);
		List<RankingGreadViewObject> list = new ArrayList<RankingGreadViewObject>();
		for (int i = 0; i < 10; i++) {
			RankingGreadViewObject item = new RankingGreadViewObject();
			item.infoid = "1";
			item.name = "教育直通车";
			item.url = "http://www.baidu.com";
			list.add(item);
		}
		greadView = (TvFocusGridView2) findViewById(R.id.rankings_gread);
		greadView.setClickable(true);
		greadView.setEnabled(true);
		Myadapter adapter = new Myadapter(this,list);
		greadView.setAdapter(adapter);
		greadView.setClipToPadding(false);//  是否允许ViewGroup在padding中绘制     具体解释:http://www.tuicool.com/articles/m6N36zQ
		greadView.setSelected(true);//支持选择
		greadView.setSelection(1);// 选择当前下标为 0  第一个/

//		View findViewByIds1 = currentFocus.findViewById(R.id.rankings_item);
//		findViewByIds1.setBackgroundResource(R.drawable.bg_border1);
		greadView.setSelector(android.R.color.transparent);//设置选中后的透明效果
		greadView.setMySelector(R.drawable.selectbac);//设置选中后的边框
		greadView.setMyScaleValues(1.2f, 1.2f);//设置选中后 默认扩大倍数
		currentFocus = greadView.getChildAt(1);
		Log.e("move", currentFocus+"");
		greadView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				if (currentFocus != null) {
					View findViewByIds = currentFocus.findViewById(R.id.rankings_item);
					findViewByIds.setBackgroundResource(R.drawable.bg_border2);
				}
				currentFocus = greadView.getChildAt(arg2);
				View findViewById = arg1.findViewById(R.id.rankings_item);
				findViewById.setBackgroundResource(R.drawable.bg_border1);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		greadView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(RankingsActivity.this, VideoDetailsActivity.class);
				startActivity(intent);
			}
		});
	}

}
