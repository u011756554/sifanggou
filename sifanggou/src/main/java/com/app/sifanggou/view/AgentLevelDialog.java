package com.app.sifanggou.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sifanggou.R;
import com.app.sifanggou.view.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class AgentLevelDialog extends BaseDialog {
    private TextView tvCancel;
    private TextView tvConfirm;

    private WheelView wvAgentLevel;

    private List<String> agentLevelList = new ArrayList<String>();

    public AgentLevelDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_agentlevel);
        initView();
    }

    private void initView() {
        addClickCancel();
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        wvAgentLevel = (WheelView) findViewById(R.id.agentlevel_wv);

        tvCancel.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

        tvConfirm.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

        if (agentLevelList != null) {
            refreshView(0);
        }
    }

    private void refreshView(int index) {
        if (wvAgentLevel != null && agentLevelList != null && agentLevelList.size() > 0) {
            wvAgentLevel.setItems(agentLevelList, index);
            wvAgentLevel.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(int selectedIndex, String item) {
                    Toast.makeText(mContext, "Item:"+item+" index:"+selectedIndex, Toast.LENGTH_LONG).show();
                    if (mListener != null) {
                        mListener.update(item);
                    }
                }
            });
            if (mListener != null) {
                mListener.update(agentLevelList.get(index));
            }
        }
    }

    public void setData(List<String> list,int index) {
        this.agentLevelList = list;
        if (wvAgentLevel != null) {
            refreshView(index);
        }
    }
}
