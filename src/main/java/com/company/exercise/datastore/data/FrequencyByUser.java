package com.company.exercise.datastore.data;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * Stores event frequency by user
 */
public class FrequencyByUser {
  private Map<String, FrequencyByDay> frequencyByUser;
  private String stream;

  public FrequencyByUser(String stream) {
    this.stream = stream;
    this.frequencyByUser = new ConcurrentHashMap<>();
  }


  /***
   * Increment the frequency for an event
   *
   * @param userId User ID
   * @param date Date
   * @param event Event name
   */
  public void increment(String userId,
                        Date date,
                        String event) {
    FrequencyByDay countByDay =
        frequencyByUser.computeIfAbsent(userId, key -> new FrequencyByDay());

    countByDay.increment(date, event);
  }

  /***
   * @return The map that stores the event frequency state information
   */
  public Map<String, FrequencyByDay> getMap() {
    return frequencyByUser;
  }

  /***
   * @return The stream name
   */
  public String getStream() {
    return stream;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FrequencyByUser that = (FrequencyByUser) o;

    if (!frequencyByUser.equals(that.frequencyByUser)) return false;
    return stream.equals(that.stream);

  }

  @Override
  public int hashCode() {
    int result = frequencyByUser.hashCode();
    result = 31 * result + stream.hashCode();
    return result;
  }
}
