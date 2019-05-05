package weixin.thrid.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5Util {

	public static final String Md5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			return new BigInteger(1, md.digest()).toString(16).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static final String Bit32(String str){
		try{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		}catch(Exception e){
			return null;
		}
	}
	
	public static final String Bit16(String str){
		String m32 = Bit32(str);
		return m32!=null?m32.substring(8, 24):null;
	}
	
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private static final String toHexString(byte[] b) {
	    StringBuilder sb = new StringBuilder(b.length * 2);
	    for (int i = 0; i < b.length; i++) {
	        sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
	        sb.append(HEX_DIGITS[b[i] & 0x0f]);
	    }
	    return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println( Md5("123456") );
		System.out.println( Bit32("123456") );
		System.out.println( Bit16("123456") );
	}
}
