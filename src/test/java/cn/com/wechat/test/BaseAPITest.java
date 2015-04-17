package cn.com.wechat.test;

import cn.com.wechat.api.base.WechatBaseAPI;
import cn.com.wechat.config.WechatConfig;

public class BaseAPITest {
	public static void main(String[] args) {
		accessToken();
		//callbackIP();
	}
	
	public static void accessToken() {
		WechatConfig config = WechatConfig.getInstance("./config.xml");
		WechatBaseAPI baseApi = WechatBaseAPI.getInstance(config);
		String accessToken = baseApi.getAccessToken();
		System.out.println(accessToken);
		
		WechatConfig config1 = WechatConfig.getInstance("./config.xml");
		WechatBaseAPI baseApi1 = WechatBaseAPI.getInstance(config1);
		String accessToken1 = baseApi1.getAccessToken();
		System.out.println(accessToken1);
	}
	
	public static void callbackIP() {
		WechatConfig config = WechatConfig.getInstance("./config.xml");
		WechatBaseAPI baseApi = WechatBaseAPI.getInstance(config);
		baseApi.getCallbackIP();
	}
}
