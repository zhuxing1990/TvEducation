package com.vunke.education.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.education.R;
import com.vunke.education.adapter.RankingsAdapter;
import com.vunke.education.base.BaseActivity;
import com.vunke.education.base.Configs;
import com.vunke.education.log.WorkLog;
import com.vunke.education.model.RanKingsBean;
import com.vunke.education.network_request.NetWorkRequest;
import com.vunke.education.view.TvFocusGridView2;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;


public class RankingsActivity extends BaseActivity {
	private static final String TAG = "RankingsActivity";
	private TvFocusGridView2 greadView =  null;
	private View currentFocus = null;
	private RanKingsBean bean;
	private RankingsAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rankings);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		greadView = (TvFocusGridView2) findViewById(R.id.rankings_gread);
		greadView.setClickable(true);
		greadView.setEnabled(true);
		greadView.setClipToPadding(false);//  是否允许ViewGroup在padding中绘制     具体解释:http://www.tuicool.com/articles/m6N36zQ
		greadView.setSelected(true);//支持选择
		greadView.setSelection(1);// 选择当前下标为 0  第一个/
//		View findViewByIds1 = currentFocus.findViewById(R.id.rankings_item);
//		findViewByIds1.setBackgroundResource(R.drawable.bg_border1);
		greadView.setSelector(android.R.color.transparent);//设置选中后的透明效果
		greadView.setMySelector(R.drawable.bg_border1);//设置选中后的边框
		greadView.setMyScaleValues(1.2f, 1.2f);//设置选中后 默认扩大倍数

		Log.e("move", currentFocus+"");
		initListener();
	}

	@NonNull
	private void initData() {
		try {
			OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.SELECT_RANKINGS).tag(this).execute(new StringCallback() {
				@Override
				public void onSuccess(String s, Call call, Response response) {
					WorkLog.i(TAG, "onSuccess: ------------------------------------------------------------"+s);
					try {
						JSONObject js = new JSONObject(s);
						if (js.has("code")){
							int code = js.getInt("code");
							switch (code){
								case 200:
									Gson gson = new Gson();
									bean = gson.fromJson(s,RanKingsBean.class);
									break;
								case 400:
									break;
								case 500:
									break;
							}
						}
					}catch (Exception e){
						e.printStackTrace();
					}
				}

				@Override
				public void onError(Call call, Response response, Exception e) {
					super.onError(call, response, e);
					WorkLog.i(TAG, "onError: ---------------------------------------------------------------");
				}

				@Override
				public void onAfter(String s, Exception e) {
					super.onAfter(s, e);
					if (bean!=null && bean.getCode().equals("200")){
						initGridView();
					}
				}
			});

		}catch (Exception e ){
			e.printStackTrace();
		}
	}
	public void initGridView(){
		adapter = new RankingsAdapter(bean.getAll(),mcontext);
		greadView.setAdapter(adapter);
	}

	private void initListener() {
		greadView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Configs.intent = new Intent();
				Configs.intent.setClass(RankingsActivity.this, VideoDetailsActivity.class);
				WorkLog.i(TAG, "onItemClick: infoId:"+bean.getAll().get(position).getInfoid());
				Configs.intent.putExtra("infoId",bean.getAll().get(position).getInfoid());
				startActivity(Configs.intent);
			}
		});
	}

}
