package com.company.exercise.datastore.data;

/***
 * Key that uniquely identifies a user object
 */
public class UserKey {
  private String stream;
  private String userId;

  public UserKey(String stream, String userId) {
    this.stream = stream;
    this.userId = userId;
  }

  public String getStream() {
    return stream;
  }

  public String getUserId() {
    return userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserKey that = (UserKey) o;

    if (!stream.equals(that.stream)) return false;
    return userId.equals(that.userId);

  }

  @Override
  public int hashCode() {
    int result = stream.hashCode();
    result = 31 * result + userId.hashCode();
    return result;
  }
}
