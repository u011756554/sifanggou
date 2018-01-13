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
import com.app.sifanggou.activity.ChatListActivity;
import com.app.sifanggou.activity.ChuCangHistoryActivity;
import com.app.sifanggou.activity.InviteCodeActivity;
import com.app.sifanggou.activity.MyAccountActivity;
import com.app.sifanggou.activity.MyScanCodeActivity;
import com.app.sifanggou.activity.PrinterActivity;
import com.app.sifanggou.activity.SaleOrderTabActivity;
import com.app.sifanggou.activity.SetActivity;
import com.app.sifanggou.activity.ShangPinGuanLiActivity;
import com.app.sifanggou.activity.ShouCangActivity;
import com.app.sifanggou.activity.ShouCangEdActivity;
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
	@ViewInject(R.id.rl_guanzhu)
	private RelativeLayout rlGuanZhu;
	@ViewInject(R.id.rl_save)
	private RelativeLayout rlSave;
	@ViewInject(R.id.rl_yaoqingma)
	private RelativeLayout rlYaoQingMa;
	@ViewInject(R.id.rl_save_others)
	private RelativeLayout rlSaveOthers;
	@ViewInject(R.id.rl_zizhaghao)
	private RelativeLayout rlZiZhangHao;
	@ViewInject(R.id.rl_print)
	private RelativeLayout rlPrint;
	@ViewInject(R.id.rl_set)
	private RelativeLayout rlSet;
	@ViewInject(R.id.rl_scan)
	private RelativeLayout rlScan;
	@ViewInject(R.id.rl_mymessage)
	private RelativeLayout rlMyMessage;

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

		rlSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ShouCangActivity.class);
				startActivity(intent);
			}
		});

		rlYaoQingMa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), InviteCodeActivity.class);
				startActivity(intent);
			}
		});

		rlSaveOthers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ShouCangEdActivity.class);
				startActivity(intent);
			}
		});

		rlZiZhangHao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MyAccountActivity.class);
				startActivity(intent);
			}
		});

		rlPrint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PrinterActivity.class);
				startActivity(intent);
			}
		});

		rlSet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SetActivity.class);
				startActivity(intent);
			}
		});

		rlScan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MyScanCodeActivity.class);
				startActivity(intent);
			}
		});

		rlMyMessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ChatListActivity.class);
				startActivity(intent);
			}
		});
	}
}
