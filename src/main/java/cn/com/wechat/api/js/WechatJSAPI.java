package cn.com.wechat.api.js;

import java.security.MessageDigest;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import cn.com.wechat.api.base.WechatBaseAPI;
import cn.com.wechat.config.WechatConfig;

/**
 * 微信js-sdk接口
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class WechatJSAPI {
	private static WechatConfig config;
	private static WechatJSAPI wechatJSAPI = new WechatJSAPI();
	
	private static String jsApiTicket = "";
	private static Long jsApiTicketDeadline = Long.MIN_VALUE;
	
	private WechatJSAPI() {
	}
	
	/**
	 * 获取实例
	 * @param config 微信配置对象
	 * @return 接口对象
	 */
	public static WechatJSAPI getInstance(WechatConfig config) {
		WechatJSAPI.config = config;
		
		return wechatJSAPI;
	}
	
	/**
	 * 获取js-sdk的ticket，用于生成签名
	 * @return 成功：ticket；失败：null
	 */
	public String getJsSdkTicket() {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			//到期时间推前30秒
			if (WechatJSAPI.jsApiTicket.length()>0 && WechatJSAPI.jsApiTicketDeadline>(System.currentTimeMillis()-30000)) {
				return WechatJSAPI.jsApiTicket;
			}
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(config.getUrlGetJsapiGetJsTicket(accessToken));  
			HttpResponse response = httpclient.execute(request); 
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (result.has("errcode")) {
					int errcode = result.getInt("errcode");
					if (errcode != 0) {
						System.out.println(result.toString());
						return null;
					}
					
					WechatJSAPI.jsApiTicket = result.getString("ticket");
					WechatJSAPI.jsApiTicketDeadline = System.currentTimeMillis() + ((Integer)result.get("expires_in"))*1000;
					return WechatJSAPI.jsApiTicket;
				}
			} else {
				System.out.println("http get请求返回失败:status=" + response.getStatusLine().getStatusCode());
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("获取js-sdk-ticket异常");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * JS-JDK签名算法函数
	 * @param noncestr 随机字符串
	 * @param jsApiTicket getJsSdkTicket返回的ticket
	 * @param timestamp 10位时间戳字符串
	 * @param url 调用js-sdk的完整url，包含查询参数，不包含#以及以后部分
	 * @return 成功：签名字符串；失败：null
	 */
	public String jsJdkSignature(String noncestr, String jsApiTicket, String timestamp, String url) {
		try {
			String[] str = {noncestr, jsApiTicket, timestamp, url};
			Arrays.sort(str);
			
			String bigStr = "jsapi_ticket=" + jsApiTicket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;

			//sha1签名
			String reSignature = SHA1Encode(bigStr);
			return reSignature;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//sha1加密   
    private String SHA1Encode(String sourceString) {  
        String resultString = null;  
        try {  
           resultString = new String(sourceString);  
           MessageDigest md = MessageDigest.getInstance("SHA-1");  
           resultString = byte2hexString(md.digest(resultString.getBytes()));  
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        return resultString;  
    }  
    
    private final String byte2hexString(byte[] bytes) {  
        StringBuffer buf = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            if (((int) bytes[i] & 0xff) < 0x10) {  
                buf.append("0");  
            }  
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));  
        }  
        return buf.toString().toLowerCase();  
    }  
}
