package com.gaia.dataSource.idaeTest;

import com.gaia.dataSource.common.GaiaKafka;
public class KafkaCRUDTest {

    public static void main(String[] args) {

        GaiaKafka gaiaKafka = new GaiaKafka();
        String res = gaiaKafka.myNewTopic("michael", 3, (short)4);

//        String res = gaiaKafka.myDescTopic("michael");

        System.out.println(res);
    }
}
