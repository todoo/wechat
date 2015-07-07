package cn.com.wechat.message;

/**
 * 普通微信消息
 * @version v1.0.0
 * @author DayBreak
 *
 */
public abstract class NormalWecahtMessage extends WechatMessage {
	protected String msgID;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6369879943567948528L;
	
	/**
	 * 获取回复消息对象的xml数据
	 * 根据微信实际情况，有些消息没有回复消息，始终返回null，例如事件消息
	 * @return 成功：回复微信消息的xml字符串；失败：null
	 */
	public abstract String getReplyXmlData();
	
	/**
	 * 获取客服发送接口的消息json字符串
	 * @return
	 */
	public abstract String getCustomSendMsgJson();
	
	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}
}
