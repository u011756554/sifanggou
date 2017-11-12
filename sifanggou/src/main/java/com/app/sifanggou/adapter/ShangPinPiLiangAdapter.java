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

public class ShangPinPiLiangAdapter extends SetBaseAdapter<CommodityInfoBean> {
    public ShangPinPiLiangAdapter(Context context, List<CommodityInfoBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shangpin_piliang,null);
            holder = new ViewHolder();
            holder.ivSelect = (ImageView) convertView.findViewById(R.id.iv_select);
            holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvXiaoLiang = (TextView) convertView.findViewById(R.id.tv_xiaoliang);
            holder.tvShouCang = (TextView) convertView.findViewById(R.id.tv_shoucang);
            holder.tvHuoJia = (TextView) convertView.findViewById(R.id.tv_huojia);
            holder.tvGengXin = (TextView) convertView.findViewById(R.id.tv_gengxin);
            holder.rlPriceEdit = (RelativeLayout) convertView.findViewById(R.id.rl_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CommodityInfoBean bean = mList.get(position);
        if (bean.isSelect()) {
            holder.ivSelect.setSelected(true);
        } else {
            holder.ivSelect.setSelected(false);
        }
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
        if (!TextUtils.isEmpty(bean.getAdd_time())) {
            holder.tvGengXin.setText("最近更新  "+ bean.getAdd_time());
        }
        holder.rlPriceEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceEditListener != null ) {
                    priceEditListener.updatePrice(bean.getCommodity_id(),bean.getA_price(),bean.getB_price());
                }
            }
        });
        return convertView;
    }

    private class ViewHolder{
        private ImageView ivSelect;
        private ImageView ivPic;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvXiaoLiang;
        private TextView tvShouCang;
        private TextView tvHuoJia;
        private TextView tvGengXin;
        private RelativeLayout rlPriceEdit;
    }

    public interface PriceEditListener {
        void updatePrice(String commodity_id,String a_price,String b_price);
    }

    private PriceEditListener priceEditListener;

    public void setPriceEditListener(PriceEditListener listener) {
        this.priceEditListener = listener;
    }
}
