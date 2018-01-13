package com.app.sifanggou.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.BaseBean;
import com.app.sifanggou.bean.BusinessStaffBean;
import com.app.sifanggou.bean.ZhiWeiType;

import java.util.List;

/**
 * Created by Administrator on 2018/1/7.
 */

public class AccountAdapter extends SetBaseAdapter<BusinessStaffBean> {

    public AccountAdapter(Context context, List<BusinessStaffBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_account,null);
            holder = new ViewHolder();
            holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
            holder.tvMobile = (TextView) convertView.findViewById(R.id.tv_mobile);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BusinessStaffBean businessStaffBean = mList.get(position);
        if (!TextUtils.isEmpty(businessStaffBean.getName())) {
            holder.tvName.setText(businessStaffBean.getName());
        }
        if (!TextUtils.isEmpty(businessStaffBean.getUser_name())) {
            holder.tvMobile.setText(businessStaffBean.getUser_name());
        }
        if (!TextUtils.isEmpty(businessStaffBean.getRole())) {
            for(int i = 0 ; i < ZhiWeiType.values().length ; i++) {
                if (businessStaffBean.getRole().equals(ZhiWeiType.values()[i].getType())) {
                    holder.tvType.setText("("+ZhiWeiType.values()[i].getValue()+")");
                }
            }
        }
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.delete(businessStaffBean);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView tvName;
        private TextView tvType;
        private TextView tvMobile;
        private ImageView ivDelete;
    }

    public interface UpdateListener {
        void delete(BusinessStaffBean businessStaffBean);
    }

    private UpdateListener mListener;

    public void setListener(UpdateListener listener) {
        this.mListener = listener;
    }


}
