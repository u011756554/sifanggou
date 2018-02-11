package com.app.sifanggou.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.adapter.GuangGaoPagerAdapter;
import com.app.sifanggou.bean.AgentLevelBean;
import com.app.sifanggou.bean.AgentLevelType;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.HuoJiaType;
import com.app.sifanggou.bean.ProductType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.AgentLevelResponseBean;
import com.app.sifanggou.net.bean.GetBusinessCanAllocateShelfNumResponseBean;
import com.app.sifanggou.net.bean.GetCommodityTypeListResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;
import com.app.sifanggou.utils.PreManager;
import com.app.sifanggou.view.AgentLevelDialog;
import com.app.sifanggou.view.BaseDialog;
import com.app.sifanggou.view.tree.Node;
import com.app.sifanggou.view.tree.NodeResource;
import com.app.sifanggou.view.tree.TreeListView;
import com.app.sifanggou.view.tree.TreeUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import pub.devrel.easypermissions.EasyPermissions;

import static com.app.sifanggou.utils.CommonUtils.showToast;

/**
 * Created by Administrator on 2017/10/6.
 */

public class AddProductActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    @ViewInject(R.id.ll_putong)
    private LinearLayout llPuTong;
    @ViewInject(R.id.ll_daili)
    private LinearLayout llDaiLi;
    @ViewInject(R.id.tv_putonghuojia)
    private TextView tvPuTongHuoJia;
    @ViewInject(R.id.tv_putonghuojia_line)
    private TextView viewPuTongHuoJia;
    @ViewInject(R.id.tv_dailihuojia)
    private TextView tvDaiLiHuoJia;
    @ViewInject(R.id.tv_dailihuojia_line)
    private TextView viewDaiLiHuoJia;
    @ViewInject(R.id.sv_putong)
    private ScrollView svPutong;
    @ViewInject(R.id.sv_daili)
    private ScrollView svDaili;

    private LoginResponseBean loginBean;
    private HuoJiaType huoJiaType = HuoJiaType.PUTONG;

    //普通商品属性
    private ArrayList<String> puTongUrlList = new ArrayList<String>();
    @ViewInject(R.id.edt_dec)
    private EditText edtDec;
    @ViewInject(R.id.edt_name)
    private EditText edtName;
    @ViewInject(R.id.edt_pinpai)
    private EditText edtPinPai;
    @ViewInject(R.id.edt_price_tongji)
    private EditText edtTongJiPrice;
    @ViewInject(R.id.edt_price_xiaji)
    private EditText edtXiaJiPrice;
    @ViewInject(R.id.edt_kucun)
    private EditText edtKuCun;
    @ViewInject(R.id.edt_chandi)
    private EditText edtChanDi;
    @ViewInject(R.id.edt_guige)
    private EditText edtGuiGe;
    @ViewInject(R.id.edt_dengji)
    private EditText edtZhiLiangDengJi;
    @ViewInject(R.id.tv_fenlei)
    private TextView tvFenLei;
    @ViewInject(R.id.rl_putong_add)
    private RelativeLayout rlPuTongAdd;
    @ViewInject(R.id.rl_fenlei)
    private RelativeLayout rlFenLei;
    @ViewInject(R.id.rl_putong_pic)
    private RelativeLayout rlPutongPic;
    @ViewInject(R.id.viewpager_putong)
    private ViewPager putongViewPager;
    @ViewInject(R.id.iv_putong_camera)
    private ImageView ivPutongCamera;

    //代理商品属性
    private ArrayList<String> daiLiUrlList = new ArrayList<String>();
    private ArrayList<String> hetongUrlList = new ArrayList<String>();
    @ViewInject(R.id.edt_dec_daili)
    private EditText edtDecDaiLi;
    @ViewInject(R.id.edt_name_daili)
    private EditText edtNameDaiLi;
    @ViewInject(R.id.edt_pinpai_daili)
    private EditText edtPinPaiDaiLi;
    @ViewInject(R.id.edt_price_tongji_daili)
    private EditText edtTongJiPriceDaiLi;
    @ViewInject(R.id.edt_price_xiaji_daili)
    private EditText edtXiaJiPriceDaiLi;
    @ViewInject(R.id.edt_kucun_daili)
    private EditText edtKuCunDaiLi;
    @ViewInject(R.id.edt_chandi_daili)
    private EditText edtChanDiDaiLi;
    @ViewInject(R.id.edt_guige_daili)
    private EditText edtGuiGeDaiLi;
    @ViewInject(R.id.edt_dengji_daili)
    private EditText edtZhiLiangDengJiDaiLi;
    @ViewInject(R.id.tv_fenlei_daili)
    private TextView tvFenLeiDaiLi;
    @ViewInject(R.id.tv_jiebie_daili)
    private TextView tvJieBieDaiLi;
    @ViewInject(R.id.rl_fenlei_daili)
    private RelativeLayout rlFenLeiDaiLi;
    @ViewInject(R.id.rl_jiebie_daili)
    private RelativeLayout rlJiBieDaiLi;
    @ViewInject(R.id.viewpager_daili)
    private ViewPager dailiViewPager;
    @ViewInject(R.id.rl_daili_pic)
    private RelativeLayout rlDaiLiPic;
    @ViewInject(R.id.rl_daili_add)
    private RelativeLayout rlDaiLiAdd;
    @ViewInject(R.id.iv_daili_camera)
    private ImageView ivDaiLiCamera;

    @ViewInject(R.id.viewpager_hetong)
    private ViewPager heTongViewPager;
    @ViewInject(R.id.rl_hetong_pic)
    private RelativeLayout rlHeTongPic;
    @ViewInject(R.id.rl_hetong_add)
    private RelativeLayout rlHeTongAdd;
    @ViewInject(R.id.iv_hetong_camera)
    private ImageView ivHeTongCamera;

    private List<NodeResource> nodeList;
    private Node firstNode = new Node();
    private Node secondNode = new Node();
    private Node thirdNode = new Node();
    private Node firstDaiLiNode = new Node();
    private Node secondDaiLiNode = new Node();
    private Node thirdDaiLiNode = new Node();

    private AgentLevelDialog agentLevelDialog;
    private List<AgentLevelBean> agentLevelDataList = new ArrayList<AgentLevelBean>();
    private List<String> agentLevelList = new ArrayList<String>();
    private AgentLevelBean agentLevelBean;
    //普通货架 viewpager
    private GuangGaoPagerAdapter putongAdapter;
    private List<View> putongViewList = new ArrayList<View>();
    private int putongCurrentIndex = 0;
    //代理货架 viewpager
    private GuangGaoPagerAdapter dailiAdapter;
    private List<View> dailiViewList = new ArrayList<View>();
    private int dailiCurrentIndex = 0;
    //代理货架合同 viewpager
    private GuangGaoPagerAdapter hetongAdapter;
    private List<View> hetongViewList = new ArrayList<View>();
    private int hetongCurrentIndex = 0;

    //初始化数据
    public static String KEY_INITDATA = "key_AddProductActivity_initdata";
    private CommodityInfoBean initCommodityInfoBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initView();
        initListener();
        initData();
    }

    private void getData() {
        initCommodityInfoBean = (CommodityInfoBean) getIntent().getSerializableExtra(KEY_INITDATA);
    }

    private void initView() {
        addBack(R.id.rl_back);
        if (initCommodityInfoBean == null) {
            setTitle("添加商品");
        } else {
            setTitle("编辑商品");
        }


        agentLevelDialog = new AgentLevelDialog(AddProductActivity.this);
        //定义普通货架图片
        putongAdapter = new GuangGaoPagerAdapter(putongViewList);
        putongViewPager.setAdapter(putongAdapter);
        //定义代理货架图片
        dailiAdapter = new GuangGaoPagerAdapter(dailiViewList);
        dailiViewPager.setAdapter(dailiAdapter);
        //定义代理货架合同图片
        hetongAdapter = new GuangGaoPagerAdapter(hetongViewList);
        heTongViewPager.setAdapter(hetongAdapter);
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        refreshEditData(initCommodityInfoBean);
        refreshHuoJiaType();
        pushEventNoProgress(EventCode.HTTP_GETCOMMODITYTYPELIST);
        pushEventNoProgress(EventCode.HTTP_GETAGENTLEVELINFO);
    }

    /**
     * 刷新编辑商品数据
     * @param infoBean
     */
    private void refreshEditData(CommodityInfoBean infoBean) {
        if (infoBean == null) return;
        //不能编辑的属性
        edtPinPai.setEnabled(false);
        edtPinPaiDaiLi.setEnabled(false);
        edtChanDi.setEnabled(false);
        edtChanDiDaiLi.setEnabled(false);

        //初始化货架
        if (!TextUtils.isEmpty(infoBean.getType())) {
            for (HuoJiaType type : HuoJiaType.values()) {
                if (type.getType().equals(infoBean.getType())) {
                    huoJiaType = type;
                    refreshHuoJiaType();
                    break;
                }
            }
        }
        //初始化商品图片
        if (!TextUtils.isEmpty(infoBean.getCommodity_pic_url())) {
            String dataArray[] = infoBean.getCommodity_pic_url().split(",");
            ArrayList<String> dataList = new ArrayList<String>();
            for (int i = 0 ; i<dataArray.length ; i++) {
                dataList.add(dataArray[i]);
            }
            if (huoJiaType == HuoJiaType.DAILI) {
                daiLiUrlList = dataList;
                refreshDaiLiView();


            } else if (huoJiaType == HuoJiaType.PUTONG) {
                puTongUrlList = dataList;
                refreshPuTongView();
            }
        }

        //初始化合同图片
        if (huoJiaType == HuoJiaType.DAILI && !TextUtils.isEmpty(infoBean.getAgency_contract_pic_url())) {
            String hetongArray[] = infoBean.getAgency_contract_pic_url().split(",");
            ArrayList<String> hetongList = new ArrayList<String>();
            for (int i = 0 ; i<hetongArray.length ; i++) {
                hetongList.add(hetongArray[i]);
            }
            hetongUrlList = hetongList;
            refreshHeTongView();
        }

        //初始化其它信息
        if (huoJiaType == HuoJiaType.DAILI) {
            refreshInitDaliData(infoBean);
        } else {
            refreshInitPutongData(infoBean);
        }
    }

    /**
     * 初始化普通商品信息
     * @param bean
     */
    private void refreshInitPutongData(CommodityInfoBean bean) {
        if (bean == null) return;
        edtName.setText(bean.getCommodity_name());
        edtDec.setText(bean.getIntro());
        edtPinPai.setText(bean.getBrand_name());

        if (!TextUtils.isEmpty(bean.getA_price())) {
            float aPrice = Float.valueOf(bean.getA_price()) / 100;
            edtTongJiPrice.setText(aPrice + "");
        }
        if (!TextUtils.isEmpty(bean.getB_price())) {
            float bPrice = Float.valueOf(bean.getB_price()) / 100;
            edtXiaJiPrice.setText(bPrice + "");
        }

        edtKuCun.setText(bean.getCollection_num());
        edtChanDi.setText(bean.getProduction_place());
        edtGuiGe.setText(bean.getSpecification());
        edtZhiLiangDengJi.setText(bean.getQuality_grade());

        firstNode.setCurId(bean.getFirst_level_category_code());
        firstNode.setValue(bean.getFirst_level_category_name());
        secondNode.setCurId(bean.getSecond_level_category_code());
        secondNode.setValue(bean.getSecond_level_category_name());
        thirdNode.setCurId(bean.getThird_level_category_code());
        thirdNode.setValue(bean.getThird_level_category_name());
        tvFenLei.setText(bean.getFirst_level_category_name()+"  "+
                bean.getSecond_level_category_name()+"  "+
                bean.getThird_level_category_name());
    }

    /**
     * 初始化代理商品信息
     * @param bean
     */
    private void refreshInitDaliData(CommodityInfoBean bean) {
        if (bean == null) return;
        edtNameDaiLi.setText(bean.getCommodity_name());
        edtDecDaiLi.setText(bean.getIntro());
        edtPinPaiDaiLi.setText(bean.getBrand_name());

        if (!TextUtils.isEmpty(bean.getA_price())) {
            float aPrice = Float.valueOf(bean.getA_price()) / 100;
            edtTongJiPriceDaiLi.setText(aPrice + "");
        }
        if (!TextUtils.isEmpty(bean.getB_price())) {
            float bPrice = Float.valueOf(bean.getB_price()) / 100;
            edtXiaJiPriceDaiLi.setText(bPrice + "");
        }

        edtKuCunDaiLi.setText(bean.getCollection_num());
        edtChanDiDaiLi.setText(bean.getProduction_place());
        edtGuiGeDaiLi.setText(bean.getSpecification());
        edtZhiLiangDengJiDaiLi.setText(bean.getQuality_grade());

        firstDaiLiNode.setCurId(bean.getFirst_level_category_code());
        firstDaiLiNode.setValue(bean.getFirst_level_category_name());
        secondDaiLiNode.setCurId(bean.getSecond_level_category_code());
        secondDaiLiNode.setValue(bean.getSecond_level_category_name());
        thirdDaiLiNode.setCurId(bean.getThird_level_category_code());
        thirdDaiLiNode.setValue(bean.getThird_level_category_name());
        tvFenLeiDaiLi.setText(bean.getFirst_level_category_name()+"  "+
                bean.getSecond_level_category_name()+"  "+
                bean.getThird_level_category_name());
        refreshAgentLevel(agentLevelDataList,bean);
    }

    /**
     * 刷新代理级别
     * @param agentLevelDataList
     * @param bean
     */
    private void refreshAgentLevel(List<AgentLevelBean> agentLevelDataList,CommodityInfoBean bean) {
        if (agentLevelDataList == null || bean == null) return;
        for(AgentLevelBean alb : agentLevelDataList) {
            if (bean.getAgent_level().equals(alb.getLevel_num())) {
                agentLevelBean = alb;
                tvJieBieDaiLi.setText(alb.getLevel_name());
                break;
            }
        }
    }

    /**
     * 刷新普通货架界面
     */
    private void refreshPuTongView() {
        if (puTongUrlList.size() > 0) {
            rlPutongPic.setVisibility(View.VISIBLE);
            rlPuTongAdd.setVisibility(View.GONE);
        } else {
            rlPutongPic.setVisibility(View.GONE);
            rlPuTongAdd.setVisibility(View.VISIBLE);
        }
        putongViewList.clear();
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for(String path : puTongUrlList) {
            ImageView iv = new ImageView(AddProductActivity.this);
            iv.setLayoutParams(param);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (path.startsWith(File.separator)) {
                ImageLoaderUtil.displayWithCache("file://"+path,iv);
            } else {
                ImageLoaderUtil.displayWithCache(path,iv);
            }

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                    if (EasyPermissions.hasPermissions(AddProductActivity.this, perms)) {
                        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

                        startActivity(BGAPhotoPreviewActivity.newIntent(AddProductActivity.this, takePhotoDir,puTongUrlList , putongCurrentIndex));
                    } else {
                        EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
                    }
                }
            });
            putongViewList.add(iv);
        }
        putongAdapter.notifyDataSetChanged();
        putongViewPager.setCurrentItem(0);
        putongCurrentIndex = 0;
    }

    /**
     * 刷新代理货架有关界面
     */
    private void refreshDaiLiView() {
        if (daiLiUrlList.size() > 0) {
            rlDaiLiPic.setVisibility(View.VISIBLE);
            rlDaiLiAdd.setVisibility(View.GONE);
        } else {
            rlDaiLiPic.setVisibility(View.GONE);
            rlDaiLiAdd.setVisibility(View.VISIBLE);
        }
        dailiViewList.clear();
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for(String path : daiLiUrlList) {
            ImageView iv = new ImageView(AddProductActivity.this);
            iv.setLayoutParams(param);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (path.startsWith(File.separator)) {
                ImageLoaderUtil.displayWithCache("file://"+path,iv);
            } else {
                ImageLoaderUtil.displayWithCache(path,iv);
            }

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                    if (EasyPermissions.hasPermissions(AddProductActivity.this, perms)) {
                        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

                        startActivity(BGAPhotoPreviewActivity.newIntent(AddProductActivity.this, takePhotoDir,daiLiUrlList , dailiCurrentIndex));
                    } else {
                        EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
                    }
                }
            });
            dailiViewList.add(iv);
        }
        dailiAdapter.notifyDataSetChanged();
        dailiViewPager.setCurrentItem(0);
        dailiCurrentIndex = 0;
    }

    /**
     * 刷新代理货架合同有关界面
     */
    private void refreshHeTongView() {
        if (hetongUrlList.size() > 0) {
            rlHeTongPic.setVisibility(View.VISIBLE);
            rlHeTongAdd.setVisibility(View.GONE);
        } else {
            rlHeTongPic.setVisibility(View.GONE);
            rlHeTongAdd.setVisibility(View.VISIBLE);
        }
        hetongViewList.clear();
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for(String path : hetongUrlList) {
            ImageView iv = new ImageView(AddProductActivity.this);
            iv.setLayoutParams(param);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (path.startsWith(File.separator)) {
                ImageLoaderUtil.displayWithCache("file://"+path,iv);
            } else {
                ImageLoaderUtil.displayWithCache(path,iv);
            }

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                    if (EasyPermissions.hasPermissions(AddProductActivity.this, perms)) {
                        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

                        startActivity(BGAPhotoPreviewActivity.newIntent(AddProductActivity.this, takePhotoDir,hetongUrlList , hetongCurrentIndex));
                    } else {
                        EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
                    }
                }
            });
            hetongViewList.add(iv);
        }
        hetongAdapter.notifyDataSetChanged();
        heTongViewPager.setCurrentItem(0);
        hetongCurrentIndex = 0;
    }

    private void refreshHuoJiaType() {
        if (huoJiaType == HuoJiaType.PUTONG) {
            tvPuTongHuoJia.setSelected(true);
            viewPuTongHuoJia.setSelected(true);
            tvDaiLiHuoJia.setSelected(false);
            viewDaiLiHuoJia.setSelected(false);
            svPutong.setVisibility(View.VISIBLE);
            svDaili.setVisibility(View.GONE);
        } else {
            tvPuTongHuoJia.setSelected(false);
            viewPuTongHuoJia.setSelected(false);
            tvDaiLiHuoJia.setSelected(true);
            viewDaiLiHuoJia.setSelected(true);
            svPutong.setVisibility(View.GONE);
            svDaili.setVisibility(View.VISIBLE);
        }

        if (initCommodityInfoBean != null) {
            if (huoJiaType == HuoJiaType.DAILI) {
                llPuTong.setEnabled(false);
                llPuTong.setClickable(false);
            } else if (huoJiaType == HuoJiaType.PUTONG) {
                llDaiLi.setEnabled(false);
                llDaiLi.setClickable(false);
            }
        }
    }

    private void initListener() {
        llPuTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huoJiaType = HuoJiaType.PUTONG;
                refreshHuoJiaType();
            }
        });

        llDaiLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huoJiaType = HuoJiaType.DAILI;
                refreshHuoJiaType();
            }
        });

        rlPuTongAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this,ProductPicActivity.class);
                startActivityForResult(intent,REQUEST_PIC_CODE);
            }
        });

        rlDaiLiAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this,ProductPicActivity.class);
                startActivityForResult(intent,REQUEST_PIC_DAILI_CODE);
            }
        });

        rlHeTongAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this,ProductPicActivity.class);
                startActivityForResult(intent,REQUEST_PIC_HETONG_CODE);
            }
        });

        rlFenLei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nodeList != null) {
                    showPopWindow(nodeList);
                }
            }
        });

        rlFenLeiDaiLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nodeList != null) {
                    showPopWindow(nodeList);
                }
            }
        });

        rlJiBieDaiLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agentLevelDialog.setListener(new BaseDialog.DialogListener() {
                    @Override
                    public void update(Object object) {
                        String daili = (String) object;
                        for(AgentLevelBean alb : agentLevelDataList) {
                            if (alb.getLevel_name().equals(daili)) {
                                agentLevelBean = alb;
                                tvJieBieDaiLi.setText(daili);
                                break;
                            }
                        }
                    }
                });
                agentLevelDialog.show();
            }
        });

        ivPutongCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (puTongUrlList != null && puTongUrlList.size() > 0) {
                    Intent intent = new Intent(AddProductActivity.this,ProductPicActivity.class);
                    intent.putExtra(ProductPicActivity.KEY_DATA,puTongUrlList);
                    startActivityForResult(intent,REQUEST_PIC_CODE);
                }
            }
        });

        ivDaiLiCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daiLiUrlList != null && daiLiUrlList.size() > 0) {
                    Intent intent = new Intent(AddProductActivity.this,ProductPicActivity.class);
                    intent.putExtra(ProductPicActivity.KEY_DATA,daiLiUrlList);
                    startActivityForResult(intent,REQUEST_PIC_DAILI_CODE);
                }
            }
        });

        ivHeTongCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hetongUrlList != null && hetongUrlList.size() > 0) {
                    Intent intent = new Intent(AddProductActivity.this,ProductPicActivity.class);
                    intent.putExtra(ProductPicActivity.KEY_DATA,hetongUrlList);
                    startActivityForResult(intent,REQUEST_PIC_HETONG_CODE);
                }
            }
        });

        putongViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                putongCurrentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        dailiViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dailiCurrentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        heTongViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hetongCurrentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setRightTextClickListener("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (huoJiaType == HuoJiaType.PUTONG) {
                    if (TextUtils.isEmpty(edtName.getText().toString())) {
                        CommonUtils.showToast("请输入商品名称");
                        return;
                    }
                    if (puTongUrlList.size() <= 0) {
                        CommonUtils.showToast("请添加商品图片");
                        return;
                    }
                    if (TextUtils.isEmpty(edtDec.getText().toString())) {
                        CommonUtils.showToast("请输入商品描述");
                        return;
                    }
                    if (TextUtils.isEmpty(edtPinPai.getText().toString())) {
                        CommonUtils.showToast("请输入商品品牌");
                        return;
                    }
                    if (TextUtils.isEmpty(edtTongJiPrice.getText().toString())) {
                        CommonUtils.showToast("请输入商品同级价格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtXiaJiPrice.getText().toString())) {
                        CommonUtils.showToast("请输入商品下级价格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtKuCun.getText().toString())) {
                        CommonUtils.showToast("请输入库存");
                        return;
                    }
                    if (TextUtils.isEmpty(edtChanDi.getText().toString())) {
                        CommonUtils.showToast("请输入产地");
                        return;
                    }
                    if (TextUtils.isEmpty(edtGuiGe.getText().toString())) {
                        CommonUtils.showToast("请输入商品规格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtZhiLiangDengJi.getText().toString())) {
                        CommonUtils.showToast("请输入商品质量等级");
                        return;
                    }
                    if (TextUtils.isEmpty(firstNode.getCurId())
                            && TextUtils.isEmpty(secondNode.getCurId())
                            && TextUtils.isEmpty(thirdNode.getCurId())) {
                        CommonUtils.showToast("请选择商品分类");
                        return;
                    }

                    String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                    String mobile = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                    String trans_no = System.currentTimeMillis() + "";
                    String sign = CommonUtils.getSign(business_code,mobile,trans_no,PreManager.getString(getApplicationContext(),AppContext.USER_PWD));
                    String commodity_name = edtName.getText().toString();
                    String type = "";
                    if (huoJiaType == HuoJiaType.PUTONG) {
                        type = ProductType.COMMON.getType();
                    } else {
                        type = ProductType.AGENCY.getType();
                    }
                    String brand_name = edtPinPai.getText().toString();
                    String first_level_category_code = "";
                    String second_level_category_code = "";
                    String third_level_category_code = "";
                    if (!TextUtils.isEmpty(firstNode.getCurId())) {
                        first_level_category_code = firstNode.getCurId();
                    }
                    if (!TextUtils.isEmpty(secondNode.getCurId())) {
                        second_level_category_code = secondNode.getCurId();
                    }
                    if (!TextUtils.isEmpty(thirdNode.getCurId())) {
                        third_level_category_code = thirdNode.getCurId();
                    }
                    StringBuffer sbf = new StringBuffer();
                    for(String url : puTongUrlList) {
                        sbf.append(url).append(",");
                    }
                    String commodity_pic_url = sbf.toString().substring(0,sbf.toString().length() - 1);
                    String quality_grade = edtZhiLiangDengJi.getText().toString();
                    String production_place = edtChanDi.getText().toString();
                    String specification = edtGuiGe.getText().toString();
                    String intro = edtDec.getText().toString();

                    String a_price = (int)(Float.valueOf(edtTongJiPrice.getText().toString()) * 100) + "";
                    String b_price = (int)(Float.valueOf(edtXiaJiPrice.getText().toString()) * 100) + "";

                    if (initCommodityInfoBean == null) {
                        pushEventBlock(EventCode.HTTP_ADDBUSINESSCOMMODITY,business_code,mobile,trans_no,sign,commodity_name,type,brand_name,first_level_category_code,second_level_category_code,third_level_category_code,
                                commodity_pic_url,quality_grade,production_place,specification,intro,a_price,b_price);
                    } else {
                        pushEventBlock(EventCode.HTTP_UPDATEBUSINESSCOMMODITY,business_code,mobile,trans_no,sign,initCommodityInfoBean.getCommodity_id(),commodity_name,first_level_category_code,second_level_category_code,
                                third_level_category_code,type,"","",commodity_pic_url,specification,intro,a_price,b_price);
                    }
                } else {
                    if (TextUtils.isEmpty(edtNameDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品名称");
                        return;
                    }
                    if (daiLiUrlList.size() <= 0) {
                        CommonUtils.showToast("请添加商品图片");
                        return;
                    }
                    if (hetongUrlList.size() <= 0) {
                        CommonUtils.showToast("请添加合同图片");
                        return;
                    }
                    if (TextUtils.isEmpty(edtDecDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品描述");
                        return;
                    }
                    if (TextUtils.isEmpty(edtPinPaiDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品品牌");
                        return;
                    }
                    if (TextUtils.isEmpty(edtTongJiPriceDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品同级价格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtXiaJiPriceDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品下级价格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtKuCunDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入库存");
                        return;
                    }
                    if (TextUtils.isEmpty(edtChanDiDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入产地");
                        return;
                    }
                    if (TextUtils.isEmpty(edtGuiGeDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品规格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtZhiLiangDengJiDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品质量等级");
                        return;
                    }
                    if (TextUtils.isEmpty(firstDaiLiNode.getCurId())
                            && TextUtils.isEmpty(secondDaiLiNode.getCurId())
                            && TextUtils.isEmpty(thirdDaiLiNode.getCurId())) {
                        CommonUtils.showToast("请选择商品分类");
                        return;
                    }
                    if (agentLevelBean == null) {
                        showToast("选择代理级别");
                        return;
                    }

                    String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                    String mobile = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                    String trans_no = System.currentTimeMillis() + "";
                    String sign = CommonUtils.getSign(business_code,mobile,trans_no,PreManager.getString(getApplicationContext(),AppContext.USER_PWD));
                    String commodity_name = edtNameDaiLi.getText().toString();
                    String type = "";
                    if (huoJiaType == HuoJiaType.PUTONG) {
                        type = ProductType.COMMON.getType();
                    } else {
                        type = ProductType.AGENCY.getType();
                    }
                    String brand_name = edtPinPaiDaiLi.getText().toString();
                    String first_level_category_code = "";
                    String second_level_category_code = "";
                    String third_level_category_code = "";
                    if (!TextUtils.isEmpty(firstDaiLiNode.getCurId())) {
                        first_level_category_code = firstDaiLiNode.getCurId();
                    }
                    if (!TextUtils.isEmpty(secondDaiLiNode.getCurId())) {
                        second_level_category_code = secondDaiLiNode.getCurId();
                    }
                    if (!TextUtils.isEmpty(thirdDaiLiNode.getCurId())) {
                        third_level_category_code = thirdDaiLiNode.getCurId();
                    }
                    StringBuffer sbf = new StringBuffer();
                    for(String url : daiLiUrlList) {
                        sbf.append(url).append(",");
                    }
                    String commodity_pic_url = sbf.toString().substring(0,sbf.toString().length() - 1);
                    StringBuffer sbfHetong = new StringBuffer();
                    for(String url : hetongUrlList) {
                        sbfHetong.append(url).append(",");
                    }
                    String agency_contract_pic_url = sbfHetong.toString().substring(0,sbfHetong.toString().length() - 1);
                    String agent_level = "";
                    for(AgentLevelType alvt : AgentLevelType.values()) {
                        if (alvt.getCode().equals(agentLevelBean.getLevel_num())) {
                            agent_level = alvt.getType();
                            break;
                        }
                    }
                    String quality_grade = edtZhiLiangDengJiDaiLi.getText().toString();
                    String production_place = edtChanDiDaiLi.getText().toString();
                    String specification = edtGuiGeDaiLi.getText().toString();
                    String intro = edtDecDaiLi.getText().toString();
                    String a_price = (int)(Float.valueOf(edtTongJiPriceDaiLi.getText().toString()) * 100) + "";
                    String b_price = (int)(Float.valueOf(edtXiaJiPriceDaiLi.getText().toString()) * 100) + "";

                    if (initCommodityInfoBean == null) {
                        pushEventBlock(EventCode.HTTP_ADDBUSINESSCOMMODITY,business_code,mobile,trans_no,sign,commodity_name,type,brand_name,first_level_category_code,second_level_category_code,third_level_category_code,
                                commodity_pic_url,quality_grade,production_place,specification,intro,a_price,b_price,agency_contract_pic_url,agent_level);
                    } else {
                        pushEventBlock(EventCode.HTTP_UPDATEBUSINESSCOMMODITY,business_code,mobile,trans_no,sign,initCommodityInfoBean.getCommodity_id(),commodity_name,first_level_category_code,second_level_category_code,
                                third_level_category_code,type,agency_contract_pic_url,agent_level,commodity_pic_url,specification,intro,a_price,b_price);
                    }

                }
            }
        });
    }

    private static final int REQUEST_PIC_CODE = 0x123;
    private static final int REQUEST_PIC_DAILI_CODE = 0x124;
    private static final int REQUEST_PIC_HETONG_CODE = 0x125;
    public static final String KEY_PIC = "key_AddProductActivity_key";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_PIC_CODE) {
                if (data != null) {
                    ArrayList<String> dataList = (ArrayList<String>) data.getSerializableExtra(KEY_PIC);
                    if (dataList != null && dataList.size() > 0) {
                        puTongUrlList.clear();
                        puTongUrlList.addAll(dataList);
                        refreshPuTongView();
                    }
                }
            }
            if (requestCode == REQUEST_PIC_DAILI_CODE) {
                if (data != null) {
                    ArrayList<String> dataList = (ArrayList<String>) data.getSerializableExtra(KEY_PIC);
                    if (dataList != null && dataList.size() > 0) {
                        daiLiUrlList.clear();
                        daiLiUrlList.addAll(dataList);
                        refreshDaiLiView();
                    }
                }
            }
            if (requestCode == REQUEST_PIC_HETONG_CODE) {
                if (data != null) {
                    ArrayList<String> dataList = (ArrayList<String>) data.getSerializableExtra(KEY_PIC);
                    if (dataList != null && dataList.size() > 0) {
                        hetongUrlList.clear();
                        hetongUrlList.addAll(dataList);
                        refreshHeTongView();
                    }
                }
            }
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_UPDATEBUSINESSCOMMODITY) {
            if (event.isSuccess()) {
                finish();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSCANALLOCATESHELFNUM) {
            if (event.isSuccess()) {
                GetBusinessCanAllocateShelfNumResponseBean bean = (GetBusinessCanAllocateShelfNumResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null && bean.getData() != null && !TextUtils.isEmpty(bean.getData().getShelf_num())) {
                    if (huoJiaType == HuoJiaType.PUTONG) {
                        tvPuTongHuoJia.setText("普通商品货架("+bean.getData().getShelf_num()+")");
                    } else {
                        tvDaiLiHuoJia.setText("代理权商品货架("+bean.getData().getShelf_num()+")");
                    }
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

        if (event.getEventCode() == EventCode.HTTP_GETCOMMODITYTYPELIST) {
            if (event.isSuccess()) {
                GetCommodityTypeListResponseBean bean = (GetCommodityTypeListResponseBean) event.getReturnParamAtIndex(0);
                System.out.print(bean.getData());
                if (bean != null && bean.getData() != null && bean.getData().getCommodity_type_list() != null && bean.getData().getCommodity_type_list().size() > 0) {
                    if (nodeList != null) {
                        nodeList.clear();
                    }
                    nodeList = TreeUtils.commodityTypeBeanToNodeResource(bean.getData().getCommodity_type_list());
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSCOMMODITY) {
            if (event.isSuccess()) {
                finish();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

        if (event.getEventCode() == EventCode.HTTP_GETAGENTLEVELINFO) {
            AgentLevelResponseBean dataBean = (AgentLevelResponseBean) event.getReturnParamAtIndex(0);
            if (dataBean != null && dataBean.getData() != null) {
                agentLevelDataList = dataBean.getData().getAgent_level_list();
                agentLevelList.clear();
                for(AgentLevelBean mlb : agentLevelDataList) {
                    agentLevelList.add(mlb.getLevel_name());
                }
                agentLevelDialog.setData(agentLevelList,0);
                if (initCommodityInfoBean != null) {
                    refreshAgentLevel(agentLevelDataList,initCommodityInfoBean);
                }
            }
        } else {
            showToast(event.getFailMessage());
        }
    }

    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    private PopupWindow popupWindow;
    private void showPopWindow(final List<NodeResource> list){
        if(popupWindow != null && popupWindow.isShowing()){
            return;
        }
        View view = getLayoutInflater().inflate(R.layout.popwindow_company, null);
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_content);
        TreeListView treeListView = new TreeListView(AddProductActivity.this, list,0);
        treeListView.setListener(new TreeListView.UpdateListener() {

            @Override
            public void update(Node node) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                if (huoJiaType == HuoJiaType.PUTONG) {
                    getFenLei(node);
                } else {
                    getFenLeiDaiLi(node);
                }

            }

        });
        rl.addView(treeListView);
        popupWindow = new PopupWindow(view,CommonUtils.dip2px(AddProductActivity.this, 200), CommonUtils.dip2px(AddProductActivity.this, 320), true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(getWindow().getDecorView().getRootView(),Gravity.CENTER,0,0);
    }

    private void getFenLei(Node node) {
        if (node == null || nodeList == null) {
            return;
        }
        Node firstNodeThis = new Node();
        Node secondNodeThis = new Node();
        Node thirdNodeThis = new Node();

        thirdNodeThis = node;
        for (NodeResource sNode : nodeList) {
            if (sNode.getCurId().equals(thirdNodeThis.getParentId())) {
                secondNodeThis.setValue(sNode.getValue());
                secondNodeThis.setCurId(sNode.getCurId());
                secondNodeThis.setParentId(sNode.getParentId());
                break;
            }
        }
        for (NodeResource fNode : nodeList) {
            if (fNode.getCurId().equals(secondNodeThis.getParentId())) {
                firstNodeThis.setValue(fNode.getValue());
                firstNodeThis.setCurId(fNode.getCurId());
                firstNodeThis.setParentId(fNode.getParentId());
                break;
            }
        }
        if (!TextUtils.isEmpty(firstNodeThis.getCurId())) {
            firstNode = firstNodeThis;
            secondNode = secondNodeThis;
            thirdNode = thirdNodeThis;
            tvFenLei.setText(firstNode.getValue()+"  "+secondNode.getValue()+"  "+thirdNode.getValue());
        } else if (!TextUtils.isEmpty(secondNodeThis.getCurId())) {
            firstNode = secondNodeThis;
            secondNode = thirdNodeThis;
            tvFenLei.setText(firstNode.getValue()+"  "+secondNode.getValue());
        } else if (!TextUtils.isEmpty(thirdNodeThis.getCurId())){
            firstNode = thirdNodeThis;
            tvFenLei.setText(firstNode.getValue());
        }

    }

    private void getFenLeiDaiLi(Node node) {
        if (node == null || nodeList == null) {
            return;
        }
        Node firstNodeThis = new Node();
        Node secondNodeThis = new Node();
        Node thirdNodeThis = new Node();

        thirdNodeThis = node;
        for (NodeResource sNode : nodeList) {
            if (sNode.getCurId().equals(thirdNodeThis.getParentId())) {
                secondNodeThis.setValue(sNode.getValue());
                secondNodeThis.setCurId(sNode.getCurId());
                secondNodeThis.setParentId(sNode.getParentId());
                break;
            }
        }
        for (NodeResource fNode : nodeList) {
            if (fNode.getCurId().equals(secondNodeThis.getParentId())) {
                firstNodeThis.setValue(fNode.getValue());
                firstNodeThis.setCurId(fNode.getCurId());
                firstNodeThis.setParentId(fNode.getParentId());
                break;
            }
        }
        if (!TextUtils.isEmpty(firstNodeThis.getCurId())) {
            firstDaiLiNode = firstNodeThis;
            secondDaiLiNode = secondNodeThis;
            thirdDaiLiNode = thirdNodeThis;
            tvFenLeiDaiLi.setText(firstDaiLiNode.getValue()+"  "+secondDaiLiNode.getValue()+"  "+thirdDaiLiNode.getValue());
        } else if (!TextUtils.isEmpty(secondNodeThis.getCurId())) {
            firstDaiLiNode = secondNodeThis;
            secondDaiLiNode = thirdNodeThis;
            tvFenLeiDaiLi.setText(firstDaiLiNode.getValue()+"  "+secondDaiLiNode.getValue());
        } else if (!TextUtils.isEmpty(thirdNodeThis.getCurId())){
            firstDaiLiNode = thirdNodeThis;
            tvFenLeiDaiLi.setText(firstDaiLiNode.getValue());
        }
    }
}
