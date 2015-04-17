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
	
	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}
}
