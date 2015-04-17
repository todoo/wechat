package cn.com.wechat.pojo;

import java.util.Date;

/**
 * 微信用户基本信息对象
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class WechatUserInfo {
	public static final int SUBSCRIBE_YES = 1;
	public static final int SUBSCRIBE_NO = 0;
	public static final int SEX_MAN = 1;
	public static final int SEX_WOMAN = 2;
	public static final int SEX_UNKNOW = 0;
	
	private int subscribe;
	private String openid;
	private String nickname;
	private int sex;
	private String city;
	private String country;
	private String province;
	private String language;
	private String headimgurl;
	private Date subscribeTime;
	private String unionid;
	
	public int getSubscribe() {
		return subscribe;
	}
	
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}
	
	public String getOpenid() {
		return openid;
	}
	
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public int getSex() {
		return sex;
	}
	
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getHeadimgurl() {
		return headimgurl;
	}
	
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	public Date getSubscribeTime() {
		return subscribeTime;
	}
	
	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	
	public String getUnionid() {
		return unionid;
	}
	
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
}
