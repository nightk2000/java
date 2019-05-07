package weixin.thrid.entity;

public class WxVideoMsgEntity extends WxMsgEntity {

	// 必填，通过素材管理接口上传多媒体文件，得到的id。 
	private String mediaId;
	// 选填，视频/音乐/图文消息的标题 
	private String title;
	// 选填，视频/音乐/图文消息的描述 
	private String description;

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	@Override
	public String getMsgType() {
		return "video";
	}
}
