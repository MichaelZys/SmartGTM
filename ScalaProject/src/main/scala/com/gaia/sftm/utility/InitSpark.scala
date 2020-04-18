package com.gaia.sftm.utility

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

class InitSpark {

  def getSparkSession():SparkSession={

    System.setProperty("HADOOP_USER_NAME", "hadoop")
    //    下面的代码本意是想1
    //    System.setProperty("user.name", "root")

    val conf = new SparkConf
    conf.set("spark.sql.adaptive.enabled", "true")
    conf.set("spark.sql.adaptive.shuffle.targetPostShuffleInputSize", "128M")
    conf.set("spark.sql.adaptive.join.enabled", "true")
    conf.set("spark.sql.autoBroadcastJoinThreshold", "20971520")
//    conf.set("hive.metastore.uris","thrift://10.10.17.11:9083")

    val spark = SparkSession
      .builder()
      .config(conf)
//      .appName("Uni_Visit")
            .master("local[*]")
      .enableHiveSupport()
      .getOrCreate()

    spark

  }

}
