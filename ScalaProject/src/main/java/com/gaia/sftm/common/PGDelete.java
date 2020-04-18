package com.gaia.sftm.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.text.*;

public class PGDelete {

    private static CallableStatement callableStatement = null;

    public static void deleteData(Connection con, String tabl) throws SQLException {

        //清空表
        System.out.println("********************开始删除数据*************************");
        Date dNow = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

        System.out.println("当前时间为: " + ft.format(dNow.getTime()-86400000L));

        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(tabl);
        sql.append(" where data_version = ");
        sql.append("'");
        sql.append(ft.format(dNow.getTime()-86400000L));
        sql.append("'");
        System.out.println(sql);
        callableStatement = con.prepareCall(sql.toString());
        callableStatement.execute();
        System.out.println("********************删除数据完成！*************************");
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
