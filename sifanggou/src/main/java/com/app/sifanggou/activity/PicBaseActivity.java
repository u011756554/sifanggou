package com.app.sifanggou.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.app.sifanggou.AppContext;
import com.app.sifanggou.listener.OSSCallBackListener;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PictureUtils;
import com.app.sifanggou.utils.PreManager;

import static com.app.sifanggou.utils.CommonUtils.showToast;

/**
 * Created by Administrator on 2017/10/20.
 */

public abstract class PicBaseActivity extends BaseActivity implements OSSCallBackListener{
    private static final int CODE_IMAGE_CAPTURE = 0x11;
    private static final int SELECT_PIC = 0x13;
    private static final int HANDLER_CODE1 = 0x12;
    private static final int HANDLER_CODE2 = 0x14;
    private String tag;

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case HANDLER_CODE1:
                    dismissProgressDialog();
                    break;
                case HANDLER_CODE2:
                    OssData data = (OssData) msg.obj;
                    PicBaseActivity.this.onSuccess(data.getRequest(),data.getResult(),tag);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOSS();
    }

    protected OSS oss;
    protected void initOSS() {
        String accessKeyId = PreManager.getString(getApplicationContext(), AppContext.OSS_ACCESSKEYID);
        String secretKeyId = PreManager.getString(getApplicationContext(),AppContext.OSS_SECRETKEYID);
        String securityToken = PreManager.getString(getApplicationContext(),AppContext.OSS_SECURITYTOKEN);
        if (TextUtils.isEmpty(accessKeyId) || TextUtils.isEmpty(accessKeyId) || TextUtils.isEmpty(accessKeyId)) {
            return;
        }
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, secretKeyId, securityToken);
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        configuration.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        configuration.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        configuration.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(getApplicationContext(), AppContext.OSS_ENDPOINT, credentialProvider, configuration);
    }

    protected void uploadFile(String filePath,String tag) {
        this.tag = tag;
        showProgressDialog(null,"正在上传");
        PutObjectRequest put = new PutObjectRequest(AppContext.OSS_BUCKET, CommonUtils.getAndroidId(this) + System.currentTimeMillis(), filePath);
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                mHandler.sendEmptyMessage(HANDLER_CODE1);
                OssData data = new OssData();
                data.setRequest(request);
                data.setResult(result);

                Message msg = new Message();
                msg.what = HANDLER_CODE2;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                mHandler.sendEmptyMessage(HANDLER_CODE1);
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                PicBaseActivity.this.onFailure(request,clientExcepion,serviceException);
            }
        });
    }

    @Override
    public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {

    }

    /**
     * 照相获取图片
     */
    protected void selectPicFromCamera(String tag) {
        this.tag = tag;
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = CommonUtils.getTempUri(PictureUtils.instance().getUriPath(), this);
        if (uri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CODE_IMAGE_CAPTURE);
        } else {
            showToast("调取相机失败");
        }
    }

    /**
     * 从图库获取图片
     */
    protected void selectPicFromLocal(String tag) {
        this.tag = tag;
        Intent intent2 =new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent2, SELECT_PIC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK != resultCode) {
            return;
        }
        switch(requestCode) {
            case SELECT_PIC:
                if(data == null) {
                    showToast("获取图片失败");
                    return;
                }
                Uri selectedImage = data.getData();
                String[] filePathColumns = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String picturePath = c.getString(columnIndex);
                uploadFile(picturePath,tag);
                break;

            case CODE_IMAGE_CAPTURE:
                if(data == null) {
                    try {
                        String caputrePicturePath = PictureUtils.instance().getUriPath();
                        System.out.println("path:"+caputrePicturePath);
                        String resultpath = PictureUtils.instance().compressFileToFile(caputrePicturePath);
                        uploadFile(resultpath,tag);
                    } catch (Exception e) {
                        // TODO: handle exception
                        showToast("获取头像失败");
                        e.printStackTrace();
                    }

                } else {
                    Uri uri = data.getData();
                    if(uri == null){
                        System.out.println("拍照测试，返回isnull");
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            Bitmap photo = (Bitmap) bundle.get("data");
                            String path = PictureUtils.instance().compressBitmapToFile(photo);
                            uploadFile(path,tag);
                        }
                    }else{
                        System.out.println(uri.toString());
                        String[] cameraKeys = { MediaStore.Images.Media.DATA };
                        Cursor cur = getContentResolver().query(uri, cameraKeys, null, null, null);
                        cur.moveToFirst();
                        int index = cur.getColumnIndex(cameraKeys[0]);
                        String filePath = cur.getString(index);
                        System.out.println("path:"+filePath);
                        String path = PictureUtils.instance().compressFileToFile(filePath);
                        System.out.println("最终路径："+path);
                        cur.close();
                        uploadFile(path,tag);
                    }
                }
                break;

            default:
                super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public class OssData {
        private PutObjectRequest request;
        private PutObjectResult result;

        public PutObjectRequest getRequest() {
            return request;
        }

        public void setRequest(PutObjectRequest request) {
            this.request = request;
        }

        public PutObjectResult getResult() {
            return result;
        }

        public void setResult(PutObjectResult result) {
            this.result = result;
        }
    }
}
