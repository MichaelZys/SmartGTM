package com.gaia.dataSource.junitTest;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class KafkaCreateTest {

    private static final String NEW_TOPIC = "demo01";
    private static final String brokerUrl = "192.168.0.103:2181";

    private static AdminClient adminClient;

    @BeforeClass
    public static void beforeClass(){
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, brokerUrl);
        adminClient = AdminClient.create(properties);
    }

    @AfterClass
    public static void afterClass(){
        adminClient.close();
    }

    @Test
    public void createTopics() {
        NewTopic newTopic = new NewTopic(NEW_TOPIC,2, (short) 2);
        Collection<NewTopic> newTopicList = new ArrayList<>();
        newTopicList.add(newTopic);
        System.out.println(adminClient.createTopics(newTopicList).all());
//        Collection<String> deleteTopic = new ArrayList<>();
//        deleteTopic.add(NEW_TOPIC);
//        adminClient.deleteTopics(deleteTopic);
    }

}
