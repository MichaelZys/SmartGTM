package com.gaia.dataSource.idaeTest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.gaia.dataSource.common.GaiaKafka;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class KafkaConsumerTest {

    public static void main(String[] args) {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.0.103:6667");
//        props.put("group.id", "test2");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        KafkaConsumer consumer = new GaiaKafka().myConsumer("test2");
        consumer.subscribe(Arrays.asList("OrderDT"));
        while (true) {
//            System.out.println("1");
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
//                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                System.out.printf("offset = %d, key = %s%n", record.offset(), record.key());
                JSONObject  jsonObject = (JSONObject)JSONObject.parse(record.value());
                System.out.println(jsonObject.get("purchase_no"));
//                Map<String,Object> map = (Map<String,Object>)jsonObject;
//                Object object = map.get("purchase_no");
//                System.out.println(object.toString());
            }
        }

    }
}
