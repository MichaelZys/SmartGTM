package com.gaia.dataSource.WQPackage.vo;

public class WQOpenApi {

    public static final String WQ_OPENAPI_URL = "https://openapi.waiqin365.com/api";
    public static final int WQ_OPENAPI_TIMEOUT = 30000;
    private static final int WQ_OPENAPI_TIMEOUT_MAX = 600000;
    private String openid;
    private String appkey;
    private String openurl;
    private int timeout;

    public WQOpenApi(String openid, String appkey)
    {
        this.openid = openid;
        this.appkey = appkey;
    }

    public String getOpenid()
    {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppkey() {
        return this.appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getOpenurl()
    {
        return this.openurl == null ? "https://openapi.waiqin365.com/api" : this.openurl;
    }

    public void setOpenurl(String openurl)
    {
        this.openurl = openurl;
    }

    public int getTimeout()
    {
        return this.timeout == 0 ? 30000 : this.timeout;
    }

    public void setTimeout(int timeout)
    {
        if (timeout > 600000)
        {
            this.timeout = 30000;
        }
        else
        {
            this.timeout = timeout;
        }
    }
}
