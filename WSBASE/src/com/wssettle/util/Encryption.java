package com.wssettle.util;



import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base64;
/**
 * 需要的jar:
 * bcprov-ext-jdk15on-149.jar
 * bcprov-jdk15on-149.jar
 * commons-codec-1.10.jar
 * @author cs
 *
 */
public class Encryption {
	/**
	 * des 获得key
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static Key getKeyDes(){
		try{
		//生成key
		KeyGenerator keyGenerator=KeyGenerator.getInstance("DES");
		keyGenerator.init(56);
		SecretKey secretKey=keyGenerator.generateKey();
		byte[] bytesKey=secretKey.getEncoded();
		
		//key转换
		DESKeySpec desKeySpec=new DESKeySpec(bytesKey);
		SecretKeyFactory factory=SecretKeyFactory.getInstance("DES");
		Key convertSecretKey=factory.generateSecret(desKeySpec);
		return convertSecretKey;
		}catch(Exception e){
			return null;
		}
		
	
	}
	/**
	 * 通过des 进行加密
	 * @return
	 */
	public static String encrytionByDes(String str){
		try{
			Key convertSecretKey=getKeyDes();
			if(convertSecretKey==null) return str;
			//加密
			
			Cipher cipher=Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE,convertSecretKey);
			byte[] result=cipher.doFinal(str.getBytes());
			return Hex.encodeHexString(result);
		}catch(Exception e){
			System.out.println("加密失败，出错了！,返回原字符串");
			return str;
		}
	}
	
	/**
	 * des 解密
	 * @param str
	 * @return
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 *//*
	public static String decryptByDes(String str){
		//获得key
		Key convertSecretKey=getKeyDes();
		//解密
		try{
		Cipher cipher=Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
		//byte[] result=cipher.doFinal(str.getBytes());
		byte[] result=cipher.doFinal((str));
		return new String(result,"UTF-8");  
		}catch(Exception e){
			System.out.println(e.getMessage());
			return str;
		}
	}*/
	
	/**
	 * 基于base64加密
	 * @param str
	 * @return
	 */
	public static String encodeByBase64(String str){
		return new String(Base64.encode(str.getBytes()));
	}
	/**
	 * 基于base64解密
	 * @param str
	 * @return
	 */
	public static String decodeBase64(String str){
		return new String(Base64.decode(str.getBytes()));
	}
	
	/** 
     * DES算法密钥 
     */  
    private static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128, -65 };  
    /** 
     * 数据加密，算法（DES） 
     * 
     * @param data 
     *            要进行加密的数据 
     * @return 加密后的数据 
     */  
    public static String encryptBasedDes(String data) {  
        String encryptedData = null;  
        try {  
            // DES算法要求有一个可信任的随机数源  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(DES_KEY);  
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey key = keyFactory.generateSecret(deskey);  
            // 加密对象  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);  
            // 加密，并把字节数组编码成字符串  
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));  
        } catch (Exception e) {  
//            log.error("加密错误，错误信息：", e);  
            throw new RuntimeException("加密错误，错误信息：", e);  
        }  
        return encryptedData;  
    }  
    /** 
     * 数据解密，算法（DES） 
     * 
     * @param cryptData 
     *            加密数据 
     * @return 解密后的数据 
     */  
    public static String decryptBasedDes(String cryptData) {  
        String decryptedData = null;  
        try {  
            // DES算法要求有一个可信任的随机数源  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(DES_KEY);  
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey key = keyFactory.generateSecret(deskey);  
            // 解密对象  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.DECRYPT_MODE, key, sr);  
            // 把字符串解码为字节数组，并解密  
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));  
        } catch (Exception e) {  
//            log.error("解密错误，错误信息：", e);  
            throw new RuntimeException("解密错误，错误信息：", e);  
        }  
        return decryptedData;  
    } 
}
