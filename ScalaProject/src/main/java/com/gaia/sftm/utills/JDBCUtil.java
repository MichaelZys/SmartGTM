package com.gaia.sftm.utills;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    private static String url;
    private static String user;
    private static String password;
    private static String driver;
    private static Connection con;
    private static InputStream resourceAsStream;

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static String getDriver() {
        return driver;
    }

    static {

        try {
            Properties properties = new Properties();
            //把当前类加载至内存，获取配置文件信息
            ClassLoader classLoader = JDBCUtil.class.getClassLoader();
            resourceAsStream = classLoader.getResourceAsStream("JDBCInfo.properties");
            properties.load(resourceAsStream);

            /*URL resource = classLoader.getResource("/JDBCInfo.properties");
            assert resource != null;
            String path = resource.getPath();
            properties.load(new FileInputStream(path));
             */

            //57
            url = properties.getProperty("url");
            if (InetAddress.getLocalHost().getHostName().contains("gaialab")) {
                url.replace("10.10.17.14","192.168.0.108");
//                conf.set("hive.metastore.uris", "thrift://10.10.17.11:9083")
            }
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");

            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resourceAsStream != null) {
                    resourceAsStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    获取连接  57
     */
    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}

