package com.gaia.ESProject.test

import com.gaia.ESProject.utility.{SparkInit, common}
import org.elasticsearch.spark.sql._


object ConnES {
  def main(args: Array[String]): Unit = {

    val spark = SparkInit.getSpark("connES")

    import spark.implicits._

    //获取es配置map
    val options = common.getHashMap("esconfig.properties")

    //获取es的index
    val index = common.getHashMap("esindex.properties").get("ods_visit").get


    val df = spark.sql(
      """
        |select
        |concat_ws('_',bu,id) as gaia_id,
        |status,
        |creator_id,
        |creator_name,
        |create_time,
        |modifyier_id,
        |modifyier_name,
        |modify_time,
        |visitor,
        |visitor_name,
        |visit_type,
        |flow_type,
        |plan_source,
        |approval_status,
        |customer,
        |customer_name,
        |date_format(visit_date, 'yyyy-MM-dd') as visit_date,
        |visit_time,
        |sequence,
        |visit_status,
        |flow_set_id,
        |flow_set_name,
        |is_finished,
        |arrive_time,
        |arrive_lla,
        |arrive_rg_type,
        |arrive_pos_offset,
        |leave_time,
        |leave_lla,
        |leave_rg_type,
        |leave_pos_offset,
        |time_consume,
        |call_date,
        |bu
        |from ods_sftm_new.ods_visit
      """.stripMargin)

    df.repartition(320).toDF().saveToEs(index, options)
    //    case class dmp(user_id: String, tag_id:String, add_time:String, update_time:String)

    spark.stop()
  }
}
