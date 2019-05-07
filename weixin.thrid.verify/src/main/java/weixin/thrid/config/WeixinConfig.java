package weixin.thrid.config;

public class WeixinConfig {

	public static final String WeixinUrl = "https://mp.weixin.qq.com/cgi-bin/";
	
	public static final String Token = "bforcehotel";
	
	public static final String AesKey = "wUMFkrNukl6rcwu9EP1hvUamHDZUaeJ8kZCGH27X8zD";
	
	public static final String AppID = "wx1a7e2ee1f72dcfa8";
	
	public static final String AppSecret = "f3aaa98ba7c9c14696391600a33c1571";
	
	public static final String BaseUrl = "http://hotel.develop.b-force.cn/";
	
	
	private static String Ticket = "";
	
	private static String AccessToken = "";
	
	
	
	public static final String getTicket() {
		return Ticket;
	}
	
	public static synchronized final void setTicket(String ticket) {
		Ticket = ticket;
	}
	
	public static final String getAccessToken() {
		return AccessToken;
	}
	
	public static synchronized final void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}
	
}
