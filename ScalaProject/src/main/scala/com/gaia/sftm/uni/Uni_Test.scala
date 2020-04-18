package com.gaia.sftm.uni

import com.gaia.sftm.utility.InitSpark

object Uni_Test {

  def main(args: Array[String]): Unit = {
    val spark = new InitSpark().getSparkSession()



    spark.sql("select * from ods_sftm_new.ods_cust_dealer limit 5")
      .collect()

  }





}
