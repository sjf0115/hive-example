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
 * 功能：获取所有表
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/3/2 22:00
 */
public class QueryTableExample {
    private static final Logger LOG = LoggerFactory.getLogger(QueryTableExample.class);
    private static String driverName ="org.apache.hive.jdbc.HiveDriver";
    private static String url="jdbc:hive2://localhost:10000/default";
    private static String user = "";
    private static String passwd = "";

    static {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过 HQL 获取所有表
     * @param dbName
     * @return
     * @throws SQLException
     */
    public static List<String> getTablesBySQL(String dbName) throws SQLException {
        if (StringUtils.isBlank(dbName)) {
            return Lists.newArrayList();
        }
        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            String sql = "SHOW TABLES IN " + dbName;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<String> tables = new ArrayList<>();
            while (rs.next()) {
                String tableName = rs.getString(1);
                tables.add(tableName);
            }
            return tables;
        }
    }

    /**
     * 通过 getMetaData 获取所有表
     * @param dbName
     * @return
     * @throws SQLException
     */
    public static List<TableMeta> getTablesByMeta(String dbName) throws SQLException {
        if (StringUtils.isBlank(dbName)) {
            return Lists.newArrayList();
        }
        try (Connection conn = DriverManager.getConnection(url,user,passwd)) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, dbName, "%", new String[]{"TABLE"});

            List<TableMeta> tables = new ArrayList<>();
            while (rs.next()) {
                TableMeta table = new TableMeta();
                table.setTableName(rs.getString("TABLE_NAME"));
                table.setComments(rs.getString("REMARKS"));
                tables.add(table);
            }
            return tables;
        }
    }

    public static void main(String[] args) throws SQLException {
        String dbName = "profile";
        List<TableMeta> tablesViaJdbcMeta = getTablesByMeta(dbName);
        for (TableMeta table : tablesViaJdbcMeta) {
            LOG.info("表名: " + table.getTableName() + ", 注释: " + table.getComments());

        }

        LOG.info("------------------------------------------------");

        List<String> tablesBySQL = getTablesBySQL(dbName);
        for (String table : tablesBySQL) {
            LOG.info("表名: " + table);
        }
    }
}
