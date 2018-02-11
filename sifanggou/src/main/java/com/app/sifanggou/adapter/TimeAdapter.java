package com.app.sifanggou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.DateBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class TimeAdapter extends SetBaseAdapter<DateBean> {
    public TimeAdapter(Context context, List<DateBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_time,null);
            holder = new ViewHolder();
            holder.tvTime = convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DateBean bean = mList.get(position);
        holder.tvTime.setText(bean.getYear()+"年"+bean.getMonth()+"月");
        return convertView;
    }

    private class ViewHolder {
        private TextView tvTime;
    }
}
