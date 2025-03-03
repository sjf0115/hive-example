package com.data.example.jdbc;

import com.data.example.bean.Column;
import com.data.example.bean.ColumnBuilder;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.metastore.api.TableMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：获取所有列
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/3/2 22:00
 */
public class QueryColumnExample {
    private static final Logger LOG = LoggerFactory.getLogger(QueryColumnExample.class);
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
     * 通过 HQL 获取所有列
     * @param dbName
     * @return
     * @throws SQLException
     */
    public static List<Column> getColumnsBySQL(String dbName, String tableName) throws SQLException {
        if (StringUtils.isBlank(dbName)) {
            return Lists.newArrayList();
        }
        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            List<Column> columns = new ArrayList<>();
            String sql = "DESCRIBE " + dbName + "." + tableName;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String columnName = rs.getString(1).trim();
                String columnType = rs.getString(2).trim().toUpperCase();
                String columnComment = rs.getString(3).trim();
                Column column = new ColumnBuilder()
                        .setColumnName(columnName)
                        .setColumnType(columnType)
                        .setColumnComment(columnComment)
                        .build();
                columns.add(column);
            }
            return columns;
        }
    }

    /**
     * 通过 MetaData 获取所有列
     * @param dbName
     * @return
     * @throws SQLException
     */
    public static List<Column> getColumnByMeta(String dbName, String tableName) throws SQLException {
        if (StringUtils.isBlank(dbName)) {
            return Lists.newArrayList();
        }
        try (Connection conn = DriverManager.getConnection(url,user,passwd)) {
            List<Column> columns = new ArrayList<>();
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getColumns(
                    null,    // catalog (Hive 中通常为 null)
                    dbName,      // schemaPattern (数据库名称)
                    tableName,     // tableNamePattern (表名称)
                    "%"             // columnNamePattern (通配符，匹配所有列)
            );
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME"); // 列名
                String columnType = rs.getString("TYPE_NAME"); // 数据类型
                int columnSize = rs.getInt("COLUMN_SIZE"); // 列大小
                String columnComment = rs.getString("REMARKS"); // 列备注
                Column column = new ColumnBuilder()
                        .setColumnName(columnName)
                        .setColumnType(columnType)
                        .setColumnComment(columnComment)
                        .setColumnSize(columnSize)
                        .build();
                columns.add(column);

            }
            return columns;
        }
    }

    public static void main(String[] args) throws SQLException {
        String dbName = "profile";
        String tableName = "user_profile_attr_label_df";
        List<Column> columns = getColumnByMeta(dbName, tableName);
        for (Column column : columns) {
            LOG.info("列信息: " + column.toString());

        }

        LOG.info("------------------------------------------------");

        List<Column> columns2 = getColumnsBySQL(dbName, tableName);
        for (Column column : columns2) {
            LOG.info("列信息: " + column.toString());
        }
    }
}
