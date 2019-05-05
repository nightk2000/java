package weixin.thrid.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import weixin.thrid.aes.WXBizMsgCrypt;
import weixin.thrid.config.WeixinConfig;


public class WeixinSecurity {



	/**
	 * 功能：验证回调通知签名
	 * @date 2015-11-19 
	 * @param weixinSign
	 * @param map
	 * @return
	 */
	public static final boolean SignCheck(String requestSign,Map<String,String> map,String ApiKey)
	{
		StringBuffer buff = new StringBuffer();

        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        for(String key:keys){
        	Object value = map.get(key);
        	if( !key.equals("sign") && !key.equals("key") && value!=null && value!="" ){
        		buff.append(key+"="+map.get(key)+"&");
        	}
        }
        buff.append("key="+ApiKey);
        
		return Md5Util.Bit32(buff.toString()).toUpperCase().equals(requestSign);
	}
	public static final boolean SignCheck(Map<String,String> map,String ApiKey)
	{
		String sign = map.get("sign");
		if( sign==null ){
			return false;
		}
		map.remove("sign");
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        StringBuffer buff = new StringBuffer();
        for(String key:keys){
        	Object value = map.get(key);
        	if( !key.equals("sign") && !key.equals("key") && value!=null && value!="" ){
        		buff.append(key+"="+map.get(key)+"&");
        	}
        }
        buff.append("key="+ApiKey);
        return Md5Util.Bit32(buff.toString()).toUpperCase().equals(sign);
	}
	
	
	/**
	 * 微信异步通知解码
	 * @param msgSignature
	 * 		req.getParameter(msg_signature)
	 * @param timestamp
	 * 		req.getParameter(timestamp)
	 * @param nonce
	 * 		req.getParameter(nonce)
	 * @param xmlData
	 * 		req.getInputStream()
	 * @return
	 */
	public static final String deCrypt(String msgSignature,String timestamp,String nonce,String xmlData)
	{
		try{
			WXBizMsgCrypt pc = new WXBizMsgCrypt(WeixinConfig.Token, WeixinConfig.AesKey, WeixinConfig.AppID);
			String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, xmlData);
//			System.out.println("解密后明文: " + result2);
			return result2;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public static final String encrypt(String xmlMsg)
	{
		String timestamp = String.valueOf(System.currentTimeMillis()/1000);
		String nonce = RandStr(10);
		try{
			WXBizMsgCrypt pc = new WXBizMsgCrypt(WeixinConfig.Token, WeixinConfig.AesKey, WeixinConfig.AppID);
			String mingwen = pc.encryptMsg(xmlMsg, timestamp, nonce);
			return mingwen;
		}catch(Exception ex){
			
		}
		return null;
	}
	
	/**
	 * Rand the abcABC012......
	 * @param length
	 * @return
	 */
	public static final String RandStr(int length){
		Random Rand = new Random();
		char[] RandStr = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").toCharArray();
		String rand = "";
		for(int i=0,s=RandStr.length;i<length;i++){
			rand = rand + RandStr[Rand.nextInt(s)];
		}
		return rand;
	}
}
