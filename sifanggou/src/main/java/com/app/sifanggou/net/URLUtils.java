package com.app.sifanggou.net;

public class URLUtils {

	private static final String SERVER = "http://119.23.9.134:8080/SiFangGou/api/"; 
	
	public static final String GETVERIFYCODE = SERVER + "business/GetVerifyCode";
		
	public static final String GETPROVINCECITYZONE = SERVER + "business/GetProvinceCityZone";
		
	public static final String GETCITYMARKET = SERVER + "business/GetCityMarket";

	public static final String GETALIYUNSTSTOKEN = SERVER + "business/GetAliyunStsToken";

	public static final String BUSINESSREGIST = SERVER + "business/BusinessRegist";

	public static final String GETAGENTLEVELINFO = SERVER + "business/GetAgentLevelInfo";

	public static final String BUSINESSLOGIN = SERVER + "business/BusinessLogin";

	public static final String BUSINESSCHANGEPASSWORD = SERVER + "business/BusinessChangePassword";

	public static final String GETBUSINESSCANALLOCATESHELFNUM = SERVER + "business/GetBusinessCanAllocateShelfNum";

	public static final String GETSHELFAMOUNT = SERVER + "business/GetShelfAmount";

	public static final String ADDBUSINESSBUYSHELFNUM = SERVER + "business/AddBusinessBuyShelfNum";

	public static final String GETCOMMODITYTYPELIST = SERVER + "business/GetCommodityTypeList";

    public static final String ADDBUSINESSCOMMODITY = SERVER + "business/AddBusinessCommodity";

	public static final String GETBUSINESSCOMMODITYINFO = SERVER + "business/GetBusinessCommodityInfo";

	public static final String BATCHUPDATECOMMODITYSHELFSTATUS = SERVER + "business/BatchUpdateCommodityShelfStatus";

	public static final String UPDATECOMMODITYPRICE = SERVER + "business/UpdateCommodityPrice";
}

