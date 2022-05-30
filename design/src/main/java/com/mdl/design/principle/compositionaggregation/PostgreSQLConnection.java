package com.mdl.design.principle.compositionaggregation;

/**
 * Created by meidanlong
 */
public class PostgreSQLConnection extends DBConnection {
    @Override
    public String getConnection() {
        return "PostgreSQL数据库连接";
    }
}
