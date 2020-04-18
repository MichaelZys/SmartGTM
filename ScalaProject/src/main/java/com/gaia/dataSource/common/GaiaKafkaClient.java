package com.gaia.dataSource.common;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class GaiaKafkaClient {

    private String bootstrapServer;
    private AdminClient adminClient;

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public void setBootstrapServer(String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }

    public  GaiaKafkaClient(String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }

    public AdminClient getAdminClient(){

        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        adminClient = AdminClient.create(properties);
        return adminClient;
    }

//    public void createTopicList(String name, int numPartitions, short replicationFactor){
//
//        NewTopic newTopic = new NewTopic(name, numPartitions, replicationFactor);
//        Collection<NewTopic> topicsList = new ArrayList<NewTopic>();
//        topicsList.add(newTopic);
//
//
//    }
//    public void deleteTopicList(){
//
//    }
}
