package net.t1y.cloud3.sdk.util;
import android.os.Build;
import android.util.Base64;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
    public static final int MD5_16 = 16;
    public static final int MD5_32 = 32;
    public static final String AES_CBC_P7 =  "AES/CBC/PKCS7Padding";
    public static final String AES_CBC_P5 = "AES/CBC/PKCS5Padding";
    public static Encode getEncoder(){
        return new Encode();
    }
    public static Decode getDecoder(){
        return new Decode();
    }
    public static class Encode{
        public String aes(String iv,String cip,String key,String text){
            return Aes.encode(iv, cip, key, text);
        }
        public String base64(byte[] s){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
                return java.util.Base64.getEncoder().encodeToString(s);
            }
            return android.util.Base64.encodeToString(s, Base64.NO_WRAP);
        }
        public String md5(String str,int length){
            try {
                byte[] secretBytes = MessageDigest.getInstance("md5").digest(str.getBytes());
                String md5code = new BigInteger(1, secretBytes).toString(16);
                for (int i = 0; i < 32 - md5code.length(); i++) {
                    md5code = "0" + md5code;
                }
                if(length == MD5_16)return md5code.substring(8,24);
                return md5code;
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("没有这个md5算法！");
            }
        }
    }
    public static class Decode{
        public byte[] base64(String s){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
                return java.util.Base64.getDecoder().decode(s);
            }
            return android.util.Base64.decode(s,Base64.DEFAULT);
        }
        public String aes(String iv,String cip,String key,String text){
            return Aes.decode(iv, cip, key, text);
        }
    }
    static class Aes{
        public static String encode(String iv,String cip,String key,String text) {
            try {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
                Cipher cipher = Cipher.getInstance(cip);
                cipher.init(1, secretKeySpec, ivParameterSpec);
                byte[] encryptedData = cipher.doFinal(text.getBytes());
                return getEncoder().base64(encryptedData);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        public static String decode(String iv,String key,String cip, String text) {
            try {
                byte[] decodeByte = getDecoder().base64(text);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
                Cipher cipher = Cipher.getInstance(cip);
                cipher.init(2, secretKeySpec, ivParameterSpec);
                byte[] bytes = cipher.doFinal(decodeByte);
                return new String(bytes);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
