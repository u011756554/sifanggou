package com.app.sifanggou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.FirstChildNodeTypeBean;
import com.app.sifanggou.view.MyGridView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/1.
 */

public class CategorySecondTypeAdapter extends SetBaseAdapter<FirstChildNodeTypeBean> {

    public CategorySecondTypeAdapter(Context context, List<FirstChildNodeTypeBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categorysecondtype,null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mgType = (MyGridView) convertView.findViewById(R.id.mv_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FirstChildNodeTypeBean bean = mList.get(position);
        holder.tvName.setText(bean.getName());
        return convertView;
    }

    private class ViewHolder{
        private TextView tvName;
        private MyGridView mgType;
    }

}
