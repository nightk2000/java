package weixin.thrid.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixin.thrid.config.WeixinConfig;
import weixin.thrid.util.ReaderUtil;
import weixin.thrid.util.WeixinSecurity;

/**
 * 授权消息回调
 */
@WebServlet(name="AuthMessage",urlPatterns={"/weixin/auth/message.do"}, description="")
public class ThridNotifyServlet extends BaseServlet {

	private static final long serialVersionUID = -8481211900611878537L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Map<String,String> map = new HashMap<String,String>();
		Enumeration<?> enu = req.getParameterNames();  
		while(enu.hasMoreElements()){
			String key = (String)enu.nextElement();
			map.put( key, req.getParameter(key) );
		}
		
		String input = ReaderUtil.InputRead(req.getInputStream(), req.getContentLength());
		String decryptData = WeixinSecurity.deCrypt(map.get("msg_signature")
				, map.get("timestamp")
				, map.get("nonce"), input);
		if( decryptData==null ){
			return;
		}
		Map<String,String> xmlMap = ReaderUtil.XmlReader(decryptData);
		if( xmlMap.containsKey("InfoType") ){
			if( xmlMap.get("InfoType").equals("component_verify_ticket") )
			{// 微信服务器每隔10分钟推送的component_verify_ticket
				if( xmlMap.containsKey("ComponentVerifyTicket") ){
					WeixinConfig.setTicket(xmlMap.get("ComponentVerifyTicket"));
				}
				outText(req, resp, "success");
			}
			
			else if( xmlMap.get("InfoType").equals("authorized") )
			{// 客户扫码授权码，同意对云图进行授权
				/**
				 * AuthorizerAppid
				 * AuthorizationCode
				 * AuthorizationCodeExpiredTime
				 */
				//ThridWeixinAuthManager.apiQueryAuth(xmlMap.get("AuthorizationCode"));
				//  to  database
				//ThridWeixinAuthManager.getAuthorizerInfo( xmlMap.get( "AuthorizerAppid" ) );
				
				outText(req, resp, "success");
			}
			else if( xmlMap.get("InfoType").equals("unauthorized") )
			{// 客户从微信公众平台后台取消对云图的授权
				/**
				 * AuthorizerAppid
				 */
				//unAuth( xmlMap.get("AuthorizerAppid") );
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
}
