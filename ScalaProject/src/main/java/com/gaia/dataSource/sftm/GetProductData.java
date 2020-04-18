package com.gaia.dataSource.sftm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaia.dataSource.WQPackage.vo.*;
import com.gaia.dataSource.WQPackage.tools.WQApiHandler;
//import com.google.gson.JsonObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.gaia.dataSource.common.GaiaKafka;
import com.gaia.dataSource.common.Brand;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class GetProductData {

    public static void main(String[] args) {

        String openid = "6551855645179371040";
        String appkey = "97k9808W20sL2By1SL";

        WQOpenApi wqOpenApi = new WQOpenApi(openid, appkey);
        WQRequest request = new WQRequest();
        request.setWqOpenApi(wqOpenApi);
        request.setModule("dmsdatasearch");
        request.setVersion("v1");
        request.setOperation("getDmsSalesOrderInfos");

        JSONObject body = new JSONObject();
        body.put("page_number", "1");
        body.put("start_create_date", "2016-12-26");
        body.put("end_create_date", "2016-12-26");

        request.setRequestdata(body.toJSONString());
        try {
            WQResponse res = WQApiHandler.handleOpenApi(request);
            System.out.println("return_code:" + res.getReturn_code());
            System.out.println("return_message:" + res.getReturn_msg());
//            System.out.println(res.getResponse_data());
//            System.out.println(res.getMsg_id());
            System.out.println(res.getEmpty());


            //要把属性抽象到数据库中,
            // 容易出事, 抽象到文件中吧.
//            Properties props = new Properties();
//            // Kafka服务端的主机名和端口号
//            props.put("bootstrap.servers", "192.168.0.103:6667");
//            props.put("acks", "all");
//            props.put("retries", 0);
//            props.put("batch.size", 16384);
//            props.put("linger.ms", 1);
//            props.put("buffer.memory", 33554432);
//            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


//            KafkaProducer<String, String> producer = new KafkaProducer<>(props);
//            for (int i = 0; i < 50; i++) {
//                producer.send(new ProducerRecord<String, String>("first", Integer.toString(i), "hello world-" + i));
//            }
//            KafkaProducer producer = new GaiaKafka().myProducer();
//            JSONArray jsonArray = JSONObject.parseArray(res.getResponse_data());
//            for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
//                JSONObject jsonObject = (JSONObject) iterator.next();
////                System.out.println(jsonObject);
//                producer.send(new ProducerRecord<>("OrderDT_test", jsonObject.toString()));
////                Log.i(TAG,jsonObject .get("logId").toString());
//            }
//
//            //        KafkaProducer producer = new KafkaProducer();
//            producer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
