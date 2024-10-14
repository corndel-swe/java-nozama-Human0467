package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import com.corndel.nozama.models.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {


  public static List<User> findAll() throws SQLException {
    var query = "SELECT id, username, firstName, lastName, email, avatar FROM users";
    try (var con = DB.getConnection();
         var stmt = con.createStatement();
         var rs = stmt.executeQuery(query);) {

      var users = new ArrayList<User>();
      while (rs.next()) {
        var id = rs.getInt("id");
        var username = rs.getString("username");
        var firstName = rs.getString("firstName");
        var lastName = rs.getString("lastName");
        var email = rs.getString("email");
        var avatar = rs.getString("avatar");

        users.add(new User(id, username, firstName, lastName, email, avatar));
      }

      return users;
    }
  }

  public static User findById(int id) throws SQLException {
    // make the query
    var query = "SELECT id, username, firstName, lastName, email, avatar\n";
    query += "FROM users \n";
    query += ("WHERE id = ?");

    // try with resources - get connection
    try (var con = DB.getConnection();
         var stmt = con.prepareStatement(query);) {

      // sub in the id argument
      stmt.setInt(1, id);

      // try with resources - execute query
      try (var rs = stmt.executeQuery();) {

        if (rs.next()) {
          var username = rs.getString("username");
          var firstName = rs.getString("firstName");
          var lastName = rs.getString("lastName");
          var email = rs.getString("email");
          var avatar = rs.getString("avatar");

          User user = new User(id, username, firstName, lastName, email, avatar);
          return user;
        } else {
          System.out.println("not a valid id.");
          return null;
        }
      }
    }
  }


  public static User deleteUser(int id) throws SQLException {
    var query = "DELETE FROM users WHERE id = ?";
    try (var connection = DB.getConnection();
         var statement = connection.prepareStatement(query)) {
      statement.setInt(1, id);

      System.out.println(statement);

      int deletedRow = statement.executeUpdate();
      System.out.println(deletedRow);
      if (deletedRow > 0) {
        System.out.println("USER Deleted by ID " + id);
      } else {
        System.out.println("no user found by ID " + id);
      }
    }
    return new User(id);
  }


  public static User loginUser(String username, String password) throws SQLException {
    var query = "SELECT id, username, firstName, lastName, email, avatar FROM users WHERE username = ? AND password = ?";

    try (var connection = DB.getConnection();
         var statement = connection.prepareStatement(query)) {

      statement.setString(1, username);
      statement.setString(2, password);
      System.out.println(statement);
      try (var resultSet = statement.executeQuery();) {
        if(resultSet.next()){
          var userId = resultSet.getInt("id");
          var userName = resultSet.getString("username");
          var firstName = resultSet.getString("firstName");
          var lastName = resultSet.getString("lastName");
          var email = resultSet.getString("email");
          var avatar = resultSet.getString("avatar");
          System.out.println("CORRECT USERNAME AND PASSWORD");
          return new User(userId, userName,firstName,lastName,email,avatar);
        } else {
          System.out.println("No userName or password was found");
          return null;
        }

      }
    }

  }


  public static void main(String[] args) throws SQLException {
    var user = UserRepository.deleteUser(23);
    System.out.println(user);

    var userLogin = UserRepository.loginUser("Ebba.Cole", "nz5H7F98ukot7yv");
    System.out.println(userLogin);
  }
}
