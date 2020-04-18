package com.gaia.sftm.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PGUpdate {

    private static CallableStatement callableStatement = null;

    public static void updateData(Connection con, String tabl, String para) throws SQLException {


        //清空表
        System.out.println("********************开始把Flag置为0*************************");

        StringBuilder sql = new StringBuilder("update ");
        sql.append(tabl);
        sql.append(" set confvalue = ");
        sql.append(para.toString());
        sql.append(" where confkey = 'sftm_hive2pg' ");
        System.out.println(sql.toString());
        callableStatement = con.prepareCall(sql.toString());
        callableStatement.execute();
        System.out.println("********************重置数据完成！*************************");
        try {
            //先判断，如果callableStatement为null，是不能调用方法的
            if (callableStatement != null) {
                callableStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
