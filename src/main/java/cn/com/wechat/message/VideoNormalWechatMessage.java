package cn.com.wechat.message;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 微信视屏消息
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class VideoNormalWechatMessage extends NormalWecahtMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3674253891347764825L;

	private String mediaID;
	private String thumbMediaID;
	
	private String title;
	private String description;
	
	public VideoNormalWechatMessage() {
		this.msgType = WechatMessage.MSG_TYPE_NORMAL_VIDEO;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
			
			Element videoElement = document.createElement("Video");
			root.appendChild(videoElement);
			
			Element mediaIDElement = document.createElement("MediaId");
			mediaIDElement.setTextContent(this.mediaID);
			videoElement.appendChild(mediaIDElement);
			
			if (this.title != null && this.title.length()>0) {
				Element titleElement = document.createElement("Title");
				titleElement.setTextContent(this.title);
				videoElement.appendChild(titleElement);
			}
			
			if (this.description != null && this.description.length()>0) {
				Element descriptionElement = document.createElement("Description");
				descriptionElement.setTextContent(this.description);
				videoElement.appendChild(descriptionElement);
			}
			
			//将document转换为xml字符串
			TransformerFactory transFactory = TransformerFactory.newInstance();
	        Transformer transFormer = transFactory.newTransformer();
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

}
