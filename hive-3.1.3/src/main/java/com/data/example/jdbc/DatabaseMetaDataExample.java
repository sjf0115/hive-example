package com.data.example.jdbc;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.metastore.api.TableMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/3/2 22:39
 */
public class DatabaseMetaDataExample {
    private static final Logger LOG = LoggerFactory.getLogger(QueryTableExample.class);
    private static String driverName ="org.apache.hive.jdbc.HiveDriver";
    private static String url="jdbc:hive2://localhost:10000/default";
    private static String user = "";
    private static String passwd = "";


    public static void main(String[] args) {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(url,user,passwd)) {
            DatabaseMetaData meta = conn.getMetaData();

            // 获取数据库产品名称和版本
            String productName = meta.getDatabaseProductName(); // 如 "Hive"
            String productVersion = meta.getDatabaseProductVersion(); // 如 "3.1.2"
            int majorVersion = meta.getDatabaseMajorVersion();
            int minorVersion = meta.getDatabaseMinorVersion();

            LOG.info("productName: " + productName + ", productVersion: " + productVersion
                    + ", majorVersion: " + majorVersion + ", minorVersion: " + minorVersion);

            // 获取 JDBC 驱动信息
            String driverName = meta.getDriverName(); // 如 "Hive JDBC"
            String driverVersion = meta.getDriverVersion();// 如 12345

            ResultSet catalogs = meta.getCatalogs();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
