
package sanhakin.api.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class EncDecSupportUtil {

    public String encryptAesToHex(String key, String iv, String plain){
        try{
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec=new SecretKeySpec(key.getBytes("UTF-8"),"AES");
            IvParameterSpec ivSpec=new IvParameterSpec(iv.getBytes("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);
            byte[] encrypted=cipher.doFinal(plain.getBytes("UTF-8"));
            return bytesToHex(encrypted);
        }catch(Exception e){throw new RuntimeException(e);}
    }

    public String decryptAesHex(String key,String iv,String hexCipher){
        try{
            byte[] cipherBytes=hexStringToByteArray(hexCipher);
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec=new SecretKeySpec(key.getBytes("UTF-8"),"AES");
            IvParameterSpec ivSpec=new IvParameterSpec(iv.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
            byte[] decrypted=cipher.doFinal(cipherBytes);
            return new String(decrypted,"UTF-8");
        }catch(Exception e){throw new RuntimeException(e);}
    }

    private static byte[] hexStringToByteArray(String s){
        int len=s.length();
        byte[] data=new byte[len/2];
        for(int i=0;i<len;i+=2){
            data[i/2]=(byte)((Character.digit(s.charAt(i),16)<<4)+Character.digit(s.charAt(i+1),16));
        }
        return data;
    }

    private static String bytesToHex(byte[] bytes){
        StringBuilder sb=new StringBuilder();
        for(byte b:bytes) sb.append(String.format("%02X",b));
        return sb.toString();
    }
}
