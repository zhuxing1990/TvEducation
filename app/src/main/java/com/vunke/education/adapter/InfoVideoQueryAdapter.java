package com.vunke.education.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vunke.education.R;
import com.vunke.education.model.InfoVideoQueryBean;
import com.vunke.education.util.PicassoUtil;

import java.util.List;


public class InfoVideoQueryAdapter extends DefaultAdapter<InfoVideoQueryBean.InfoVideoBean> {
    private static final String TAG = "InfoVideoQueryAdapter";
    private Context context;
    public InfoVideoQueryAdapter(List<InfoVideoQueryBean.InfoVideoBean> list, Context context) {
        super(list);
        this.context = context;
    }

    @Override
    protected BaseHolder getHolder() {
        return new InfoVideoHolder();
    }
    public class InfoVideoHolder extends BaseHolder<InfoVideoQueryBean.InfoVideoBean>{
        private ImageView videodetails_item_videoimg;
        private TextView videodetails_item_text;
        @Override
        protected View initView() {
            View view  = View.inflate(context, R.layout.listview_item_videodetails,null);
            videodetails_item_videoimg = (ImageView) view.findViewById(R.id.videodetails_item_videoimg);
            videodetails_item_text = (TextView) view.findViewById(R.id.videodetails_item_text);
            return view;
        }

        @Override
        protected void refreshView(InfoVideoQueryBean.InfoVideoBean data, int position, ViewGroup parent) {
//            WorkLog.i(TAG, "refreshView: data:"+data.getMainpicurl());
            PicassoUtil.getInstantiation().onBigNetImage(context,data.getMainpicurl(),videodetails_item_videoimg);
            videodetails_item_text.setText(data.getTitle());
        }
    }
}
//public class InfoVideoQueryAdapter extends BaseAdapter {
//
//    private List<Map<String, Object>> datalists;
//    private Context context;
//
//    public InfoVideoQueryAdapter(List<Map<String, Object>> datalists, Context context) {
//        this.datalists = datalists;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return datalists.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return datalists.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null){
//        View view = View.inflate(context, R.layout.listview_item_videodetails, null);
//
//        ImageView iv_details = (ImageView) view.findViewById(R.id.videodetails_videoimg);
//        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
//
//        Map<String, Object> map = datalists.get(position);
//        iv_details.setImageResource((Integer) map.get("image"));
//        tv_name.setText(map.get("name") + "");
//            return view;
//        }
//        return convertView;
//    }
//}
