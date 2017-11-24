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
import com.app.sifanggou.bean.BaseBean;
import com.app.sifanggou.bean.CarItemBean;
import com.app.sifanggou.view.MyListView;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class CarItemAdapter extends SetBaseAdapter<CarItemBean> {

    public CarItemAdapter(Context context, List<CarItemBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_caritem,null);
            holder = new ViewHolder();
            holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvJian = (TextView) convertView.findViewById(R.id.tv_jian);
            holder.edtCount = (EditText) convertView.findViewById(R.id.edt_count);
            holder.tvAdd = (TextView) convertView.findViewById(R.id.tv_add);
            holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CarItemBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getPrice())) {
            holder.tvPrice.setText("￥"+ Float.valueOf(bean.getPrice())/100);
        }
        if (!TextUtils.isEmpty(bean.getCommodity_name())) {
            holder.tvName.setText(bean.getCommodity_name());
        }
        if (!TextUtils.isEmpty(bean.getCommodity_num())) {
            holder.edtCount.setText(bean.getCommodity_num());
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivPic;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvJian;
        private EditText edtCount;
        private TextView tvAdd;
        private ImageView ivDelete;
    }
}
