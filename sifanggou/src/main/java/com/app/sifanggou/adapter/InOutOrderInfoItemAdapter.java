package com.app.sifanggou.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.CommodityOrderBean;
import com.app.sifanggou.utils.ImageLoaderUtil;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class InOutOrderInfoItemAdapter extends SetBaseAdapter<CommodityOrderBean> {
    public InOutOrderInfoItemAdapter(Context context, List<CommodityOrderBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_inoutorderinfoitem,null);
            holder = new ViewHolder();
            holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommodityOrderBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getPrice())) {
            float price = Float.valueOf(bean.getPrice()) / 100;
            holder.tvPrice.setText(price + "");
        }
        if (!TextUtils.isEmpty(bean.getCommodity_name())) {
            holder.tvName.setText(bean.getCommodity_name());
        }
        if (!TextUtils.isEmpty(bean.getCommodity_pic_url())) {
            ImageLoaderUtil.display(bean.getCommodity_pic_url(),holder.ivPic);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivPic;
        private TextView tvName;
        private TextView tvPrice;
    }

}
