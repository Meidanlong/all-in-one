package com.mdl.design.principle.compositionaggregation;

/**
 * Created by meidanlong
 */
public class MySQLConnection extends DBConnection {
    @Override
    public String getConnection() {
        return "MySQL数据库连接";
    }
}
