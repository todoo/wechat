package cn.com.wechat.message.handler;

import cn.com.wechat.message.ImageNormalWechatMessage;
import cn.com.wechat.message.LinkNormalWechatMessage;
import cn.com.wechat.message.LocationNormalWechatMessage;
import cn.com.wechat.message.ShortvideoNormalWechatMessage;
import cn.com.wechat.message.TextNormalWechatMessage;
import cn.com.wechat.message.VideoNormalWechatMessage;
import cn.com.wechat.message.VoiceNormalWechatMessage;

/**
 * 微信普通消息处理接口
 * @version v1.0.0
 * @author DayBreak
 *
 */
public interface NormalWechatMessageHadler {
	/**
	 * 处理文本消息
	 * @param message 文本消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String text(TextNormalWechatMessage message);
	
	/**
	 * 处理图片消息
	 * @param message 图片消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String image(ImageNormalWechatMessage message);
	
	/**
	 * 处理语音消息
	 * @param message 语言消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String voice(VoiceNormalWechatMessage message);
	
	/**
	 * 处理视屏消息
	 * @param message 视屏消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String video(VideoNormalWechatMessage message);
	
	/**
	 * 处理小视屏消息
	 * @param message 小视屏消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String shortvideo(ShortvideoNormalWechatMessage message);
	
	/**
	 * 处理位置消息
	 * @param message 位置消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String location(LocationNormalWechatMessage message);
	
	/**
	 * 处理链接消息
	 * @param message 链接消息对象
	 * @return 回复微信消息的xml字符串，无回复返回null
	 */
	public String link(LinkNormalWechatMessage message);
}
