package com.gaia.dataSource


import org.apache.spark.SparkConf
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
//import com.gaia.dataSource.{GeneralFunction, DTOrder, SparkFunction}


object Dingtong {

  def main(args: Array[String]): Unit = {

    val appSecret = "f365af86245142a7851eccdcebbe90a9"
    val appKey = "B4C32F5B"
    val toAppKey = "B6CFE8F3"
    val ver = "1_0"
    val api = "kpi.delivery.result"
    val pageIndex = "1"
    val pageSize = "2"
    val body = """{"activityDate":"2020-04-15"}"""


    //获得顶通列str
    val dt = new DTOrder
    val colStr = dt.getDTOrderColStr()

    val gf = new GeneralFunction
    val res = gf.getRes(appSecret, api, appKey, pageIndex, pageSize, toAppKey,
      ver, body)
    val resSeqRow = gf.getResSeqRow(res, colStr)

    resSeqRow.foreach(println)


    val spark = new SparkFunction().getSparkSession()

    //    val schemaString = "ttShipToCode,code,orderPlacedAt,ttShipToName,lon,warehouseCode,deliveredAt,divisionCode,shipToCity,shipToAddress1,shipToName,ttShipToAddress1,shipToState,totalCases,lat,shipTo"
    val fields = colStr.split(",")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)

    val rdd = spark.sparkContext.parallelize(resSeqRow)




    val df = spark.createDataFrame(rdd, schema)

    df.show()

    df.write.mode(SaveMode.Overwrite).saveAsTable("ods_sftm_new.ods_dtorder_tmp")


    spark.stop()


  }


}
