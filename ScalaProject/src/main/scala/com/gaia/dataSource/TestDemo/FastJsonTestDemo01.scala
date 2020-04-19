package com.gaia.dataSource.TestDemo

import com.alibaba.fastjson.{JSON, JSONObject}

/**
  * @author michael
  * @create 2020-04-19 14:26
  */
object FastJsonTestDemo01 {

  def main(args: Array[String]): Unit = {


    //Json转字符串
    //Json中包含数组, 字符串, 需要把字符串解析出来, 把数组解析出来.

    //
    val str:String = "{\"code\":\"0\",\"msg\":\"Success\",\"notify\":true,\"data\":[],\"error\":false}"
    val json:JSONObject = JSON.parseObject(str)
    println(json.get("code"))


    //字符串转Json
  }

}
