package com.gaia.ESProject.utility

import java.io.{BufferedInputStream, FileInputStream}
import java.security.MessageDigest
import java.util.Properties

import scala.collection.JavaConversions._

object common {

  def getMD5(s: String): String = {

    val md5 = MessageDigest.getInstance("MD5")
    val encoded = md5.digest((s).getBytes)
    encoded.map("%02x".format(_)).mkString.toUpperCase

  }

  def getHashMap(fileName: String): scala.collection.mutable.HashMap[String, String] = {

    var filePath: String = ""

    val osName = System.getProperty("os.name")
    if (osName.equals("Windows 10")) {
      filePath = "D:\\Github\\SmartGTM\\ESProject\\src\\main\\resources"
    } else {
      filePath = "/opt/data/prodfile"
    }
    val prod = new Properties
    val inputStream = new BufferedInputStream(new FileInputStream(filePath + "/" + fileName))
    prod.load(inputStream)

    //    println(prod.getProperty("a"))

    val option = new scala.collection.mutable.HashMap[String, String]

    //遍历properties, 赋给option
    for (key <- prod.stringPropertyNames) {
      option += (key -> prod.getProperty(key))
    }
    option
  }

}
