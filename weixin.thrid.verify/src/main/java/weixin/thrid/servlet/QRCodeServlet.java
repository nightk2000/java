package weixin.thrid.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixin.thrid.config.WeixinConfig;
import weixin.thrid.entity.ExecResult;
import weixin.thrid.entity.WebAuthEntity;

public class QRCodeServlet extends BaseServlet {

	private static final long serialVersionUID = 4973005831228488875L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String key = ID24();
		//RedisUtil.setVal("weixin_auth_"+key, ticket.getUserId(), RedisUtil.MINUTE*20);
		
		String backUrl = WeixinConfig.BaseUrl + "thrid/callback.do?key="+key;
		//System.out.println(backUrl);
		ExecResult Result = thridWeixinAuthManager.thridAuthUrl(backUrl);
		
		res.sendRedirect(Result.getValue().toString());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
