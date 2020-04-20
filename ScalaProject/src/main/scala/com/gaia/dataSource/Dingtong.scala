package com.gaia.dataSource


import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import com.alibaba.fastjson.{JSON, JSONArray}


import scala.util.parsing.json.JSONObject


object Dingtong {

  def main(args: Array[String]): Unit = {



    val appSecret = "f365af86245142a7851eccdcebbe90a9"
    val appKey = "B4C32F5B"
    val toAppKey = "B6CFE8F3"
    val ver = "1_0"
    val api = "kpi.delivery.result"
    val pageSize = "100"
    val body = """{"activityDate":"2020-04-15"}"""

    var flag:Boolean = true

    var pageIndex:Int = 1

    //获得顶通列str
    val dt = new DTOrder
    val colStr = dt.getDTOrderColStr()

    val spark = new SparkFunction().getSparkSession()

    val fields = (colStr + ",oriData").split(",")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)


    while (flag) {

      // http
      println("第" + pageIndex + "次调用Http获取数据")
      val gf = new GeneralFunction
      var res = gf.getRes(appSecret, api, appKey, pageIndex.toString, pageSize,
        toAppKey, ver, body)

      println(res)

      // page + 1
      pageIndex += 1

//      println(pageIndex)

      var resJson = JSON.parseObject(res)
      println(resJson.get("msg"))
      var data: JSONArray = resJson.getJSONArray("data")
      println(data.size())
      println("获取" + data.size() + "条数据")

      if( data.size() == 0)
        flag = false

      //Seq(Row)
      var resSeqRow = gf.getResSeqRow(res, colStr)


      var rdd = spark.sparkContext.parallelize(resSeqRow)

      var df = spark.createDataFrame(rdd, schema)

      df.write.mode(SaveMode.Append).saveAsTable("ods_sftm_new.ods_dtorder_tmp")


    }
    spark.stop()

  }


}
