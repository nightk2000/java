package weixin.thrid.entity;

public class WxVoiceMsgEntity extends WxMsgEntity {

	// 必填，通过素材管理接口上传多媒体文件，得到的id。 
	private String mediaId;
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public String getMsgType() {
		return "voice";
	}
}
