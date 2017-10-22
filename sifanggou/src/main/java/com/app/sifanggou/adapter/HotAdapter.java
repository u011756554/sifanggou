package com.app.sifanggou.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.DiPaiBean;
import com.app.sifanggou.utils.ImageLoaderUtil;

public class HotAdapter extends SetBaseAdapter<DiPaiBean> {

	public HotAdapter(Context context, List<DiPaiBean> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub\
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dipai, null);
			holder = new ViewHolder();
			holder.tvDec =  (TextView) convertView.findViewById(R.id.tv_dec);
			holder.tvPrice =  (TextView) convertView.findViewById(R.id.tv_price);
			holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
			holder.ivStar = (ImageView) convertView.findViewById(R.id.iv_star);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		DiPaiBean bean = mList.get(position);
		if (!TextUtils.isEmpty(bean.getUrl())) {
			ImageLoaderUtil.display(bean.getUrl(), holder.ivPic);
		}
		if (!TextUtils.isEmpty(bean.getPrice())) {
			holder.tvPrice.setText("￥"+bean.getPrice()+"/套");
		}
		if (!TextUtils.isEmpty(bean.getName())) {
			holder.tvDec.setText(bean.getName());
		}
		holder.ivStar.setSelected(bean.isStar());
		return convertView;
	}
	
	private class ViewHolder {
		private ImageView ivPic;
		private ImageView ivStar;
		private TextView tvPrice;
		private TextView tvDec;
	}

}
