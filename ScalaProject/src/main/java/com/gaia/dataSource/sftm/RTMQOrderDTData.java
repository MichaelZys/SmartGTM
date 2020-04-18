package com.gaia.dataSource.sftm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaia.dataSource.WQPackage.vo.WQResponse;
import com.gaia.dataSource.common.Brand;
import com.gaia.dataSource.common.GaiaKafka;
import com.gaia.dataSource.common.GetAPIData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

public class RTMQOrderDTData {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {

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
//
//        // 创建一个生产者
        DefaultMQProducer producer = new DefaultMQProducer("RTMQDemo02");
        producer.setNamesrvAddr("192.168.0.106:9876");
        producer.start();


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
                    System.out.println(jsonObject.toString());
                    //Create a message instance, specifying topic, tag and message body.
                    Message msg = new Message("Demo02", "order01", "KEY",
                            jsonObject.toString().getBytes(RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = producer.send(msg);

                    System.out.printf("%s%n", sendResult);
//                    producer.send(new ProducerRecord<>("OrderDT", jsonObject.toString()));
                }
            }
            page_number++;
        }
        producer.shutdown();


    }
}
