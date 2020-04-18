package com.gaia.dataSource


import java.io.FileInputStream

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.security.MessageDigest
import java.util
import java.util.Properties

import scala.collection.JavaConversions._
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}


import scala.collection.mutable.ListBuffer


object Dingtong {


  def getResponse(url: String, header: String = null): String = {
    val httpClient = HttpClients.createDefault() // 创建 client 实例
    val get = new HttpGet(url) // 创建 get 实例

    if (header != null) { // 设置 header
      val json = JSON.parseObject(header)
      json.keySet().toArray.map(_.toString).foreach(key => get.setHeader(key, json.getString(key)))
    }

    val response = httpClient.execute(get) // 发送请求
    EntityUtils.toString(response.getEntity) // 获取返回结果
  }

  def postResponse(url: String, params: String = null, header: String = null): String = {
    val httpClient = HttpClients.createDefault() // 创建 client 实例
    val post = new HttpPost(url) // 创建 post 实例

    // 设置 header
    if (header != null) {
      val json = JSON.parseObject(header)
      json.keySet().toArray.map(_.toString).foreach(key => post.setHeader(key, json.getString(key)))
    }

    if (params != null) {
      post.setEntity(new StringEntity(params, "UTF-8"))
    }

    val response = httpClient.execute(post) // 创建 client 实例
    EntityUtils.toString(response.getEntity, "UTF-8") // 获取返回结果
  }





  def hashMD5(content: String): String = {
    val md5 = MessageDigest.getInstance("MD5")
    val encoded = md5.digest((content).getBytes)
    encoded.map("%02x".format(_)).mkString.toUpperCase
  }


  def main(args: Array[String]): Unit = {

    val appSecret = "f365af86245142a7851eccdcebbe90a9"
    val appKey = "B4C32F5B"
    val toAppKey = "B6CFE8F3"
    val ver = "1_0"
    val api = "kpi.delivery.result"
    val pageIndex = "1"
    val pageSize = "2"
    val para = """{"activityDate":"2020-04-15","warehouseCode":"403011"}"""

    val gf = new com.gaia.dataSource.GeneralFunction

    val sign = gf.getSign(appSecret, api, appKey, pageIndex, pageSize, toAppKey, ver, para, appSecret)
    val md5Sign = hashMD5(sign)
    val postUrl = "http://122.144.129.75:9210/api/datax/in/queryPage?appKey=%s&toAppKey=%s&ver=%s&api=%s&pageIndex=%s&pageSize=%s&sign=%s".format(appKey, toAppKey, ver, api, pageIndex, pageSize, md5Sign)

    println("This post response: ")



    val res = postResponse(postUrl, para, """{"X-DB": "tngt-kpi-prod"}""")
    val json: JSONObject = JSON.parseObject(res)
    val data: JSONArray = json.getJSONArray("data")

    //从properties文件中读配置
    val properties = new Properties
    val path = Thread.currentThread().getContextClassLoader.getResource("dtOrderColumnList.properties").getPath
    properties.load(new FileInputStream(path))
    properties.getProperty("colName")

    //获取列List
    val colStr = properties.getProperty("colName")
    val colList = colStr.split(",")

//    var sendList   = new util.ArrayList<String>();
    val sendList = new ListBuffer[String]




    //其中data是JSONObjectArray, 循环取每条
    for(i<- 0 to data.size()-1){
      var sigleMessage:String = ""
      // 循环取每列值
      for(j <- colList) {
//        sigleMessage += j
//        sigleMessage += " "
        sigleMessage +=  data.getJSONObject(i).get(j)
        sigleMessage += ","
//        println(sigleMessage)
      }
//      println(sigleMessage.dropRight(1))
      sendList.add(sigleMessage.dropRight(1))

    }
//
    for (i <- sendList){
      println(i)
    }

    println("结束List")
//
//
//    val list: List[AnyRef] = data.iterator().toList
//
//    val listOBJ: List[String] = list.map(m=>
//      m.toString
//    )

//    for(i<-listOBJ){
//      println(i)
//    }





//    val schema = StructType(
//      Seq(
//        StructField("warehouseCode",StringType,true)
//        ,StructField("companyCode",StringType,true)
//        ,StructField("divisionCode",StringType,true)
//        ,StructField("code",StringType,true)
//        ,StructField("orderPlacedAt",StringType,true)
//        ,StructField("deliveredAt",StringType,true)
//        ,StructField("podReturnedAt",StringType,true)
//        ,StructField("shipTo",StringType,true)
//        ,StructField("shipToName",StringType,true)
//        ,StructField("shipToState",StringType,true)
//        ,StructField("shipToCity",StringType,true)
//        ,StructField("shipToAddress1",StringType,true)
//        ,StructField("shipToAttentionTo",StringType,true)
//        ,StructField("shipToPhoneNum",StringType,true)
//        ,StructField("ttShipToCode",StringType,true)
//        ,StructField("ttShipToName",StringType,true)
//        ,StructField("ttShipToAddress1",StringType,true)
//        ,StructField("ttShipToAttentionTo",StringType,true)
//        ,StructField("ttShipToPhoneNum",StringType,true)
//        ,StructField("totalCases",StringType,true)
//        ,StructField("lat",StringType,true)
//        ,StructField("lon",StringType,true)
//        ,StructField("sapPlant",StringType,true)
//      )
//    )

//    val schema = new StructType()
//      .add("warehouseCode",StringType,true)
////      .add("companyCode",StringType,true)
//      .add("divisionCode",StringType,true)
//      .add("code",StringType,true)
//      .add("orderPlacedAt",StringType,true)
//      .add("deliveredAt",StringType,true)
////      .add("podReturnedAt",StringType,true)
//      .add("shipTo",StringType,true)
//      .add("shipToName",StringType,true)
//      .add("shipToState",StringType,true)
//      .add("shipToCity",StringType,true)
//      .add("shipToAddress1",StringType,true)
////      .add("shipToAttentionTo",StringType,true)
////      .add("shipToPhoneNum",StringType,true)
//      .add("ttShipToCode",StringType,true)
//      .add("ttShipToName",StringType,true)
//      .add("ttShipToAddress1",StringType,true)
////      .add("ttShipToAttentionTo",StringType,true)
////      .add("ttShipToPhoneNum",StringType,true)
//      .add("totalCases",StringType,true)
//      .add("lat",StringType,true)
//      .add("lon",StringType,true)
////      .add("sapPlant",StringType,true)

    System.setProperty("HADOOP_USER_NAME", "hadoop")
    System.setProperty("user.name", "root")

//    val conf = new SparkConf
    val conf = new SparkConf().setAppName("test").setMaster("local[*]")
//    conf.set("spark.sql.adaptive.enabled", "true")
//    conf.set("spark.sql.adaptive.shuffle.targetPostShuffleInputSize", "128M")
//    conf.set("spark.sql.adaptive.join.enabled", "true")
//    conf.set("spark.sql.autoBroadcastJoinThreshold", "20971520")

    val sc = new SparkContext(conf)

//    val sqlContext = new org.apache.spark.sql.SQLContext(sc)


    val spark = SparkSession
      .builder()
      .config(conf)
      .enableHiveSupport()
      .getOrCreate()


    import spark.implicits._

    val input = sc.parallelize(sendList)
//      .map(_.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\{","").replaceAll("\\}",""))
//      .map(_.split(","))
//      .map(attributes => Row(attributes(0), attributes(1).trim))
      .map(x=>Row(x))
    input.foreach{
      println
    }
//    val schemaString = "ttShipToCode,code,orderPlacedAt,ttShipToName,lon,warehouseCode,deliveredAt,divisionCode,shipToCity,shipToAddress1,shipToName,ttShipToAddress1,shipToState,totalCases,lat,shipTo"
    val fields = colStr.split(",")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))

    val schema = StructType(fields)
    val peopleDF = spark.createDataFrame(input, schema)
    peopleDF.show()


//    val df = spark.createDataFrame(input, schema)
//    df.show()
//    df.write.mode(SaveMode.Overwrite).saveAsTable("ods_sftm_new.ods_test")
//    input.collect().foreach {println}
//    input.toDF().show()

//    val spark = SparkSession
//      .builder()
//      .config(conf)
//      .appName("Uni_Store")
//      //      .master("local[*]")
//      .enableHiveSupport()
//      .getOrCreate()

//    spark.createDataFrame(input, schema)












    //    println(JSON.parseObject(data.toString))


    //    val j : JSONArray  = JSON.parseArray(json.get("data"))
    //    println(j)
    //
    //    for(i <- j.toArray){
    //      println(i)
    //    }
    //    for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
    //      JSONObject jsonObject = (JSONObject) iterator.next();
    //      producer.send(new ProducerRecord<>("OrderDT", jsonObject.toString()));
    //    }

  }


}
