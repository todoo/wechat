package cn.com.wechat.pojo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 场景二维码post数据对象
 * @version v1.0.0
 * @author DayBreak
 *
 */
public class SceneQrcodePostData {
	public static final String ACTION_TEMP_QRCODE = "QR_SCENE";
	public static final String ACTION_LIMIT_QRCODE = "QR_LIMIT_SCENE";
	public static final String ACTION_LIMIT_STR_QRCODE = "QR_LIMIT_STR_SCENE";
	
	private Integer expireSeconds;
	
	private String actionName;
	private Long sceneID;
	private String sceneStr;
	
	public Integer getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(Integer expireSeconds) {
		if (expireSeconds == null || expireSeconds <= 0 || expireSeconds > 1800) {
			this.expireSeconds = 1800;
		} else {
			this.expireSeconds = expireSeconds;
		}
	}

	public String getActionName() {
		return actionName;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public Long getSceneID() {
		return sceneID;
	}
	
	public void setSceneID(Long sceneID) {
		this.sceneID = sceneID;
	}
	
	public String getSceneStr() {
		return sceneStr;
	}
	
	public void setSceneStr(String sceneStr) {
		this.sceneStr = sceneStr;
	}
	
	public JSONObject getJSONObject() {
		try {
			JSONObject json = new JSONObject();
			if (this.actionName == null || (!this.actionName.equals(ACTION_LIMIT_QRCODE) && !this.actionName.equals(ACTION_LIMIT_STR_QRCODE) && !this.actionName.equals(ACTION_TEMP_QRCODE))) {
				System.out.println("对象数据错误，无法转换为json");
				return null;
			}
			
			if (this.actionName.equals(ACTION_TEMP_QRCODE)) {
				//临时二维码，添加有效期参数
				if (this.expireSeconds == null) {
					System.out.println("对象数据错误，无法转换为json");
					return null;
				}
				json.put("expire_seconds", this.expireSeconds);
			}
			
			json.put("action_name", this.actionName);
			
			JSONObject scene = new JSONObject();
			if (!this.actionName.equals(ACTION_LIMIT_STR_QRCODE) && this.sceneID != null) {
				scene.put("scene_id", this.sceneID);
			} else if (this.actionName.equals(ACTION_LIMIT_STR_QRCODE) && this.sceneStr != null && this.sceneStr.length()>0) {
				scene.put("scene_str", this.sceneStr);
			} else {
				System.out.println("对象数据错误，无法转换为json");
				return null;
			}
			
			JSONObject actionInfo = new JSONObject();
			actionInfo.put("scene", scene);
			
			json.put("action_info", actionInfo);
			
			return json;
			
		} catch (JSONException e) {
			System.out.println("对象数据错误，无法转换为json");
			e.printStackTrace();
		}
		
		return null;
	}
}
