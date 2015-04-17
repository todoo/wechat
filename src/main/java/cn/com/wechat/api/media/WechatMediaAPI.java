package cn.com.wechat.api.media;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
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
public class WechatMediaAPI {
	public static final String MEDIA_TYPE_IMAGE = "image";
	public static final String MEDIA_TYPE_VOICE = "voice";
	public static final String MEDIA_TYPE_THUMB = "thumb";
	
	private static WechatConfig config;
	private static WechatMediaAPI wechatMediaAPI = new WechatMediaAPI();
	
	private WechatMediaAPI() {
	}
	
	/**
	 * 获取实例
	 * @param config 微信配置对象
	 * @return 接口对象
	 */
	public static WechatMediaAPI getInstance(WechatConfig config) {
		WechatMediaAPI.config = config;
		
		return wechatMediaAPI;
	}
	
	/**
	 * 上传临时素材
	 * @param type 素材类型
	 * @param file 素材文件
	 * @return 成功：media_id; 失败：null
	 */
	public String uploadTempMedia(String type, File file) {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(WechatMediaAPI.config.getUrlPostMediaapiUploadTempMedia(accessToken, type));
			MultipartEntity multipartEntity = new MultipartEntity();
			FileBody fileBody = new FileBody(file);
			FormBodyPart formBodyPart = new FormBodyPart("media",fileBody);
			multipartEntity.addPart(formBodyPart);
			request.setEntity(multipartEntity);
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (result.has("errcode")) {
					System.out.println(result.toString());
					return null;
				}
				return result.getString("media_id");
			} else {
				System.out.println("http post请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("上传临时素材失败");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 上传临时素材
	 * @param type 素材类型
	 * @param mediaStream 素材输入流
	 * @param filename 输入流文件名
	 * @return 成功：media_id; 失败：null
	 */
	public String uploadTempMedia(String type, InputStream mediaStream, String filename) {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(WechatMediaAPI.config.getUrlPostMediaapiUploadTempMedia(accessToken, type));
			MultipartEntity multipartEntity = new MultipartEntity();
			InputStreamBody mediaStreamBody = new InputStreamBody(mediaStream, filename);
			FormBodyPart formBodyPart = new FormBodyPart("media",mediaStreamBody);
			multipartEntity.addPart(formBodyPart);
			request.setEntity(multipartEntity);
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (result.has("errcode")) {
					System.out.println(result.toString());
					return null;
				}
				return result.getString("media_id");
			} else {
				System.out.println("http post请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("上传临时素材失败");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取临时素材
	 * @param mediaID
	 * @param filename 返回媒体文件名
	 * @return 成功：媒体输入流；失败：null
	 */
	public InputStream getTempMedia(String mediaID, StringBuffer filename) {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(config.getUrlGetMediaapiGetTempMedia(accessToken, mediaID));
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				if (response.getHeaders("content-type")[0].getValue().equalsIgnoreCase("text/plain")) {
					String strResult = EntityUtils.toString(response.getEntity());
					JSONObject result = new JSONObject(strResult); 
					if (result.has("errcode")) {
						System.out.println(result.toString());
						return null;
					}
					return null;
				}
				
				String disposition = response.getHeaders("Content-disposition")[0].getValue();
				Pattern pattern = Pattern.compile("^attachment; filename=\"(.*)\"$");
				Matcher matcher = pattern.matcher(disposition);
				if (matcher.matches()) {
					filename.append(matcher.group(1));
				} else {
					filename.append("");
				}
				HttpEntity entity = response.getEntity();
				return entity.getContent();
			} else {
				System.out.println("http get请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("获取临时素材失败");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 新增永久素材
	 * @param type 素材类型
	 * @param file 素材文件
	 * @return 成功：media_id; 失败：null
	 */
	public String uploadMedia(String type, File file) {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(WechatMediaAPI.config.getUrlPostMediaapiUploadMedia(accessToken, type));
			MultipartEntity multipartEntity = new MultipartEntity();
			FileBody fileBody = new FileBody(file);
			FormBodyPart formBodyPart = new FormBodyPart("media",fileBody);
			multipartEntity.addPart(formBodyPart);
			request.setEntity(multipartEntity);
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (result.has("errcode")) {
					System.out.println(result.toString());
					return null;
				}
				return result.getString("media_id");
			} else {
				System.out.println("http post请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("上传永久素材失败");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取永久返回流的素材
	 * @param mediaID
	 * @return 成功：媒体字节数组；失败：null
	 */
	public byte[] getStreamMedia(String mediaID) {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(config.getUrlPostMediaapiGetMedia(accessToken));
			
			StringEntity reqEntity = new StringEntity("{\"media_id\":\"" + mediaID + "\"}","UTF-8"); 
			reqEntity.setContentType("application/json");
			request.setEntity(reqEntity);
			
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				InputStream in = response.getEntity().getContent();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				
				byte[] buffer = new byte[1024];
				int len;
				while((len=in.read(buffer)) > 0) {
					out.write(buffer,0,len);
				}
				//分别以string和byte数组的形式获取返回值
				String strResult = out.toString("UTF-8");
				byte[] bytes = out.toByteArray();
				
				try {
					//返回值为json格式字符串
					JSONObject result = new JSONObject(strResult); 
					if (result.has("errcode")) {
						//错误返回
						System.out.println(result.toString());
						return null;
					}
					//图文或视频返回，此函数忽略
					return null;
				} catch (Exception e) {
					
				}
				//返回的为媒体流
				out.close();
				in.close();
				
				return bytes;
			} else {
				System.out.println("http get请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("获取临时素材失败");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取永久媒体数目
	 * @return 
	 * {
  	 *  "voice_count":COUNT,
     *  "video_count":COUNT,
     *  "image_count":COUNT,
     *  "news_count":COUNT
     * }
	 */
	public JSONObject getMediaCount() {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(config.getUrlGetMediaapiGetMediaCount(accessToken));  
			HttpResponse response = httpclient.execute(request); 
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (result.has("errcode")) {
					//错误返回
					System.out.println(result.toString());
					return null;
				}
				return result;
			} else {
				System.out.println("http get请求返回失败:status=" + response.getStatusLine().getStatusCode());
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("获取媒体数目异常");
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteMedia(String mediaID) {
		try {
			//获取accesstoken
			WechatBaseAPI baseAPI = WechatBaseAPI.getInstance(config);
			String accessToken = baseAPI.getAccessToken();
			if (accessToken == null) {
				throw new Exception();
			}
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost request = new HttpPost(WechatMediaAPI.config.getUrlPostMediaapiDeleteMedia(accessToken));
			
			StringEntity reqEntity = new StringEntity("{\"media_id\":\"" + mediaID + "\"}","UTF-8"); 
			reqEntity.setContentType("application/json");
			request.setEntity(reqEntity);
			
			HttpResponse response = httpclient.execute(request);  
			if (response.getStatusLine().getStatusCode()==200) { 
				String strResult = EntityUtils.toString(response.getEntity()); 
				JSONObject result = new JSONObject(strResult); 
				if (result.has("errcode")) {
					int errcode = result.getInt("errcode");
					if (errcode == 0) {
						return true;
					} else {
						System.out.println(result.toString());
						return false;
					}
				}
				return false;
			} else {
				System.out.println("http post请求失败:status=" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.out.println("删除永久素材失败");
			e.printStackTrace();
		}
		return false;
	}
}
