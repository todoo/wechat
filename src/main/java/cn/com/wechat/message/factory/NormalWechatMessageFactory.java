package cn.com.wechat.message.factory;

import cn.com.wechat.message.ArticleNormalWechatMessage;
import cn.com.wechat.message.ImageNormalWechatMessage;
import cn.com.wechat.message.LinkNormalWechatMessage;
import cn.com.wechat.message.LocationNormalWechatMessage;
import cn.com.wechat.message.ShortvideoNormalWechatMessage;
import cn.com.wechat.message.TextNormalWechatMessage;
import cn.com.wechat.message.VideoNormalWechatMessage;
import cn.com.wechat.message.VoiceNormalWechatMessage;
import cn.com.wechat.message.WechatMessage;

/**
 * 微信普通消息工厂
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class NormalWechatMessageFactory extends WechatMessageFactory {
	
	@Override
	public WechatMessage createReceiveWechatMessage(String xmlData) {
		//获取消息类型
		String msgType = getWechatMessageType(xmlData);
		if (null == msgType)
			return null;
		
		WechatMessage msg = null;
		if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_TEXT)) {
			//文本消息
			msg = new TextNormalWechatMessage();
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_IMAGE)) {
			//图片消息
			msg = new ImageNormalWechatMessage();
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_VOICE)) {
			//语音消息
			msg = new VoiceNormalWechatMessage();
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_VIDEO)) {
			//视屏消息
			msg = new VideoNormalWechatMessage();
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_SHORTVIDEO)) {
			//小视屏消息
			msg = new ShortvideoNormalWechatMessage();
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_LOCATION)) {
			//位置消息
			msg = new LocationNormalWechatMessage();
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_LINK)) {
			//链接消息
			msg = new LinkNormalWechatMessage();
		}
		
		if (msg != null) {
			if (!msg.setReceiveXmlData(xmlData)) {
				msg = null;
			}
		}
		
		return msg;
	}

	@Override
	public WechatMessage createReplyWechatMessage(String msgType) {
		if (null == msgType)
			return null;
		WechatMessage msg = null;
		if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_TEXT)) {
			//文本消息
			msg = new TextNormalWechatMessage();
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_IMAGE)) {
			//图片消息
			msg = new ImageNormalWechatMessage();
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_VOICE)) {
			//语音消息
			msg = new VoiceNormalWechatMessage();
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_VIDEO)) {
			//视屏消息
			msg = new VideoNormalWechatMessage();
		} else if (msgType.equalsIgnoreCase(WechatMessage.MSG_TYPE_NORMAL_NEWS)) {
			//图文消息
			msg = new ArticleNormalWechatMessage();
		}
		
		return msg;
	}
}
