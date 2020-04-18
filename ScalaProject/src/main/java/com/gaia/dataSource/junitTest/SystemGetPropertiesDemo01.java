package com.gaia.dataSource.junitTest;

import org.junit.Test;

import java.util.Map;
import java.util.Properties;
import java.util.Iterator;

public class SystemGetPropertiesDemo01 {

    @Test
    public void Test01(){

        System.out.println(System.getProperty("java.home"));
        System.out.println(System.getProperty("michel.home","ceshi"));
        System.out.println(System.getProperty("michel.home"));

    }

    @Test
    public void test02() {
        Properties properties = System.getProperties();
        Iterator<Map.Entry<Object, Object>> iterator = properties.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> entry = iterator.next();
            System.out.println(entry.getKey() + "===" + entry.getValue());
        }
    }


}
