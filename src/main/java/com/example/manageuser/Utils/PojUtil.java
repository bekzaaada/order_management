package com.example.manageuser.Utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class PojUtil {

    private static final Logger log = LoggerFactory.getLogger(PojUtil.class);
    private static final String hi = "f7T8glE(M9bS6dLt";



    public static String String2SHA256Str(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    public static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
    public static String base64_encode(byte[] data)
    {
        org.apache.tomcat.util.codec.binary.Base64 base64 = new org.apache.tomcat.util.codec.binary.Base64();
        return new String(base64.encode(data));
    }
    public static String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try
        {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally
        {
            pw.close();
        }
    }

    public static byte[] base64_decode(String bstr)
    {
        org.apache.tomcat.util.codec.binary.Base64 base64 = new Base64();
        return base64.decode(bstr.getBytes(StandardCharsets.UTF_8));
    }
    public static String generateRandomString(int len) {
        String chars = "1234567890-=!@#$%^&*()_+qwertyuiop[]QWERTYUIOP{}|asdfghjkl;'ASDFGHJKL:zxcvbnm,./ZXCVBNM<>?";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }


    public static String random_number(int len)
    {
        String chars = "1234567890";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
    public static boolean password_check(String password)
    {
        return password != null && password.length() >= 6 && password.length() <= 32;
    }

    public static String random_uuid()
    {
        return UUID.randomUUID().toString();
    }

    public static Date format2date(String str_date)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            return  simpleDateFormat.parse(str_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String timestamp_sec_to_str(long timestamp)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(timestamp*1000);
        return simpleDateFormat.format(date);
    }

    public static Date timestamp_sec_to_date(long timestamp)
    {
        return new Date(timestamp*1000);
    }

    public static String timeid()
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");

        return simpleDateFormat.format(new Date());
    }

    public static boolean inarray(String src,String[] array)
    {
        for(int i=0;i<array.length;++i)
            if(src.equals(array[i]))
                return true;
        return false;
    }

    public static String sha256password_hash(String password)
    {
        String generatedPassword = null;
        String salt="be44361a-96c2-4edd-8789-9d0b1593af61";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static float random(float begin,float end)
    {
        Random r=new Random();
        return r.nextFloat()*(end-begin)+begin;
    }

    private boolean isNullOrEmpty(Object obj)
    {
        if(obj==null)
            return true;
        return obj.toString().trim().isEmpty();
    }


}
