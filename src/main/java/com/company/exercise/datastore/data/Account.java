package com.company.exercise.datastore.data;

/***
 * Account information
 */
public class Account {
  private String stream;
  private String accountId;
  private String accountName;
  private String status;
  private String plan;

  public Account(String stream,
                 String accountId,
                 String accountName,
                 String status,
                 String plan) {
    this.stream = stream;
    this.accountId = accountId;
    this.accountName = accountName;
    this.status = status;
    this.plan = plan;
  }

  public String getStream() {
    return stream;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getAccountName() {
    return accountName;
  }

  public String getStatus() {
    return status;
  }

  public String getPlan() {
    return plan;
  }
}
