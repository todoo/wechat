package cn.com.wechat.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONObject;

import cn.com.wechat.api.media.WechatMediaAPI;
import cn.com.wechat.api.sendmsg.WechatCustomAPI;
import cn.com.wechat.config.WechatConfig;

public class MediaAPITest {
	private static WechatConfig config = WechatConfig.getInstance("./config.xml");
	private static WechatMediaAPI mediaAPI = WechatMediaAPI.getInstance(config);
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//8WxndryW9qGxHzmGOWfEliHAJeZI53Ydoz1Y-82e5FA_wFnqI0T6sjlzCQ8Rjc1B
		//nH_q8al9q_Qmx5kYwanXC_9kUT1i3M7YeH59TBEl3qGX6aFQcl7jvG-v0SUJ47tw
		//uploadTempMedia();
		//getTempMedia();
		//gJ8X6Ovv5V2SjHhORoN84sa-3OzdD5Q8OpT-khE1oDY
		//uploadMedia();
		//getMedia();
		getMediaCount();
		deleteMedia();
		getMediaCount();
	}
	
	public static void uploadTempMedia() throws FileNotFoundException {
		File file = new File("./src.jpg");
		InputStream in = new FileInputStream(file);
		//String mediaID = mediaAPI.uploadTempMedia(WechatMediaAPI.MEDIA_TYPE_IMAGE, file);
		String mediaID = mediaAPI.uploadTempMedia(WechatMediaAPI.MEDIA_TYPE_IMAGE, in, file.getName());
		System.out.println(mediaID);
	}
	
	public static void getTempMedia() throws IOException {
		String mediaID = "nH_q8al9q_Qmx5kYwanXC_9kUT1i3M7YeH59TBEl3qGX6aFQcl7jvG-v0SUJ47tw";
		StringBuffer filename = new StringBuffer();
		InputStream in = mediaAPI.getTempMedia(mediaID, filename);
		if (in != null) {
			FileOutputStream out = new FileOutputStream("./" + filename.toString());
		
			byte[] buffer = new byte[1024];
			int l;
			while((l=in.read(buffer)) > 0) {
				out.write(buffer,0,l);
			}
			out.flush();
			out.close();
		}
		in.close();
	}
	
	public static void uploadMedia() {
		File file = new File("./src.jpg");
		//InputStream in = new FileInputStream(file);
		//String mediaID = mediaAPI.uploadTempMedia(WechatMediaAPI.MEDIA_TYPE_IMAGE, file);
		String mediaID = mediaAPI.uploadMedia(WechatMediaAPI.MEDIA_TYPE_IMAGE, file);
		System.out.println(mediaID);
	}
	
	public static void getMedia() throws IOException {
		String mediaID = "gJ8X6Ovv5V2SjHhORoN84sa-3OzdD5Q8OpT-khE1oDY";
		String filename = "gJ8X6Ovv5V2SjHhORoN84sa-3OzdD5Q8OpT-khE1oDY.jpg";
		byte[] bytes = mediaAPI.getStreamMedia(mediaID);
		FileOutputStream out = new FileOutputStream("./" + filename);
		out.write(bytes);
		out.flush();
		out.close();
	}
	
	public static void getMediaCount() {
		JSONObject re = mediaAPI.getMediaCount();
		System.out.println(re.toString());
	}

	public static void deleteMedia() {
		boolean re = mediaAPI.deleteMedia("gJ8X6Ovv5V2SjHhORoN84sa-3OzdD5Q8OpT-khE1oDY");
		System.out.println(re);
	}
}
