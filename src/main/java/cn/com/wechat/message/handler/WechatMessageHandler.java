package cn.com.wechat.message.handler;

import cn.com.wechat.message.ClickEventWechatMessage;
import cn.com.wechat.message.ImageNormalWechatMessage;
import cn.com.wechat.message.LinkNormalWechatMessage;
import cn.com.wechat.message.LocationEventWechatMessage;
import cn.com.wechat.message.LocationNormalWechatMessage;
import cn.com.wechat.message.ScanEventWechatMessage;
import cn.com.wechat.message.ShortvideoNormalWechatMessage;
import cn.com.wechat.message.SubscribeEventWechatMessage;
import cn.com.wechat.message.TextNormalWechatMessage;
import cn.com.wechat.message.VideoNormalWechatMessage;
import cn.com.wechat.message.ViewEventWechatMessage;
import cn.com.wechat.message.VoiceNormalWechatMessage;
import cn.com.wechat.message.WechatMessage;
import cn.com.wechat.message.factory.EventWechatMessageFactory;
import cn.com.wechat.message.factory.NormalWechatMessageFactory;
import cn.com.wechat.message.factory.WechatMessageFactory;

/**
 * 微信消息处理类
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class WechatMessageHandler{
	private static NormalWechatMessageFactory normalFactory = new NormalWechatMessageFactory();
	private static EventWechatMessageFactory eventFactory = new EventWechatMessageFactory();
	
	private NormalWechatMessageHadler normalHandler;
	private EventWechatMessageHadler eventHandler;
	
	/**
	 * 构造函数
	 * @param normalHandler 普通消息接口实现类
	 * @param eventHandler 事件消息接口实现类
	 */
	public WechatMessageHandler(NormalWechatMessageHadler normalHandler, EventWechatMessageHadler eventHandler) {
		this.normalHandler = normalHandler;
		this.eventHandler = eventHandler;
	}
	
	/**
	 * 进行微信消息处理
	 * @param xmlData 微信消息xml字符串
	 * @return 回复消息的xml字符串，无回复返回null
	 */
	public String handdle(String xmlData) {
		//获取消息类型
		String msgType = WechatMessageFactory.getWechatMessageType(xmlData);
		if (null == msgType)
			return null;
		
		if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_TEXT)) {
			//文本消息
			TextNormalWechatMessage message = (TextNormalWechatMessage) normalFactory.createReceiveWechatMessage(xmlData); 
			return this.normalHandler.text(message);
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_IMAGE)) {
			//图片消息
			ImageNormalWechatMessage message = (ImageNormalWechatMessage) normalFactory.createReceiveWechatMessage(xmlData); 
			return this.normalHandler.image(message);
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_VOICE)) {
			//语音消息
			VoiceNormalWechatMessage message = (VoiceNormalWechatMessage) normalFactory.createReceiveWechatMessage(xmlData); 
			return this.normalHandler.voice(message);
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_VIDEO)) {
			//视屏消息
			VideoNormalWechatMessage message = (VideoNormalWechatMessage) normalFactory.createReceiveWechatMessage(xmlData); 
			return this.normalHandler.video(message);
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_SHORTVIDEO)) {
			//小视屏消息
			ShortvideoNormalWechatMessage message = (ShortvideoNormalWechatMessage) normalFactory.createReceiveWechatMessage(xmlData); 
			return this.normalHandler.shortvideo(message);
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_LOCATION)) {
			//位置消息
			LocationNormalWechatMessage message = (LocationNormalWechatMessage) normalFactory.createReceiveWechatMessage(xmlData); 
			return this.normalHandler.location(message);
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_LINK)) {
			//链接消息
			LinkNormalWechatMessage message = (LinkNormalWechatMessage) normalFactory.createReceiveWechatMessage(xmlData); 
			return this.normalHandler.link(message);
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_EVENT_EVENT)) {
			//获取事件类型
			String event = EventWechatMessageFactory.getEventWechatMessageEvent(xmlData);
			if (null == event) {
				return null;
			}
			
			if (event.equalsIgnoreCase(WechatMessage.MSG_EVENT_TYPE_SUBSCRIBE)) {
				//未关注关注消息
				SubscribeEventWechatMessage message = (SubscribeEventWechatMessage) eventFactory.createReceiveWechatMessage(xmlData);
				return this.eventHandler.subscribe(message);
			} else if (event.equalsIgnoreCase(WechatMessage.MSG_EVENT_TYPE_SCAN)) {
				//已关注关注消息
				ScanEventWechatMessage message = (ScanEventWechatMessage) eventFactory.createReceiveWechatMessage(xmlData);
				return this.eventHandler.scan(message);
			} else if (event.equalsIgnoreCase(WechatMessage.MSG_EVENT_TYPE_LOCATION)) {
				//地理位置消息
				LocationEventWechatMessage message = (LocationEventWechatMessage) eventFactory.createReceiveWechatMessage(xmlData);
				return this.eventHandler.location(message);
			} else if (event.equalsIgnoreCase(WechatMessage.MSG_EVENT_TYPE_CLICK)) {
				//自定义菜单点击消息
				ClickEventWechatMessage message = (ClickEventWechatMessage) eventFactory.createReceiveWechatMessage(xmlData);
				return this.eventHandler.click(message);
			} else if (event.equalsIgnoreCase(WechatMessage.MSG_EVENT_TYPE_VIEW)) {
				//自定义菜单链接消息
				ViewEventWechatMessage message = (ViewEventWechatMessage) eventFactory.createReceiveWechatMessage(xmlData);
				return this.eventHandler.view(message);
			}
		}
		
		return null;
	}
}
