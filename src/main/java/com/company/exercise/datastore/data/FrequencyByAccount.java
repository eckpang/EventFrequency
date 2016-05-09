package com.company.exercise.datastore.data;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * Stores event frequency by account
 */
public class FrequencyByAccount {
  private Map<String, FrequencyByDay> frequencyByAccount;
  private String stream;

  public FrequencyByAccount(String stream) {
    this.stream = stream;
    this.frequencyByAccount = new ConcurrentHashMap<>();
  }

  /***
   * Increment the frequency for an event by account
   *
   * @param accountId Account ID of the event
   * @param date Date of the event
   * @param event Event name
   */
  public void increment(String accountId,
                        Date date,
                        String event) {
    FrequencyByDay countByDay =
        frequencyByAccount.computeIfAbsent(accountId, key -> new FrequencyByDay());

    countByDay.increment(date, event);
  }

  /***
   * @return The map that stores the event frequency state information
   */
  public Map<String, FrequencyByDay> getMap() {
    return frequencyByAccount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FrequencyByAccount that = (FrequencyByAccount) o;

    if (!frequencyByAccount.equals(that.frequencyByAccount)) return false;
    return stream.equals(that.stream);

  }

  @Override
  public int hashCode() {
    int result = frequencyByAccount.hashCode();
    result = 31 * result + stream.hashCode();
    return result;
  }

  /***
   * @return The stream name of the event
   */

  public String getStream() {
    return stream;
  }
}
