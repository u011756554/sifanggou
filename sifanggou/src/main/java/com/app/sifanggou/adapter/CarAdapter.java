package com.app.sifanggou.adapter;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.CarBean;
import com.app.sifanggou.bean.CarItemBean;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.view.MyListView;

import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */

public class CarAdapter extends SetBaseAdapter<CarBean> {
    public CarAdapter(Context context, List<CarBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_car,null);
            holder = new ViewHolder();
            holder.ivSelect = (ImageView) convertView.findViewById(R.id.iv_select);
            holder.ivExtend = (ImageView) convertView.findViewById(R.id.iv_extend);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.rlSelect = (RelativeLayout) convertView.findViewById(R.id.rl_select);
            holder.ivChat = (ImageView) convertView.findViewById(R.id.iv_chat);
            holder.lvItem = (MyListView) convertView.findViewById(R.id.lv_item);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CarBean bean = mList.get(position);
        if (bean.isSelect()) {
            holder.ivSelect.setSelected(true);
        } else {
            holder.ivSelect.setSelected(false);
        }
        holder.ivSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.isSelect()) {
                    bean.setSelect(false);
                } else {
                    bean.setSelect(true);
                }
                notifyDataSetChanged();
            }
        });

        if (bean.isShow()) {
            holder.lvItem.setVisibility(View.VISIBLE);
        } else {
            holder.lvItem.setVisibility(View.GONE);
        }
        holder.rlSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.isShow()) {
                    bean.setShow(false);
                } else {
                    bean.setShow(true);
                }
                notifyDataSetChanged();
            }
        });
        if (bean.getCommodity_info_list() != null) {
            CarItemAdapter itemAdapter = new CarItemAdapter(mContext,bean.getCommodity_info_list());
            if (mListener != null) {
                itemAdapter.setListener(mListener);
            }
            holder.lvItem.setAdapter(itemAdapter);

            Float totalPrice = 0f;
            for(CarItemBean item : bean.getCommodity_info_list()) {
                if (!TextUtils.isEmpty(item.getPrice()) && !TextUtils.isEmpty(item.getCommodity_num())) {
                    totalPrice = totalPrice + Float.valueOf(item.getPrice()) * Integer.valueOf(item.getCommodity_num());
                }
            }
            holder.tvPrice.setText(totalPrice/100 + "");
        }
        if (!TextUtils.isEmpty(bean.getBusiness_name())) {
            holder.tvName.setText(bean.getBusiness_name());
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView ivSelect;
        private TextView tvName;
        private ImageView ivExtend;
        private RelativeLayout rlSelect;
        private ImageView ivChat;
        private MyListView lvItem;
        private TextView tvPrice;
    }

    private CarItemAdapter.DataUpdateListener mListener;

    public void setListener(CarItemAdapter.DataUpdateListener listener) {
        this.mListener = listener;
    }
}
