package com.app.sifanggou.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.app.sifanggou.net.bean.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */

public class CarAdapter extends SetBaseAdapter<BaseResponseBean> {
    public CarAdapter(Context context, List<BaseResponseBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
