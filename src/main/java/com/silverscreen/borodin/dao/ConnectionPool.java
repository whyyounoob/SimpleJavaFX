package com.silverscreen.borodin.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

  private static BasicDataSource ds = new BasicDataSource();

  static {
    ds.setUrl("jdbc:sqlite:F:\\Projects\\SimpleJavaFX\\humans.db");
    ds.setMinIdle(5);
    ds.setMaxIdle(10);
    ds.setMaxOpenPreparedStatements(15);
  }

  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
  }

  private ConnectionPool() {}
}
