package cn.com.wechat.api.account;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import cn.com.wechat.api.base.WechatBaseAPI;
import cn.com.wechat.config.WechatConfig;

/**
 * 微信帐号管理接口
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class WechatAccountAPI {
	private static WechatConfig config;
	private static WechatAccountAPI wechatAccountAPI = new WechatAccountAPI();
	
	private WechatAccountAPI() {
	}
	
	/**
	 * 获取实例
	 * @param config 微信配置对象
	 * @return 接口对象
	 */
	public static WechatAccountAPI getInstance(WechatConfig config) {
		WechatAccountAPI.config = config;
		
		return wechatAccountAPI;
	}
	
	/**
	 * 创建二维码ticket
	 * @param postJson 临时二维码或永久二维码格式不同,可由类SceneQrcodePostData生成
	 * @return 成功：{"ticket":"ticket","expire_seconds":60,"url":"url"}  失败:null
	 */
	public JSONObject createQrcode(JSONObject postJson) {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(config.getUrlPostAccountapiCreateQrcode(accessToken));
			
			StringEntity reqEntity = new StringEntity(postJson.toString(), "UTF-8");
			request.setEntity(reqEntity);
			
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (result.has("errcode")) {
					System.out.println(result.toString());
					return null;
				}
				return result;
			} else {
				System.out.println("http post请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("创建场景二维码失败");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过ticket换取二维码输入流
	 * @param ticket createQrcode获取的二维码ticket
	 * @return 成功：二维码输入流，可以生成二维码图片，jpg格式	失败：null
	 */
	public InputStream getQrcode(String ticket) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(config.getUrlGetAccountapiGetQrcode(ticket));  
			HttpResponse response = httpclient.execute(request); 
			if (response.getStatusLine().getStatusCode()==200) { 
				
				return response.getEntity().getContent(); 
			} else {
				System.out.println("http get请求返回失败:status=" + response.getStatusLine().getStatusCode());
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("获取场景二维码图片异常");
			e.printStackTrace();
		}
		return null;
	}
}
