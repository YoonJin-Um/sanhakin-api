package sanhakin.api.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/*
 * Support Encryption and Decription Utility
 * List
 * 		AES Encryption And Decription
 * by JaeHyuk 2019.11.18
 */
public class EncDecSupportUtil {
	
	/**
	 * AES - String is encrypted with AES and returned to Hex
	 * @param keyString
	 * @param initialVectorParam
	 * @param target
	 */
	public String EncryptAesStringToHex(String keyString, String initialVectorParam, String target) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException  {
		SecretKeySpec key = new SecretKeySpec(keyString.getBytes(), "AES");
		IvParameterSpec initalVector = new IvParameterSpec(initialVectorParam.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");		// AES/CFB/NoPadding | AES/CFB8/NoPadding
		cipher.init(Cipher.ENCRYPT_MODE, key, initalVector);
		byte[] byteData = cipher.doFinal(target.getBytes());
		
		return bytes2Hex(byteData);
	}
	/**
	 * AES - String Hex is decrypted with AES and returned to String
	 * @param keyString
	 * @param initialVectorParam
	 * @param target
	 */
	public String DecryptAesStringToHex(String keyString,String initialVectorParam, String target) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec key = new SecretKeySpec(keyString.getBytes(), "AES");
		IvParameterSpec initalVector = new IvParameterSpec(initialVectorParam.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");		// AES/CFB/NoPadding | AES/CFB8/NoPadding
		cipher.init(Cipher.DECRYPT_MODE, key, initalVector);
		byte[] byteData = cipher.doFinal(hex2byte(target)); 

		return new String(byteData);
	}
	
	private static byte[] hex2byte(String s){
		if(s == null) return null;
		int l = s.length();
		if(l%2 == 1) return null;
		byte[] b = new byte[l/2];
		for(int i = 0 ; i < l/2 ;i++) {
			b[i] = (byte)Integer.parseInt(s.substring(i*2,i*2+2),16);
		}
		return b;
	}
	
	private static String byte2Hex(byte b) {
		String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};    
		int nb = b & 0xFF;
		int i_1 = (nb >> 4) & 0xF;
		int i_2 = nb & 0xF;
		return HEX_DIGITS[i_1] + HEX_DIGITS[i_2];
	}
	
	private static String bytes2Hex(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int x = 0; x < b.length; x++) {
			sb.append(byte2Hex(b[x]));
		}
		return sb.toString();
	}
}