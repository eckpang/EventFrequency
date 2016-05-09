package com.company.exercise.datastore.data;

/***
 * Key that uniquely identifies an account object
 */
public class AccountKey {
  private String stream;
  private String accountId;

  public AccountKey(String stream, String accountId) {
    this.stream = stream;
    this.accountId = accountId;
  }

  /***
   * @return The stream name of the account key
   */
  public String getStream() {
    return stream;
  }

  /***
   * @return The account ID of the account key
   */
  public String getAccountId() {
    return accountId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AccountKey that = (AccountKey) o;

    if (!stream.equals(that.stream)) return false;
    return accountId.equals(that.accountId);
  }

  @Override
  public int hashCode() {
    int result = stream.hashCode();
    result = 31 * result + accountId.hashCode();
    return result;
  }
}
