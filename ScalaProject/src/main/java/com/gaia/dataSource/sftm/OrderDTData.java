package com.gaia.dataSource.sftm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.gaia.dataSource.WQPackage.vo.WQResponse;
import com.gaia.dataSource.common.Brand;
import com.gaia.dataSource.common.GetAPIData;
import com.gaia.dataSource.common.GaiaKafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Iterator;


public class OrderDTData {


    public static void main(String[] args) {

        //写死的获取经销商订单相关的信息
        String module = "dmsdatasearch";
        String version = "v1";
        String operation = "getDmsSalesOrderInfos";


        //获取哪个事业部的信息
        //参数康面可以从args中传入
        Brand brand = new Brand("km");
        String openid = brand.getOpenid();
        String appkey = brand.getAppkey();


//        System.out.println(openid);
//        System.out.println(appkey);

        GetAPIData data = new GetAPIData();

        // 创建一个生产者
        GaiaKafka gaiaKafka = new GaiaKafka();
        KafkaProducer producer = gaiaKafka.myProducer();

        Boolean flag = true;
        Integer page_number = 1;
        while (flag) {
            // 写body相关
            JSONObject body = new JSONObject();
            body.put("page_number", page_number);
            body.put("start_modify_date", "2020-04-11");
            body.put("end_modify_date", "2020-04-11");
            WQResponse res = data.WQData(openid, appkey, module, version, operation, body);
            if (res.getEmpty()) {
                flag = false;
                System.out.println("page_number=" + page_number.toString() + ", 结束.");
            } else {
                System.out.println("处理第" + page_number.toString() + "页数据");
                JSONArray jsonArray = JSONObject.parseArray(res.getResponse_data());
                for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
                    JSONObject jsonObject = (JSONObject) iterator.next();
                    producer.send(new ProducerRecord<>("OrderDT", jsonObject.toString()));
                }
            }
            page_number++;
        }
        producer.close();


    }
}
