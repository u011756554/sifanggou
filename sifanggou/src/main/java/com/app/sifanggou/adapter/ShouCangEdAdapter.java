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
import com.app.sifanggou.bean.ConcernedBean;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/2/12.
 */

public class ShouCangEdAdapter extends SetBaseAdapter<ConcernedBean> {
    public ShouCangEdAdapter(Context context, List<ConcernedBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holoder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shoucanged,null);
            holoder = new ViewHolder();
            holoder.ivPic = convertView.findViewById(R.id.iv_pic);
            holoder.tvName = convertView.findViewById(R.id.tv_name);
            holoder.tvTime = convertView.findViewById(R.id.tv_time);
            holoder.rlContent = convertView.findViewById(R.id.rl_content);
            convertView.setTag(holoder);
        } else {
            holoder = (ViewHolder) convertView.getTag();
        }
        ConcernedBean bean = mList.get(position);
        if (bean.getPartner_business_info() != null && !TextUtils.isEmpty(bean.getPartner_business_info().getHead_pic_url())) {
            ImageLoaderUtil.display(bean.getPartner_business_info().getHead_pic_url(),holoder.ivPic);
        }
        if (bean.getPartner_business_info() != null && !TextUtils.isEmpty(bean.getPartner_business_info().getName())) {
            holoder.tvName.setText(bean.getPartner_business_info().getName());
        }
        if (!TextUtils.isEmpty(bean.getCollect_time())) {
            holoder.tvTime.setText(bean.getCollect_time());
        }
        holoder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUpdateListener != null) {
                    mUpdateListener.update(bean);
                }
            }
        });
        return convertView;

    }

    private class ViewHolder {
        private ImageView ivPic;
        private TextView tvName;
        private TextView tvTime;
        private RelativeLayout rlContent;
    }

    public interface UpdateListener {
        void update(ConcernedBean bean);
    }

    private UpdateListener mUpdateListener;

    public void setListener(UpdateListener listener) {
        this.mUpdateListener = listener;
    }
}
