package com.gaia.dataSource

import com.alibaba.fastjson.JSON
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

/**
  * @author michael
  * @create 2020-04-18 20:16
  */
class HttpFunction {

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

}
