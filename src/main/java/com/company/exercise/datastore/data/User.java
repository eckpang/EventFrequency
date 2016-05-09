package com.company.exercise.datastore.data;

/***
 * User information
 */
public class User {
  private String stream;
  private String userId;
  private String userName;
  private String role;

  public User(String stream,
              String userId,
              String userName,
              String role) {
    this.stream = stream;
    this.userId = userId;
    this.userName = userName;
    this.role = role;
  }

  public String getStream() {
    return stream;
  }

  public String getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public String getRole() {
    return role;
  }
}
