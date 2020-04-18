package com.gaia.dataSource.common;

import com.alibaba.fastjson.JSONObject;
import com.gaia.dataSource.WQPackage.tools.WQApiHandler;
import com.gaia.dataSource.WQPackage.vo.WQOpenApi;
import com.gaia.dataSource.WQPackage.vo.WQRequest;
import com.gaia.dataSource.WQPackage.vo.WQResponse;

public class GetAPIData {

    public WQResponse WQData(String openid, String appkey, String module, String version, String operation, JSONObject body){
        WQOpenApi wqOpenApi = new WQOpenApi(openid, appkey);
        WQRequest request = new WQRequest();
        request.setWqOpenApi(wqOpenApi);
        request.setModule(module);
        request.setVersion(version);
        request.setOperation(operation);
        request.setRequestdata(body.toJSONString());

        WQResponse res = new WQResponse();

        try {
            res = WQApiHandler.handleOpenApi(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
