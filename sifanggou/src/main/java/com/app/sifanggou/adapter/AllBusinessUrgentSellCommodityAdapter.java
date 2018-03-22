package com.app.sifanggou.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.AgentLevelType;
import com.app.sifanggou.bean.AllBusinessUrgentSellCommodityBean;
import com.app.sifanggou.bean.ProductType;
import com.app.sifanggou.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/1/14.
 */

public class AllBusinessUrgentSellCommodityAdapter extends SetBaseAdapter<AllBusinessUrgentSellCommodityBean> {
    public AllBusinessUrgentSellCommodityAdapter(Context context, List<AllBusinessUrgentSellCommodityBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_allbusinessurgentsellcommodity,null);
            holder = new ViewHolder();
            holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvXiaoLiang = (TextView) convertView.findViewById(R.id.tv_xiaoliang);
            holder.tvOldPrice = (TextView) convertView.findViewById(R.id.tv_old_price);
            holder.tvHuoJia = (TextView) convertView.findViewById(R.id.tv_huojia);
            holder.tvGengXin = (TextView) convertView.findViewById(R.id.tv_gengxin);
            holder.rlContent = (RelativeLayout) convertView.findViewById(R.id.rl_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AllBusinessUrgentSellCommodityBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getCommodity_pic_url())) {
            String[] urlList = bean.getCommodity_pic_url().split(",");
            ImageLoaderUtil.display(urlList[0],holder.ivPic);
        }
        if (!TextUtils.isEmpty(bean.getCommodity_name())) {
            holder.tvName.setText(bean.getCommodity_name());
        }
        if (!TextUtils.isEmpty(bean.getSell_price())) {
            float price = Float.valueOf(bean.getSell_price()) / 100;
            holder.tvPrice.setText("￥"+price);
        }
        if (!TextUtils.isEmpty(bean.getOriginal_price())) {
            float price = Float.valueOf(bean.getOriginal_price()) / 100;
            holder.tvOldPrice.setText("原价"+price);
        }
        if (!TextUtils.isEmpty(bean.getSale_num())) {
            holder.tvXiaoLiang.setText("销量  "+ bean.getSale_num());
        }
        if (!TextUtils.isEmpty(bean.getAgent_level())) {
            for (AgentLevelType alt : AgentLevelType.values()) {
                if (alt.getType().equals(bean.getAgent_level())) {
                    holder.tvHuoJia.setText(alt.getName());
                }
            }
        } else {
            holder.tvHuoJia.setText("");
        }

        if (bean.getBusiness_info() != null && !TextUtils.isEmpty(bean.getBusiness_info().getMarket_name())) {
            holder.tvGengXin.setText("市场  "+ bean.getBusiness_info().getMarket_name());
        }

        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addListener != null) {
                    addListener.click(bean);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivPic;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvOldPrice;
        private TextView tvXiaoLiang;
        private TextView tvHuoJia;
        private TextView tvGengXin;
        private RelativeLayout rlContent;
    }

    public interface AddListener {
        void click(AllBusinessUrgentSellCommodityBean bean);
    }

    private AddListener addListener;

    public void setListener(AddListener listener) {
        this.addListener = listener;
    }
}
