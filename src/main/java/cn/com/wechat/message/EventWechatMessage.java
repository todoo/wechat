package cn.com.wechat.message;

/**
 * 微信事件消息
 * @version v1.0.0
 * @author DayBreak
 *
 */
public abstract class EventWechatMessage extends WechatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7448677306604172641L;
	
	protected String event;

	public String getEvent() {
		return event;
	}
}
