package cn.com.wechat.message.handler;

import cn.com.wechat.message.ClickEventWechatMessage;
import cn.com.wechat.message.LocationEventWechatMessage;
import cn.com.wechat.message.ScanEventWechatMessage;
import cn.com.wechat.message.SubscribeEventWechatMessage;
import cn.com.wechat.message.ViewEventWechatMessage;

/**
 * 微信事件消息处理接口
 * @version v1.0.0
 * @author DayBreak
 *
 */
public interface EventWechatMessageHadler {
	/**
	 * 处理未关注关注消息
	 * @param message 关注事件消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String subscribe(SubscribeEventWechatMessage message);
	
	/**
	 * 处理已关注关注消息
	 * @param message 关注事件消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String scan(ScanEventWechatMessage message);
	
	/**
	 * 处理上报位置消息
	 * @param message 上报位置事件消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String location(LocationEventWechatMessage message);
	
	/**
	 * 处理自定义菜单点击消息
	 * @param message 自定义菜单点击事件消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String click(ClickEventWechatMessage message);
	
	/**
	 * 处理自定义菜单链接消息
	 * @param message 自定义菜单点击事件消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String view(ViewEventWechatMessage message);
}
