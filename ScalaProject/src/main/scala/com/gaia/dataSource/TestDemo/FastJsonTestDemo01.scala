package com.gaia.dataSource.TestDemo

import com.alibaba.fastjson.{JSON, JSONObject, JSONArray}


/**
  * @author michael
  * @create 2020-04-19 14:26
  */
object FastJsonTestDemo01 {

  def main(args: Array[String]): Unit = {


    //Json转字符串
    //Json中包含数组, 字符串, 需要把字符串解析出来, 把数组解析出来.

    //
    val str:String = "{\"code\":\"0\",\"msg\":\"Success\",\"notify\":true,\"data\":[1,2,3],\"error\":false}"
    val json:JSONObject = JSON.parseObject(str)
    println(json.get("code"))
    println(json.get("data"))

    val jsonArray:JSONArray = json.getJSONArray("data")

    println(jsonArray.size())

//    jsonArray.forEach(println)




    //字符串转Json
  }

}
