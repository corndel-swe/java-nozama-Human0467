package com.corndel.nozama;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DB {
  // Set the dbUrl
  private static final Dotenv dotenv = Dotenv.load();
  public static final String dbUrl = dotenv.get("DB_URL");

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(dbUrl);
  }
}
