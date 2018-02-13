package com.app.sifanggou.adapter;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.AgentLevelType;
import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class BusinessInfoBeanAdapter extends SetBaseAdapter<BusinessInfoBean> {
    public BusinessInfoBeanAdapter(Context context, List<BusinessInfoBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_businessinfo,null);
            holder = new ViewHolder();
            holder.ivHead = (ImageView) convertView.findViewById(R.id.iv_head);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvFanWei = (TextView) convertView.findViewById(R.id.tv_fanwei);
            holder.tvMaxLevel = (TextView) convertView.findViewById(R.id.tv_jiebie);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
            holder.tvLevel = (TextView) convertView.findViewById(R.id.tv_level);
            holder.ivPic1 = (ImageView) convertView.findViewById(R.id.iv_pic1);
            holder.ivPic2 = (ImageView) convertView.findViewById(R.id.iv_pic2);
            holder.ivPic3 = (ImageView) convertView.findViewById(R.id.iv_pic3);
            holder.llContent = (LinearLayout) convertView.findViewById(R.id.ll_content);
            holder.llPic = convertView.findViewById(R.id.ll_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BusinessInfoBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getHead_pic_url())) {
            ImageLoaderUtil.display(bean.getHead_pic_url(),holder.ivHead);
        }
        if (!TextUtils.isEmpty(bean.getName())) {
            holder.tvName.setText(bean.getName());
        }
        if (!TextUtils.isEmpty(bean.getScope())) {
            holder.tvFanWei.setText(bean.getScope());
        }
        if (!TextUtils.isEmpty(bean.getAgent_level())) {
            for(AgentLevelType alvt : AgentLevelType.values()) {
                if (alvt.getType().equals(bean.getAgent_level())) {
                    holder.tvMaxLevel.setText(alvt.getName());
                    break;
                }
            }
        }
        if (!TextUtils.isEmpty(bean.getTotal_commodity_num())) {
            holder.tvCount.setText(bean.getTotal_commodity_num());
        }

        if (!TextUtils.isEmpty(bean.getAgent_level())) {
            for(AgentLevelType alvt : AgentLevelType.values()) {
                if (alvt.getType().equals(bean.getAgent_level())) {
                    holder.tvLevel.setText(alvt.getCode());
                    break;
                }
            }
        }

        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.click(bean);
                }
            }
        });

        if (bean.getFirst_show_commodity_list() != null && bean.getFirst_show_commodity_list().size() > 0) {
            holder.llPic.setVisibility(View.VISIBLE);
            if (bean.getFirst_show_commodity_list().size() == 1) {
                ImageLoaderUtil.display(bean.getFirst_show_commodity_list().get(0).getCommodity_pic_url(),holder.ivPic1);
                holder.ivPic1.setVisibility(View.VISIBLE);
                holder.ivPic2.setVisibility(View.INVISIBLE);
                holder.ivPic3.setVisibility(View.INVISIBLE);
            } else if (bean.getFirst_show_commodity_list().size() == 2) {
                ImageLoaderUtil.display(bean.getFirst_show_commodity_list().get(0).getCommodity_pic_url(),holder.ivPic1);
                ImageLoaderUtil.display(bean.getFirst_show_commodity_list().get(1).getCommodity_pic_url(),holder.ivPic2);
                holder.ivPic1.setVisibility(View.VISIBLE);
                holder.ivPic2.setVisibility(View.VISIBLE);
                holder.ivPic3.setVisibility(View.INVISIBLE);
            }else if (bean.getFirst_show_commodity_list().size() >= 3) {
                ImageLoaderUtil.display(bean.getFirst_show_commodity_list().get(0).getCommodity_pic_url(),holder.ivPic1);
                ImageLoaderUtil.display(bean.getFirst_show_commodity_list().get(1).getCommodity_pic_url(),holder.ivPic2);
                ImageLoaderUtil.display(bean.getFirst_show_commodity_list().get(2).getCommodity_pic_url(),holder.ivPic3);
                holder.ivPic1.setVisibility(View.VISIBLE);
                holder.ivPic2.setVisibility(View.VISIBLE);
                holder.ivPic3.setVisibility(View.VISIBLE);
            }
        } else {
            holder.llPic.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewHolder {
        private ImageView ivHead;
        private TextView tvName;
        private TextView tvFanWei;
        private TextView tvMaxLevel;
        private TextView tvCount;
        private TextView tvLevel;
        private ImageView ivPic1;
        private ImageView ivPic2;
        private ImageView ivPic3;
        private LinearLayout llPic;
        private LinearLayout llContent;
    }

    public interface UpdateListener {
        void click(BusinessInfoBean bean);
    }

    private UpdateListener mListener;

    public void setListener(UpdateListener listener) {
        this.mListener = listener;
    }
}
