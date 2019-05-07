package weixin.thrid.entity;

public class WxTextMsgEntity extends WxMsgEntity {

	// 必填，消息内容（换行：在content中能够换行，微信客户端就支持换行显示） 
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getMsgType() {
		return "text";
	}
}
