package cn.com.wechat.api.sendmsg;

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
 * 微信素材管理接口
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class WechatCustomAPI {
	private static WechatConfig config;
	private static WechatCustomAPI wechatCustomAPI = new WechatCustomAPI();
	
	private WechatCustomAPI() {
	}
	
	/**
	 * 获取实例
	 * @param config 微信配置对象
	 * @return 接口对象
	 */
	public static WechatCustomAPI getInstance(WechatConfig config) {
		WechatCustomAPI.config = config;
		
		return wechatCustomAPI;
	}
	
	/**
	 * 发送客服消息
	 * 用户与公众号有交互的48小时内，可以调用该接口主动发送消息给用户
	 * @return 是否发生成功
	 */
	public boolean customSend(String sendJson) {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(WechatCustomAPI.config.getUrlPostSendmsgapiCustomSend(accessToken));
			StringEntity reqEntity = new StringEntity(sendJson,"UTF-8"); 
			reqEntity.setContentType("application/json");
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (0 == result.getInt("errcode")) {
					return true;
				} else {
					System.out.println(result.toString());
					return false;
				}
			} else {
				System.out.println("http post请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("发送客户消息失败");
			e.printStackTrace();
		}
		return false;
	}
}
