package weixin.thrid.entity;

public class ExecResult {

	private int code = 0;
	private String msg = "";
	private Object value = "";
	
	public ExecResult() {
		
	}
	
	public ExecResult(int code,String msg) {
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
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public ExecResult Failed(ResCode code){
		this.code = code.getCode();
		this.msg = code.getMsg();
		return this;
	}
	
	public ExecResult Failed(ResCode code,String msg){
		this.code = code.getCode();
		this.msg = msg;
		return this;
	}
	
	public ExecResult Failed(int code,String msg){
		this.code = code;
		this.msg = msg;
		return this;
	}
	
	
	public ExecResult Success(Object value){
		this.code = ResCode.Success.getCode();
		this.value = value;
		return this;
	}
	
	public ExecResult Success(Object value,String msg){
		this.code = ResCode.Success.getCode();
		this.value = value;
		this.msg = msg;
		return this;
	}
	
	public ExecResult Success(int code,Object value){
		this.code = code;
		this.value = value;
		return this;
	}
	
	public ExecResult Success(int code,Object value, String msg){
		this.code = code;
		this.value = value;
		this.msg = msg;
		return this;
	}
	
}
