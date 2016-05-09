package com.company.exercise.datastore.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FrequencyByEvent {
  private Map<String, Long> frequencyByEvent;

  public FrequencyByEvent() {
    this.frequencyByEvent = new ConcurrentHashMap<>();
  }

  /***
   * Increment the frequency for an event
   *
   * @param event Event name
   */
  public void increment(String event) {
    Long count = frequencyByEvent.get(event);
    if (count == null) {
      frequencyByEvent.put(event, 1L);
    } else {
      frequencyByEvent.put(event, count + 1);
    }
  }

  /***
   * @return The map that stores the event frequency state information
   */
  public Map<String, Long> getMap() {
    return frequencyByEvent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FrequencyByEvent that = (FrequencyByEvent) o;

    return frequencyByEvent.equals(that.frequencyByEvent);

  }

  @Override
  public int hashCode() {
    return frequencyByEvent.hashCode();
  }
}
