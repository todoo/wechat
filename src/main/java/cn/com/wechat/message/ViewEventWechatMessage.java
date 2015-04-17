package cn.com.wechat.message;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 微信view类型的自定义菜单点击事件消息
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class ViewEventWechatMessage extends EventWechatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2787443188313123440L;
	
	private String eventKey;
	
	public ViewEventWechatMessage() {
		this.msgType = WechatMessage.MSG_TYPE_EVENT_EVENT;
		this.event = WechatMessage.MSG_EVENT_TYPE_VIEW;
	}
	
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
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
			NodeList eventList = root.getElementsByTagName("Event");
			//this.event = eventList.item(0).getTextContent();
			if (!this.event.equalsIgnoreCase(eventList.item(0).getTextContent())) {
				throw new Exception();
			}
			NodeList eventKeyList = root.getElementsByTagName("EventKey");
			this.eventKey = eventKeyList.item(0).getTextContent();
			
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
