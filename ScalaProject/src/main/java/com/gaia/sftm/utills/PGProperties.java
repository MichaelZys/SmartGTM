package com.gaia.sftm.utills;

import java.util.Properties;


public class PGProperties {
    private static Properties pro =null;

    public static Properties getPro(){
        pro = new Properties();
        pro.put("user", JDBCUtil.getUser());
        pro.put("password",JDBCUtil.getPassword());
        pro.put("driver",JDBCUtil.getDriver());

        return pro;
    }
}
