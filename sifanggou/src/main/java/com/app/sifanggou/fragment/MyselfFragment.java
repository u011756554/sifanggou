package com.app.sifanggou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.sifanggou.R;
import com.app.sifanggou.activity.AdressActivity;
import com.app.sifanggou.activity.ChuCangHistoryActivity;
import com.app.sifanggou.activity.ShangPinGuanLiActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MyselfFragment extends BaseFragment{
	@ViewInject(R.id.rl_shangpinguanli)
	private RelativeLayout rlShangPinGuanLi;
	@ViewInject(R.id.rl_chucanghistory)
	private RelativeLayout rlChuCangHistory;
	@ViewInject(R.id.rl_dizhi)
	private RelativeLayout rlDiZhi;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_myself);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initListener();
	}

	private void initView() {

	}

	private void initListener() {
		rlShangPinGuanLi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ShangPinGuanLiActivity.class);
				startActivity(intent);
			}
		});

		rlChuCangHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ChuCangHistoryActivity.class);
				startActivity(intent);
			}
		});

		rlDiZhi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AdressActivity.class);
				startActivity(intent);
			}
		});
	}
}
