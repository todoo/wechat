package cn.com.wechat.test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.wechat.api.media.WechatMediaAPI;
import cn.com.wechat.api.menu.WechatMenuAPI;
import cn.com.wechat.config.WechatConfig;

public class MenuAPITest {
	private static WechatConfig config = WechatConfig.getInstance("./config.xml");
	private static WechatMenuAPI menuAPI = WechatMenuAPI.getInstance(config);
	
	public static void main(String[] args) throws IOException, JSONException {
		createMenu();
	}
	
	public static void createMenu() throws IOException, JSONException {
		FileInputStream in = new FileInputStream("./webapp/context/menu.json");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = in.read(bytes)) > 0) {
			out.write(bytes, 0, len);
		}
		
		String menuJson = out.toString();
		out.close();
		in.close();
		
		menuJson = menuJson.replace(" ", "");
		menuJson = menuJson.substring(menuJson.indexOf('{'));
		System.out.println((int)menuJson.charAt(0));
		JSONObject json = new JSONObject(menuJson);
		
		boolean r = menuAPI.createMenu(json);
		System.out.println(r);
	}
}
