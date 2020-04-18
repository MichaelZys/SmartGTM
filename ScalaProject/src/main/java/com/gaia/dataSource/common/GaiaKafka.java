package com.gaia.dataSource.common;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class GaiaKafka {

    public KafkaProducer myProducer() {

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.0.103:6667");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer(props);

        return producer;

    }

    public KafkaConsumer myConsumer(String strGroupID) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.0.103:6667");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, strGroupID);
        //        props.put("group.id", strGroupID);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        return consumer;
    }

    public String myNewTopic(String name, int numPartitions, short replicationFactor) {

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.0.103:6667");

        AdminClient adminClient;
        adminClient = AdminClient.create(props);

        NewTopic newTopic = new NewTopic(name, numPartitions, replicationFactor);

        Collection<NewTopic> newTopicList = new ArrayList<>();
        newTopicList.add(newTopic);


        System.out.println(adminClient.createTopics(newTopicList).all());
        return newTopic.toString();
    }

    public String myDescTopic(String name) {

//        Properties props = new Properties();
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.103:6667");

//        AdminClient adminClient = new AdminClient(props);
//        KafkaAdminClient client = new KafkaAdminClient(props);


        TopicDescription descTopic = new TopicDescription(name, false, null);
        return descTopic.toString();
    }
}
