package cn.com.wechat.message;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 微信小视屏消息
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class ShortvideoNormalWechatMessage extends NormalWecahtMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5078965317792514392L;
	
	private String mediaID;
	private String thumbMediaID;
	
	public ShortvideoNormalWechatMessage() {
		this.msgType = WechatMessage.MSG_TYPE_NORMAL_SHORTVIDEO;
	}
	
	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	public String getThumbMediaID() {
		return thumbMediaID;
	}

	public void setThumbMediaID(String thumbMediaID) {
		this.thumbMediaID = thumbMediaID;
	}

	@Override
	public boolean setReceiveXmlData(String xmlData) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmlData);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			
			Element root = document.getDocumentElement();
			//获取文本消息
			NodeList toUserNameList = root.getElementsByTagName("ToUserName");
			this.toUserName = toUserNameList.item(0).getTextContent();
			NodeList fromUserNameList = root.getElementsByTagName("FromUserName");
			this.fromUserName = fromUserNameList.item(0).getTextContent();
			NodeList createTimeList = root.getElementsByTagName("CreateTime");
			this.createTime = Integer.parseInt(createTimeList.item(0).getTextContent());
			NodeList msgTypeList = root.getElementsByTagName("MsgType");
			//this.msgType = msgTypeList.item(0).getTextContent();
			if (!this.msgType.equalsIgnoreCase(msgTypeList.item(0).getTextContent())) {
				throw new Exception();
			}
			NodeList mediaIDList = root.getElementsByTagName("MediaId");
			this.mediaID = mediaIDList.item(0).getTextContent();
			NodeList thumbMediaIDList = root.getElementsByTagName("ThumbMediaId");
			this.thumbMediaID = thumbMediaIDList.item(0).getTextContent();
			NodeList msgIDList = root.getElementsByTagName("MsgId");
			this.msgID = msgIDList.item(0).getTextContent();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			//xml数据解析有误，格式不正确
			return false;
		}
	}

	@Override
	public String getReplyXmlData() {
		// TODO Auto-generated method stub
		return null;
	}

}
