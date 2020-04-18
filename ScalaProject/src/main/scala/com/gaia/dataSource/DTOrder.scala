package com.gaia.dataSource

import java.io.FileInputStream
import java.util.Properties

/**
  * @author michael
  * @create 2020-04-18 20:42
  */
class DTOrder {

  def getDTOrderColStr(): String ={
    //从properties文件中读配置
    val properties = new Properties
    val path = Thread.currentThread().getContextClassLoader.getResource("dtOrderColumnList.properties").getPath
    properties.load(new FileInputStream(path))
    properties.getProperty("colName")
  }

}
