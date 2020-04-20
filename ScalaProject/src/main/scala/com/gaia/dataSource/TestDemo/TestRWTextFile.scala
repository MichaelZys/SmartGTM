package com.gaia.dataSource.TestDemo

import java.io.{File, FileWriter, PrintWriter}

import scala.io.Source

/**
  * @author michael
  * @create 2020-04-20 22:43
  */
object TestRWTextFile {

  def main(args: Array[String]): Unit = {

//    var filePath = "D:\\Github\\SmartGTM\\ScalaProject\\src\\main\\resources\\test.text"
//
//    //
//    val source = Source.fromFile(filePath, "UTF-8")
//
//    //读取所有行
//    //    val lineIterator = source.getLines()
//    //
//    //    lineIterator.foreach(println)
//
//    //如果先读, size会变成0? 什么鬼
//    val lines = source.getLines().toArray
//
//    println(lines.size)
//    source.close()

//    //文件写入
//    val writer = new PrintWriter(new File
//    ("D:\\Github\\SmartGTM\\ScalaProject\\src\\main\\resources\\test1.text"))
//    for (i <- 1 to 10) {
//      writer.println(i)
//    }
//    writer.close()

    //文件追加

    val out = new FileWriter("D:\\Github\\SmartGTM\\ScalaProject\\src\\main" +
      "\\resources\\test2.text",true)
    for (i <- 0 to 15){
      out.write(i.toString + "\n")
    }
    out.close()


  }
}
