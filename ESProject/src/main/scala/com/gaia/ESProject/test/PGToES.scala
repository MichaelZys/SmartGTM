package com.gaia.ESProject.test

import com.gaia.ESProject.utility.{SparkInit, common}
import org.elasticsearch.spark.sql._


object PGToES {

  def main(args: Array[String]): Unit = {

    val spark = SparkInit.getSpark("connES")

    val options = common.getHashMap("esconfig.properties")

//    val index = common.getHashMap("esindex.properties").get("ods_visit").get

    val pgUrl = "jdbc:postgresql://192.168.0.108:5432/KSF_KNOODLE_DEV?prepareThreshold=0"
    // 读取pg中的数据
    val df1 = spark.sqlContext.read.format("jdbc")
      .option("url", pgUrl)
      .option("user", "data_man")
      .option("password", "data_man@123!4")
      .option("dbtable", "retail.a_test")
      .load()

    df1.show()

//    df1.createOrReplaceTempView("store")

    df1.repartition(1).toDF().saveToEs("my_index/_doc", options)

  }

}
