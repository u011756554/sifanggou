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
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public class ProductCollectGridAdapter extends SetBaseAdapter<CommodityInfoBean> {

    public ProductCollectGridAdapter(Context context, List<CommodityInfoBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_productcollectgrid,null);
            holder = new ViewHolder();
            holder.ivPic = convertView.findViewById(R.id.iv_pic);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommodityInfoBean infoBean = mList.get(position);
        //计算尺寸
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.ivPic.getLayoutParams();
        int size = (CommonUtils.getScreenWidth((Activity) mContext)
                - CommonUtils.dip2px(mContext,30)
                - CommonUtils.dip2px(mContext,20)) / 3;
        params.width = size;
        params.height = size;

        holder.ivPic.setLayoutParams(params);

        if (!TextUtils.isEmpty(infoBean.getCommodity_pic_url())) {
            String[] urlArray = infoBean.getCommodity_pic_url().split(",");
            String url = urlArray[0];
            ImageLoaderUtil.display(url,holder.ivPic);

        }
        holder.tvName.setText(infoBean.getCommodity_name());
        if (!TextUtils.isEmpty(infoBean.getPrice())) {
            float price = Float.valueOf(infoBean.getPrice()) / 100;
            holder.tvPrice.setText("￥"+price);
        } else if (!TextUtils.isEmpty(infoBean.getA_price())) {
            float price = Float.valueOf(infoBean.getA_price()) / 100;
            holder.tvPrice.setText("￥"+price);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivPic;
        private TextView tvName;
        private TextView tvPrice;
    }
}
