package cn.com.wechat.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.wechat.api.account.WechatAccountAPI;
import cn.com.wechat.api.media.WechatMediaAPI;
import cn.com.wechat.config.WechatConfig;
import cn.com.wechat.pojo.SceneQrcodePostData;

public class AccountAPITest {
	private static WechatConfig config = WechatConfig.getInstance("./config.xml");
	private static WechatAccountAPI accountAPI = WechatAccountAPI.getInstance(config);
	
	public static void main(String[] args) throws IOException, JSONException {
		String ticket = createQrcode();
		getQrcode(ticket);	
	}
	
	public static String createQrcode() throws JSONException {
		SceneQrcodePostData post = new SceneQrcodePostData();
		post.setActionName(SceneQrcodePostData.ACTION_LIMIT_STR_QRCODE);
		post.setExpireSeconds(1800);
		post.setSceneID(new Long(1));
		post.setSceneStr("12");
		
		//{"expire_seconds":60,"ticket":"gQHT7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0pFd0hkY3Zsb1ZUWXctVGJpR0x5AAIEnyQuVQMEPAAAAA==","url":"http://weixin.qq.com/q/JEwHdcvloVTYw-TbiGLy"}
		JSONObject ticket = accountAPI.createQrcode(post.getJSONObject());
		System.out.println(ticket.toString());
		return ticket.getString("ticket");
	}
	
	public static void getQrcode(String ticket) throws IOException {
		//String ticket = "gQGU7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2JFeE5pMXJsX1ZTQTVMdzN3bUx5AAIEIhUvVQMEPAAAAA==";
		InputStream in = accountAPI.getQrcode(ticket);
		FileOutputStream out = new FileOutputStream("./limit_str.jpg");
		
		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = in.read(bytes)) > 0) {
			out.write(bytes, 0, len);
		}
		
		out.flush();
		out.close();
		in.close();
	}
}
