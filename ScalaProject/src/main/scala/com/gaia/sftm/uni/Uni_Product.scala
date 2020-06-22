package com.gaia.sftm.uni

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
  * @author michael
  * @create 2020-03-02 18:04
  */
object Uni_Product {
  def main(args: Array[String]): Unit = {

    System.setProperty("HADOOP_USER_NAME", "hadoop")
    System.setProperty("user.name", "root")

    val conf = new SparkConf
    conf.set("spark.sql.adaptive.enabled", "true")
    conf.set("spark.sql.adaptive.shuffle.targetPostShuffleInputSize", "128M")
    conf.set("spark.sql.adaptive.join.enabled", "true")
    conf.set("spark.sql.autoBroadcastJoinThreshold", "20971520")

    val spark = SparkSession
      .builder()
      .config(conf)
      .appName("Uni_Product")
//      .master("local[*]")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._

    val df_uni_product: DataFrame = spark.sql(
      """
        |select
        |    prd_id,
        |    status,
        |    prd_waiqin365_id,
        |    prd_code,
        |    prd_short_code,
        |    prd_barcode,
        |    prd_name,
        |    class_name,
        |    prd_spec,
        |    prd_brand,
        |    prd_unit,
        |    prd_valid_period,
        |    prd_weight,
        |    prd_sequ,
        |    prd_remarks,
        |    create_time,
        |    with_tag_new,
        |    with_tag_hot,
        |    with_tag_gift,
        |    with_tag_sale,
        |    with_tag_ex1,
        |    prd_suggest_price,
        |    prd_cost_price,
        |    prd_price,
        |    prd_sale_status,
        |    prd_short_name,
        |    prd_teu_coefficient,
        |    prd_same_price_code,
        |    creator_id,
        |    create_name,
        |    modifyier_id,
        |    modifyier_name,
        |    modifier_time,
        |    call_date,
        |    bu,
        |    prd_exts,
        |    prd_units
        |from
        |(
        |    select row_number() over(partition by prd_waiqin365_id order by call_date desc) as uuid, * from ods_sftm_new.ods_product_tmp
        |) as t1
        |where t1.uuid = 1
      """.stripMargin)

    df_uni_product.write.mode(SaveMode.Overwrite).saveAsTable("ods_sftm_new.ods_product")
    //  下面的repartition, 产生N个HDFS文件, 也可以按照列来产生...具体可以参考API文档.
    //    df_uni_store.repartition(1).write.mode(SaveMode.Overwrite).saveAsTable("ods_sftm_new.ods_cust_store")


    spark.sql(
      """
        |select
        |    t1.prd_id,
        |    t1.status,
        |    t1.prd_waiqin365_id,
        |    t1.prd_code,
        |    t1.prd_short_code,
        |    t1.prd_barcode,
        |    t1.prd_name,
        |    t1.class_name,
        |    t1.prd_spec,
        |    t1.prd_brand,
        |    t1.prd_unit,
        |    t1.prd_valid_period,
        |    t1.prd_weight,
        |    t1.prd_sequ,
        |    t1.prd_remarks,
        |    t1.create_time,
        |    t1.with_tag_new,
        |    t1.with_tag_hot,
        |    t1.with_tag_gift,
        |    t1.with_tag_sale,
        |    t1.with_tag_ex1,
        |    t1.prd_suggest_price,
        |    t1.prd_cost_price,
        |    t1.prd_price,
        |    t1.prd_sale_status,
        |    t1.prd_short_name,
        |    t1.prd_teu_coefficient,
        |    t1.prd_same_price_code,
        |    t1.creator_id,
        |    t1.create_name,
        |    t1.modifyier_id,
        |    t1.modifyier_name,
        |    t1.modifier_time,
        |    t1.call_date,
        |    t1.bu,
        |    t1.prd_exts,
        |    t1.prd_units,
        |    replace(replace(item,'"prd_ext_key": "系列", "prd_ext_value": "',''),'"','') as prd_type from
        |(
        |select *,replace(replace(exts_item,'{',''),'}','') as item
        |from ods_sftm_new.ods_product lateral view explode(split(substring(prd_exts,2, length(prd_exts)-2),"},")) tmp as exts_item
        |) as t1 where item like '%系列%' and bu = 'km'
      """.stripMargin).write.mode(SaveMode.Overwrite).saveAsTable("dm_sftm.dm_km_product")
    spark.stop()

  }
}
