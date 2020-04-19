package com.gaia.dataSource

import java.net.InetAddress

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.StructType
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
  * @author michael
  * @create 2020-04-18 20:48
  */
class SparkFunction {

  def getDataFrame(spark: SparkSession, rdd: RDD[Row], schema: StructType)
  : DataFrame = {
    spark.createDataFrame(rdd, schema)
  }

  def getSparkContext(sparkConf: SparkConf): SparkContext = {
    //    val conf = new SparkConf()
    new SparkContext(sparkConf)

  }

  def getSparkSession(): SparkSession = {

    System.setProperty("HADOOP_USER_NAME", "hadoop")
    System.setProperty("user.name", "root")

    val conf = new SparkConf

    conf.set("spark.sql.adaptive.enabled", "true")
    conf.set("spark.sql.adaptive.shuffle.targetPostShuffleInputSize", "128M")
    conf.set("spark.sql.adaptive.join.enabled", "true")
    conf.set("spark.sql.autoBroadcastJoinThreshold", "20971520")
    conf.set("spark.sql.warehouse.dir", "/warehouse/tablespace/managed/hive")
    conf.set("spark.debug.maxToStringFields", "300")
    conf.set("spark.app.name","the lowest level spark")

    //如果运行在本地, 就是local[*]
    //
    if (System.getProperty("os.name") == "Windows 10") {
      conf.set("spark.master", "local[*]")
    }
    else {
      //      conf.set("spark.master","local[*]")
    }

    //
    if (InetAddress.getLocalHost.getHostName.contains("SMART")) {
      conf.set("hive.metastore.uris", "thrift://10.10.17.11:9083")
    }
    else {
      conf.set("hive.metastore.uris", "thrift://192.168.0.103:9083")
    }

    SparkSession
      .builder()
      .config(conf)
      .enableHiveSupport()
      .getOrCreate()

  }

}
