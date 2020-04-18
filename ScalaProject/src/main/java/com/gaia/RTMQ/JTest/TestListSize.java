package com.gaia.RTMQ.JTest;

import org.junit.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TestListSize {

    @Test
    public void test01() {
        //    List list = new List();
//    ArrayList list = new List();
//    List<String> a = new List<String>;

        // 使用泛型的原因是, 内部会经常跑进去一些不符合的数据
        List list =  new ArrayList();
        list.add("a");
        list.add("b");
        list.add(2);

        System.out.println(list.size());

        for(Object a : list){
            System.out.println(a);
        }

    }

    @Test
    public void test02(){
        // 使用反省后, 不适合的, 会直接报错
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
//        list.add(2);
    }


    @Test
    public void test03(){

        Date dNow = new Date( );
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

        System.out.println("当前时间为: " + ft.format(dNow.getTime()-86400000L));

        StringBuilder sql = new StringBuilder("delete from ");
        sql.append("");
        sql.append(" where data_version = ");
        sql.append("'");
        sql.append(ft.format(dNow.getTime()-86400000L));
        sql.append("'");
        System.out.println(sql);


    }

}
