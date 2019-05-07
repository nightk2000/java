package weixin.thrid.entity;

public class WebAuthEntity {
	
	public enum SCOPE{
		SNSAPI_BASE,
		SNSAPI_USERINFO
	};

	// 客户公众号的appid
	private String appid;
	// 回调地址
	private String redirectUrl;
	// 授权作用域，snsapi_base：只取关键信息，不会有确认环节；snsapi_userinfo：可取个人资料，需要会员确认
	private String scope;
	// 重定向后会带上state参数，开发者可以填写任意参数值，最多128字节
	private String state;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(SCOPE scope) {
		if( scope==SCOPE.SNSAPI_USERINFO ){
			this.scope = "snsapi_userinfo";
		}else{
			this.scope = "snsapi_base";
		}
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
