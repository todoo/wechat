package cn.com.wechat.message;

import java.io.Serializable;

/**
 * 微信消息
 * @version v1.0.0
 * @author DayBreak
 *
 */
public abstract class WechatMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5986570232345199295L;
	
	public static final String MSG_TYPE_NORMAL_TEXT = "text";
	public static final String MSG_TYPE_NORMAL_IMAGE = "image";
	public static final String MSG_TYPE_NORMAL_VOICE = "voice";
	public static final String MSG_TYPE_NORMAL_VIDEO = "video";
	public static final String MSG_TYPE_NORMAL_SHORTVIDEO = "shortvideo";
	public static final String MSG_TYPE_NORMAL_LOCATION = "location";
	public static final String MSG_TYPE_NORMAL_LINK = "link";
	public static final String MSG_TYPE_NORMAL_NEWS = "news";
	public static final String MSG_TYPE_EVENT_EVENT = "event";
	
	public static final String MSG_EVENT_TYPE_SUBSCRIBE = "subscribe";
	public static final String MSG_EVENT_TYPE_SCAN = "SCAN";
	public static final String MSG_EVENT_TYPE_LOCATION = "LOCATION";
	public static final String MSG_EVENT_TYPE_CLICK = "CLICK";
	public static final String MSG_EVENT_TYPE_VIEW = "VIEW";
	
	protected String toUserName;
	protected String fromUserName;
	protected Integer createTime;
	protected String msgType;
	
	public String getToUserName() {
		return toUserName;
	}
	
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
	public String getFromUserName() {
		return fromUserName;
	}
	
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	public int getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	
	public String getMsgType() {
		return msgType;
	}
	
	/**
	 * 解析微信消息的xml数据,用于接收微信消息对象
	 * @return true:xml格式正确; false:xml数据格式不正确
	 */
	public abstract boolean setReceiveXmlData(String xmlData);
}
