package com.gaia.dataSource.junitTest;

import com.gaia.dataSource.common.GaiaKafkaOP;
import org.junit.Test;

public class KafkaCreateDemo01 {

    @Test
    public void test01(){

        GaiaKafkaOP op = new GaiaKafkaOP();
        op.createTopicList("192.168.0.103:6667", "demo01", 2, (short)2);


    }
}
