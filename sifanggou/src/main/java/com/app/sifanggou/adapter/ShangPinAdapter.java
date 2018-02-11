package com.app.sifanggou.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.ProductType;
import com.app.sifanggou.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */

public class ShangPinAdapter extends SetBaseAdapter<CommodityInfoBean> {

    public ShangPinAdapter(Context context, List<CommodityInfoBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shangpin,null);
            holder = new ViewHolder();
            holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvXiaoLiang = (TextView) convertView.findViewById(R.id.tv_xiaoliang);
            holder.tvShouCang = (TextView) convertView.findViewById(R.id.tv_shoucang);
            holder.tvHuoJia = (TextView) convertView.findViewById(R.id.tv_huojia);
            holder.tvGengXin = (TextView) convertView.findViewById(R.id.tv_gengxin);
            holder.rlContent = convertView.findViewById(R.id.rl_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommodityInfoBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getCommodity_pic_url())) {
            String[] urlList = bean.getCommodity_pic_url().split(",");
            ImageLoaderUtil.display(urlList[0],holder.ivPic);
        }
        if (!TextUtils.isEmpty(bean.getCommodity_name())) {
            holder.tvName.setText(bean.getCommodity_name());
        }
        if (!TextUtils.isEmpty(bean.getA_price())) {
            float price = Float.valueOf(bean.getA_price()) / 100;
            holder.tvPrice.setText("￥"+price);
        }
        if (!TextUtils.isEmpty(bean.getSale_num())) {
            holder.tvXiaoLiang.setText("销量  "+ bean.getSale_num());
        }
        if (!TextUtils.isEmpty(bean.getCollection_num())) {
            holder.tvShouCang.setText("被收藏  "+ bean.getCollection_num());
        }
        if (!TextUtils.isEmpty(bean.getType())) {
            if (bean.getType().equals(ProductType.COMMON.getType())){
                holder.tvHuoJia.setText("货架  "+ "普通");
            } else if(bean.getType().equals(ProductType.AGENCY.getType())) {
                holder.tvHuoJia.setText("货架  "+ "代理");
            }
        }
        if (bean.getBusiness_info() != null && !TextUtils.isEmpty(bean.getBusiness_info().getMarket_name())) {
            holder.tvGengXin.setText("市场：  "+ bean.getBusiness_info().getMarket_name());
        }
        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.click(bean);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder{
        private ImageView ivPic;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvXiaoLiang;
        private TextView tvShouCang;
        private TextView tvHuoJia;
        private TextView tvGengXin;
        private RelativeLayout rlContent;
    }

    public interface ClickListener{
        void click(CommodityInfoBean bean);
    }

    private ClickListener mClickListener;

    public void setListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }
}
