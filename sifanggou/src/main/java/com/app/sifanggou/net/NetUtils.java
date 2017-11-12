package com.app.sifanggou.net;

import com.app.sifanggou.net.httprunner.AddBusinessBuyShelfNumHttpRunner;
import com.app.sifanggou.net.httprunner.AddBusinessCommodityHttpRunner;
import com.app.sifanggou.net.httprunner.AddBusinessUrgentSellCommodityHttpRunner;
import com.app.sifanggou.net.httprunner.BatchUpdateCommodityShelfStatusHttpRunner;
import com.app.sifanggou.net.httprunner.BusinessChangePasswordHttpRunner;
import com.app.sifanggou.net.httprunner.BusinessLoginHttpRunner;
import com.app.sifanggou.net.httprunner.BusinessRegistHttpRunner;
import com.app.sifanggou.net.httprunner.GetAgentLevelInfoHttpRunner;
import com.app.sifanggou.net.httprunner.GetAliyunstsTokenHttpRunner;
import com.app.sifanggou.net.httprunner.GetBusinessCanAllocateShelfNumHttpRunner;
import com.app.sifanggou.net.httprunner.GetBusinessCommodityInfoHttpRunner;
import com.app.sifanggou.net.httprunner.GetBusinessUrgentSellCommoditHttpRunner;
import com.app.sifanggou.net.httprunner.GetCityMarketHttpRunner;
import com.app.sifanggou.net.httprunner.GetCommodityTypeListHttpRunner;
import com.app.sifanggou.net.httprunner.GetProvinceCityZoneHttpRunner;
import com.app.sifanggou.net.httprunner.GetShelfAmountHttpRunner;
import com.app.sifanggou.net.httprunner.GetVerifyCodeHttpRunner;
import com.app.sifanggou.net.httprunner.UpdateCommodityPriceHttpRunner;

public class NetUtils {
	
	public static void init() {
		final AndroidEventManager eventManager = AndroidEventManager.getInstance();
		eventManager.registerEventRunner(EventCode.HTTP_GETVERIFYCODE, new GetVerifyCodeHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_GETPROVINCECITYZONE, new GetProvinceCityZoneHttpRunner()); 
		eventManager.registerEventRunner(EventCode.HTTP_GETCITYMARKET, new GetCityMarketHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_GETALIYUNSTSTOKEN, new GetAliyunstsTokenHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_BUSINESSREGIST, new BusinessRegistHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_GETAGENTLEVELINFO, new GetAgentLevelInfoHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_BUSINESSLOGIN, new BusinessLoginHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_BUSINESSCHANGEPASSWORD, new BusinessChangePasswordHttpRunner());
        eventManager.registerEventRunner(EventCode.HTTP_GETBUSINESSCANALLOCATESHELFNUM, new GetBusinessCanAllocateShelfNumHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_GETSHELFAMOUNT, new GetShelfAmountHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_ADDBUSINESSBUYSHELFNUM, new AddBusinessBuyShelfNumHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_GETCOMMODITYTYPELIST, new GetCommodityTypeListHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_ADDBUSINESSCOMMODITY, new AddBusinessCommodityHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_GETBUSINESSCOMMODITYINFO, new GetBusinessCommodityInfoHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_BATCHUPDATECOMMODITYSHELFSTATUS, new BatchUpdateCommodityShelfStatusHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_UPDATECOMMODITYPRICE, new UpdateCommodityPriceHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_GETBUSINESSURGENTSELLCOMMODIT, new GetBusinessUrgentSellCommoditHttpRunner());
		eventManager.registerEventRunner(EventCode.HTTP_ADDBUSINESSURGENTSELLCOMMODITY, new AddBusinessUrgentSellCommodityHttpRunner());
	}
}


