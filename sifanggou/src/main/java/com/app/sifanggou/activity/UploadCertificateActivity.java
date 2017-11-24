package com.app.sifanggou.activity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.AgentLevelBean;
import com.app.sifanggou.bean.AgentLevelType;
import com.app.sifanggou.bean.CityDataBean;
import com.app.sifanggou.bean.CityMarketBean;
import com.app.sifanggou.bean.ProvinceDataBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.AgentLevelResponseBean;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.CityMarketResponseBean;
import com.app.sifanggou.net.bean.CityResponseBean;
import com.app.sifanggou.net.bean.ProvinceResponseBean;
import com.app.sifanggou.net.bean.ProvinceResponseBean.DataBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;
import com.app.sifanggou.utils.PictureUtils;
import com.app.sifanggou.view.AgentLevelDialog;
import com.app.sifanggou.view.BaseDialog;
import com.app.sifanggou.view.ChangeHeadDialog;
import com.app.sifanggou.view.ConfirmDialog;
import com.app.sifanggou.view.MarketSelectDialog;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static com.app.sifanggou.utils.CommonUtils.showToast;

public class UploadCertificateActivity extends PicBaseActivity {

	@ViewInject(R.id.rl_delete)
	private RelativeLayout rlDelete;
	@ViewInject(R.id.rl_market)
	private RelativeLayout rlMarket;
	@ViewInject(R.id.edt_market)
	private EditText edtMarket;
	@ViewInject(R.id.rl_dali)
	private RelativeLayout rlDaiLi;
	@ViewInject(R.id.edt_daili)
	private EditText edtDaiLi;
	@ViewInject(R.id.btn_finish)
	private Button btnFinish;
	@ViewInject(R.id.edt_market_name)
	private EditText edtMarketName;
	@ViewInject(R.id.edt_menpaihao)
	private EditText edtMenPaiHao;
	@ViewInject(R.id.rl_yingyezhizhao)
	private RelativeLayout rlYingYeZhiZhao;
	@ViewInject(R.id.iv_yingyepic)
	private ImageView ivYingYePic;
	@ViewInject(R.id.rl_dailishang)
	private RelativeLayout rlDailiShang;
	@ViewInject(R.id.iv_dailishang)
	private ImageView ivDailiShang;
	
	private MarketSelectDialog marketDialog;
	private AgentLevelDialog agentLevelDialog;
	private List<ProvinceDataBean> provinceDataList = new ArrayList<ProvinceDataBean>();
	private List<CityDataBean> cityDataList = new ArrayList<CityDataBean>();
	private List<CityMarketBean> marketDataList = new ArrayList<CityMarketBean>();
	private List<AgentLevelBean> agentLevelDataList = new ArrayList<AgentLevelBean>();
	private List<String> provinceList = new ArrayList<String>();
	private List<String> cityList = new ArrayList<String>();
	private List<String> marketList = new ArrayList<String>();
	private List<String> agentLevelList = new ArrayList<String>();

	public static final String KEY_PHONE = "key_UploadCertificateActivity_phone";
	public static final String KEY_CODE = "key_UploadCertificateActivity_code";
	public static final String KEY_INVITECODE = "key_UploadCertificateActivity_invitecode";
	public static final String KEY_PWD = "key_UploadCertificateActivity_pwd";

	private String phone;
	private String code;
	private String invteCode;
	private String pwd;
	private CityMarketBean cityMarketBean;
	private AgentLevelBean agentLevelBean;

	private ChangeHeadDialog yingYeDialog;
	private ChangeHeadDialog heTongDialog;
	private String business_license; //商家营业执照图片URL
	private String highest_agency_contract_pic_url;//商家最高代理级别的代理合同url

	private ConfirmDialog confirmDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initData();
		initView();
		initListener();
	}
	
	private void initView() {
		marketDialog = new MarketSelectDialog(UploadCertificateActivity.this);
		agentLevelDialog = new AgentLevelDialog(UploadCertificateActivity.this);
	}
	
	private void initListener() {
		edtMarket.setEnabled(false);
		edtDaiLi.setEnabled(false);
		rlDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btnFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cityMarketBean == null) {
					showToast("选择市场");
					return;
				}
				if (TextUtils.isEmpty(edtMarketName.getText().toString())) {
					showToast("输入店铺名称");
					return;
				}
				if (TextUtils.isEmpty(edtMenPaiHao.getText().toString())) {
					showToast("输入门牌号");
					return;
				}
				if (TextUtils.isEmpty(business_license)) {
					showToast("请上传营业执照");
					return;
				}
				if (TextUtils.isEmpty(highest_agency_contract_pic_url)) {
					showToast("请上传代理合同");
					return;
				}
				if (agentLevelBean == null) {
					showToast("选择代理级别");
					return;
				}
				String name = cityMarketBean.getName();
				String province = cityMarketBean.getProvince();
				String province_name = cityMarketBean.getProvince_name();
				String city = cityMarketBean.getCity();
				String city_name = cityMarketBean.getCity_name();
				String lon = "0";
				String lat = "0";
				String market_code = cityMarketBean.getMarket_code();
				String head_pic_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505414876514&di=e986ebb63011ff3b5abc5c3048317050&imgtype=0&src=http%3A%2F%2Fimg.jdzj.com%2FUserDocument%2F2015b%2Fzhonglongky%2FPicture%2F20151023111322.jpg";
				String legal_person_id = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505414876514&di=e986ebb63011ff3b5abc5c3048317050&imgtype=0&src=http%3A%2F%2Fimg.jdzj.com%2FUserDocument%2F2015b%2Fzhonglongky%2FPicture%2F20151023111322.jpg";
				String agent_level = "";
				for(AgentLevelType alvt : AgentLevelType.values()) {
					if (alvt.getCode().equals(agentLevelBean.getLevel_num())) {
						agent_level = alvt.getType();
						break;
					}
				}
				String integrate_distribute_type = "province";
				String invite_code = invteCode;
				String mobile = phone;
				String verify_code = code;
				String password = "";
				try {
					password = CommonUtils.EncoderByMd5(pwd);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String registration_id = "";
				String device_type = "android";

				pushEventBlock(EventCode.HTTP_BUSINESSREGIST,name,province,province_name,city,city_name,lon,lat,market_code,head_pic_url,business_license,legal_person_id,agent_level,
						highest_agency_contract_pic_url,invite_code,mobile,verify_code,password,registration_id,device_type,integrate_distribute_type);
			}
		});
		
		rlMarket.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				marketDialog.setListener(new MarketSelectDialog.MarketListener() {
					
					@Override
					public void selectProvince(String province) {
						// TODO Auto-generated method stub
						selectMyProvice(province);
					}
					
					@Override
					public void selectCity(String city) {
						// TODO Auto-generated method stub
						selectMyCity(city);
					}

					@Override
					public void selectMarket(String market) {
						for(CityMarketBean cmb : marketDataList) {
							if (cmb.getName().equals(market)) {
								cityMarketBean = cmb;
								edtMarket.setText(market);
								break;
							}
						}
					}
				});
				marketDialog.show();
			}
		});
		rlDaiLi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				agentLevelDialog.setListener(new BaseDialog.DialogListener() {
					@Override
					public void update(Object object) {
						String daili = (String) object;
						for(AgentLevelBean alb : agentLevelDataList) {
							if (alb.getLevel_name().equals(daili)) {
								agentLevelBean = alb;
								edtDaiLi.setText(daili);
								break;
							}
						}
					}
				});
				agentLevelDialog.show();
			}
		});

		rlYingYeZhiZhao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (yingYeDialog == null) {
					yingYeDialog = new ChangeHeadDialog(UploadCertificateActivity.this);
				}
				yingYeDialog.setListener(new ChangeHeadDialog.ChangeHeadDialogListener() {

					@Override
					public void pic() {
						selectPicFromLocal(TAG_YINGYEPIC);
					}

					@Override
					public void capture() {
						selectPicFromCamera(TAG_YINGYEPIC);
					}
				});
				yingYeDialog.show();
			}
		});

		rlDailiShang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (heTongDialog == null) {
					heTongDialog = new ChangeHeadDialog(UploadCertificateActivity.this);
				}
				heTongDialog.setListener(new ChangeHeadDialog.ChangeHeadDialogListener() {

					@Override
					public void pic() {
						selectPicFromLocal("");
					}

					@Override
					public void capture() {
						selectPicFromCamera("");
					}
				});
				heTongDialog.show();
			}
		});
	}
	
	private void initData() {
		phone = getIntent().getStringExtra(KEY_PHONE);
		code = getIntent().getStringExtra(KEY_CODE);
		invteCode = getIntent().getStringExtra(KEY_INVITECODE);
		pwd = getIntent().getStringExtra(KEY_PWD);

		pushEventNoProgress(EventCode.HTTP_GETPROVINCECITYZONE);
		pushEventNoProgress(EventCode.HTTP_GETAGENTLEVELINFO);
	}
	
	private void selectMyProvice(String province) {
		String provinceCode = "";
		for(ProvinceDataBean pdata : provinceDataList) {
			if (pdata.getName().equals(province)) {
				provinceCode = pdata.getCode();
			}
		}
		if (!TextUtils.isEmpty(provinceCode)) {
			cityList.clear();
			cityDataList.clear();
			pushEventNoProgress(EventCode.HTTP_GETPROVINCECITYZONE, provinceCode);
		}
	}
	
	private void selectMyCity(String city) {
		String cityCode = "";
		String provinceCode = "";
		for(CityDataBean pdata : cityDataList) {
			if (pdata.getName().equals(city)) {
				cityCode = pdata.getCode();
				provinceCode = pdata.getProvince_code();
			}
		}
		
		if (!TextUtils.isEmpty(cityCode) && !TextUtils.isEmpty(provinceCode)) {
			marketDataList.clear();
			marketList.clear();
			pushEventNoProgress(EventCode.HTTP_GETCITYMARKET, provinceCode,cityCode);
		}
	}
	
	@Override
	public void onEventRunEnd(Event event) {
		// TODO Auto-generated method stub
		super.onEventRunEnd(event);
		if (event.getEventCode() == EventCode.HTTP_GETPROVINCECITYZONE) {
			if (event.isSuccess()) {
				String province_code = (String) event.getReturnParamAtIndex(1);
				if (TextUtils.isEmpty(province_code)) {
					ProvinceResponseBean data = (ProvinceResponseBean) event.getReturnParamAtIndex(0);
					if (data != null && data.getData() != null) {
						provinceDataList = data.getData().getProvince_info();
						cityDataList.clear();
						provinceList.clear();
						cityList.clear();
						for(ProvinceDataBean pdata : provinceDataList) {
							provinceList.add(pdata.getName());
						}
						marketDialog.setProvince(provinceList, 0);
						if (provinceList.size() > 0) {
							selectMyProvice(provinceList.get(0));
						}
					}
				} else {
					CityResponseBean data = (CityResponseBean) event.getReturnParamAtIndex(0);
					if (data != null && data.getData() != null) {
						cityDataList = data.getData().getCity_info();
						cityList.clear();
						for(CityDataBean pdata : cityDataList) {
							cityList.add(pdata.getName());
						}
						marketDialog.setCity(cityList, 0);
						if (cityList.size() > 0) {
							selectMyCity(cityList.get(0));
						}
					}
				}
			} else {
				showToast(event.getFailMessage());
			}
		}
		
		if (event.getEventCode() == EventCode.HTTP_GETCITYMARKET) {
			if (event.isSuccess()) {
				CityMarketResponseBean dataBean = (CityMarketResponseBean) event.getReturnParamAtIndex(0);
				if (dataBean != null && dataBean.getData() != null) {
					marketDataList = dataBean.getData().getCity_market();
					marketList.clear();
					for(CityMarketBean cmb : marketDataList) {
						marketList.add(cmb.getName());
					}
					marketDialog.setMarket(marketList, 0);
				}
			} else {
				showToast(event.getFailMessage());
			}
		}

		if (event.getEventCode() == EventCode.HTTP_GETAGENTLEVELINFO) {
			if (event.isSuccess()) {
				AgentLevelResponseBean dataBean = (AgentLevelResponseBean) event.getReturnParamAtIndex(0);
				if (dataBean != null && dataBean.getData() != null) {
					agentLevelDataList = dataBean.getData().getAgent_level_list();
					agentLevelList.clear();
					for(AgentLevelBean mlb : agentLevelDataList) {
						agentLevelList.add(mlb.getLevel_name());
					}
					agentLevelDialog.setData(agentLevelList,0);
				}
			} else {
				showToast(event.getFailMessage());
			}
		}

		if (event.getEventCode() == EventCode.HTTP_BUSINESSREGIST) {
			if (event.isSuccess()) {
				BaseResponseBean bean = (BaseResponseBean) event.getReturnParamAtIndex(0);
				if (bean != null && !TextUtils.isEmpty(bean.getMessage())) {
					if (confirmDialog == null) {
						confirmDialog = new ConfirmDialog(UploadCertificateActivity.this);
					}
					if (confirmDialog.isShowing()) {
						return;
					}
					confirmDialog.setContent(bean.getMessage());
					confirmDialog.setListener(new BaseDialog.DialogListener() {
						@Override
						public void update(Object object) {
							String result = (String) object;
							if ("ok".equals(result)) {
								finish();
							}
						}
					});
					confirmDialog.show();
				}
			} else {
				showToast(event.getFailMessage());
			}
		}
	}

	private static final String TAG_YINGYEPIC = "TAG_YingYePic";
	@Override
	public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult,String tag) {
		String url = oss.presignPublicObjectURL(AppContext.OSS_BUCKET,putObjectRequest.getObjectKey());
		System.out.println("上传图片："+url);
		if (!TextUtils.isEmpty(url)) {
			if (TAG_YINGYEPIC.equals(tag)) {
				business_license = url;
				ivYingYePic.setVisibility(View.VISIBLE);
				ImageLoaderUtil.display(url,ivYingYePic);
			} else {
				highest_agency_contract_pic_url = url;
				ivDailiShang.setVisibility(View.VISIBLE);
				ImageLoaderUtil.display(url,ivDailiShang);
			}

		}
	}
}
