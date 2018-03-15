package com.app.sifanggou.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.app.sifanggou.AppContext;
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
import com.app.sifanggou.activity.UploadCertificateActivity;
import com.app.sifanggou.bean.AgentLevelType;
import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessInfoByCodeResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.net.httprunner.GetBusinessInfoByCodeHttpRunner;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;
import com.app.sifanggou.utils.PreManager;
import com.app.sifanggou.view.ChangeHeadDialog;
import com.lidroid.xutils.view.annotation.ViewInject;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class MyselfFragment extends PicBaseFragment{
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
	@ViewInject(R.id.tv_mymessage_count)
	private TextView tvMessageCount;
	@ViewInject(R.id.iv_head)
	private ImageView ivHead;
	@ViewInject(R.id.tv_level)
	private TextView tvLevel;
	@ViewInject(R.id.tv_rezheng)
	private TextView tvRenZheng;
	@ViewInject(R.id.tv_name)
	private TextView tvName;
	@ViewInject(R.id.tv_addr)
	private TextView tvAddr;
	@ViewInject(R.id.rl_kefu)
	private RelativeLayout rlKeFu;
	@ViewInject(R.id.tv_kefu)
	private TextView tvKeFu;

	private LoginResponseBean loginBean;
	private ChangeHeadDialog headDialog;
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

	@Override
	public void onResume() {
		super.onResume();
		initData();
		RongIMClient.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
			@Override
			public void onSuccess(Integer integer) {
				if (integer.intValue() != 0) {
					tvMessageCount.setText(integer.intValue()+"条未读信息");
				} else {
					tvMessageCount.setText("");
				}
			}

			@Override
			public void onError(RongIMClient.ErrorCode errorCode) {

			}
		});
	}

	private void initData() {
		loginBean = PreManager.get(getActivity().getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
		if (loginBean != null
				&& loginBean.getData() != null
				&& loginBean.getData().getLogin_info() != null
				&& loginBean.getData().getLogin_info().getBusiness_info() != null) {
			if (!TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
				pushEventNoProgress(EventCode.HTTP_GETBUSINESSINFOBYCODE,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code());
			}

		}
	}

	private void refreshView(BusinessInfoBean bean) {
		if (bean == null) return;
		if (!TextUtils.isEmpty(bean.getHead_pic_url())) {
			ImageLoaderUtil.display(bean.getHead_pic_url(),ivHead);
		}
		if (!TextUtils.isEmpty(bean.getGrade())) {
			tvLevel.setText(bean.getGrade());
		}
		if (!TextUtils.isEmpty(bean.getName())) {
			tvName.setText(bean.getName());
			if (!TextUtils.isEmpty(bean.getMarket_name())) {
				tvAddr.setText(bean.getMarket_name() + bean.getShop_number());
			}
		}

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

		ivHead.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (headDialog == null) {
					headDialog = new ChangeHeadDialog(getActivity());
				}
				headDialog.setListener(new ChangeHeadDialog.ChangeHeadDialogListener() {

					@Override
					public void pic() {
						selectPicFromLocal(TAG_HEADPIC);
					}

					@Override
					public void capture() {
						selectPicFromCamera(TAG_HEADPIC);
					}
				});
				headDialog.show();
			}
		});

		rlKeFu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity())
						.setCancelable(true)
						.setTitle("拨打电话")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String number = tvKeFu.getText().toString();
						if (!TextUtils.isEmpty(number)) {
							if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
									!= PackageManager.PERMISSION_GRANTED){
								ActivityCompat.requestPermissions(getActivity(),
										new String[]{Manifest.permission.CALL_PHONE},
										PERMISSION_CALL);
							} else {
								Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
								startActivity(intent);
							}

						}
					}
				}).create().show();
			}
		});
	}

	private static final String TAG_HEADPIC = "TAG_YingYePic";
	@Override
	public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult,String tag) {
		String url = oss.presignPublicObjectURL(AppContext.OSS_BUCKET,putObjectRequest.getObjectKey());
		System.out.println("上传图片："+url);
		if (!TextUtils.isEmpty(url)) {
			if (TAG_HEADPIC.equals(tag)) {
				ImageLoaderUtil.display(url,ivHead);
				if (loginBean != null && loginBean.getData() != null
						&& loginBean.getData().getLogin_info() != null
						&& loginBean.getData().getLogin_info().getBusiness_info() != null
						&& !TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
					pushEventBlock(EventCode.HTTP_UPDATEBUSINESSINFO,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),
							"","","","","","","","",url,"");
				}
			} else {

			}

		}
	}

	@Override
	public void onEventRunEnd(Event event) {
		super.onEventRunEnd(event);
		if (event.getEventCode() == EventCode.HTTP_UPDATEBUSINESSINFO) {
			if (event.isSuccess()) {
				CommonUtils.showToast("修改头像成功");
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}
		if (event.getEventCode() == EventCode.HTTP_GETBUSINESSINFOBYCODE) {
			if (event.isSuccess()) {
				GetBusinessInfoByCodeResponseBean bean = (GetBusinessInfoByCodeResponseBean) event.getReturnParamAtIndex(0);
				if (bean != null && bean.getData() != null && bean.getData().getBusiness_info() != null) {
					refreshView(bean.getData().getBusiness_info());
				}
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}
	}

	private static final int PERMISSION_CALL = 0x011;

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch(requestCode) {
			case PERMISSION_CALL:
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					String number = tvKeFu.getText().toString();
					if (!TextUtils.isEmpty(number)) {
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
						startActivity(intent);
					}
				} else {
					CommonUtils.showToast("请开启应用拨打电话权限");
				}

		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}
