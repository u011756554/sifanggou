package com.app.sifanggou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.CommodityTypeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/30.
 */

public class CategoryTypeAdapter extends SetBaseAdapter<CommodityTypeBean> {
    private int defItem;//声明默认选中的项

    public CategoryTypeAdapter(Context context, List<CommodityTypeBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpater_category,null);
            holder = new ViewHolder();
            holder.llContent = (LinearLayout) convertView.findViewById(R.id.ll_content);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommodityTypeBean bean = mList.get(position);
        if (defItem == position) {
            bean.setSelected(true);
            if (mListener != null) {
                mListener.check(position);
            }
        } else {
            bean.setSelected(false);
        }
        holder.tvName.setText(bean.getName());
        if (bean.isSelected()) {
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.color_banner));
            holder.llContent.setBackgroundColor(mContext.getResources().getColor(R.color.color_main));
        } else {
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.llContent.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        return convertView;
    }

    private class ViewHolder {
        private LinearLayout llContent;
        private TextView tvName;
    }

    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }

    public interface CheckListener {
        void check(int position);
    }

    private CheckListener mListener;

    public void setListener(CheckListener listener) {
        this.mListener = listener;
    }
}
