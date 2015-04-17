package cn.com.wechat.api.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.com.wechat.config.WechatConfig;

/**
 * 微信基础接口
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class WechatBaseAPI {
	private static WechatConfig config;
	private static WechatBaseAPI wechatBaseAPI = new WechatBaseAPI();
	
	private static String accessToken = "";
	private static Long accessTokenDeadline = Long.MIN_VALUE;
	
	private WechatBaseAPI() {
	}
	
	/**
	 * 获取实例
	 * @param config 微信配置对象
	 * @return 接口对象
	 */
	public static WechatBaseAPI getInstance(WechatConfig config) {
		WechatBaseAPI.config = config;
		
		return wechatBaseAPI;
	}
	
	/**
	 * 获取基础access_token
	 * @return 成功：access_token字符串	失败：null
	 */
	public String getAccessToken() {
		try {
			//到期时间推前30秒
			if (WechatBaseAPI.accessToken.length()>0 && WechatBaseAPI.accessTokenDeadline>(System.currentTimeMillis()-30000)) {
				return WechatBaseAPI.accessToken;
			}
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(WechatBaseAPI.config.getUrlGetBaseapiAccessToken());  
			HttpResponse response = httpclient.execute(request); 
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (result.has("access_token")) {
					WechatBaseAPI.accessToken = result.getString("access_token");
					WechatBaseAPI.accessTokenDeadline = System.currentTimeMillis() + ((Integer)result.get("expires_in"))*1000;
					return WechatBaseAPI.accessToken;
				} else {
					System.out.println(result.get("errcode").toString()+ ":" + result.getString("errmsg"));
					throw new Exception();
				}
			} else {
				System.out.println("http请求返回失败:status=" + response.getStatusLine().getStatusCode());
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("获取access_token异常");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取微信服务器IP地址
	 * @return 
	 * 成功：ip列表字符串，"["127.0.0.1","127.0.0.1",...]"
	 * 失败：null
	 */
	public String getCallbackIP() {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			String access_token = this.getAccessToken();
			if (access_token == null) {
				throw new Exception();
			}
			HttpGet request = new HttpGet(WechatBaseAPI.config.getUrlGetBaseapiCallbackIp(access_token));  
			HttpResponse response = httpclient.execute(request); 
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (result.has("ip_list")) {
					JSONArray ipList = result.getJSONArray("ip_list");
					String ipListStr = ipList.toString();
					return ipListStr;
				} else {
					System.out.println(result.get("errcode").toString()+ ":" + result.getString("errmsg"));
					throw new Exception();
				}
			} else {
				System.out.println("http请求返回失败:status=" + response.getStatusLine().getStatusCode());
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("获取微信服务器IP地址异常");
			e.printStackTrace();
		}
		return null;
	}
}
