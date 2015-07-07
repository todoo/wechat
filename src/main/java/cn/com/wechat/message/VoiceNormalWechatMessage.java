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
 * 微信音频消息
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class VoiceNormalWechatMessage extends NormalWecahtMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6590545858557656609L;
	
	private String format;
	private String mediaID;
	private String recognition;

	public VoiceNormalWechatMessage() {
		this.msgType = WechatMessage.MSG_TYPE_NORMAL_VOICE;
	}
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}
	
	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
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
			NodeList formatList = root.getElementsByTagName("Format");
			this.format = formatList.item(0).getTextContent();
			NodeList recognitionList = root.getElementsByTagName("Recognition");
			if (recognitionList.getLength()>0) {
				//开启语音识别，接收语音识别结果
				this.recognition = recognitionList.item(0).getTextContent();
			} else {
				this.recognition = "";
			}
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
			
			Element voiceElement = document.createElement("Voice");
			root.appendChild(voiceElement);
			Element mediaIDElement = document.createElement("MediaId");
			mediaIDElement.setTextContent(this.mediaID);
			voiceElement.appendChild(mediaIDElement);
			
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
			JSONObject voice = new JSONObject();
			voice.put("media_id", this.mediaID);
			
			JSONObject json = new JSONObject();
			json.put("touser", this.toUserName);
			json.put("msgtype", this.msgType);
			json.put("voice", voice);
			
			return json.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
