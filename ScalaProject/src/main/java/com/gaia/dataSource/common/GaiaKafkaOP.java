package com.gaia.dataSource.common;

import com.gaia.dataSource.common.GaiaKafkaClient;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.ArrayList;
import java.util.Collection;

public class GaiaKafkaOP {


    private String bootstrapServr;

    public String getBootstrapServr() {
        return bootstrapServr;
    }

    public void setBootstrapServr(String bootstrapServr) {
        this.bootstrapServr = bootstrapServr;
    }

//    public GaiaKafkaOP(String bootstrapServr) {
//        this.bootstrapServr = bootstrapServr;
//    }

    public void createTopicList(String bootstrapServr, String name, int numPartitions, short replicationFactor){

        // 1. 获取clent
        AdminClient client = new GaiaKafkaClient(bootstrapServr).getAdminClient();

        // 2. 配置需要创建的topic信息
        NewTopic newTopic = new NewTopic(name,numPartitions, replicationFactor);
        Collection<NewTopic> newTopicList = new ArrayList<>();
        newTopicList.add(newTopic);

        // 3. 创建topic
        System.out.println(client.createTopics(newTopicList).values());

        // 4. 关闭
        client.close();


    }
}
