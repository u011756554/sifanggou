package com.app.sifanggou.adapter;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class BusinessInfoBeanAdapter extends SetBaseAdapter<BusinessInfoBean> {
    public BusinessInfoBeanAdapter(Context context, List<BusinessInfoBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_businessinfo,null);
            holder = new ViewHolder();
            holder.ivHead = (ImageView) convertView.findViewById(R.id.iv_head);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvFanWei = (TextView) convertView.findViewById(R.id.tv_fanwei);
            holder.tvMaxLevel = (TextView) convertView.findViewById(R.id.tv_jiebie);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
            holder.tvLevel = (TextView) convertView.findViewById(R.id.tv_level);
            holder.ivPic1 = (ImageView) convertView.findViewById(R.id.iv_pic1);
            holder.ivPic2 = (ImageView) convertView.findViewById(R.id.iv_pic2);
            holder.ivPic3 = (ImageView) convertView.findViewById(R.id.iv_pic3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BusinessInfoBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getHead_pic_url())) {
            ImageLoaderUtil.display(bean.getHead_pic_url(),holder.ivHead);
        }
        if (!TextUtils.isEmpty(bean.getName())) {
            holder.tvName.setText(bean.getName());
        }
        if (!TextUtils.isEmpty(bean.getGrade())) {
            holder.tvFanWei.setText(bean.getGrade());
        }
        if (!TextUtils.isEmpty(bean.getAgent_level())) {
            holder.tvMaxLevel.setText(bean.getAgent_level());
        }
        if (!TextUtils.isEmpty(bean.getCity())) {
            holder.tvCount.setText(bean.getCity());
        }
        if (!TextUtils.isEmpty(bean.getAgent_level())) {
            holder.tvLevel.setText(bean.getAgent_level());
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivHead;
        private TextView tvName;
        private TextView tvFanWei;
        private TextView tvMaxLevel;
        private TextView tvCount;
        private TextView tvLevel;
        private ImageView ivPic1;
        private ImageView ivPic2;
        private ImageView ivPic3;
    }
}