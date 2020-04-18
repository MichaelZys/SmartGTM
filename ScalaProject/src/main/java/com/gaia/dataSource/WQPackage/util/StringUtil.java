package com.gaia.dataSource.WQPackage.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUtil {

    private static final char[] CODE_CHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz_0123456789"
            .toCharArray();
    public static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmSS");

    public static String getTimestamp()
    {
        return df.format(new Date());
    }

    public static String randomString(int length)
    {
        char[] chs = new char[length];
        char[] strs = CODE_CHARS;
        for (int i = 0; i < length; i++)
        {
            Double ds = Double.valueOf(Math.random() * 63.0D);
            chs[i] = strs[ds.intValue()];
        }
        return new String(chs);
    }

    public static String getUUID()
    {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static long getUUIDHashCode()
    {
        return getUUID().hashCode();
    }
}
