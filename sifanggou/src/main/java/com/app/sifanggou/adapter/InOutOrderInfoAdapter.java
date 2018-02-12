package com.app.sifanggou.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.CommodityOrderBean;
import com.app.sifanggou.bean.OrderNoBaseBean;
import com.app.sifanggou.view.MyListView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class InOutOrderInfoAdapter extends SetBaseAdapter<OrderNoBaseBean> {
    private Gson gson = new Gson();
    public static final String TYPE_JIEKUAN = "已结款";
    public static final String TYPE_SHOUKUAN = "已收款";
    private String type = "";
    public InOutOrderInfoAdapter(Context context, List<OrderNoBaseBean> list,String type) {
        super(context, list);
        this.type = type;
    }

    public InOutOrderInfoAdapter(Context context, List<OrderNoBaseBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_inoutorderinfo,null);
            holder = new ViewHolder();
            holder.ivExtend = (ImageView) convertView.findViewById(R.id.iv_extend);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.rlSelect = (RelativeLayout) convertView.findViewById(R.id.rl_select);
            holder.lvItem = (MyListView) convertView.findViewById(R.id.lv_item);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.btnOrder = (Button) convertView.findViewById(R.id.btn_order);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final OrderNoBaseBean orderNoBaseBean = mList.get(position);
        if (orderNoBaseBean.getSeller_business_info() != null && !TextUtils.isEmpty(orderNoBaseBean.getSeller_business_info().getName())){
            holder.tvName.setText(orderNoBaseBean.getSeller_business_info().getName()
                    +"  "+orderNoBaseBean.getSeller_business_info().getMarket_name()
                    +"  "+orderNoBaseBean.getSeller_business_info().getShop_number());
        } else if(orderNoBaseBean.getBuyer_business_info() != null
                && !TextUtils.isEmpty(orderNoBaseBean.getBuyer_business_info().getName())) {
            holder.tvName.setText(orderNoBaseBean.getBuyer_business_info().getName()
                    +"  "+orderNoBaseBean.getBuyer_business_info().getMarket_name()
                    +"  "+orderNoBaseBean.getBuyer_business_info().getShop_number());
        }
        if (!TextUtils.isEmpty(orderNoBaseBean.getTotal_amount())){
            float price = Float.valueOf(orderNoBaseBean.getTotal_amount()) / 100;
            holder.tvPrice.setText(price + "");
        }
        if (!TextUtils.isEmpty(orderNoBaseBean.getCreate_time())){
            holder.tvTime.setText(orderNoBaseBean.getCreate_time());
        }

        if (orderNoBaseBean.isShow()) {
            holder.lvItem.setVisibility(View.VISIBLE);
        } else {
            holder.lvItem.setVisibility(View.GONE);
        }


        holder.rlSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderNoBaseBean.isShow()) {
                    orderNoBaseBean.setShow(false);
                } else {
                    orderNoBaseBean.setShow(true);
                }
                notifyDataSetChanged();
            }
        });

        if (!TextUtils.isEmpty(orderNoBaseBean.getCommodity_info_list())) {
            try {
                JSONArray ja = new JSONArray(orderNoBaseBean.getCommodity_info_list());
                if (ja != null && ja.length() > 0) {
                    List<CommodityOrderBean> list = new ArrayList<CommodityOrderBean>();
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        CommodityOrderBean commodityOrderBean = gson.fromJson(jo.toString(), CommodityOrderBean.class);
                        list.add(commodityOrderBean);
                    }
                    InOutOrderInfoItemAdapter itemAdapter = new InOutOrderInfoItemAdapter(parent.getContext(), list);
                    holder.lvItem.setAdapter(itemAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (type.equals(TYPE_JIEKUAN)) {
            holder.btnOrder.setVisibility(View.VISIBLE);
            holder.btnOrder.setText("已结款");
            holder.btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (daiJieListener != null) {
                        daiJieListener.jieKuan(orderNoBaseBean);
                    }
                }
            });
        } else if(type.equals(TYPE_SHOUKUAN)) {
            holder.btnOrder.setVisibility(View.VISIBLE);
            holder.btnOrder.setText("已收款");
            holder.btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (daiShouListener != null) {
                        daiShouListener.shouKuan(orderNoBaseBean);
                    }
                }
            });
        } else {
            holder.btnOrder.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView tvName;
        private ImageView ivExtend;
        private TextView tvTime;
        private RelativeLayout rlSelect;
        private MyListView lvItem;
        private TextView tvPrice;
        private Button btnOrder;
    }

    public interface DaiShouListener {
        void shouKuan(OrderNoBaseBean bean);
    }

    private DaiShouListener daiShouListener;

    public void setDaiShouListener(DaiShouListener listener) {
        this.daiShouListener = listener;
    }

    public interface DaiJieListener {
        void jieKuan(OrderNoBaseBean bean);
    }

    private DaiJieListener daiJieListener;

    public void setDaiJieListener(DaiJieListener listener) {
        this.daiJieListener = listener;
    }

}
