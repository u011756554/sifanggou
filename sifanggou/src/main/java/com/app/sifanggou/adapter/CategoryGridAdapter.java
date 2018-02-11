package com.app.sifanggou.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.CategoryType;
import com.app.sifanggou.bean.SecondChildNodeTypeBean;
import com.app.sifanggou.utils.CommonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2018/1/1.
 */

public class CategoryGridAdapter extends SetBaseAdapter<SecondChildNodeTypeBean> {

    public CategoryGridAdapter(Context context, List<SecondChildNodeTypeBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categorygrid,null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SecondChildNodeTypeBean bean = mList.get(position);
//        float screenWidth = CommonUtils.getScreenWidth((Activity) mContext);
//        int size = (int) ((3 * screenWidth/4 - CommonUtils.dip2px(mContext,40)) / 3);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.ivPic.getLayoutParams();
//        params.width = size;
//        params.height = size;
//        holder.ivPic.setLayoutParams(params);

        for(CategoryType cT : CategoryType.values()) {
            if (cT.getChildName().equals(bean.getName())) {
                holder.tvName.setText(bean.getName());
                InputStream is = null;
                Bitmap bitmap = null;
                try {
                    is = mContext.getAssets().open(bean.getName()+".jpg");
                    bitmap= BitmapFactory.decodeStream(is);
                    holder.ivPic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return convertView;
    }

    private class ViewHolder{
        private ImageView ivPic;
        private TextView tvName;
    }
}
