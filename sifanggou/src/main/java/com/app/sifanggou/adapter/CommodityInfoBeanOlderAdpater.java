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
import com.app.sifanggou.bean.AgentLevelType;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.ProductType;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/2/12.
 */

public class CommodityInfoBeanOlderAdpater extends SetBaseAdapter<CommodityInfoBean> {
    public CommodityInfoBeanOlderAdpater(Context context, List<CommodityInfoBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_commodityinfobeanold,null);
            holder = new ViewHolder();
            holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.ivAdd = (ImageView) convertView.findViewById(R.id.iv_add);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvXiaoLiang = (TextView) convertView.findViewById(R.id.tv_xiaoliang);
            holder.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tvHuoJia = (TextView) convertView.findViewById(R.id.tv_huojia);
            holder.tvGuiGe = (TextView) convertView.findViewById(R.id.tv_guige);
            holder.edtCount = (EditText) convertView.findViewById(R.id.edt_count);
            holder.tvAdd = (TextView) convertView.findViewById(R.id.tv_add);
            holder.tvJian = (TextView) convertView.findViewById(R.id.tv_jian);
            holder.rlContent = (RelativeLayout) convertView.findViewById(R.id.rl_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CommodityInfoBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getCommodity_pic_url())) {
            String[] urlList = bean.getCommodity_pic_url().split(",");
            ImageLoaderUtil.display(urlList[0],holder.ivPic);
        }
        if (!TextUtils.isEmpty(bean.getCommodity_name())) {
            holder.tvName.setText(bean.getCommodity_name());
        }
        if (!TextUtils.isEmpty(bean.getPrice())) {
            float price = Float.valueOf(bean.getPrice()) / 100;
            holder.tvPrice.setText("￥"+price);
        }
        if (!TextUtils.isEmpty(bean.getSale_num())) {
            holder.tvXiaoLiang.setText("销量  "+ bean.getSale_num());
        }
        if (!TextUtils.isEmpty(bean.getCollection_num())) {
            holder.tvNum.setText("被收藏  "+ bean.getCollection_num());
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

        if (!TextUtils.isEmpty(bean.getSpecification())) {
            holder.tvGuiGe.setText("规格  "+ bean.getSpecification());
        }

        holder.edtCount.setText(bean.getSelectCount()+"");

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
                bean.setSelectCount(count);
            }
        });
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.valueOf(bean.getSelectCount());
                count = count + 1;
                bean.setSelectCount(count);
                editCount.setText(count + "");
            }
        });

        holder.tvJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.valueOf(bean.getSelectCount());
                if (count != 0) {
                    count = count - 1;
                }
                bean.setSelectCount(count);
                editCount.setText(count + "");
            }
        });

        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addListener != null && bean.getSelectCount() > 0) {
                    addListener.add(bean);
                }
            }
        });

        holder.ivPic.setOnClickListener(new View.OnClickListener() {
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
        private ImageView ivAdd;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvXiaoLiang;
        private TextView tvNum;
        private TextView tvHuoJia;
        private TextView tvGuiGe;
        private TextView tvJian;
        private TextView tvAdd;
        private EditText edtCount;
        private RelativeLayout rlContent;
    }

    public interface AddListener {
        void add(CommodityInfoBean bean);
        void click(CommodityInfoBean bean);
    }

    private AddListener addListener;

    public void setListener(AddListener listener) {
        this.addListener = listener;
    }
}
