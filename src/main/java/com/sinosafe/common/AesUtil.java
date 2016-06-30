package com.sinosafe.common;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class AesUtil {
    private static BASE64Encoder base64Encoder;
    private static BASE64Decoder base64Decoder;
    private static IvParameterSpec iv;
    private static String CipherType = "AES/CBC/PKCS5Padding"; //"算法/模式/补码方式


    //测试环境
    private static final String AES_KEY= FileUtils.getAESKey();//"0c26e8d0671d4a15";
    //生产
//    private static final String AES_KEY="e92511edc1634b36";
    private static final String CHARSET="UTF-8";


    static {
        base64Decoder = new BASE64Decoder();
        base64Encoder = new BASE64Encoder();
        iv = new IvParameterSpec("0102030405060708".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
    }

    public static String defaultEncrypt(String sSrc) throws Exception {
        //return URLEncoder.encode(encrypt, CHARSET);
        return AesUtil.Encrypt(sSrc, AES_KEY);
    }
    public static String defaultUrlEncrypt(String sSrc) throws Exception {
        String encrypt = AesUtil.Encrypt(sSrc, AES_KEY);
        return URLEncoder.encode(encrypt, CHARSET);

    }

    public static String defaultDecrypt(String sSrc) throws Exception {
       String decode = URLDecoder.decode(sSrc, CHARSET);
        return Decrypt(decode, AES_KEY);
    }

    // 加密
    public static String Encrypt(String sSrc,String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
//        判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes(CHARSET);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(CipherType);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(CHARSET));

        return base64Encoder.encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc,String sKey) throws Exception {
    	/*sSrc = new String(sSrc.getBytes(),CHARSET);*/
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes(CHARSET);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(CipherType);

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = base64Decoder.decodeBuffer(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return  new String(original,CHARSET);
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }



    
    public static void main(String[] args) throws Exception {
    /*    String points="G8%2FWcqgI74B85v4%2By7WL%2Bw%3D%3D";
       // System.out.println(URLDecoder.decode(points,CHARSET));
        System.out.println(AesUtil.Decrypt(URLDecoder.decode(points, CHARSET),AES_KEY));

        System.out.println(AesUtil.defaultDecrypt(points));*/
        Double a=25.2;
        Double b=25.6;
        System.out.printf(""+a.intValue());
        System.out.printf(""+b.intValue());

        String str ="ISyUAt7h8lGhlYKhgRDE%2BnFDZgPQsLa%2BBadEd8Q%2B94RUM91xIJkEUxuDUzIuD7eg";
        String name="xcooNecSkawjvniAQ07yzg%3D%3D";
      //  String token ="8c00558923f95eeec7bb5462e0b9ac6a";
        System.out.println(AesUtil.defaultDecrypt("Jel2riRR2R9z%2BRxZHp5u5w%3D%3D"));
        System.out.println(AesUtil.defaultDecrypt(name));
        //System.out.println(AesUtil.defaultDecrypt(token));

    }
}