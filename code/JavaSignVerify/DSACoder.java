import sun.misc.BASE64Encoder;
import java.io.IOException;
import sun.misc.BASE64Decoder;
 
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


public class DSACoder extends Coder{
	private static final String ALGORITHM = "DSA";
	private static final int KEY_SIZE = 1024;
	private static final String DEFAULT_SEED = "0F22507A10ALJFLAJFeLAKJDJDFLAKJDFJ";
	private static final String PUBLIC_KEY = "DSAPublicKey";
	private static final String PRIVATE_KEY = "DSAPrivateKey";
	/** 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = initKey(DEFAULT_SEED);
		//甲用私钥签名
		String privateKey = getPrivateKey(map);
		String data ="信息安全试验八";
		System.out.println("签名前的信息是："+data);
		System.out.println("私钥是："+privateKey);
		String sign = sign(data.getBytes(), privateKey);
		System.out.println("签名后的文本是："+sign);
		
		//乙用公钥验证签名
		String publicKey = getPublicKey(map);
		System.out.println("公钥是："+publicKey);
		boolean isVerify = verify(data.getBytes(), publicKey, sign);
		if(isVerify){
			System.out.println("通过验证，签名有效！");
		}
	}
	//初始化密钥对,利用随机产生数产生自己的密钥对
	public static Map<String,Object> initKey(String seed) throws NoSuchAlgorithmException{
		KeyPairGenerator kengen = KeyPairGenerator.getInstance(ALGORITHM);
		SecureRandom random = new SecureRandom();
		random.setSeed(seed.getBytes());
		kengen.initialize(KEY_SIZE, random);
		
		KeyPair keyPair = kengen.genKeyPair();
		DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
		DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PUBLIC_KEY, publicKey);
		map.put(PRIVATE_KEY, privateKey);
		return map;
		
	}
	
	//获得自己的公钥
	public static String getPublicKey(Map<String,Object> map){
		Key key = (Key) map.get(PUBLIC_KEY);
		return encryBase64(key.getEncoded());
	}
	
	//获得自己的私钥
	public static String getPrivateKey(Map<String,Object> map){
		Key key = (Key) map.get(PRIVATE_KEY);
		return encryBase64(key.getEncoded());
	}
	//签名
	public static String sign(byte[] data, String privateKey) throws Exception{
		byte[] privateKeyBytes = decryptBase64(privateKey);
		//利用PKCS8EncodedKeySpec来还原私钥PrivateKey
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initSign(priKey);
		signature.update(data);
		return encryBase64(signature.sign()); 
	}
	//验证签名
	public static boolean verify(byte[] data ,String publicKey,String sign)throws Exception{
		byte[] publicKeyBytes = decryptBase64(publicKey);
		//利用X509EncodedKeySpec来还原公钥PublicKey
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		
		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initVerify(pubKey);
		signature.update(data);
		return signature.verify(decryptBase64(sign));
	}
}
