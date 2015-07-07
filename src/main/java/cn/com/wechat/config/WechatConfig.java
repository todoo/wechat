package cn.com.wechat.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 微信配置类
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class WechatConfig {
	private String token;
	private String encodingAESKey;
	private String appID;
	private String appSecret;
	private String serverRootUrl;
	
	private String urlGetBaseapiAccessToken;
	private String urlGetBaseapiCallbackIp;
	
	private String urlPostSendmsgapiCustomSend;
	
	private String urlPostMediaapiUploadTempMedia;
	private String urlGetMediaapiGetTempMedia;
	private String urlPostMediaapiUploadMedia;
	private String urlPostMediaapiGetMedia;
	private String urlGetMediaapiGetMediaCount;
	private String urlPostMediaapiDeleteMedia;
	
	private String urlGetUserapiGetUserInfo;
	private String urlGetUserapiGetAccessToken;
	
	private String urlPostMenuapiCreateMenu;
	
	private String urlPostAccountapiCreateQrcode;
	private String urlGetAccountapiGetQrcode;
	
	private String urlGetJsapiGetJsTicket;
	
	private static String XML_CONFIG_FILEPATH = "";
	private static WechatConfig CONFIG = new WechatConfig();
	
	private WechatConfig() {
		
	}
	
	/**
	 * 获取配置实例
	 * @param xmlConfFilePath 配置文件路径
	 * @return 配置对象
	 */
	public static WechatConfig getInstance(String xmlConfFilePath) {
		try {
			if (!XML_CONFIG_FILEPATH.equals(xmlConfFilePath)) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(xmlConfFilePath);
				
				Element root = document.getDocumentElement();
				NodeList baseList = root.getElementsByTagName("base");
				
				//base
				Element base = (Element) baseList.item(0);
				NodeList token = base.getElementsByTagName("token");
				WechatConfig.CONFIG.token = token.item(0).getTextContent();
				
				NodeList encodingAESKey = base.getElementsByTagName("encodingAESKey");
				WechatConfig.CONFIG.encodingAESKey = encodingAESKey.item(0).getTextContent();
				
				NodeList appID = base.getElementsByTagName("appID");
				WechatConfig.CONFIG.appID = appID.item(0).getTextContent();
				
				NodeList appSecret = base.getElementsByTagName("appSecret");
				WechatConfig.CONFIG.appSecret = appSecret.item(0).getTextContent();

				NodeList serverRootUrl = base.getElementsByTagName("serverRootUrl");
				WechatConfig.CONFIG.serverRootUrl = serverRootUrl.item(0).getTextContent();
				
				//baseAPI
				NodeList baseAPIList = root.getElementsByTagName("baseAPI");
				Element baseAPI = (Element) baseAPIList.item(0);
				
				NodeList accessTokenList = baseAPI.getElementsByTagName("accessToken");
				WechatConfig.CONFIG.urlGetBaseapiAccessToken = accessTokenList.item(0).getTextContent() + "&appid="+WechatConfig.CONFIG.appID+"&secret="+WechatConfig.CONFIG.appSecret;
				
				NodeList callbackIPList = baseAPI.getElementsByTagName("callbackIP");
				WechatConfig.CONFIG.urlGetBaseapiCallbackIp = callbackIPList.item(0).getTextContent();
				
				//sendmsgAPI
				NodeList sendmsgAPIList = root.getElementsByTagName("sendMsg");
				Element sendmsgAPI = (Element) sendmsgAPIList.item(0);
				
				NodeList customSendList = sendmsgAPI.getElementsByTagName("customSend");
				WechatConfig.CONFIG.urlPostSendmsgapiCustomSend = customSendList.item(0).getTextContent();
				
				//mediaAPI
				NodeList mediaAPIList = root.getElementsByTagName("media");
				Element mediaAPI = (Element) mediaAPIList.item(0);
				
				NodeList uploadTempMediaList = mediaAPI.getElementsByTagName("uploadTempMedia");
				WechatConfig.CONFIG.urlPostMediaapiUploadTempMedia = uploadTempMediaList.item(0).getTextContent();
				
				NodeList getTempMediaList = mediaAPI.getElementsByTagName("getTempMedia");
				WechatConfig.CONFIG.urlGetMediaapiGetTempMedia = getTempMediaList.item(0).getTextContent();
				
				NodeList uploadMediaList = mediaAPI.getElementsByTagName("uploadMedia");
				WechatConfig.CONFIG.urlPostMediaapiUploadMedia = uploadMediaList.item(0).getTextContent();
				
				NodeList getMediaList = mediaAPI.getElementsByTagName("getMedia");
				WechatConfig.CONFIG.urlPostMediaapiGetMedia = getMediaList.item(0).getTextContent();
				
				NodeList getMediaCountList = mediaAPI.getElementsByTagName("getMediaCount");
				WechatConfig.CONFIG.urlGetMediaapiGetMediaCount = getMediaCountList.item(0).getTextContent();
				
				NodeList delMediaCountList = mediaAPI.getElementsByTagName("deleteMedia");
				WechatConfig.CONFIG.urlPostMediaapiDeleteMedia = delMediaCountList.item(0).getTextContent();
				
				//userAPI
				NodeList userAPIList = root.getElementsByTagName("user");
				Element userAPI = (Element) userAPIList.item(0);
				
				NodeList getUserInfoList = userAPI.getElementsByTagName("getUserInfo");
				WechatConfig.CONFIG.urlGetUserapiGetUserInfo = getUserInfoList.item(0).getTextContent();

				NodeList getAccessTokenList = userAPI.getElementsByTagName("getAccessToken");
				WechatConfig.CONFIG.urlGetUserapiGetAccessToken = getAccessTokenList.item(0).getTextContent();
				
				//menuAPI
				NodeList menuAPIList = root.getElementsByTagName("menu");
				Element menuAPI = (Element) menuAPIList.item(0);
				
				NodeList createMenuList = menuAPI.getElementsByTagName("createMenu");
				WechatConfig.CONFIG.urlPostMenuapiCreateMenu = createMenuList.item(0).getTextContent();
				
				//accountAPI
				NodeList accountAPIList = root.getElementsByTagName("account");
				Element accountAPI = (Element) accountAPIList.item(0);
				
				NodeList createQrcodeList = accountAPI.getElementsByTagName("createQrcode");
				WechatConfig.CONFIG.urlPostAccountapiCreateQrcode = createQrcodeList.item(0).getTextContent();
				
				NodeList getQrcodeList = accountAPI.getElementsByTagName("getQrcode");
				WechatConfig.CONFIG.urlGetAccountapiGetQrcode = getQrcodeList.item(0).getTextContent();
				
				//jsAPI
				NodeList jsAPIList = root.getElementsByTagName("js");
				Element jsAPI = (Element) jsAPIList.item(0);
				
				NodeList getJsTicketList = jsAPI.getElementsByTagName("getJsTicket");
				WechatConfig.CONFIG.urlGetJsapiGetJsTicket = getJsTicketList.item(0).getTextContent();
				
				XML_CONFIG_FILEPATH = xmlConfFilePath;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return WechatConfig.CONFIG;
	}
	
	public String getToken() {
		return token;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}

	public String getAppID() {
		return appID;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getServerRootUrl() {
		return serverRootUrl;
	}

	public String getUrlGetBaseapiAccessToken() {
		return urlGetBaseapiAccessToken;
	}

	public String getUrlGetBaseapiCallbackIp(String accessToken) {
		return urlGetBaseapiCallbackIp + "?access_token=" + accessToken;
	}

	public String getUrlPostSendmsgapiCustomSend(String accessToken) {
		return urlPostSendmsgapiCustomSend + "?access_token=" + accessToken;
	}

	public String getUrlPostMediaapiUploadTempMedia(String accessToken, String type) {
		return urlPostMediaapiUploadTempMedia + "?access_token=" + accessToken + "&type=" + type;
	}

	public String getUrlGetMediaapiGetTempMedia(String accessToken, String mediaID) {
		return urlGetMediaapiGetTempMedia + "?access_token=" + accessToken + "&media_id=" + mediaID;
	}

	public String getUrlPostMediaapiUploadMedia(String accessToken, String type) {
		return urlPostMediaapiUploadMedia + "?access_token=" + accessToken + "&type=" + type;
	}

	public String getUrlPostMediaapiGetMedia(String accessToken) {
		return urlPostMediaapiGetMedia + "?access_token=" + accessToken;
	}

	public String getUrlGetMediaapiGetMediaCount(String accessToken) {
		return urlGetMediaapiGetMediaCount + "?access_token=" + accessToken;
	}

	public String getUrlPostMediaapiDeleteMedia(String accessToken) {
		return urlPostMediaapiDeleteMedia + "?access_token=" + accessToken;
	}

	public String getUrlGetUserapiGetUserInfo(String accessToken, String openid, String lang) {
		return urlGetUserapiGetUserInfo+ "?access_token=" + accessToken + "&openid=" + openid + "&lang=" + lang;
	}

	public String getUrlGetUserapiGetAccessToken(String code) {
		return urlGetUserapiGetAccessToken + "&appid=" + this.appID + "&secret=" + this.appSecret + "&code=" + code;
	}

	public String getUrlPostMenuapiCreateMenu(String accessToken) {
		return urlPostMenuapiCreateMenu + "?access_token=" + accessToken;
	}

	public String getUrlPostAccountapiCreateQrcode(String accessToken) {
		return urlPostAccountapiCreateQrcode + "?access_token=" + accessToken;
	}

	public String getUrlGetAccountapiGetQrcode(String ticket) {
		try {
			return urlGetAccountapiGetQrcode + "?ticket=" + URLEncoder.encode(ticket, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlGetAccountapiGetQrcode + "?ticket=" + ticket;
	}

	public String getUrlGetJsapiGetJsTicket(String accessToken) {
		return urlGetJsapiGetJsTicket + "&access_token=" + accessToken;
	}	
	
	
}
