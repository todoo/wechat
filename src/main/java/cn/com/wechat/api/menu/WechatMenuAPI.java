package cn.com.wechat.api.menu;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import cn.com.wechat.api.base.WechatBaseAPI;
import cn.com.wechat.config.WechatConfig;

/**
 * 微信自定义菜单接口
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class WechatMenuAPI {
	private static WechatConfig config;
	private static WechatMenuAPI wechatMenuAPI = new WechatMenuAPI();
	
	private WechatMenuAPI() {
	}
	
	/**
	 * 获取实例
	 * @param config 微信配置对象
	 * @return 接口对象
	 */
	public static WechatMenuAPI getInstance(WechatConfig config) {
		WechatMenuAPI.config = config;
		
		return wechatMenuAPI;
	}
	
	/**
	 * 创建自定义菜单
	 * @param menuJson 菜单json字符串，参考微信公众平台文档说明的格式
	 * @return 是否创建成功
	 */
	public boolean createMenu(JSONObject menuJson) {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(config.getUrlPostMenuapiCreateMenu(accessToken));
			
			StringEntity reqEntity = new StringEntity(menuJson.toString(), "UTF-8");
			request.setEntity(reqEntity);
			
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (result.has("errcode")) {
					int errcode = result.getInt("errcode");
					if (errcode != 0) {
						System.out.println(result.toString());
						return false;
					}
					
					return true;
				}
				return false;
			} else {
				System.out.println("http post请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("创建自定义菜单失败");
			e.printStackTrace();
		}
		return false;
	}
}
