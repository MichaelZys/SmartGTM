package com.gaia.dataSource.WQPackage.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    public static String encode(String param)
    {
        if (param == null)
        {
            return null;
        }
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
            byte[] result = md5.digest(param.getBytes("UTF-8"));
            return byte2String(result);
        }
        catch (NoSuchAlgorithmException e)
        {
            return null;
        }
        catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    private static String byte2String(byte[] in)
    {
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(in));
        String str = "";
        try
        {
            for (int j = 0; j < in.length; j++)
            {
                String tmp = Integer.toHexString(data.readUnsignedByte());
                if (tmp.length() == 1)
                {
                    tmp = "0" + tmp;
                }
                str = str + tmp;
            }
        }
        catch (Exception localException)
        {
        }
        return str;
    }

    public static String encode(byte[] b)
    {
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
            byte[] result = md5.digest(b);
            return byte2String(result);
        }
        catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static void main(String[] args)
    {
        System.out.println(encode("gaeaclient-android-000004-001001SOAP测试WSDL接入20121119100955imobii"));
    }
}
