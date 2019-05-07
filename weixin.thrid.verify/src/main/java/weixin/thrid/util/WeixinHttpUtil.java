package weixin.thrid.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import weixin.thrid.entity.ResponseEntity;

/**
 * 
 * @ClassName: HttpUtil 
 * @version V1.0  
 * @date 2016-8-17 
 */
public class WeixinHttpUtil {

	public static final ResponseEntity Post(String baseUrl,String uri,String data)
	{
		//System.out.println("==========================================");
		//System.out.println(String.format("POST,URL:%s\nPOST,Data:%s",uri,data));
		ResponseEntity Result = new ResponseEntity();
		try {
			String hostUrl = baseUrl + uri;
			URL url = new URL(hostUrl);
			Result = doRequest(url, "POST", data);
		} catch (MalformedURLException e) {
			Result.setMessage( e.getMessage() + "\r\n" + e.getLocalizedMessage() );
		}
		//System.out.println( "Result:"+Result.getBody() );
		return Result;
	}
	
	public static final ResponseEntity Get(String urlStr)
	{
		ResponseEntity Result = new ResponseEntity();
		try {
			URL url = new URL(urlStr);
			Result = doRequest(url, "GET", null);
		} catch (MalformedURLException e) {
			Result.setMessage( e.getMessage() + "\r\n" + e.getLocalizedMessage() );
		}
		return Result;
	}
	
	private static final ResponseEntity doRequest(URL url, String method,String data)
	{
		ResponseEntity Result = new ResponseEntity();
		//关闭https证书验证，默认通过，否则https类链接可能会报错
		HttpsURLConnection.setDefaultHostnameVerifier(HttpsCertClose.hv);
		HttpURLConnection httpConn = null;
		try{
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod(method);	// POST/GET
			httpConn.setConnectTimeout(10000);		// client timeout : 10s
			httpConn.setReadTimeout(20000);			// return timeout : 20s
			
			//httpConn.setRequestProperty("User-Agent", UserAgent);
			
			//设置cookie
			//if( Cookie!=null && Cookie.length()>=3 && Cookie.indexOf("=")>0 ){//至少3个字符，多个使用分号分隔，[参数名]=[参数];......
			//	httpConn.setRequestProperty("Cookie", Cookie);
			//}
			
			if( data!=null && data.length()>0 ){
				OutputStream out = httpConn.getOutputStream();
				out.write(data.getBytes("utf-8"));
				out.flush();
				out.close();
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
			String line = null;
			while((line=reader.readLine())!=null){
				Result.append( line +"\n" );
			}
			reader.close();
			
			if(Result.getBody().length()>2){
				Result.getBody().setLength(Result.getBody().length()-1);
			}
			
			Result.setCode( httpConn.getResponseCode() );
			Result.setCharset( httpConn.getContentEncoding() );
			Result.setMessage( httpConn.getResponseMessage() );
			
	    }catch(Exception e){
	    	Result.setMessage( e.getMessage() + "\r\n" + e.getLocalizedMessage() );
	    	//e.printStackTrace();
	    	System.err.println("HttpURLConnection, "+url+", "+e.getLocalizedMessage());
	    }finally{
	    	if(httpConn!=null)httpConn.disconnect();
	    }
		return Result;
	}
	
	public static final ResponseEntity upload(String hostUrl,String accessToken,String type,byte[] media)
	{
		ResponseEntity Result = new ResponseEntity();
		//关闭https证书验证，默认通过，否则https类链接可能会报错
		HttpsURLConnection.setDefaultHostnameVerifier(HttpsCertClose.hv);
		HttpURLConnection httpConn = null;
		try{
			URL url = new URL(hostUrl + "?access_token="+accessToken+"&type="+type);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");	// POST/GET
			httpConn.setConnectTimeout(10000);		// client timeout : 10s
			httpConn.setReadTimeout(20000);			// return timeout : 20s
			httpConn.setUseCaches(false);		// post can't use cache
			
			httpConn.setRequestProperty("Connection", "Keep-Alive");  
			httpConn.setRequestProperty("Charset", "UTF-8");
			
			// 设置分隔线
			String BOUNDARY = "----------" + System.currentTimeMillis();  
			httpConn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+ BOUNDARY);
			
			// 设置请求头
			StringBuilder sb = new StringBuilder();  
			sb.append("--"); // 必须多两道线  
			sb.append(BOUNDARY);  
			sb.append("\r\n");  
			sb.append("Content-Disposition: form-data;name=\""+type+"\";filelength=\""
					+media.length+"\";filename=\""+ System.currentTimeMillis() + (type.equals("image")?".jpg":".amr") + "\"\r\n");  
			sb.append("Content-Type:application/octet-stream\r\n\r\n");  
			byte[] head = sb.toString().getBytes("utf-8"); 
			
			OutputStream out = new DataOutputStream(httpConn.getOutputStream());
			// 写请求头
			out.write(head);
			// 写文件内容
			out.write(media);
			
			// 定义结尾
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
			out.write(foot);
			out.flush();  
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
			String line = null;
			while((line=reader.readLine())!=null){
				Result.append( line +"\n" );
			}
			reader.close();
			
			if(Result.getBody().length()>2){
				Result.getBody().setLength(Result.getBody().length()-1);
			}
			
			Result.setCode( httpConn.getResponseCode() );
			Result.setCharset( httpConn.getContentEncoding() );
			Result.setMessage( httpConn.getResponseMessage() );
			
	    }catch(Exception e){
	    	Result.setMessage( e.getMessage() + "\r\n" + e.getLocalizedMessage() );
	    	//e.printStackTrace();
	    	System.err.println("HttpURLConnection, "+hostUrl+", "+e.getLocalizedMessage());
	    }finally{
	    	if(httpConn!=null)httpConn.disconnect();
	    }
		return Result;
	}

	
	public static final ResponseEntity download(String url, String method,String data)
	{
		ResponseEntity Result = new ResponseEntity();
		//关闭https证书验证，默认通过，否则https类链接可能会报错
		HttpsURLConnection.setDefaultHostnameVerifier(HttpsCertClose.hv);
		HttpURLConnection httpConn = null;
		ByteArrayOutputStream outStream = null;
		try{
			URL uri = new URL(url);
			httpConn = (HttpURLConnection) uri.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod(method);	// POST/GET
			httpConn.setConnectTimeout(10000);		// client timeout : 10s
			httpConn.setReadTimeout(20000);			// return timeout : 20s
			
			if( data!=null && data.length()>0 ){
				httpConn.getOutputStream().write(data.getBytes("UTF-8"));
				httpConn.getOutputStream().flush();
				httpConn.getOutputStream().close();
			}
			
			InputStream in = httpConn.getInputStream();
			outStream = new ByteArrayOutputStream();  
			int length;
			byte[] buffer = new byte[ 1024 * 8 ];
			while ((length = in.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			in.close();
			
			Result.setBytes(outStream.toByteArray());
			Result.setCode( httpConn.getResponseCode() );
			Result.setCharset( httpConn.getContentEncoding() );
			Result.setMessage( httpConn.getResponseMessage() );
			Result.setHeaders( httpConn.getHeaderFields()); 
			
	    }catch(Exception e){
	    	Result.setMessage( e.getMessage() + "\r\n" + e.getLocalizedMessage() );
	    	//e.printStackTrace();
	    	System.err.println("HttpURLConnection, "+url+", "+e.getLocalizedMessage());
	    }finally{
	    	if(httpConn!=null)httpConn.disconnect();
	    }
		return Result;
	}
}
