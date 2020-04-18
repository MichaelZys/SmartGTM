package com.gaia.dataSource.WQPackage.vo;

import com.gaia.dataSource.WQPackage.util.StringUtil;
import com.gaia.dataSource.WQPackage.util.Md5;

public class WQRequest {
    private WQOpenApi wqOpenApi;
    private String module;
    private String version;
    private String operation;
    private String timestamp;
    private String msgid;
    private String requestdata;

    public String getRequestUrl()
    {
        if (this.wqOpenApi == null)
        {
            throw new RuntimeException("OPENAPI参数不能为空");
        }
        if (this.version == null)
        {
            throw new RuntimeException("接口版本参数不能为空");
        }
        if (this.operation == null)
        {
            throw new RuntimeException("接口操作类型参数不能为空");
        }
        if (this.timestamp == null)
        {
            setTimestamp(StringUtil.getTimestamp());
        }
        if (this.msgid == null)
        {
            setMsgid(StringUtil.getUUID());
        }
        if (this.requestdata == null)
        {
            throw new RuntimeException("接口请求消息体不能为空");
        }
        String digest = makeDigest();
        return String.format("%s/%s/%s/%s/%s/%s/%s/%s", new Object[] { this.wqOpenApi.getOpenurl(), this.module, this.version, this.operation, this.wqOpenApi.getOpenid(), this.timestamp, digest, this.msgid });
    }

    public String makeDigest()
    {
        return Md5.encode(String.format("%s|%s|%s", new Object[] { this.requestdata, this.wqOpenApi.getAppkey(), this.timestamp }));
    }

    public WQOpenApi getWqOpenApi()
    {
        return this.wqOpenApi;
    }

    public void setWqOpenApi(WQOpenApi wqOpenApi)
    {
        this.wqOpenApi = wqOpenApi;
    }

    public String getModule()
    {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOperation()
    {
        return this.operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public String getTimestamp()
    {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMsgid() {
        return this.msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getRequestdata() {
        return this.requestdata;
    }

    public void setRequestdata(String requestdata) {
        this.requestdata = requestdata;
    }
}
