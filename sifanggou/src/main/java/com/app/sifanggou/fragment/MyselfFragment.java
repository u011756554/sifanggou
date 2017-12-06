package com.app.sifanggou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.sifanggou.R;
import com.app.sifanggou.activity.AdressActivity;
import com.app.sifanggou.activity.BurOrderTabActivity;
import com.app.sifanggou.activity.BuyOrderActivity;
import com.app.sifanggou.activity.ChuCangHistoryActivity;
import com.app.sifanggou.activity.SaleOrderTabActivity;
import com.app.sifanggou.activity.ShangPinGuanLiActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MyselfFragment extends BaseFragment{
	@ViewInject(R.id.rl_shangpinguanli)
	private RelativeLayout rlShangPinGuanLi;
	@ViewInject(R.id.rl_chucanghistory)
	private RelativeLayout rlChuCangHistory;
	@ViewInject(R.id.rl_dizhi)
	private RelativeLayout rlDiZhi;
	@ViewInject(R.id.ll_dfk)
	private LinearLayout llDFK;
	@ViewInject(R.id.ll_jkrecord)
	private LinearLayout llJKRecord;
	@ViewInject(R.id.ll_dsk)
	private LinearLayout llDSK;
	@ViewInject(R.id.ll_dskrecord)
	private LinearLayout llDSKRecord;

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

		llDFK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), BurOrderTabActivity.class);
				intent.putExtra(BurOrderTabActivity.KEY_TYPE,BurOrderTabActivity.VALUE_TYPE_DAIJIE);
				startActivity(intent);
			}
		});

		llJKRecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), BurOrderTabActivity.class);
				startActivity(intent);
			}
		});

		llDSK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SaleOrderTabActivity.class);
				intent.putExtra(SaleOrderTabActivity.KEY_TYPE,SaleOrderTabActivity.VALUE_TYPE_DAISHOU);
				startActivity(intent);
			}
		});

		llDSKRecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SaleOrderTabActivity.class);
				startActivity(intent);
			}
		});
	}
}
