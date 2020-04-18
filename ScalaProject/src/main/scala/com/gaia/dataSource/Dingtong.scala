package com.gaia.dataSource


import org.apache.spark.SparkConf
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import com.gaia.dataSource.{GeneralFunction, DTOrder, SparkFunction}


object Dingtong {

  def main(args: Array[String]): Unit = {

    val appSecret = "f365af86245142a7851eccdcebbe90a9"
    val appKey = "B4C32F5B"
    val toAppKey = "B6CFE8F3"
    val ver = "1_0"
    val api = "kpi.delivery.result"
    val pageIndex = "1"
    val pageSize = "2"
    val body = """{"activityDate":"2020-04-15","warehouseCode":"403011"}"""


    //获得顶通列str
    val dt = new DTOrder
    val colStr = dt.getDTOrderColStr()

    val gf = new GeneralFunction
    val res = gf.getRes(appSecret, api, appKey, pageIndex, pageSize, toAppKey,
      ver, body)
    val resSeqRow = gf.getResSeqRow(res, colStr)


    val sparkFT = new SparkFunction
    val conf = new SparkConf()
    conf.set("spark.master", "local[*]")
    conf.set("spark.app.name", "dtOrder")
    val spark = sparkFT.getSparkSession(conf)
    val sc = sparkFT.getSparkContext(conf)
    //      .setAppName("test").setMaster("local[*]")
    //    conf.set("spark.sql.adaptive.enabled", "true")
    //    conf.set("spark.sql.adaptive.shuffle.targetPostShuffleInputSize", "128M")
    //    conf.set("spark.sql.adaptive.join.enabled", "true")
    //    conf.set("spark.sql.autoBroadcastJoinThreshold", "20971520")

    //    val schemaString = "ttShipToCode,code,orderPlacedAt,ttShipToName,lon,warehouseCode,deliveredAt,divisionCode,shipToCity,shipToAddress1,shipToName,ttShipToAddress1,shipToState,totalCases,lat,shipTo"
    val fields = colStr.split(",")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)
    val rdd = sc.parallelize(resSeqRow)

    val df = sparkFT.getDataFrame(spark, rdd, schema)

    df.write.mode("append").saveAsTable("ods_sftm_new.dtorder")





  }


}
