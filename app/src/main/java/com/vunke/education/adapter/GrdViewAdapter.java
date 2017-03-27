package com.vunke.education.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vunke.education.R;

import java.util.List;
import java.util.Map;


public class GrdViewAdapter extends BaseAdapter {

    private List<Map<String, Object>> datalists;
    private Context context;

    public GrdViewAdapter(List<Map<String, Object>> datalists, Context context) {
        this.datalists = datalists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datalists.size();
    }

    @Override
    public Object getItem(int position) {
        return datalists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
        View view = View.inflate(context, R.layout.listview_item_videodetails, null);

        ImageView iv_details = (ImageView) view.findViewById(R.id.iv_details);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

        Map<String, Object> map = datalists.get(position);
        iv_details.setImageResource((Integer) map.get("image"));
        tv_name.setText(map.get("name") + "");
            return view;
        }
        return convertView;
    }
}
