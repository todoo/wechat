package cn.com.wechat.message.factory;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import cn.com.wechat.message.ClickEventWechatMessage;
import cn.com.wechat.message.LocationEventWechatMessage;
import cn.com.wechat.message.ScanEventWechatMessage;
import cn.com.wechat.message.SubscribeEventWechatMessage;
import cn.com.wechat.message.ViewEventWechatMessage;
import cn.com.wechat.message.WechatMessage;

/**
 * 微信事件消息工厂
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class EventWechatMessageFactory extends WechatMessageFactory {

	@Override
	public WechatMessage createReceiveWechatMessage(String xmlData) {
		//获取消息类型
		String msgType = getWechatMessageType(xmlData);
		if (null == msgType || !WechatMessage.MSG_TYPE_EVENT_EVENT.equalsIgnoreCase(msgType))
			return null;
		
		//获取事件类型
		String event = getEventWechatMessageEvent(xmlData);
		if (null == event) {
			return null;
		}
		
		WechatMessage msg = null;
		if (event.equalsIgnoreCase(WechatMessage.MSG_EVENT_TYPE_SUBSCRIBE)) {
			//未关注关注消息
			msg = new SubscribeEventWechatMessage();
		} else if (event.equalsIgnoreCase(WechatMessage.MSG_EVENT_TYPE_SCAN)) {
			//已关注关注消息
			msg = new ScanEventWechatMessage();
		} else if (event.equalsIgnoreCase(WechatMessage.MSG_EVENT_TYPE_LOCATION)) {
			//地理位置消息
			msg = new LocationEventWechatMessage();
		} else if (event.equalsIgnoreCase(WechatMessage.MSG_EVENT_TYPE_CLICK)) {
			//自定义菜单点击消息
			msg = new ClickEventWechatMessage();
		} else if (event.equalsIgnoreCase(WechatMessage.MSG_EVENT_TYPE_VIEW)) {
			//自定义菜单链接消息
			msg = new ViewEventWechatMessage();
		}
		
		if (msg != null) {
			if (!msg.setReceiveXmlData(xmlData)) {
				msg = null;
			}
		}
		
		return msg;
	}

	@Override
	public WechatMessage createReplyWechatMessage(String msgType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取接收的时间xml数据的事件类型
	 * @param xmlData
	 * @return 成功:事件类型；失败：null
	 */
	public static String getEventWechatMessageEvent(String xmlData) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmlData);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			
			Element root = document.getDocumentElement();
			NodeList nodelist = root.getElementsByTagName("Event");

			return nodelist.item(0).getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
