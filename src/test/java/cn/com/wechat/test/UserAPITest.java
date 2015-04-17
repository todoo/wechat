package cn.com.wechat.test;

import java.io.IOException;

import cn.com.wechat.api.media.WechatMediaAPI;
import cn.com.wechat.api.user.WechatUserAPI;
import cn.com.wechat.config.WechatConfig;

public class UserAPITest {
	private static WechatConfig config = WechatConfig.getInstance("./config.xml");
	private static WechatUserAPI userAPI = WechatUserAPI.getInstance(config);
	
	public static void main(String[] args) throws IOException {
		getUserInfo();
	}
	
	public static void getUserInfo() {
		String openID = "oNqTTt4HeRKo8RoI3TwbyuymdE1I";
		userAPI.getUserInfo(openID, WechatUserAPI.LANG_ZHCN);
	}
}
