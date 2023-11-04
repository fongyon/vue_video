package com.example.vue_video.util;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

public class MD5Util {

    private static final String hexDigIts[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    private static final String[] slatArray=new String[26+26+10];

    static{
        for(int i=0;i<=9;i++){
            slatArray[i]=i+"";
        }
        //A-Z
        for(int i=10;i<36;i++){
            slatArray[i]=(char)(i+55)+"";
        }
        //a-z  36+26=53    97-36=61
        for(int i=36;i<62;i++){
            slatArray[i]=(char)(i+61)+"";
        }
    }

    /**
     * 默认得到5个随机字符
     * @return
     */
    public static String createSlat(){
        return createSlat(5);
    }

    public static String createSlat(int num){
        //0-127  (97 +25)  小写a-z    A -z65+25  0-9
        StringBuffer str=new StringBuffer();
        Random  rd=new Random();
        for(int i=1;i<=num;i++){
            str.append(  slatArray[rd.nextInt(slatArray.length)] );
        }
        return str.toString();
    }

    public static String MD5Encode(String origin, String slat){
        return MD5Encode(origin,slat,"UTF-8");
    }

    /**
     * MD5加密
     * @param origin 明文密码
     * @param slat 盐
     * @param charsetname 编码
     * @return
     */
    public static String MD5Encode(String origin, String slat,String charsetname){
        String resultString = null;
        try{
            resultString = new String(origin+"_wisezone_"+slat);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(null == charsetname || "".equals(charsetname)){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }else{
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        }catch (Exception e){
        }
        return resultString;
    }


    public static String byteArrayToHexString(byte b[]){
        StringBuffer resultSb = new StringBuffer();
        for(int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b){
        int n = b;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

    public static void main(String[] args) {
        System.out.println( );

        System.out.println(MD5Util.MD5Encode("888888","ab1256"));
    }

    /**
     * 取得的密码串给文件新命名
     * @return
     */
    public static String MD5EncodePath(){
        String fileUUID= UUID.randomUUID().toString();
        return  MD5Encode(fileUUID,createSlat(5));
    }
}
