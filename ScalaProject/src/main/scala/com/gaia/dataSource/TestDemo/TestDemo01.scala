package com.gaia.dataSource.TestDemo

import java.net.InetAddress

/**
  * @author michael
  * @create 2020-04-19 12:29
  */
object TestDemo01 {

  def main(args: Array[String]): Unit = {


    println(InetAddress.getLocalHost)
    println(InetAddress.getLocalHost.getHostName)
//    println(ia.getHostName)
//    println(ia.getHostAddress)
    println(System.getProperty("os.name"))
  }

}
