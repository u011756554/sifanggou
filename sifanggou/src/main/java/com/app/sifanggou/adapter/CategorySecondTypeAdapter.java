package com.app.sifanggou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.FirstChildNodeTypeBean;
import com.app.sifanggou.bean.SecondChildNodeTypeBean;
import com.app.sifanggou.view.MyGridView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/1/1.
 */

public class CategorySecondTypeAdapter extends SetBaseAdapter<FirstChildNodeTypeBean> {

    public CategorySecondTypeAdapter(Context context, List<FirstChildNodeTypeBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categorysecondtype,null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mgType = (MyGridView) convertView.findViewById(R.id.mv_type);
            holder.rlSecond = (RelativeLayout) convertView.findViewById(R.id.rl_second);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FirstChildNodeTypeBean bean = mList.get(position);
        holder.tvName.setText(bean.getName());
        if(bean.getChild_node_list() != null && bean.getChild_node_list().size() > 0) {
            Collections.sort(bean.getChild_node_list());
            CategoryGridAdapter adapter = new CategoryGridAdapter(parent.getContext(),bean.getChild_node_list());
            holder.mgType.setAdapter(adapter);

            holder.mgType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mListener != null) {
                        mListener.gridCheck(bean.getChild_node_list().get(position));
                    }
                }
            });
        } else {

        }
//        holder.rlSecond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null) {
//                    mListener.check(bean);
//                }
//            }
//        });
        return convertView;
    }

    private class ViewHolder{
        private TextView tvName;
        private MyGridView mgType;
        private RelativeLayout rlSecond;
    }

    public interface SecondListener {
        void check(FirstChildNodeTypeBean bean);
        void gridCheck(SecondChildNodeTypeBean bean);
    }

    private SecondListener mListener;

    public void setListener(SecondListener listener) {
        this.mListener = listener;
    }

}
