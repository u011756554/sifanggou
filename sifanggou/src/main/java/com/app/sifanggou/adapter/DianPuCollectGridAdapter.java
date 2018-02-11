package com.app.sifanggou.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public class DianPuCollectGridAdapter extends SetBaseAdapter<BusinessInfoBean> {

    public DianPuCollectGridAdapter(Context context, List<BusinessInfoBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpater_dianpucollectgrid,null);
            holder = new ViewHolder();
            holder.ivPic = convertView.findViewById(R.id.iv_pic);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvMarket = convertView.findViewById(R.id.tv_market);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BusinessInfoBean businessInfoBean = mList.get(position);

        //计算尺寸
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.ivPic.getLayoutParams();
        int size = (CommonUtils.getScreenWidth((Activity) mContext)
                - CommonUtils.dip2px(mContext,30)
                - CommonUtils.dip2px(mContext,20)) / 3;
        params.width = size;
        params.height = size;

        holder.ivPic.setLayoutParams(params);

        if (!TextUtils.isEmpty(businessInfoBean.getHead_pic_url())) {
            ImageLoaderUtil.display(businessInfoBean.getHead_pic_url(),holder.ivPic);
        }
        holder.tvName.setText(businessInfoBean.getName());
        holder.tvMarket.setText(businessInfoBean.getMarket_name());
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivPic;
        private TextView tvName;
        private TextView tvMarket;
    }
}
