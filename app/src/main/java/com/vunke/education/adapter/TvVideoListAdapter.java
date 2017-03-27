package com.vunke.education.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vunke.education.R;
import com.vunke.education.model.VideoListBean;

import java.util.List;

/**
 * Created by zhuxi on 2017/3/25.
 */
public class TvVideoListAdapter extends DefaultAdapter<VideoListBean>{
    private List<VideoListBean> list;
    private Context context;
    public TvVideoListAdapter(List<VideoListBean> list,Context context) {
        super(list);
        this.context = context;
        this.list = list;
    }

    @Override
    protected BaseHolder getHolder() {
        return new VideoListHolder();
    }
    public class VideoListHolder extends BaseHolder<VideoListBean>{
        private TextView videolist_gridview_name;
        private ImageView videolist_gridview_videoimg;
        private TextView videolist_gridview_videotime;
        @Override
        protected View initView() {
            View view = View.inflate(context, R.layout.item_gridview_videolist,
                    null);
            videolist_gridview_name = (TextView) view.findViewById(R.id.videolist_gridview_name);
            videolist_gridview_videoimg = (ImageView) view.findViewById(R.id.videolist_gridview_videoimg);
            videolist_gridview_videotime = (TextView) view.findViewById(R.id.videolist_gridview_videotime);
            return view;
        }

        @Override
        protected void refreshView(VideoListBean data, int position, ViewGroup parent) {
            if (TextUtils.isEmpty(data.getVideoImgPath())){
                videolist_gridview_videoimg.setImageDrawable(data.getVideoDrawable());
            }
            if (!TextUtils.isEmpty(data.getVideoName())){
                videolist_gridview_name.setText(data.getVideoName());
            }
            if (!TextUtils.isEmpty(data.getVideoTime())){
                videolist_gridview_videotime.setText(data.getVideoTime());
            }
        }
    }
}
