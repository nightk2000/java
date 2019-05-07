package weixin.thrid.entity;

public class WxMsgEntity {

	// 必填，接收方帐号（收到的OpenID） 
	private String toUserName;
	// 必填，开发者微信号 
	private String fromUserName;
	// 必填，消息创建时间戳 （整型） 
	private long createTime = System.currentTimeMillis()/1000;
	// 必填，消息类型
	private String msgType;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
