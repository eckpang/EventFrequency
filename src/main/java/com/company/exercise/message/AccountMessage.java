package com.company.exercise.message;

/***
 * A POJO that is mapped to an account JSON message
 */
public class AccountMessage implements Message {
  // Timestamp in milliseconds
  private long timestamp;

  // The customer identifier
  private String stream;

  private String userId;

  private String userName;

  private UserData userData;

  private String accountId;

  private String accountName;

  private AccountData accountData;

  public AccountMessage() {
  }

  public AccountMessage(long timestamp,
                        String stream,
                        String userId,
                        String userName,
                        String userRole,
                        String accountId,
                        String accountName,
                        String accountStatus,
                        String accountPlan) {
    this.timestamp = timestamp;
    this.stream = stream;
    this.userId = userId;
    this.userName = userName;
    this.userData = new UserData(userRole);
    this.accountId = accountId;
    this.accountName = accountName;
    this.accountData = new AccountData(accountStatus, accountPlan);
  }

  @Override
  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public String getStream() {
    return stream;
  }

  @Override
  public String getUserId() {
    return userId;
  }

  @Override
  public String getAccountId() {
    return accountId;
  }

  public String getUserName() {
    return userName;
  }

  public String getRole() {
    return userData.getRole();
  }

  public String getAccountName() {
    return accountName;
  }

  public String getAccountStatus() {
    return accountData.getStatus();
  }

  public String getAccountPlan() {
    return accountData.getPlan();
  }

  public UserData getUserData() {
    return userData;
  }

  public AccountData getAccountData() {
    return accountData;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AccountMessage that = (AccountMessage) o;

    if (timestamp != that.timestamp) return false;
    if (!stream.equals(that.stream)) return false;
    if (!userId.equals(that.userId)) return false;
    if (!userName.equals(that.userName)) return false;
    if (!userData.equals(that.userData)) return false;
    if (!accountId.equals(that.accountId)) return false;
    if (!accountName.equals(that.accountName)) return false;
    return accountData.equals(that.accountData);

  }

  @Override
  public int hashCode() {
    int result = (int) (timestamp ^ (timestamp >>> 32));
    result = 31 * result + stream.hashCode();
    result = 31 * result + userId.hashCode();
    result = 31 * result + userName.hashCode();
    result = 31 * result + userData.hashCode();
    result = 31 * result + accountId.hashCode();
    result = 31 * result + accountName.hashCode();
    result = 31 * result + accountData.hashCode();
    return result;
  }

  public static class UserData {
    private String role;

    public UserData() {
    }

    public UserData(String role) {
      this.role = role;
    }

    public String getRole() {
      return role;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      UserData userData = (UserData) o;

      return role.equals(userData.role);

    }

    @Override
    public int hashCode() {
      return role.hashCode();
    }
  }

  public static class AccountData {
    private String status;
    private String plan;

    public AccountData() {
    }

    public AccountData(String status, String plan) {
      this.status = status;
      this.plan = plan;
    }

    public String getStatus() {
      return status;
    }

    public String getPlan() {
      return plan;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      AccountData that = (AccountData) o;

      if (!status.equals(that.status)) return false;
      if (!plan.equals(that.plan)) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = status.hashCode();
      result = 31 * result + plan.hashCode();
      return result;
    }
  }
}
