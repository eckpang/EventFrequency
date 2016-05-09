package com.company.exercise.datastore.data;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * Stores event frequency by day
 */
public class FrequencyByDay {
  private Map<Date, FrequencyByEvent> frequencyByDay;

  public FrequencyByDay() {
    this.frequencyByDay = new ConcurrentHashMap<>();
  }

  /***
   * Increment the frequency for an event
   *
   * @param date Date of the event
   * @param event Event name
   */
  public void increment(Date date,
                        String event) {
    FrequencyByEvent countByEvent =
        frequencyByDay.computeIfAbsent(date, key -> new FrequencyByEvent());

    countByEvent.increment(event);
  }

  /***
   * @return The map that stores the event frequency state information
   */
  public Map<Date, FrequencyByEvent> getMap() {
    return frequencyByDay;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FrequencyByDay that = (FrequencyByDay) o;

    return frequencyByDay.equals(that.frequencyByDay);

  }

  @Override
  public int hashCode() {
    return frequencyByDay.hashCode();
  }
}
