import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NullCipher;

import sun.security.ec.ECKeyFactory;
import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.ECPublicKeyImpl;

public class ECCCoder extends Coder {
	public static final String ALGORITHM = "EC";
	public static final String PUBLIC_KEY ="ECCPublicKey";
	public static final String PRIVATE_KEY = "ECCPrivateKey";
	/**
	 * @param args
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeySpecException 
	 */
	public static void main(String[] args) throws InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		// TODO Auto-generated method stub
		System.out.println("——————————这是利用ECC椭圆曲线来加密！");
		System.out.println("——————————乙用甲的公钥来加密信息——————————");
		String data = "我是乙，我用你的公钥加密信息然后发给你，你是甲！";
		System.out.println("加密前明文是："+data);
		
		//乙获得甲的公钥
		Map<String ,Object> map = initKey();
		String publicKey = getPulblicKey(map);
		System.out.println("甲的公钥是："+publicKey);
		String encrypedText = encrypt(data.getBytes(), publicKey);
		System.out.println("加密后文本是："+encrypedText);
		
		//甲利用自己的私钥来解密信息
		System.out.println("——————————甲用自己的私钥解密信息！————————");
		String privateKey = getPrivateKey(map);
		System.out.println("甲的私钥是："+privateKey);
		byte[] decryptedText = decrypt(encrypedText.getBytes(), privateKey);
		System.out.println("解密后的文本是："+new String(decryptedText));
	}
	public static Map<String,Object> initKey() throws InvalidKeyException{
		BigInteger x1 = new BigInteger("2fe13c0537bbc11acaa07d793de4e6d5e5c94eee8",16);
		BigInteger x2 = new BigInteger("289070fb05d38ff58321f2e800536d538ccdaa3d9",16);
		ECPoint g = new ECPoint(x1,x2);
		
		BigInteger n = new BigInteger("5846006549323611672814741753598448348329118574063",10);
		int h = 2;
		int m = 163;
		int[] ks = {7,6,3};
		ECFieldF2m ecField = new ECFieldF2m(m,ks);
		BigInteger a = new BigInteger("1",2);
		BigInteger b = new BigInteger("1",2);
		EllipticCurve elliptcCurve = new EllipticCurve(ecField,a,b);
		
		ECParameterSpec ecParameterSpec = new ECParameterSpec(elliptcCurve,g,n,h);
		ECPublicKey publicKey = new ECPublicKeyImpl(g,ecParameterSpec);
		BigInteger s = new BigInteger("1234006549323611672814741753598448348329118574063",10);
		ECPrivateKey privateKey = new ECPrivateKeyImpl(s,ecParameterSpec);
		
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put(PUBLIC_KEY, publicKey);
		map.put(PRIVATE_KEY, privateKey);
		
		return map;
		
	}
	
	public static String getPulblicKey(Map<String,Object> map){
		Key key = (Key)map.get(PUBLIC_KEY);
		return encryBase64(key.getEncoded());
	}
	
	public static String getPrivateKey(Map<String,Object> map){
		Key key = (Key)map.get(PRIVATE_KEY);
		return encryBase64(key.getEncoded());
	}
	//乙用公钥加密信息
	public static String encrypt(byte[] data ,String publicKey) throws IOException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
	  byte pubKeyBytes[] = decryptBase64(publicKey);
	  X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
	  KeyFactory keyFactory = ECKeyFactory.INSTANCE;
	  ECPublicKey pubKey = (ECPublicKey)keyFactory.generatePublic(x509KeySpec);
	  ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(pubKey.getW(),pubKey.getParams());
	  
	  Cipher cipher = new NullCipher();
	  cipher.init(Cipher.ENCRYPT_MODE, pubKey, ecPublicKeySpec.getParams());
	  return encryBase64(cipher.doFinal(data));
	}
	//甲用私钥解密
	public static byte[] decrypt(byte[] data,String privateKey) throws IOException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		byte[] keyBytes = decryptBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = ECKeyFactory.INSTANCE;
		ECPrivateKey priKey = (ECPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
		ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(priKey.getS(),priKey.getParams());
		
		Cipher cipher = new NullCipher();
		cipher.init(Cipher.DECRYPT_MODE, priKey, ecPrivateKeySpec.getParams());
		return cipher.doFinal(data);
	}
	
}
