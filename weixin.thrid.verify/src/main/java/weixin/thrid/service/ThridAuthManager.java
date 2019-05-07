package weixin.thrid.service;

import java.net.URLEncoder;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import weixin.thrid.config.WeixinConfig;
import weixin.thrid.entity.ExecResult;
import weixin.thrid.entity.ResponseEntity;
import weixin.thrid.util.ReaderUtil;
import weixin.thrid.util.WeixinHttpUtil;
import weixin.thrid.util.WeixinSecurity;

public class ThridAuthManager {

	/**
	 * 参考文档地址：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1453779503&token=&lang=zh_CN
	 */
	
	
	/**
	 * 生成授权二维码链接
	 * @param backUrl
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public final ExecResult webAuthUrl(String backUrl)
	{
		ExecResult Result = new ExecResult();
		String preAuthCode = preAuthCode();
		if( preAuthCode==null ){
			Result.setMsg("预授权码获取失败！");
			return Result;
		}
		
		//此地址用于生成二维码，客户进行扫码，对其公众号进行授权
		Result.Success( WeixinConfig.WeixinUrl + "componentloginpage?component_appid=" + WeixinConfig.AppID
				+"&pre_auth_code="+preAuthCode
				+"&redirect_uri="+ URLEncoder.encode(backUrl) );
		
		return Result;
	}
	
	/**
	 * 获取预授权码
	 * @return
	 */
	private final String preAuthCode()
	{
		JSONObject data = new JSONObject();
		data.put("component_appid", WeixinConfig.AppID);
		ResponseEntity post = WeixinHttpUtil.Post(WeixinConfig.WeixinUrl, "component/api_create_preauthcode?component_access_token="
				+ WeixinConfig.getAccessToken() , data.toString());
		if( post.getCode()==200 ){
			JSONObject json = JSONObject.parseObject(post.getBody().toString());
			if( json.containsKey("pre_auth_code") ){
				return json.getString("pre_auth_code");
			}
		}
		return null;
	}
	
	/**
	 * 处理授权变更消息
	 */
	public final Map<String,String> authXmlMessageHandle(String msgSignature,String timestamp,String nonce,String xmlData)
	{
		String xmlDeCtyptData = WeixinSecurity.deCrypt(msgSignature, timestamp, nonce, xmlData);
		Map<String,String> xmlMap = ReaderUtil.XmlReader(xmlDeCtyptData);
		/**
		 * <xml>
		 * 		AppId							第三方平台appid
		 * 		CreateTime						时间戳
		 * 		InfoType						unauthorized是取消授权，updateauthorized是更新授权，authorized是授权成功通知
		 * 		AuthorizerAppid						公众号
		 * 		AuthorizationCode				授权码，可用于换取公众号的接口调用凭据，详细见上面的说明
		 * 		AuthorizationCodeExpiredTime	授权码过期时间
		 * </xml>
		 */
		return xmlMap;
	}
	
	
	/**
	 * 通过客户授权回调，接收auth_code，通过auth_code查询对应公众号具体授权信息
	 * auth_code 有效期600秒，5分钟，必须在5分钟内完成授权数据读取存储
	 * 该API用于使用授权码换取授权公众号的授权信息，并换取authorizer_access_token和authorizer_refresh_token。
	 * @param authorizationCode
	 * @return true：授权成功，false：授权失败
	 */
	public final JSONObject apiQueryAuth(String authorizationCode,String userId)
	{
		JSONObject data = new JSONObject();
		data.put("component_appid", WeixinConfig.AppID);
		data.put("authorization_code", authorizationCode);
		ResponseEntity post = WeixinHttpUtil.Post(WeixinConfig.WeixinUrl, "component/api_query_auth?component_access_token="
				+ WeixinConfig.getAccessToken() , data.toString());
		if( post.getCode()==200 )
		{
			JSONObject json = JSONObject.parseObject(post.getBody().toString());
			if( json.containsKey("authorization_info") )
			{
				json = json.getJSONObject("authorization_info");
				
				if( json.getString("authorizer_appid").equals("wx570bc396a51b8ff8")  )
				{// 微信第三方全网发布测试专用
					return json;
				}
				
				//缓存115分钟，有效期2小时（120分钟），必须在达到2小时前进行刷新
				
				//RedisUtil.setVal("access_token_"+json.getString("authorizer_appid"), json.getString("authorizer_access_token"), RedisUtil.MINUTE*115);
				
				/**
				 * authorization_info			授权信息
				 * authorizer_appid				授权方appid
				 * authorizer_access_token		授权方接口调用凭据（在授权的公众号具备API权限时，才有此返回值），也简称为令牌
				 * expires_in					有效期（在授权的公众号具备API权限时，才有此返回值）
				 * authorizer_refresh_token		接口调用凭据刷新令牌（在授权的公众号具备API权限时，才有此返回值），
				 * 								刷新令牌主要用于公众号第三方平台获取和刷新已授权用户的access_token，只会在授权时刻提供，请妥善保存。 
				 * 								一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
				 * func_info
				 * 
				 * TODO 入库操作
				 */
				
				JSONObject authorJson = getAuthorizerInfo( json.getString("authorizer_appid"),userId );
				
				System.out.println( authorJson.toJSONString() );
			}
		}
		return null;
	}
	
	/**
	 * 获取客户授权的公众号的基本信息
	 * @param authorizerAppid
	 * 				客户的公众号appid
	 * @return
	 */
	public final JSONObject getAuthorizerInfo(String authorizerAppid,String userId)
	{
		JSONObject data = new JSONObject();
		data.put("component_appid", WeixinConfig.AppID);
		data.put("authorizer_appid", authorizerAppid);
		ResponseEntity post = WeixinHttpUtil.Post(WeixinConfig.WeixinUrl, "component/api_get_authorizer_info?component_access_token="
				+ WeixinConfig.getAccessToken() , data.toString());
		if( post.getCode()==200 )
		{
			JSONObject json = JSONObject.parseObject(post.getBody().toString());
			if( json.containsKey("authorization_info") )
			{
				/**
				 * nick_name				授权方昵称
				 * head_img					授权方头像
				 * service_type_info		授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
				 * verify_type_info			授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，
				 * 							3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，
				 * 							5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
				 * user_name				授权方公众号的原始ID
				 * principal_name			公众号的主体名称
				 * alias					授权方公众号所设置的微信号，可能为空
				 * business_info			用以了解以下功能的开通状况（0代表未开通，1代表已开通）：
				 *								open_store:是否开通微信门店功能
				 *								open_scan:是否开通微信扫商品功能
				 *								open_pay:是否开通微信支付功能
				 *								open_card:是否开通微信卡券功能
				 *								open_shake:是否开通微信摇一摇功能
				 * qrcode_url				客户公众号的二维码图片的URL，开发者最好自行也进行保存
				 * authorization_info		授权信息
				 * 		appid					授权方appid
				 * 		func_info				公众号授权给开发者的权限集列表，ID为1到15时分别代表：
				 *									1、消息管理权限
				 *									2、用户管理权限
				 *									3、帐号服务权限
				 *									4、网页服务权限
				 *									5、微信小店权限
				 *									6、微信多客服权限
				 *									7、群发与通知权限
				 *									8、微信卡券权限
				 *									9、微信扫一扫权限
				 *									10、微信连WIFI权限
				 *									11、素材管理权限
				 *									12、微信摇周边权限
				 *									13、微信门店权限
				 *									14、微信支付权限
				 *									15、自定义菜单权限
				 */
				
				return json.getJSONObject("authorization_info");
			}
		}
		
		return null;
	}

	// 取消授权
	public void unAuth(String appId){
		
	}
		
}
