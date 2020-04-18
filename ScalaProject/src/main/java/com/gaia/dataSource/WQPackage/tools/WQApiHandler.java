package com.gaia.dataSource.WQPackage.tools;

import com.gaia.dataSource.WQPackage.vo.WQResponse;
import com.gaia.dataSource.WQPackage.vo.WQRequest;
import com.alibaba.fastjson.JSONObject;
import com.gaia.dataSource.WQPackage.util.HttpUtil;


public class WQApiHandler {
    public static WQResponse handleOpenApi(WQRequest request)
            throws Exception
    {
        String ret = HttpUtil.httpPost(request);
        if (ret.startsWith("{"))
        {
            return (WQResponse)JSONObject.parseObject(ret, WQResponse.class);
        }

        throw new Exception("请求地址不正确或请求方法不存在！");
    }
}
