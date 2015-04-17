package cn.com.wechat.test;

import cn.com.wechat.api.sendmsg.WechatCustomAPI;
import cn.com.wechat.config.WechatConfig;

public class SendmsgAPITest {
	private static WechatConfig config = WechatConfig.getInstance("./config.xml");
	private static WechatCustomAPI customApi = WechatCustomAPI.getInstance(config);
	
	public static void main(String[] args) {
		customSend();
	}
	
	public static void customSend() {
		String sendJson = "{"+
							"    \"touser\":\"oNqTTt4HeRKo8RoI3TwbyuymdE1I\","+
							"    \"msgtype\":\"text\","+
							"    \"text\":"+
							"    {"+
							"         \"content\":\"Hello World\""+
							"    }"+
							"}";
		customApi.customSend(sendJson);
	}
}
