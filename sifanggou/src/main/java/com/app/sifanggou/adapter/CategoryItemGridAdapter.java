package com.app.sifanggou.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.CategoryType;
import com.app.sifanggou.bean.FirstChildNodeTypeBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class CategoryItemGridAdapter extends SetBaseAdapter<FirstChildNodeTypeBean> {

    public CategoryItemGridAdapter(Context context, List<FirstChildNodeTypeBean> list) {
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
        FirstChildNodeTypeBean bean = mList.get(position);
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
