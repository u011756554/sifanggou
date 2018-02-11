package com.app.sifanggou.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.app.sifanggou.net.AndroidEventManager;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.EventManager;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;
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
            holder.rlContent = convertView.findViewById(R.id.rl_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CarItemBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getCommodity_pic_url())) {
            ImageLoaderUtil.display(bean.getCommodity_pic_url(),holder.ivPic);
        }
        if (!TextUtils.isEmpty(bean.getPrice())) {
            holder.tvPrice.setText("￥"+ Float.valueOf(bean.getPrice())/100);
        }
        if (!TextUtils.isEmpty(bean.getCommodity_name())) {
            holder.tvName.setText(bean.getCommodity_name());
        }
        if (!TextUtils.isEmpty(bean.getCommodity_num())) {
            holder.edtCount.setText(bean.getCommodity_num());
        }
        holder.edtCount.setEnabled(false);
        final EditText editCount = holder.edtCount;
        holder.edtCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = Integer.valueOf(s.toString());
                if (count >= 10000) {
                    CommonUtils.showToast("数据过大");
                    editCount.setText("0");
                }
            }
        });
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(bean.getCommodity_num())) {
                    return;
                }
                if (mListener != null) {
                    mListener.updateNum(bean.getBusiness_code(),bean.getCommodity_id(),(Integer.valueOf(bean.getCommodity_num()) + 1)+"");
                }
            }
        });
        holder.tvJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(bean.getCommodity_num())) {
                    return;
                }
                if ("0".equals(bean.getCommodity_num())) {
                    if (mListener != null) {
                        mListener.delete(bean.getBusiness_code(),bean.getCommodity_id());
                    }
                }
                if (mListener != null) {
                    mListener.updateNum(bean.getBusiness_code(),bean.getCommodity_id(),(Integer.valueOf(bean.getCommodity_num()) - 1)+"");
                }
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.delete(bean.getBusiness_code(),bean.getCommodity_id());
                }
            }
        });
        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.click(bean.getBusiness_code(),bean.getCommodity_id());
                }
            }
        });
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
        private RelativeLayout rlContent;
    }

    public interface DataUpdateListener {
        void updateNum(String business_code,String commodity_id,String commodity_num);
        void delete(String business_code,String commodity_id);
        void click(String business_code,String commodity_id);
    }

    private DataUpdateListener mListener;

    public void setListener(DataUpdateListener listener) {
        this.mListener = listener;
    }
}
