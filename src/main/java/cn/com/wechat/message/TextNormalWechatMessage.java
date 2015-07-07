package cn.com.wechat.message;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 微信文本消息
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class TextNormalWechatMessage extends NormalWecahtMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8409406774851156849L;
	
	private String content;
	
	public TextNormalWechatMessage() {
		this.msgType = WechatMessage.MSG_TYPE_NORMAL_TEXT;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
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
			NodeList contentList = root.getElementsByTagName("Content");
			this.content = contentList.item(0).getTextContent();
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
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.newDocument();
			//添加根节点
			Element root = document.createElement("xml");
			document.appendChild(root);
			
			//添加子节点
			Element toUserNameElement = document.createElement("ToUserName");
			toUserNameElement.setTextContent(this.toUserName);
			root.appendChild(toUserNameElement);
			
			Element fromUserNameElement = document.createElement("FromUserName");
			fromUserNameElement.setTextContent(this.fromUserName);
			root.appendChild(fromUserNameElement);
			
			Element createTimeElement = document.createElement("CreateTime");
			createTimeElement.setTextContent(this.createTime + "");
			root.appendChild(createTimeElement);
			
			Element msgTypeElement = document.createElement("MsgType");
			msgTypeElement.setTextContent(this.msgType);
			root.appendChild(msgTypeElement);
			
			Element countElement = document.createElement("Content");
			countElement.setTextContent(this.content);
			root.appendChild(countElement);
			
			//将document转换为xml字符串
			TransformerFactory transFactory = TransformerFactory.newInstance();
	        Transformer transFormer = transFactory.newTransformer();
	        transFormer.setOutputProperty(OutputKeys.ENCODING, System.getProperty("sun.jnu.encoding"));
	        DOMSource domSource = new DOMSource(document);
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        transFormer.transform(domSource, new StreamResult(bos));
	        String xmlStr = bos.toString();
	        xmlStr = xmlStr.substring(xmlStr.indexOf("?>")>0?(xmlStr.indexOf("?>")+2):0);//去掉xml的第一行申明
	        return xmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getCustomSendMsgJson() {
		try {
			JSONObject text = new JSONObject();
			text.put("content", this.content);
			
			JSONObject json = new JSONObject();
			json.put("touser", this.toUserName);
			json.put("msgtype", this.msgType);
			json.put("text", text);
			
			return json.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
