package cn.com.wechat.message.factory;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import cn.com.wechat.message.WechatMessage;

/**
 * 微信消息工厂
 * @version v1.0.0
 * @author DayBreak
 *
 */
public abstract class WechatMessageFactory {
	/**
	 * 创建接收的微信消息对象
	 * @return 成功：消息对象；失败：null
	 */
	public abstract WechatMessage createReceiveWechatMessage(String xmlData);
	
	/**
	 * 创建回复微信消息对象
	 * @return 成功：消息对象；失败：null
	 */
	public abstract WechatMessage createReplyWechatMessage(String msgType);
	
	/**
	 * 获取接收的xml数据的消息类型
	 * @param xmlData
	 * @return 成功：消息类型；失败：null
	 */
	public static String getWechatMessageType(String xmlData) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmlData);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			
			Element root = document.getDocumentElement();
			NodeList nodelist = root.getElementsByTagName("MsgType");

			return nodelist.item(0).getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
