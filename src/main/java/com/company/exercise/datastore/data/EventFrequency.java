package com.company.exercise.datastore.data;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * Stores event frequency by account and user
 */
public class EventFrequency {
  private Map<String, StreamFrequency> frequencyByStream;

  public EventFrequency() {
    frequencyByStream = new ConcurrentHashMap<>();
  }

  /***
   * Increment the frequency for an event
   *
   * @param stream Stream name
   * @param accountId Account ID of an event
   * @param userId User ID of an event
   * @param date Date of event
   * @param event The name of the event
   */
  public void increment(String stream,
                        String accountId,
                        String userId,
                        Date date,
                        String event) {
    StreamFrequency streamFrequency =
        frequencyByStream.computeIfAbsent(stream, key -> new StreamFrequency(key));

    streamFrequency.increment(accountId, userId, date, event);
  }

  /***
   * @return The map that stores the event frequency state information
   */
  public Map<String, StreamFrequency> getMap() {
    return frequencyByStream;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;

    EventFrequency that = (EventFrequency) o;

    return frequencyByStream.equals(that.frequencyByStream);

  }

  @Override
  public int hashCode() {
    return frequencyByStream.hashCode();
  }
}
