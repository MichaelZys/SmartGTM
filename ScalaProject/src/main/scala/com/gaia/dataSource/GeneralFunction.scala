package com.gaia.dataSource

import java.io.FileInputStream
import java.security.MessageDigest
import java.util.Properties

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import com.gaia.dataSource.HttpFunction
import org.apache.spark.sql.Row

/**
  * @author michael
  * @create 2020-04-18 10:36
  */
class GeneralFunction {

  def getSign(appSecret: String, api: String, appKey: String, pageIndex: String, pageSize: String, toAppKey: String, ver: String, para: String): String = {

    var s: String = "%sapi%sappKey%spageIndex%spageSize%stoAppKey%sver%s%s%s".format(appSecret, api, appKey, pageIndex, pageSize, toAppKey, ver, para, appSecret)
    s
  }

  def getUrl(api: String, appKey: String, pageIndex: String, pageSize: String, toAppKey: String, ver: String, md5Sign: String): String = {

    "http://122.144.129.75:9210/api/datax/in/queryPage?appKey=%s&toAppKey=%s&ver=%s&api=%s&pageIndex=%s&pageSize=%s&sign=%s".format(appKey, toAppKey, ver, api, pageIndex, pageSize, md5Sign)

  }

  def hashMD5(content: String): String = {
    val md5 = MessageDigest.getInstance("MD5")
    val encoded = md5.digest((content).getBytes)
    encoded.map("%02x".format(_)).mkString.toUpperCase
  }

  def getRes(appSecret: String, api: String, appKey: String, pageIndex: String, pageSize: String, toAppKey: String, ver: String, para: String): String = {

    //通过参数获取数据sign, 用于加密获取MD5码
    val sign: String = getSign(appSecret, api, appKey, pageIndex, pageSize,
      toAppKey, ver, para)
    println(sign)
    val md5Sign: String = hashMD5(sign)
    println(md5Sign)
    val postUrl = getUrl(api, appKey, pageIndex, pageSize, toAppKey, ver, md5Sign)
    println(postUrl)

    //获取Http实例
    val http = new HttpFunction

    //返回res
    http.postResponse(postUrl, para, """{"X-DB": "tngt-kpi-prod"}""")

  }

  def getResSeqRow(res: String, colStr: String): Seq[Row] = {

//    println(res)
//    {"code":"0","msg":"Success","notify":true,
    val json: JSONObject = JSON.parseObject(res)
    val code = json.getString("code")
    val msg = json.getString("msg")
    val notify = json.getString("notify")
//    println(code)
//    println(msg)
//    println(notify)
    val data: JSONArray = json.getJSONArray("data")



    //获取列List
    val colList = colStr.split(",")

    var seq = Seq[Row]()

    //其中data是JSONObjectArray, 循环取每条
    for (i <- 0 to data.size() - 1) {
      var sigleMessage: String = ""
      // 循环取每列值
      for (j <- colList) {
        sigleMessage += data.getJSONObject(i).get(j)
        sigleMessage += ","
      }
      sigleMessage = sigleMessage.dropRight(1).replace("|","")
      var x = sigleMessage.split(",")
      seq = seq :+ Row(x(0), x(1), x(2), x(3), x(4), x(5), x(6), x(7), x(8), x(9), x(10), x(11), x(12), x
      (13), x(14), x(15), x(16), x(17), x(18), x(19), x(20), x(21), x(22),
        data.getJSONObject(i).toString)
    }

    seq

  }


}
