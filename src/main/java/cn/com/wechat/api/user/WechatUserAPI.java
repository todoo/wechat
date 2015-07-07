package cn.com.wechat.api.user;

import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import cn.com.wechat.api.base.WechatBaseAPI;
import cn.com.wechat.config.WechatConfig;
import cn.com.wechat.pojo.WechatUserInfo;

/**
 * 微信用户管理接口
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class WechatUserAPI {
	/**
	 * 语言：简体中文
	 */
	public static final String LANG_ZHCN = "zh_CN";
	/**
	 * 语言：繁体中文
	 */
	public static final String LANG_ZHTW = "zh_TW";
	/**
	 * 语言：英文
	 */
	public static final String LANG_EN = "en ";
	
	private static WechatConfig config;
	private static WechatUserAPI wechatUserAPI = new WechatUserAPI();
	
	private WechatUserAPI() {
	}
	
	/**
	 * 获取实例
	 * @param config 微信配置对象
	 * @return 接口对象
	 */
	public static WechatUserAPI getInstance(WechatConfig config) {
		WechatUserAPI.config = config;
		
		return wechatUserAPI;
	}
	
	/**
	 * 根据用户openid获取用户基本信息
	 * @param openID 用户openid
	 * @param lang 语言
	 * @return 成功：用户信息对象；失败：null
	 */
	public WechatUserInfo getUserInfo(String openID, String lang) {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(config.getUrlGetUserapiGetUserInfo(accessToken, openID, lang));
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity(),"UTF-8");
				JSONObject result = new JSONObject(strResult); 
				if (result.has("errcode")) {
					System.out.println(result.toString());
					return null;
				}
				
				WechatUserInfo user = new WechatUserInfo();
				user.setSubscribe(result.has("subscribe")?result.getInt("subscribe"):-1);
				user.setOpenid(result.has("openid")?result.getString("openid"):null);
				user.setNickname(result.has("nickname")?result.getString("nickname"):null);
				user.setSex(result.has("sex")?result.getInt("sex"):-1);
				user.setCity(result.has("city")?result.getString("city"):null);
				user.setCountry(result.has("country")?result.getString("country"):null);
				user.setProvince(result.has("province")?result.getString("province"):null);
				user.setLanguage(result.has("language")?result.getString("language"):null);
				user.setHeadimgurl(result.has("headimgurl")?result.getString("headimgurl"):null);
				user.setSubscribeTime(result.has("subscribe_time")?new Date(result.getInt("subscribe_time")):null);
				user.setUnionid(result.has("unionid")?result.getString("unionid"):null);
				
				return user;
			} else {
				System.out.println("http get请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("获取用户信息失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取用户授权token
	 * @param code
	 * @return
		{
		   "access_token":"ACCESS_TOKEN",
		   "expires_in":7200,
		   "refresh_token":"REFRESH_TOKEN",
		   "openid":"OPENID",
		   "scope":"SCOPE",
		   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
		}
		失败：null
	 */
	public JSONObject getAccessToken(String code) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(config.getUrlGetUserapiGetAccessToken(code));
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity(),"UTF-8");
				JSONObject result = new JSONObject(strResult); 
				if (result.has("errcode")) {
					System.out.println(result.toString());
					return null;
				}
				
				return result;
			} else {
				System.out.println("http get请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("获取用户授权access_token失败");
			e.printStackTrace();
		}
		return null;
	}
}
