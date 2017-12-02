package com.app.sifanggou.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.activity.AdressActivity;
import com.app.sifanggou.bean.AdressBean;
import com.app.sifanggou.bean.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class AddressAdapter extends SetBaseAdapter<AdressBean> {
    private String type = "";
    public AddressAdapter(Context context, List<AdressBean> list,String type) {
        super(context, list);
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_address,null);
            holder = new ViewHolder();
            holder.ivSelect = (ImageView) convertView.findViewById(R.id.iv_select);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tvTimes = (TextView) convertView.findViewById(R.id.tv_times);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btn_delete);
            holder.btnEdit = (Button) convertView.findViewById(R.id.btn_edit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AdressBean bean = mList.get(position);
        if (type.equals(AdressActivity.KEY_SELECT)) {
            holder.ivSelect.setVisibility(View.VISIBLE);
            if (bean.isSelect()) {
                holder.ivSelect.setSelected(true);
            } else {
                holder.ivSelect.setSelected(false);
            }
        } else {
            holder.ivSelect.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(bean.getReceiver_name())) {
            holder.tvName.setText(bean.getReceiver_name());
        }
        if (!TextUtils.isEmpty(bean.getMobile())) {
            holder.tvPhone.setText(bean.getMobile());
        }
        if (!TextUtils.isEmpty(bean.getAddress())) {
            holder.tvAddress.setText(bean.getAddress());
        }
        if (!TextUtils.isEmpty(bean.getDelivery_time())) {
            holder.tvTimes.setText(bean.getDelivery_time());
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivSelect;
        private TextView tvName;
        private TextView tvPhone;
        private TextView tvAddress;
        private TextView tvTimes;
        private Button btnDelete;
        private Button btnEdit;
    }
}
