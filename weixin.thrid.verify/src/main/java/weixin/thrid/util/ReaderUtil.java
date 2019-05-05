package weixin.thrid.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ReaderUtil {
	

	/**
	 * <p class="detail">
	 * 功能：读取通知InputStream内容
	 * </p>
	 * @date 2015-12-28 
	 * @param stream
	 * @param length
	 * @return
	 */
	public static final String InputRead(InputStream stream,int length)
	{
		try{
			if( length>0 ){
				//读取回调通知参数，微信通知参数在request.getInputStream()内，使用request.getParameter()无法读取
				DataInputStream input = new DataInputStream(stream);
				byte[] dataOrigin = new byte[length];
				input.readFully(dataOrigin);
				input.close();
				return new String(dataOrigin);
			}
		}catch(IOException ex){
			System.err.println(ex.getMessage());
		}
		return null;
	}
	

	public static final Map<String,String> XmlReader(String xml)
	{
		try{
			Map<String,String> map = new HashMap<String, String>();
			
			Document doc = DocumentHelper.parseText(xml);
            Element nodeElement = doc.getRootElement();
            
			List<?> node = nodeElement.elements();  
            for (Iterator<?> it = node.iterator(); it.hasNext();) {  
                Element elm = (Element) it.next();  
                map.put( elm.getName() , elm.getText() );
                elm = null;  
            }
            node = null;  
            nodeElement = null;  
            doc = null;  
            return map;
		}catch (DocumentException e) {
			System.err.println("Weixin Xml Error :"+e.getMessage());
			System.err.println(xml);
			return null;
		}
	}
}
