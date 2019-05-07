package weixin.thrid.entity;

/**
 * 接口请求状态码定义
 * @ClassName: ResCodeEnum 
 * @version V1.0  
 * @date 2016-11-14 
 * @author kezhiqiang
 */
public enum ResCode {
	
	Success(200, "操作成功"),
	ParamError(400, "参数异常"),
	NoAuthor(401, "无相关权限"),
	NoLogined(403, "请登录"),
	NotFound(404, "内容不存在"),
	Fail(406, "请求失败"),
	ServerError(500, "服务器异常"),
	NetError(502, "服务器网络异常"),
	;
	
	private int code;
	private String msg;
	
	private ResCode(int code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
