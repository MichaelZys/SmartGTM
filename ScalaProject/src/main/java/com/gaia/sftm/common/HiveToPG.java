package com.gaia.sftm.common;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import com.gaia.sftm.utills.JDBCUtil;
import com.gaia.sftm.utills.PGProperties;

import java.util.Properties;

public class HiveToPG {
    private static String url = null;
    private static Properties pro = null;

    public static void ToPg(){

        SparkSession spark = com.gaia.sftm.utills.InitSpark.initSpark();
        url = JDBCUtil.getUrl();
        pro = PGProperties.getPro();

        String emp = "ods_sftm.ods_org_emp";
        String dep = "ods_sftm.ods_org_dep";
        String cust = "ods_sftm.dwd_cust";
        String info = "ods_sftm.dwd_base_info";
        String custType = "ods_sftm.ods_cust_type";
        String product = "ods_sftm.ods_product";
        String visit = "ods_sftm.ods_visit";
        String dealer = "ods_sftm.ods_cust_dealer";
        String store = "ods_sftm.ods_cust_store";

        // 1.查询Hive数据 员工表
        String empsql = "select *, from_unixtime(unix_timestamp(),'yyyy-MM-dd') as data_version from ods_sftm_new.ods_org_emp";
        appendData(spark,empsql,url,emp,pro);
//
//        // 2.查询hive数据  部门表
        String depsql = "select *, from_unixtime(unix_timestamp(),'yyyy-MM-dd') as data_version from ods_sftm_new.ods_org_dep";
        appendData(spark,depsql,url,dep,pro);

        // 3.查询 Hive经销商门店宽表
        String custsql = "select *, from_unixtime(unix_timestamp(),'yyyy-MM-dd') as data_version from dwd_sftm.dwd_cust";
        appendData(spark,custsql,url,cust,pro);

        // 4.查询 hive 部门、员工、门店、经销商宽表
        String infosql = "select *, from_unixtime(unix_timestamp(),'yyyy-MM-dd') as data_version from dwd_sftm.dwd_base_info";
        appendData(spark,infosql,url,info,pro);

        // 5.查询客户类型
        String custTypesql = "select *, from_unixtime(unix_timestamp(),'yyyy-MM-dd') as data_version from ods_sftm_new.ods_cust_type";
        appendData(spark,custTypesql,url,custType,pro);

        // 6.查询门店
        String storesql = "select *, from_unixtime(unix_timestamp(),'yyyy-MM-dd') as data_version from ods_sftm_new.ods_cust_store";
        appendData(spark,storesql,url,store,pro);
//
//        // 7.查询经销商
        String dealersql = "select *, from_unixtime(unix_timestamp(),'yyyy-MM-dd') as data_version from ods_sftm_new.ods_cust_dealer";
        appendData(spark,dealersql,url,dealer,pro);
//
//        // 8.查询商品
        String productsql = "select *, from_unixtime(unix_timestamp(),'yyyy-MM-dd') as data_version from ods_sftm_new.ods_product";
        appendData(spark,productsql,url,product,pro);

        // 9.拜访数据
//        String visitsql = "select *, from_unixtime(unix_timestamp(),'yyyy-MM-dd') as data_version from ods_sftm_new.ods_visit";
        String visitsql = "select *, date_format(visit_date,'yyyy-MM-dd') as data_version from ods_sftm_new.ods_visit where date_format(visit_date,'yyyy-MM-dd')=date_sub(current_date(),1)";
//        String visitsql = "select *, date_format(visit_date,'yyyy-MM-dd') as data_version from ods_sftm_new.ods_visit where date_format(visit_date,'yyyy-MM-dd')='2020-04-18'";
//        String visitsql = "select *, from_unixtime(unix_timestamp(),'yyyy-MM-dd') as data_version from ods_sftm_new.ods_visit where date_format(call_date,'yyyy-MM-dd')='2020-04-18'";
        System.out.println(visitsql);
        appendData(spark,visitsql,url,visit,pro);

        spark.close();

    }

    public static void appendData(SparkSession spark, String sql, String url, String name, Properties pro) {
        Dataset<Row> DF = spark.sql(sql);
        DF.write().mode("append").jdbc(url,name,pro);

    }

}

//        String empsql = "select *,unix_timestamp() as data_version from ods_sftm_new.ods_org_emp";
//        Dataset<Row> empDF = spark.sql(empsql);
//        empDF.write().mode("append").jdbc(url,"dataman.ods_org_emp",pro);
//
//        // 2.查询hive数据  部门表
//        String depsql = "select *,unix_timestamp() as data_version from ods_sftm_new.ods_org_dep";
//        Dataset<Row> depDF = spark.sql(depsql);
//        depDF.write().mode("append").jdbc(url,"dataman.ods_org_dep",pro);
//
//        // 3.查询 Hive经销商门店宽表
//        String custsql = "select *,unix_timestamp() as data_version from dwd_sftm.dwd_cust";
//        Dataset<Row> custDF = spark.sql(custsql);
//        custDF.write().mode("append").jdbc(url,"dataman.dwd_cust",pro);
//
//        // 4.查询 hive 部门、员工、门店、经销商宽表
//        String infosql = "select *,unix_timestamp() as data_version from dwd_sftm.dwd_base_info";
//        Dataset<Row> inDF = spark.sql(infosql);
//        inDF.write().mode("append").jdbc(url,"dataman.dwd_base_info",pro);

