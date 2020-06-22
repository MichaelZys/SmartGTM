package com.gaia.sftm.common;

import java.sql.*;

public class PGTruncate {

    private static CallableStatement callableStatement = null;

    public static void trunData(Connection con, String tabl) throws SQLException {


        //清空表
        System.out.println("********************开始清空数据*************************");
        callableStatement = con.prepareCall("truncate table " + tabl);
        callableStatement.execute();
        System.out.println("********************清空数据完成！*************************");
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
