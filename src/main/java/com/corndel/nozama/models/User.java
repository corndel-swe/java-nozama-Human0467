package com.corndel.nozama.models;

public class User {
  private Integer id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String avatar;


  public User() {}

  public User(Integer id, String username, String firstName, String lastName, String email, String avatar) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.avatar = (avatar == null || avatar.isEmpty()) ? DEFAULT_AVATAR : avatar;
  }
  private static final String DEFAULT_AVATAR = "https://imgcdn.stablediffusionweb.com/2024/9/16/778175e0-33f9-4224-9e6b-5c0984042816.jpg";

  public User(int id) {
  }

  public Integer getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  @Override
  public String toString() {
    return "User{id=" + id +
            ", username='" + username + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", avatar='" + avatar + '\'' + '}';
  }

}
