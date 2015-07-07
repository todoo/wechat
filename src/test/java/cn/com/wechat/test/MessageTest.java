package cn.com.wechat.test;

import cn.com.wechat.message.ArticleNormalWechatMessage;
import cn.com.wechat.message.ImageNormalWechatMessage;
import cn.com.wechat.message.SubscribeEventWechatMessage;
import cn.com.wechat.message.TextNormalWechatMessage;
import cn.com.wechat.message.VideoNormalWechatMessage;
import cn.com.wechat.message.VoiceNormalWechatMessage;
import cn.com.wechat.message.WechatMessage;
import cn.com.wechat.message.factory.EventWechatMessageFactory;
import cn.com.wechat.message.factory.NormalWechatMessageFactory;
import cn.com.wechat.message.factory.WechatMessageFactory;
import cn.com.wechat.pojo.Article;

public class MessageTest {
	public static void main(String[] args) {
		//textMessage();
		//imageMessage();
		//voiceMessage();
		//videoMessage();
		//subscribeMessage();
		articleMessage();
	}
	
	public static void textMessage() {
		String xmlData = "<xml>"+
				"<ToUserName><![CDATA[toUser]]></ToUserName>"+
				"<FromUserName><![CDATA[fromUser]]></FromUserName>"+ 
				"<CreateTime>1348831860</CreateTime>"+
				"<MsgType><![CDATA[text]]></MsgType>"+
				"<Content><![CDATA[this is a test]]></Content>"+
				"<MsgId>1234567890123456</MsgId>"+
			"</xml>";
		WechatMessageFactory factory = new NormalWechatMessageFactory();
		TextNormalWechatMessage message = (TextNormalWechatMessage)factory.createReceiveWechatMessage(xmlData);
		System.out.println(message.getReplyXmlData());
	}
	
	public static void imageMessage() {
		String xmlData = " <xml>"+
							 "<ToUserName><![CDATA[toUser]]></ToUserName>"+
							 "<FromUserName><![CDATA[fromUser]]></FromUserName>"+
							 "<CreateTime>1348831860</CreateTime>"+
							 "<MsgType><![CDATA[image]]></MsgType>"+
							 "<PicUrl><![CDATA[this is a url]]></PicUrl>"+
							 "<MediaId><![CDATA[media_id]]></MediaId>"+
							 "<MsgId>1234567890123456</MsgId>"+
							 "</xml>";
		WechatMessageFactory factory = new NormalWechatMessageFactory();
		ImageNormalWechatMessage message = (ImageNormalWechatMessage)factory.createReceiveWechatMessage(xmlData);
		System.out.println(message.getReplyXmlData());
	}
	
	public static void voiceMessage() {
		String xmlData = "<xml>"+
							"<ToUserName><![CDATA[toUser]]></ToUserName>"+
							"<FromUserName><![CDATA[fromUser]]></FromUserName>"+
							"<CreateTime>1357290913</CreateTime>"+
							"<MsgType><![CDATA[voice]]></MsgType>"+
							"<MediaId><![CDATA[media_id]]></MediaId>"+
							"<Format><![CDATA[Format]]></Format>"+
							"<MsgId>1234567890123456</MsgId>"+
							"</xml>";
		WechatMessageFactory factory = new NormalWechatMessageFactory();
		VoiceNormalWechatMessage message = (VoiceNormalWechatMessage)factory.createReceiveWechatMessage(xmlData);
		System.out.println(message.getReplyXmlData());
	}
	
	public static void videoMessage() {
		String xmlData = "<xml>"+
							"<ToUserName><![CDATA[toUser]]></ToUserName>"+
							"<FromUserName><![CDATA[fromUser]]></FromUserName>"+
							"<CreateTime>1357290913</CreateTime>"+
							"<MsgType><![CDATA[video]]></MsgType>"+
							"<MediaId><![CDATA[media_id]]></MediaId>"+
							"<ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>"+
							"<MsgId>1234567890123456</MsgId>"+
							"</xml>";
		WechatMessageFactory factory = new NormalWechatMessageFactory();
		VideoNormalWechatMessage message = (VideoNormalWechatMessage)factory.createReceiveWechatMessage(xmlData);
		System.out.println(message.getReplyXmlData());
	}
	
	public static void subscribeMessage() {
		String xmlData = "<xml><ToUserName><![CDATA[toUser]]></ToUserName>"+
							"<FromUserName><![CDATA[FromUser]]></FromUserName>"+
							"<CreateTime>123456789</CreateTime>"+
							"<MsgType><![CDATA[event]]></MsgType>"+
							"<Event><![CDATA[subscribe]]></Event>"+
							"<EventKey><![CDATA[qrscene_123123]]></EventKey>"+
							"<Ticket><![CDATA[TICKET]]></Ticket>"+
							"</xml>";
		WechatMessageFactory factory = new EventWechatMessageFactory();
		SubscribeEventWechatMessage message = (SubscribeEventWechatMessage)factory.createReceiveWechatMessage(xmlData);
		System.out.println(message.getEventKey());
	}
	
	public static void articleMessage() {
		WechatMessageFactory factory = new NormalWechatMessageFactory();
		ArticleNormalWechatMessage articles = (ArticleNormalWechatMessage) factory.createReplyWechatMessage(WechatMessage.MSG_TYPE_NORMAL_NEWS);
		Article article1 = new Article();
		article1.setTitle("1");
		article1.setUrl("1");
		Article article2 = new Article();
		article2.setTitle("2");
		article2.setUrl("2");
		Article article3 = new Article();
		article3.setTitle("3");
		article3.setUrl("3");
		article3.setPicUrl("3");
		articles.addArticle(article1, false);
		articles.addArticle(article2, false);
		articles.addArticle(article3, true);
		System.out.println(articles.getReplyXmlData());
		System.out.println(articles.getCustomSendMsgJson());
	}
}
