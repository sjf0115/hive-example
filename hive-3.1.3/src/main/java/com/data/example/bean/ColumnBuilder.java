package com.data.example.bean;

/**
 * 功能：列构造器
 * 作者：SmartSi
 * CSDN博客：https://smartsi.blog.csdn.net/
 * 公众号：大数据生态
 * 日期：2025/3/3 22:39
 */
public class ColumnBuilder {
    private String columnName;
    private String columnType;
    private String columnComment;
    private int columnSize;

    public ColumnBuilder() {
    }

    ColumnBuilder(Column column) {
        this.columnName = column.getColumnName();
        this.columnType = column.getColumnType();
        this.columnComment = column.getColumnComment();
        this.columnSize = column.getColumnSize();
    }

    public ColumnBuilder setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public ColumnBuilder setColumnType(String columnType) {
        this.columnType = columnType;
        return this;
    }

    public ColumnBuilder setColumnComment(String columnComment) {
        this.columnComment = columnComment;
        return this;
    }

    public ColumnBuilder setColumnSize(int columnSize) {
        this.columnSize = columnSize;
        return this;
    }

    public Column build() {
        return new Column(columnName, columnType, columnComment, columnSize);
    }
}
