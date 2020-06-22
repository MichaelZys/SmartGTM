package com.gaia.sftm.APP;

import com.gaia.sftm.common.HiveToPG;
import com.gaia.sftm.common.PGTruncate;
import com.gaia.sftm.common.PGDelete;
import com.gaia.sftm.common.PGUpdate;
import jodd.util.CsvUtil;
import org.apache.spark.sql.SparkSession;
import com.gaia.sftm.utills.JDBCUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class SparkToPG {
    public static void main(String[] args) {

        try {
            Connection connection = JDBCUtil.getConnection();
            // 3. 先把flag置为0, 不让其他人操作
            PGUpdate.updateData(connection, "ods_sftm.matedataconf", "0");

//            // 2. 对于拜访表, 删除今天新增的即可
//            PGDelete.deleteData(connection, "ods_sftm.ods_visit");
////            //1.清空PG表数据
//            PGTruncate.trunData(connection, "ods_sftm.ods_org_emp");
//            PGTruncate.trunData(connection, "ods_sftm.ods_org_dep");
//            PGTruncate.trunData(connection, "ods_sftm.dwd_base_info");
//            PGTruncate.trunData(connection, "ods_sftm.dwd_cust");
//            PGTruncate.trunData(connection, "ods_sftm.ods_cust_dealer");
            PGTruncate.trunData(connection, "ods_sftm.ods_cust_store");
//            PGTruncate.trunData(connection, "ods_sftm.ods_cust_type");
//            PGTruncate.trunData(connection, "ods_sftm.ods_product");


            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //***********************每天全量导入pg********************************
        //1.部门表  2.员工表  3.门店经销商融合表   4.部门、员工、门店、经销商融合表
        HiveToPG.ToPg();


    }
}
