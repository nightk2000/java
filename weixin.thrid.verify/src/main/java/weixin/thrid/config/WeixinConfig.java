package weixin.thrid.config;

public class WeixinConfig {

	public static final String Token = "";
	
	public static final String AesKey = "";
	
	public static final String AppID = "";
	
	public static final String AppSecret = "";
	
	private static String Ticket = "";
	
	public static final String getTicket() {
		return Ticket;
	}
	
	public static synchronized final void setTicket(String ticket) {
		Ticket = ticket;
	}
	
}
