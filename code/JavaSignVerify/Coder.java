import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Coder {
	public static String encryBase64(byte key[]){
		return (new BASE64Encoder()).encodeBuffer(key);
	}
	
	public static byte[] decryptBase64(String key) throws IOException{
		return (new BASE64Decoder()).decodeBuffer(key);
	}
}
