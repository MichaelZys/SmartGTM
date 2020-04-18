package com.gaia.dataSource

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.StructType
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
  * @author michael
  * @create 2020-04-18 20:48
  */
class SparkFunction {

  def getDataFrame(spark:SparkSession, rdd:RDD[Row], schema:StructType)
  : DataFrame ={
    spark.createDataFrame(rdd, schema)
  }

  def getSparkContext(sparkConf: SparkConf): SparkContext = {
    val conf = new SparkConf()
    new SparkContext(conf)

  }

  def getSparkSession(sparkConf: SparkConf): SparkSession = {

    System.setProperty("HADOOP_USER_NAME", "hadoop")
    System.setProperty("user.name", "root")
    SparkSession
      .builder()
      .config(sparkConf)
      .enableHiveSupport()
      .getOrCreate()

  }

}
