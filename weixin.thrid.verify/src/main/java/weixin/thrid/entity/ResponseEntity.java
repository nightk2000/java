package weixin.thrid.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResponseEntity {

	/**
	 * getResponseCode()
	 */
	private int code = 0;
	
	/**
	 * getContentEncoding()
	 */
	private String charset = "";
	
	/**
	 * getResponseMessage()
	 */
	private String message = "";
	
	/**
	 * getInputStream()
	 */
	private StringBuffer body = new StringBuffer();
	
	/**
	 * response headers
	 */
	private Map<String, List<String>> headers = null;
	
	/**
	 * used file download
	 */
	private byte[] bytes = null;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StringBuffer getBody() {
		return body;
	}

	public void setBody(StringBuffer body) {
		this.body = body;
	}
	
	public void setBody(String body) {
		this.body.setLength(0);
		this.body.append(body);
	}

	public void append(String str){
		this.body.append(str);
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}
	
	public String getHeader(String key) {
		if( this.headers==null || this.headers.size()==0 ){
			return null;
		}
		return Arrays.toString(this.headers.get(key).toArray());
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
