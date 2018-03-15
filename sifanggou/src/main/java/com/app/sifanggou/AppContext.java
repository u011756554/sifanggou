package com.app.sifanggou;

import java.io.File;

public class AppContext {
	public static final String TAG = "sifanggou";	//文件名字
	
	//服务器
	public static final String SERVER = "http://120.77.23.139/caipiao/api";
	//通讯key
	public static final String KEY = "RThXxpuR5n9p7CYSTWFy5awyBYnXeBdj";
	
	public static final String PACKGE_NAME = "com.app.sifanggou";
	//Log 管理信息
	public static final String LOG_NET = "net";
	public static final String LOG_MAP = "map";
	public static final String LOG_PUSH = "push";
	public static final String LOG_ERROR = "error";
	public static final String LOG_MAIN = "main";
	
	//缓存数据
	public static final String APP_NAME = "sifanggou";	//文件名字
	public static final String FILE_SAVE_ROOT_DIRECTORY = File.separator + APP_NAME + File.separator;
	public static final int PICTURE_SIZE = 500;	//暂且没用到，控制压缩图片大小，单位kb
	public static final String SAVE_PATH = "/"+APP_NAME;	//压缩文件保存路径
	public static final String TAKEPICTURE_PATH = "/"+APP_NAME+"/camera";	//通过相机拍照图片保存文件夹路径
	public static final String TAKEPICTURE_FILE = "temp.jgp";	//通过相机拍照图片保存文件名称	
	
	//数据KEY
	public static final String USER_KEY = "login_user_key";
	public static final String USER_ACCOUNT = "USER_ACCOUNT";
	public static final String USER_PWD = "USER_PWD";
	public static final String USER_PWD_NOCODE = "USER_PWD_NOCODE";
	public static final String USER_LOGIN = "login_user_login";
	public static final String USER_ID = "login_user_id";
	public static final String URL_SUCCESSCODE = "1";
	
	public static final long GUANGGAOTIME = 6000; //轮播广告停留时间
	public static final int ITEM_NUM = 10; //每页数量，最大32

	public static final String OSS_ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com"; //阿里云OSS域名
//	public static final String OSS_ACCESSKEYID = "OSS_AccessKeyId"; //阿里云OSS域名
//	public static final String OSS_SECRETKEYID = "OSS_SecretKeyId"; //阿里云OSS域名
	public static final String OSS_ACCESSKEYID = "LTAIyr4lwzs6vjZD"; //阿里云OSS域名
	public static final String OSS_SECRETKEYID = "MlQnRcJ4A6J9iKGmYTUIxQo0wSxpAr"; //阿里云OSS域名
	public static final String OSS_SECURITYTOKEN = "OSS_SecurityToken"; //阿里云OSS域名

	public static final String OSS_BUCKET = "sifanggou-oss"; //阿里云BUCKET

	public static final int PAGE = 0; //页码
	public static final int PAGE_SIZE= 10; //分页数据

	public static final String URL_XIEYI = "http://admin.4fango.com/12.html";
	public static final String URL_HUODONG = "http://admin.4fango.com/4f.html";
	public static final String URL_HEAD = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2334584121,1324775889&fm=27&gp=0.jpg";
	public static final String URL_CATEGORY = "https://weidian.com/s/1322195654?wfr=c&ifr=shopdetail";

}
