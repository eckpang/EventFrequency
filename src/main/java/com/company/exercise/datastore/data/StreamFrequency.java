package com.company.exercise.datastore.data;

import java.util.Date;

/***
 * Stores event frequency for a stream
 */
public class StreamFrequency {
  private FrequencyByAccount frequencyByAccount;
  private String stream;
  private FrequencyByUser frequencyByUser;

  public StreamFrequency(String stream) {
    this.stream = stream;

    frequencyByAccount = new FrequencyByAccount(stream);
    frequencyByUser = new FrequencyByUser(stream);
  }

  /***
   * Increment the frequency for an event
   *
   * @param accountId Account ID
   * @param userId User ID
   * @param date Date
   * @param event Event name
   */
  public void increment(String accountId,
                        String userId,
                        Date date,
                        String event) {
    frequencyByAccount.increment(accountId, date, event);
    frequencyByUser.increment(userId, date, event);
  }

  public FrequencyByAccount getFrequencyByAccount() {
    return frequencyByAccount;
  }

  public String getStream() {
    return stream;
  }

  public FrequencyByUser getFrequencyByUser() {
    return frequencyByUser;
  }

  @Override
  public int hashCode() {
    int result = frequencyByAccount.hashCode();
    result = 31 * result + stream.hashCode();
    result = 31 * result + frequencyByUser.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    StreamFrequency that = (StreamFrequency) o;

    if (!frequencyByAccount.equals(that.frequencyByAccount)) return false;
    if (!stream.equals(that.stream)) return false;
    return frequencyByUser.equals(that.frequencyByUser);

  }
}
