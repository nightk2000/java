package weixin.thrid.servlet;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = -5913330557917389713L;

	protected static final void outText(HttpServletRequest req, HttpServletResponse res, String body) {
		outPrint(req, res, "text/plain;charset=utf-8", body);
	}

	protected static final void outJson(HttpServletRequest req, HttpServletResponse res, String body) {
		outPrint(req, res, "text/json;charset=utf-8", body);
	}

	protected static final void outXml(HttpServletRequest req, HttpServletResponse res, String body) {
		outPrint(req, res, "text/xml;charset=utf-8", body);
	}

	private static final void outPrint(HttpServletRequest req, HttpServletResponse res, String contentType,
			String body) {
		res.setContentType(contentType);
		// res.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = res.getWriter();
			out.println(body);
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static final String ID24(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date())+RandNum(7);
	}
	
	/**
	 * Rand number
	 * @param length
	 * @return
	 */
	public static final String RandNum(int length){
		Random Rand = new Random();
		char[] RandStr = ("0123456789").toCharArray();
		String rand = "";
		for(int i=0,s=RandStr.length;i<length;i++){
			rand = rand + RandStr[Rand.nextInt(s)];
		}
		return rand;
	}
	
	/**
	 * Rand the abcABC...
	 * @param length
	 * @return
	 */
	public static final String RandAbc(int length){
		Random Rand = new Random();
		char[] RandStr = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		String rand = "";
		for(int i=0,s=RandStr.length;i<length;i++){
			rand = rand + RandStr[Rand.nextInt(s)];
		}
		return rand;
	}

}
