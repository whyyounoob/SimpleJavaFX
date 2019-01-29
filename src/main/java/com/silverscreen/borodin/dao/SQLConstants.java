package com.silverscreen.borodin.dao;

public class SQLConstants {
  public static final String INSERT_HUMAN = "INSERT INTO humans (name, birthday) VALUES(?, ?);";
  public static final String DELETE_HUMAN = "DELETE FROM humans WHERE id = ?;";
  public static final String GET_ALL = "SELECT * FROM humans ORDER BY name;";
  public static final String UPDATE_HUMAN = "UPDATE humans SET name = ?, birthday = ? WHERE id = ?;";
}
