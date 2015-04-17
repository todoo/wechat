package cn.com.wechat.access;

import java.security.MessageDigest;
import java.util.Arrays;

import cn.com.wechat.config.WechatConfig;

/**
 * 微信公共平台url和token接入验证类
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class WechatAccess {
	private static WechatConfig config;
	
	private static WechatAccess wechatAccess = new WechatAccess();
	
	private WechatAccess() {
	}
	
	/**
	 * 获取实例
	 * @param config 微信配置对象
	 * @return 接口对象
	 */
	public static WechatAccess getInstance(WechatConfig config) {
		WechatAccess.config = config;
		
		return wechatAccess;
	}
	
	/**
	 * 按照微信接入的验证算法进行验证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return true:验证通过    false:验证失败
	 */
	public boolean checkSignature(String signature, String timestamp, String nonce) {
		if (null == signature || null == timestamp || null == nonce)
			return false;
		
		String[] array = {WechatAccess.config.getToken(),timestamp,nonce};
		Arrays.sort(array); //进行字典序排序
		String orgStr = array[0] + array[1] + array[2]; //将字符串进行拼接
		
		//sha1加密
		String digestStr = this.SHA1Encode(orgStr);

		if (signature.equalsIgnoreCase(digestStr)) {
			return true;
		} else {
			return false;
		}
	}
	
	//sha1加密   
    private String SHA1Encode(String sourceString) {  
        String resultString = null;  
        try {  
           resultString = new String(sourceString);  
           MessageDigest md = MessageDigest.getInstance("SHA-1");  
           resultString = byte2hexString(md.digest(resultString.getBytes()));  
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        return resultString;  
    }  
    
    private final String byte2hexString(byte[] bytes) {  
        StringBuffer buf = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            if (((int) bytes[i] & 0xff) < 0x10) {  
                buf.append("0");  
            }  
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));  
        }  
        return buf.toString().toUpperCase();  
    }  

}
