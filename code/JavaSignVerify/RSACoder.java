import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.Cipher;


/**
 * 
 *  针对RSA加密中，各种 padding 对输入数据长度的要求：比如1024位的，明文长度不能超过117。
	1024位=1024bit=128byte，128-11=117。

	私钥加密：
	RSA_PKCS1_PADDING RSA_size-11
	RSA_NO_PADDING RSA_size-0
	RSA_X931_PADDING RSA_size-2
	公钥加密
	RSA_PKCS1_PADDING RSA_size-11
	RSA_SSLV23_PADDING RSA_size-11
	RSA_X931_PADDING RSA_size-2
	RSA_NO_PADDING RSA_size-0
	RSA_PKCS1_OAEP_PADDING RSA_size-2 * SHA_DIGEST_LENGTH-2
 */
public class RSACoder extends Coder{
	public static final String KEY_AlGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final String PUBLICKEY = "RSAPublicKey";
	private static final String PRIVATEKEY = "RSAPrivateKey";
	public static final String MODE = "RSA/ECB/PKCS1Padding";
	//RSA只支持ECB密码本加密模式，并且只支持NoPadding和PKCS1Padding
	//和"RSA/ECB/OAEPWITHMD5ANDMGF1PADDING", 
	//和"RSA/ECB/OAEPWITHSHA1ANDMGF1PADDING", 
	//"RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING".
	
	//甲用私钥签名
	public static String sign(byte[] data,String privateKey) throws Exception{
		long oldTime = System.currentTimeMillis();
		byte[] keyBytes = decryptBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_AlGORITHM);
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
		String result = encryBase64(signature.sign());
		long currentTime = System.currentTimeMillis();
		System.out.println("甲用私钥签名所用的时间："+(currentTime-oldTime));
		return result;
		
	}
	//甲用私钥验证签名
	public static Boolean verifySign(byte[] data,String publicKey,String sign) throws Exception{
		long oldTime = System.currentTimeMillis();
		byte[] keyBytes = decryptBase64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_AlGORITHM);
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		Boolean isVerified = signature.verify(decryptBase64(sign));
		long currentTime = System.currentTimeMillis();
		System.out.println("甲用私钥验证签名所用的时间："+(currentTime-oldTime));
		return isVerified;
	}
	//甲用私钥加密信息，传给乙
	public static String encryptByPrivateKey(byte data[],String priKey) throws Exception{
		long oldTime = System.currentTimeMillis();
		byte[] keyBytes = decryptBase64(priKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_AlGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(MODE);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		String result = encryBase64(cipher.doFinal(data));
		long currentTime = System.currentTimeMillis();
		System.out.println("甲用私钥加密信息所用的时间："+(currentTime-oldTime));
		return result;
	}
	
	//双重加密，几乎很难做到，因为密钥的长度和明文的长度之间有一定的限制。
	public static String doubleEncryptByPrivateKey(byte data[],String priKey) throws Exception{
		return encryptByPrivateKey(encryptByPrivateKey(data, priKey).getBytes(),priKey);
	}
	
	//甲用私钥解密乙传来的信息
	public static byte[] decryptByPrivateKey(String data,String priKey) throws Exception{
		long oldTime = System.currentTimeMillis();
		byte[] keyBytes = decryptBase64(priKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_AlGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(MODE);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte result[] = cipher.doFinal(decryptBase64(data));
		long currentTime = System.currentTimeMillis();
		System.out.println("甲用私钥解密信息所用的时间："+(currentTime-oldTime));
		return result;
	}
	//乙用公钥加密
	public static String encryptByPublicKey(byte data[],String pubKey) throws Exception{
		long oldTime = System.currentTimeMillis();
		byte[] keyBytes = decryptBase64(pubKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_AlGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		Cipher cipher = Cipher.getInstance(MODE);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		String result = encryBase64(cipher.doFinal(data));
		long currentTime = System.currentTimeMillis();
		System.out.println("乙用公钥加密信息所用的时间："+(currentTime-oldTime));
		return result;
	}
	//乙用公钥解密甲传来的信息
	public static byte[] decryptByPublicKey(String data,String pubKey) throws Exception{
		long oldTime = System.currentTimeMillis();
		byte[] keyBytes = decryptBase64(pubKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_AlGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		Cipher cipher = Cipher.getInstance(MODE);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte result[] = cipher.doFinal(decryptBase64(data));
		long currentTime = System.currentTimeMillis();
		System.out.println("乙用公钥加密信息所用的时间："+(currentTime-oldTime));
		return result;
	}
	//获取公钥
	public static String getPrivateKey(Map<String ,Object> keyMap){
		RSAPrivateKey key = (RSAPrivateKey)keyMap.get(PRIVATEKEY);
		return encryBase64(key.getEncoded()); 
	}
	//获得私钥
	public static String getPublicKey(Map<String,Object> keyMap){
		RSAPublicKey key = (RSAPublicKey)keyMap.get(PUBLICKEY);
		return encryBase64(key.getEncoded());
	}
	//获得公钥和私钥
	public static Map<String,Object> initKey(int keyLen) throws NoSuchAlgorithmException{
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(KEY_AlGORITHM);
		keyGenerator.initialize(keyLen);
		KeyPair keyPair = keyGenerator.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PRIVATEKEY, privateKey);
		map.put(PUBLICKEY, publicKey);
		return map;
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("请输入密钥长度：");
		Scanner scanner = new Scanner(System.in);
		
		int keyLen = scanner.nextInt();
		System.out.println("甲————————————————————————————————————————>乙,此时密钥长度为："+keyLen+"\n");
		testA(keyLen);
		System.out.println("\n"+"乙————————————————————————————————————————>甲,此时密钥长度为："+keyLen+"\n");
		testB(keyLen);

	}
	public static void testA(int keyLen) throws Exception{
		//甲利用自己的私钥加密数据
		String plainText = "h!";
		System.out.println("加密前明文是："+plainText);
		//甲获得私钥
		long oldTime = System.currentTimeMillis();
		Map<String,Object> map = initKey(keyLen);
		String privateKey = getPrivateKey(map);
		long currentTime = System.currentTimeMillis();
		System.out.println("获得私钥的时间："+(currentTime-oldTime));
		
		String cipherText = encryptByPrivateKey(plainText.getBytes(), privateKey);
		System.out.println("甲用私钥加密后的密文是："+cipherText);
		
		//String cipherText1 = doubleEncryptByPrivateKey(plainText.getBytes(), privateKey);
		//System.out.println("甲用私钥两次加密后的密文是："+cipherText1);
		//甲用自己的私钥签名
		String sign = sign(plainText.getBytes(), privateKey);
		System.out.println("甲用私钥签名后为:"+sign);
	
		//乙获得甲的密文后用甲的公钥解密
		String publicKey = getPublicKey(map);
		if(verifySign(plainText.getBytes(), publicKey, sign)){
			plainText = new String(decryptByPublicKey(cipherText, publicKey));
			System.out.println("乙用甲的公钥解密后明文是："+plainText);
		}
	}
	public static void testB(int keyLen) throws Exception{
		long oldTime = System.currentTimeMillis();
		Map<String,Object> map = initKey(keyLen);
		//乙利用甲的公钥加密后发送给甲
		String plainText = "乙传来的数据！ahahahah!";
		System.out.println("加密前明文是："+plainText);
		String publicKey = getPublicKey(map);
		long currentTime = System.currentTimeMillis();
		System.out.println("获得公钥的时间："+(currentTime-oldTime));
		
		String cipherText = encryptByPublicKey(plainText.getBytes(), publicKey);
		System.out.println("乙用甲的公钥加密后密文是："+cipherText);
		//甲获得私钥，并利用自己的私钥来解密
		String privateKey = getPrivateKey(map);
		plainText = new String(decryptByPrivateKey(cipherText, privateKey));
		System.out.println("甲用自己的私钥解密后的明文是："+plainText);
	
}
		
}
