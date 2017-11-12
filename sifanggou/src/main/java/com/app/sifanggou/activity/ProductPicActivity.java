package com.app.sifanggou.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.adapter.GuangGaoPagerAdapter;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2017/11/5.
 */

public class ProductPicActivity extends PicBaseActivity implements BGASortableNinePhotoLayout.Delegate,EasyPermissions.PermissionCallbacks{
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.snpl_moment_add_photos)
    private BGASortableNinePhotoLayout mPhotosSnpl;

    private GuangGaoPagerAdapter adapter;
    private List<View> viewList = new ArrayList<View>();

    public static final String KEY_DATA = "key_ProductPicActivity_data";
    private ArrayList<String> dataList = new ArrayList<String>();
    private int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        addBack(R.id.rl_back);
        setTitle("商品图片");

        adapter = new GuangGaoPagerAdapter(viewList);
        viewPager.setAdapter(adapter);

        mPhotosSnpl.setDelegate(this);
    }

    private void initData() {
        dataList = (ArrayList<String>) getIntent().getSerializableExtra(KEY_DATA);
        if (dataList != null) {
            refreshViewPager();
            mPhotosSnpl.setData(dataList);
        }
    }

    private void initListener() {
        setRightTextClickListener("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList == null || dataList.size() <= 0) {
                    CommonUtils.showToast("请添加图片");
                    return;
                }
                upLoadPic();
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void refreshViewPager() {
        viewList.clear();
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for(String path : dataList) {
            ImageView iv = new ImageView(ProductPicActivity.this);
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
                    if (EasyPermissions.hasPermissions(ProductPicActivity.this, perms)) {
                        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

                        startActivity(BGAPhotoPreviewActivity.newIntent(ProductPicActivity.this, takePhotoDir,dataList , currentIndex));
                    } else {
                        EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
                    }
                }
            });
            viewList.add(iv);
        }
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
        dataList = mPhotosSnpl.getData();
        refreshViewPager();
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(this, mPhotosSnpl.getMaxItemCount(), models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
    }

    private void choicePhotoWrapper(){
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, takePhotoDir, mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount(), null, false), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;

    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;

    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
        dataList = mPhotosSnpl.getData();
        refreshViewPager();
    }

    private void upLoadPic() {
        for(int i = 0 ; i < dataList.size() ; i++) {
            if (dataList.get(i).startsWith(File.separator)) {
                uploadFile(dataList.get(i),dataList.get(i));
                break;
            }
            if (i == dataList.size() - 1) {
                Intent data = new Intent();
                data.putExtra(AddProductActivity.KEY_PIC,dataList);
                setResult(Activity.RESULT_OK,data);
                finish();
            }
        }
    }

    @Override
    public void onSuccess(PutObjectRequest request, PutObjectResult result, String tag) {
        String url = oss.presignPublicObjectURL(AppContext.OSS_BUCKET,request.getObjectKey());
        if(!TextUtils.isEmpty(url)) {
            Iterator<String> i = dataList.iterator();
            while(i.hasNext()) {
                String path =  i.next();
                if (path.equals(tag)) {
                    i.remove();
                    dataList.add(url);
                    break;
                }
            }
            upLoadPic();
        } else {
            CommonUtils.showToast("图片上传失败");
        }
    }
}
