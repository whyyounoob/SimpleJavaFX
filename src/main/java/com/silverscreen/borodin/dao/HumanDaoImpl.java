package com.silverscreen.borodin.dao;

import com.silverscreen.borodin.model.Human;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HumanDaoImpl implements HumanDao {

  private static HumanDaoImpl instance;

  private HumanDaoImpl() {}

  public static synchronized HumanDaoImpl getInstance() {
    if (instance == null) {
      instance = new HumanDaoImpl();
    }
    return instance;
  }

  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

  public List<Human> getAll() {
    List<Human> humans = new ArrayList();
    try (Connection connection = ConnectionPool.getConnection(); ) {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(SQLConstants.GET_ALL);
      while (resultSet.next()) {
        Human human = new Human();
        human.setId(resultSet.getInt("id"));
        human.setName(resultSet.getString("name"));
        human.setDate(simpleDateFormat.parse(resultSet.getString("birthday")));
        human.setAge(calculateAge(human.getDate()));
        humans.add(human);
      }
      resultSet.close();
      statement.close();
    } catch (SQLException | ParseException e) {
      e.printStackTrace();
    }
    return humans;
  }

  public void save(Human human) {
    try (Connection connection = ConnectionPool.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.INSERT_HUMAN);
      preparedStatement.setString(1, human.getName());
      preparedStatement.setString(2, simpleDateFormat.format(human.getDate()));
      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update(Human human) {
    try (Connection connection = ConnectionPool.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_HUMAN);
      preparedStatement.setString(1, human.getName());
      preparedStatement.setString(2, simpleDateFormat.format(human.getDate()));
      preparedStatement.setInt(3, human.getId());
      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void delete(int id) {
    try (Connection connection = ConnectionPool.getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.DELETE_HUMAN);
      preparedStatement.setInt(1, id);
      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private int calculateAge(Date birthDate) {
    int age;
    Calendar now = Calendar.getInstance();
    Calendar dob = Calendar.getInstance();
    dob.setTime(birthDate);
    int year1 = now.get(Calendar.YEAR);
    int year2 = dob.get(Calendar.YEAR);
    age = year1 - year2;
    int month1 = now.get(Calendar.MONTH);
    int month2 = dob.get(Calendar.MONTH);
    if (month2 > month1) {
      age--;
    } else if (month1 == month2) {
      int day1 = now.get(Calendar.DAY_OF_MONTH);
      int day2 = dob.get(Calendar.DAY_OF_MONTH);
      if (day2 > day1) {
        age--;
      }
    }
    return age;
  }
}
