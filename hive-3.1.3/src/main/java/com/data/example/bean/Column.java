package com.data.example.bean;

/**
 * 功能：数据库列信息
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/3/3 22:38
 */
public class Column {
    private String columnName;
    private String columnType;
    private String columnComment;
    private int columnSize;

    public Column() {
    }

    public Column(String columnName, String columnType, String columnComment, int columnSize) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnComment = columnComment;
        this.columnSize = columnSize;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnComment='" + columnComment + '\'' +
                ", columnSize=" + columnSize +
                '}';
    }
}
