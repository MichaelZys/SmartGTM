package com.gaia.dataSource.TestDemo

import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author michael
  * @create 2020-04-18 12:25
  */
object FileTest {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
//    val spark = SparkSession
//      .builder()
//      .config(conf)
//      .enableHiveSupport()
//      .getOrCreate()

    val schema = StructType(List(
      StructField("integer_column", StringType, nullable = true),
      StructField("string_column", StringType, nullable = true),
      StructField("date_column", StringType, nullable = true)
    ))



    val input = Seq(
      Row("First Value", "First Value", java.sql.Date.valueOf("2010-01-01").toString),
      Row(2.toString, "Second Value", java.sql.Date.valueOf("2010-02-01").toString)
    )

//    input.map(x=>x.)

    val rdd = sc.parallelize(input)

//    rdd.foreach(println)

    val df = sqlContext.createDataFrame(rdd, schema)

//    df.show()

  }

}
