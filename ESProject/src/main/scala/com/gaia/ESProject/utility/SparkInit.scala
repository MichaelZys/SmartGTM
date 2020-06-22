package com.gaia.ESProject.utility

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkInit {

  def getSpark(s:String):SparkSession={

    val conf = new SparkConf()

    //获取SparkConf
    val hashMap = common.getHashMap("sparkconfig.properties")

    for (elem <- hashMap) {
      conf.set(elem._1,elem._2)
    }
    //    conf.set("es.index.auto.create", "true")
    //    conf.set("hive.execution.engine", "mr")
//    conf.set("hive.metastore.uris", "thrift://10.0.101.54:9083")
//    conf.set("spark.sql.warehouse.dir", "/warehouse/tablespace/managed/hive")

    val osName = System.getProperty("os.name")
    if(osName.equals("Windows 10")){
      SparkSession.builder()
        .master("local[*]")
        .appName(s)
        .config(conf)
        .enableHiveSupport()
        .getOrCreate()
    }
    else {
      SparkSession.builder()
        .appName(s)
        .config(conf)
        .enableHiveSupport()
        .getOrCreate()
    }


  }

}
